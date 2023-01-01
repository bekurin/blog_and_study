참고문서
- [kotlinx.coroutines 문서](https://kotlinlang.org/docs/coroutines-overview.html)

launch, withContext, async 등 코틀린에서 coroutine을 사용하기 위해서는 Thrad를 어떻게 사용할지에 대한 정책을 설정해줘야합니다.

kotlin에서는 이러한 정책을 Dispatcher라고 부르며 다음과 같은 코드로 정의했습니다.

```kotlin
public actual object Dispatchers {
	@JvmStatic  
	public actual val Default: CoroutineDispatcher = DefaultScheduler
	@JvmStatic  
	public actual val Main: MainCoroutineDispatcher get() = MainDispatcherLoader.dispatcher
	@JvmStatic  
	public actual val Unconfined: CoroutineDispatcher = kotlinx.coroutines.Unconfined
	@JvmStatic  
	public val IO: CoroutineDispatcher = DefaultIoScheduler
}
```

이 중 Main dispatcher는 싱글 Thread 환경에서 UI 객체 동작을 제한 할 때 사용합니다. 따라서 Main을 사용하기 위해서는 android, swing, javafx와 같은 UI 환경이 필요하기 때문에 Default, Unconfined, IO를 위주로 coroutine의 동작을 살펴보고 그 중 Default와 IO의 성능을 비교해보겠습니다.

### 공통 함수
```kotlin
private suspend fun busyWork() {  
    delay(1L)  
    List(7000000) { Random.nextLong() }.maxOrNull()  
}  
  
private suspend fun readUrl(url: URL, index: Int) {  
    val connection = url.openConnection()  
    delay(1)  
    BufferedReader(InputStreamReader(connection.getInputStream())).use { buffer ->  
        var line: String?  
        while (buffer.readLine().also { line = it } != null) {  
            if (line?.startsWith("<!DOCTYPE html>") == true) {  
                println("[dispatcherIO$$index] ${line?.substring(0 until 15)}")  
            }  
        }  
    }  
}
```
Dispatcher의 동작을 확인하기 위해 사용되는 함수들입니다.
- busyWork(): 1초의 딜레이를 가진 후에 랜덤 Long값으로 생성된 7000000개의 배열에서 최댓값을 찾는다.
- readUrl(url: URL, index: Int): url 경로로 접속하여 html 파일을 1줄씩 읽는다.

### Dispatcher Default: CPU 자원을 많이 사용하는 대규모 계산 리소스에 적합
코루틴을 생성할 때 아무런 설정을 하지 않으면 Default가 기본으로 설정됩니다. 그만큼 Default는 일반적인 연산에서 좋은 성능을 보여줍니다.

Default를 사용할 경우 최소 2개에서 최대 컴퓨터 core 개수만큼까지 Thread를 사용합니다. 

다음은 Default의 성능을 확인하기 위한 코드입니다.
```kotlin
suspend fun dispatcherDefault(count: Int) {  
    return coroutineScope {  
        repeat(count) { index ->  
            launch(Dispatchers.Default) {  
                println("[${Thread.currentThread().name}] Before doBusyWork on dispatcherDefault$$index")  
                busyWork()  
                println("[${Thread.currentThread().name}] After doBusyWork on dispatcherDefault$$index")  
            }  
        }    }}
```

함수의 동작은 다음과 같습니다.
- Count만큼 반복하면 코루틴을 실행시킨다.
- 현재 Thread와 작업 전을 출력한다.
- busyWork()를 실행한다.
- 현재 Thread와 작업 후를 출력한다.

### Dispatcher IO: 파일 I/O 또는 Blocking Network I/O 와 같은 I/O 작업에 적합
I/O는 이름에서 짐작할 수 있듯이 I/O 집약적인 작업에서 Blocking을 제거하기 위해 설계된 on-demand 공유 스레드 풀을 사용합니다.

다은은 IO의 성능을 확인하기 위한 코드입니다.
```kotlin
suspend fun dispatcherIO(count: Int) {  
    return coroutineScope {  
        repeat(count) { index ->  
            launch(Dispatchers.IO) {  
                println("[${Thread.currentThread().name}] Before readUrl on dispatcherIO$$index")  
                readUrl(URL("https://www.facebook.com/"), index)
                println("[${Thread.currentThread().name}] After readUrl on dispatcherIO$${index}")  
            }  
        }    }}
```
함수의 동작은 다음과 같습니다.
- Count만큼 반복하며 코루틴을 실행한다.
- 현재 Thread와 작업 전을 출력한다.
- readUrl()을 실행한다.
- 현재 Thread와 작업 후를 출력한다.

### Dispatcher Unconfined: 일반적으로 적합하지 않음
unconfined의 경우 처음 작업부터 suspend 전까지는 실행한 Thread 작업을 하고 이후 작업은 기본으로 설정된 Thread에서 작업을 실행합니다.

다음은 unconfined 동작 확인을 위한 코드입니다.
```kotlin
suspend fun dispatcherUnconfined(count: Int) {  
    return coroutineScope {  
        repeat(count) { index ->  
            launch(Dispatchers.Unconfined) {  
                println("[${Thread.currentThread().name}] Before delay on dispatcherUnconfined$$index")  
                delay(Random.nextLong(10))  
                println("[${Thread.currentThread().name}] First delay on dispatcherUnconfined$$index")  
                delay(Random.nextLong(100))  
                println("[${Thread.currentThread().name}] Second delay on dispatcherUnconfined$$index")  
            }  
        }    }}
```
함수의 동작은 다음과 같습니다.
- count만큼 반복하며 코루틴을 실행한다.
- 첫 번째 delay() 실행 전 Thread를 출력한다.
- 첫 번째 delay() 실행 후 Thread를 출력한다.
- 두  번째 delay() 실행 후 Thread를 출력한다.

다음은 dispatcherUnconfined(3)를 실행하였을 경우 출력문입니다.
```kotlin
[main] Before delay on dispatcherUnconfined$0
[main] Before delay on dispatcherUnconfined$1
[main] Before delay on dispatcherUnconfined$2
[kotlinx.coroutines.DefaultExecutor] First delay on dispatcherUnconfined$1
[kotlinx.coroutines.DefaultExecutor] First delay on dispatcherUnconfined$2
[kotlinx.coroutines.DefaultExecutor] First delay on dispatcherUnconfined$0
[kotlinx.coroutines.DefaultExecutor] Second delay on dispatcherUnconfined$0
[kotlinx.coroutines.DefaultExecutor] Second delay on dispatcherUnconfined$2
[kotlinx.coroutines.DefaultExecutor] Second delay on dispatcherUnconfined$1
```
Before delay...의 출력문은 모두 main Thread에서 실행된 것에 비해 나머지 First delay..., Second delay... 는 DefaultExecutor에서 실행된 것을 볼 수 있습니다.

이러한 특성 미뤄보았을 때 만약 모든 코루틴 작업을 unconfined로 정의하면 코루틴이 가지는 동시성 효과를 누릴 수 없습니다.

또한 [When should I use `Dispatchers.Unconfined` vs `EmptyCoroutineContext`?](https://stackoverflow.com/questions/55169711/when-should-i-use-dispatchers-unconfined-vs-emptycoroutinecontext) 에서는 GlobalScope를 차단하는 경우 non-suspending API를 사용하는 경우에 유용하다고 합니다.

하지만 GlobalScope로 만들어진 코루틴의 경우 취소 시켜주지 않으면 어플리케이션 전반에 걸쳐 실행이 되므로 사용을 권장하지 않고, Non-suspeding API의 경우 추후 suspend 로직이 추가되게 되면 Default 또는 IO로 변경해주어야하고, suspend 추가가 없다고 하더라도 Default와 IO를 적절하게 사용하는 것이 더 좋은 성능을 낼 수 있다고 생각합니다.

위와 같은 이유로 unconfined를 "일반적으로 적합하지 않음" 이라고 판단했습니다.

### 성능 비교 (작업 컴퓨터: 맥북 M1 Pro 16형)
Dispatcher에 따른 성능 차이를 비교합니다. 비교 방식은 dispatcherDefault()와 dispatcherIO() 함수에서 Dispatcher의 타입만 변경하여 전체 작업 실행 시간의 평균을 비교하는 방식으로 진행하였습니다.

#### IO에 유리한 작업 비교 (실행 함수, Dispatcher 타입, 평균 작업 시간)
평균 작업 시간의 경우 count의 값을 1000으로 설정하여 비교하였습니다.
- dispatcherIO() / IO / 14ms
- disptacherIO() / Default / 39ms
- disptacherIO() / Unconfined / 369ms

#### Default에 유리한 작업 비교 (실행 함수, Dispatcher 타입, 평균 작업 시간)
평균 작업 시간의 경우 count의 값을 100으로 설정하여 비교하였습니다.
- dispatcherDefault() / IO / 77ms  
- dispatcherDefault() / Default / 51ms
- dispatcherDefault() / Unconfined / 70ms

### 결론
3종류의 kotlin의 코루틴 Dispatcher 개념과 성능을 알아보았습니다.

그 중에서도 html 응답을 읽는 작업에서는 IO가 가장 좋은 성능을 내고, CPU 연산이 많은 작업에서는 Default가 가장 좋은 성능을 내주었습니다.

성능 비교를 분석하면 다음과 같습니다.
- IO 작업은 IO를 사용하는 것이 좋은 성능을 낼 수 있다.
- CPU 연산 작업은 Default가 좋은 성능을 낼 수 있다.
- Unconfined는 IO 작업에 적합하지 않다.
- CPU 연산 작업의 경우 IO를 사용하면 OutOfMemoryError가 발생할 수 있다. (Defalut에 유리한 작업에서 count를 1000으로 설정한 경우 Error가 발생함)
- CPU 연산 작업에서 Unconfined가 IO 보다 좋은 성능을 낸다.

IO 작업이 확실한 경우에만 IO를 사용하고, IO 작업이 아닌 경우 Default를 사용하는 것이 좋다고 생각합니다.