![[Pasted image 20230122141313.png]]
(테스트를 작성해야하는 이유를 4문장으로 가장 잘 설명한 그림이 아닐까?)

어떤 서비스를 개발해도 시간이 지남에 따라 서비스는 복잡해지고 규모는 커지게 됩니다.
그렇게 점점 복잡한 기능을 가지면서 성장하다보면 어느 순간 개발자 1명이 모든 기능을 이해하기 어려울 정도가 되고, 개발자가 기존의 기능을 수정하거나 새로운 기능을 추가하는 경우 코드가 서비스에 어떤 영향을 끼치는지 파악하기 힘들 정도가 됩니다.

이게 바로 테스트 코드를 작성하는 많은 이유 중에 하나 일 것 같은데요. 
개발자가 추가적으로 작성한 또는 리팩토링을 통해 개선한 코드가 다른 코드에 영향을 주지 않는다는 보증의 의미로 "절대 뚫리지 않는 창" 처럼 생각됩니다.

그렇기에 개발자들이 그토록 서비스를 만드는 동안에 구현한 기능이 정상적으로 작동하는지  검증하는 테스트를 작성하게 되는 것 같습니다.

하지만 이러한 테스트는 아무런 제약 없이 작성하게 되면 코드를 작성한 개발자를 제외하고, 다른 사람들은 코드를 이해하는데 어려움이 있게 되는 상황이 발생할 수 있습니다.

이러한 제약에는 AAA, GWT, DCI 등 여러 종류가 있지만 본문에서는 같은 기능에 대해 GWT와 DCI로 나눠 코드가 어떻게 달라지는 지 비교하여 상황에 따라 어떤 테스트 제약을 사용해야 가독성이 좋아질 수 있는지 알아보겠습니다.

GWT(given-when-then) 구조의 테스트 코드를 짜다보면 기능 검증을 위해 데이터를 준비하는 코드(given)가 실행(when)과 검증(then)을 하는 코드보다 길어질 때가 있습니다. 이런 경우 테스트 코드를 짜면서도 "배보다 배꼽이 더 큰게 아닌가" 라는 생각이 들게 됩니다.

이런 경우에 DCI(Describe Context It)구조를 적용해볼 수 있습니다.
DCI는 검증하고자 하는 함수, 테스트하고자 하는 상황, 검증으로 구조화하여 테스트를 진행하는 것을 말합니다.
예시에서도 확인 할 수 있지만 GWT 구조에서 고민하던 given 절의 코드가 길어지는 것을 방지하고, when과 then의 코드 검증에 집중될 수 있는 것을 확인할 수 있습니다.

### 랭킹보드 구현
랭킹 보드는 Redis를 사용하여 구현하였습니다. [간단 구현](https://github.com/bekurin/blog_and_study/blob/main/Kotiln/kotlin-redis/src/main/kotlin/core/kotlinredis/repository/RedisRanking.kt)

## 테스트 코드 작성
- 테스트의 대상이 되는 클래스의 이름을 sut(System Unter Test)로 설정하였습니다. 
- 테스트 코드는 라인 수 관계로 랭킹보드 생성에 대한 성공, 실패 케이스만 다룹니다.

### GWT 패턴을 사용하여 테스트 구현
```kotlin
@SpringBootTest     
internal class RankingServiceTest constructor(  
    @Autowired  
    val sut: RankingService,  
) {  
    @Test  
    fun `save()는 성공적으로 저장하면 저장된 회원을 반환한다`() {  
        //given  
        val entity = Member("3", "Bob", Point(123.0))  
  
        //when  
        val save = sut.save(entity)  
  
        //then  
        assertEquals(entity, save)  
        sut.delete(entity)
    }  
  
    @Test  
    fun `save()는 저장에 실패하면 예외를 반환한다`() {  
        //given  
        val entity = Member("4", "Bob", Point(123.0))  
        sut.save(entity)  
  
        //when  
        //then        
        assertThrows(RuntimeException::class.java) { sut.save(entity) }  
        sut.delete(entity)
    }
	...
}
```

위의 테스트를 실행하면 다음과 같이 테스트가 진행되게 됩니다.

![[Screen Shot 2023-01-15 at 9.37.49 PM.png]]
코드에서 보나 출력문에서 보나 둘 다 직관적으로 이해가 되는 것을 알 수 있습니다.

하지만 GWT 방식으로 테스트를 진행하게 되면 몇가지 문제가 있습니다.
- GWT에서는 모든 테스트 케이스에 맞는 객체를 만들 수 없기 때문에 공유 객체는 사용할 수 없다.
- 테스트의 실행 순서가 보장되지 않기 때문에 몇 백가지의 테스트가 있다고 할 때 위의 결과처럼 save() 함수에 대한 결과만 찾기가 쉽지 않다.

이러한 문제점 보다 더 큰 장점이 있습니다.
- 테스트 케이스마다 테스트에 적합한 객체가 주어지고, 생성한 객체의 수명이 해당 테스트 케이스에서 종료된다.
- 테스트가 직관적이다.

테스트를 위한 객체가 테스트 안에서 수명이 다한다는 것은 객체의 생명주기 관리가 쉬워진다는 것을 의미합니다.

통합 테스트의 경우 실제 Redis, RDS 등에 데이터가 적재됩니다. 
만약 이러한 데이터들을 생성만 하고 관리를 해주지 않는다면 크게 2가지 문제가 발생하게 됩니다.
하나는 다른 테스트 코드에 영향을 줄 수 있다. 다른 하나는 힙에 계속 정보가 쌓여 테스트 속도에 문제가 발생하고, 심한 경우 Heap overflow가 발생하여 테스트가 종료됩니다.

### DCI 패턴을 사용하여 테스트 구현
```kotlin
@Nested  
@DisplayName("save()는 ")  
inner class DescribeSave {  
    private val entity = subject()  
  
    @AfterEach  
    fun cleanUp() {  
        sut.delete(entity)  
    }  
  
    @Nested
    @DisplayName("성공적으로 저장하면 ")
    inner class ContextSuccess {  
        @Test  
        fun `저장된 회원을 반환한다`() {  
            val save = sut.save(entity)  
            assertEquals(entity, save)  
        }  
    }  
  
    @Nested  
    @DisplayName("저장에 실패하면 ")  
    inner class ContextFail {  
        @Test  
        fun `예외를 반환한다`() {  
            sut.save(entity)  
            assertThrows(RuntimeException::class.java) { sut.save(entity) }  
        }  
    }  
}
```
![[Screen Shot 2023-01-15 at 11.47.04 AM.png]
![[Screen Shot 2023-01-31 at 7.54.46 AM.png]]

같은 맥락을 가진 테스트를 묶어 객체를 생성하여 공유하기 때문에 일반적인 GWT 구조와는 다르게 given 절의 코드가 없는 것을 확인할 수 있습니다. 

테스트 케이스를 나누는 기준을 생각해보면 실행(when)에 대한 검증(then)를 검증하는 과정이라고 생각합니다. 그렇다고 한다면 given 절에 생성되는 객체를 context 마다 공유하여 제거한다면 실행과 검증에 더 초점을 맞출 수 있습니다.

이러한 DCI에서도 몇가지 문제가 있습니다.
- 공유 객체로 테스트 케이스를 검증하기 때문에 GWT보다 객체 관리가 어렵다.
- DCI는 런닝 커브가 있다.

장점 또한 존재합니다.
- 실행과 검증에 초점을 맞춘 테스트를 작성할 수 있다.
- Nested로 작성되기 때문에 원하는 테스트 케이스를 찾기 쉽다.

### 마무리
처음부터 DCI 구조로 코드를 작성하는 것은 과최적화라는 생각이 듭니다. 그렇다는 말은 구현에 시간이 꽤 걸린다는 것입니다. 물론 시간만 충분하다면 요구사항 구현, 테스트 코드 작성에 많은 시간을 투자하여 개발하겠지만 현실 세계는 그렇지 않습니다.

그에 반해 GWT는 직관적이고, 이해하기 쉽다.
DCI를 사용하면 분리된 context들에 대해 given 절을 통합하고, 실행과 검증에 몰입할 수 있다.
GWT와 DCI는 자세하게 보면 사실 구조는 비슷하게 되어 있다는 것을 눈치챌 수 있을 것이다.

따라서 앞으로 테스트 코드를 작성하게 된다면 DCI를 먼저 생각하는 것이 아니라 GWT로 테스트 코드를 작성하면서 given 절의 준비 코드가 실행과 검증보다 길어지고, 성공과 실패와 같이 나눠진 context에서 given절의 자원을 공유해도 문제가 될게 없다는 판단이 확고해지면 DCI로의 변환을 고려해볼 것 같다.
[]()
DCI의 장단점
장점
- 상황에 대한 테스트를 공유 자원을 통해 진행하므로 실행과 검증에 집중할 수 있다.
단점

GWT의 장단점
장점
- 테스트를 위해 설정하는 리소스의 생명 주기가 해당 테스트에서 끝나기 때문에 BeforeEach, AfterEach와 같은 방식으로 제거 또는 설정을 하지 않아도 된다.
- 직관적이게 이해할 수 있다. 필요한 모든 리소스를 해당 함수에서 찾을 수 있다.
단점
- 통합 테스트의 경우 데이터를 삭제하는 로직을 함수의 끝에 넣어주어여한다.
- 
