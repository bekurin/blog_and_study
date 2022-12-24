함수형 프로그래밍에 대해 공부하면 map, filter, reduce, fold 등과 같은 함수들에 대해서 들어볼 수 있습니다. 

그 중에서도 map과 filter는 좋은 예제들과 직관적인 사용법 덕분에 학습에 있어 큰 어려움이 없습니다. 
하지만 reduce와 fold와 같은 함수들은 html 문자열 만들기 리스트의 합, 평균 구하기 등의 기초적인 예제로 기능을 설명하여 map과 filter에 비해 학습이 더 어렵습니다.

<iframe src="https://giphy.com/embed/3o7btPCcdNniyf0ArS" width="480" height="268" frameBorder="0" class="giphy-embed" allowFullScreen></iframe><p><a href="https://giphy.com/gifs/cbc-comedy-what-3o7btPCcdNniyf0ArS"></a></p>
앞서 설명한 기초적인 예제에 더해 조금은 더 실무적인 관점의 예제를 사용하여 fold와 map을 사용하여 구현하고, 코드의 차이를 알아보겠습니다.

> Q1. 프로그래밍 언어, 선호도 ex) java,good 가 저장된 배열이 있을 때 각 언어의 선호도를 good: +1, bad: -1로 계산하여 hashMap으로 반환하는 기능을 개발하라

### 공통 함수

먼저 map과 fold에서 사용될 공통 함수 removeOne, addOne, changeMapByPrefer를 알아보겠습니다.

```kotlin
private fun removeOne(
	result: MutableMap<String, Int>, 
	key: String
): MutableMap<String, Int> {  
    result[key] = result[key]?.minus(1) ?: -1
    return result  
}
```

hashMap에 key 값이 있다면 1 감소시켜주고, 처음 저장되는 값이면 -1로 값을 초기화해준다. 

```kotlin
private fun addOne(
	result: MutableMap<String, Int>, 
	key: String
): MutableMap<String, Int> {  
    result[key] = result[key]?.plus(1) ?: 1
    return result  
}
```

hashMap에 key 값이 있다면 1 증가시켜주고, 처음 저장되는 값이면 1로 값을 초기화한다.

```kotlin
private fun changeMapByPrefer(  
    map: MutableMap<String, Int>,  
    key: String,  
    prefer: String,  
): MutableMap<String, Int> {  
    return when (prefer) {  
        "good" -> addOne(map, key)  
        "bad" -> removeOne(map, key)  
        else -> throw Exception("Unknown Prefer Type")  
    }  
}
```

prefer의 값에 따라 addOne, removeOne을 호출하고, 유효하지 않은 값이 들어오면 Exception을 던져준다.

### map을 사용하여 요구사항 구현

```kotlin
fun getCountHashMapUsingMap(preferList: List<String>): MutableMap<String, Int> {  
    var result = mutableMapOf<String, Int>()  
    preferList.map { item ->  
        var split = item.split(",")  
        changeMapByPrefer(result, split[0], split[1])  
    }  
    return result  
}
```

배열을 순환하며 해쉬맵으로 결과를 만들어야하기 때문에 지역 변수 result를 만들어 map을 진행하는 과정에서 result를 수정해주는 것으로 요구사항을 구현하였습니다.

### fold를 사용하여 요구사항 구현

```kotlin
fun getCountHashMapUsingFold(preferList: List<String>): MutableMap<String, Int> {  
    return preferList.fold(mutableMapOf()) { result, item ->  
        val split = item.split(",")  
        changeMapByPrefer(result, split[0], split[1])  
    }  
}
```

reduce는 순환하는 값의 첫 번째 원소의 타입과  반환 타입이 같아야 하지만 fold의 경우 첫 번째 값을 원하는대로 설정할 수 있습니다. 

따라서 아무런 값이 들어있지 않은 MutableMap 초기값으로 설정하여 지역 변수를 설정하지 않고, fold의 각 단계의 반환 값(result)를 사용하여 요구사항을 구현하였습니다.

### 결론
map과 fold를 사용하여 같은 기능을 구현해보았습니다. 코드가 간결하게 읽히는 것은 물론이고, 코드 줄 수를 보더라도 map은 5줄, fold는 3줄로 40%나 fold가 더 작습니다.

동작 확인을 위해 만들어진 코드를 실행하여 결과를 확인해보겠습니다.
```kotlin
fun main() {  
    val preferList = listOf("java,good", "kotlin,good", "c,bad", "c#,good", "c,bad", "c#,good", "kotlin,good", "java,bad", "python,good", "python,bad")  
  
    val mapResult = getCountHashMapUsingMap(preferList)  
    println("mapResult = $mapResult")  
  
    val foldResult = getCountHashMapUsingFold(preferList)  
    println("foldResult = $foldResult")  
}
>> mapResult = {java=0, kotlin=2, c=-2, c#=2, python=0}
>> foldResult = {java=0, kotlin=2, c=-2, c#=2, python=0}
```

출력문에서도 볼 수 있듯이 map으로 구현한 것과 fold로 구현한 것은 기능적으로는 같이 동작합니다. 하지만 map의 경우 결과 hashMap을 만들기 위해서 map이 동작되는 코드 밖에 있는 지역 변수(result)를 사용합니다.

이처럼 지역 변수를 사용해야한다면 함수형 프로그래밍에서 사용하는 method chaining 과정에서 함수를 한번 return 했다가 연산을 이어가야할 수도 있습니다.

결과적으로 입력과 출력의 타입이 다를 때 fold를 고려해봐야할 이유를 3가지로 정리하면 다음과 같습니다.
- map의 경우 일괄적인 데이터 처리에 유용하게 사용될 수 있지만 자료형을 변경하는 것에는 어려움이 있다.
- 비슷한 함수로 reduce도 있지만 reduce의 경우 sequence, collection의 첫 번째 원소의 타입과 반환 타입이 같아야한다는 조건이 있다.
- fold의 경우 초기값을 마음대로 설정할 수 있다. 초기값이 곧 결과 반환 타입이 된다.