![[Pasted image 20230122141313.png]]
(테스트를 작성해야하는 이유를 4문장으로 가장 잘 설명한 그림이 아닐까?)

어떤 서비스를 개발해도 시간이 지남에 따라 서비스는 복잡해지고 규모는 커지게 됩니다.
점점 복잡한 기능을 가지면서 성장하다보면 어느 순간 개발자 1명이 모든 기능을 이해하기 어려울 정도가 되고, 개발자가 기존의 기능을 수정하거나 새로운 기능을 추가하는 경우 코드가 서비스에 어떤 영향을 끼치는지 파악하기 힘들 정도가 됩니다.

여기까지 테스트 코드가 하나도 없다고 한다면 "진정한 기도 메타가 시작되지 않을까?"라는 생각이 듭니다.

이런 의미에서 테스트 코드는 개발자가 수정, 추가한 코드가 다른 코드에 영향을 주지 않는다는 보증의 의미로 "절대 뚫리지 않는 창" 처럼 생각되기도 합니다.

하지만 이러한 테스트는 아무런 제약 없이 작성하면 코드를 작성한 개발자를 제외하고, 다른 사람들은 코드를 이해하는데 어려움이 있게 되는 상황이 발생할 수 있습니다.

여러 제약이 있지만 그 중에서도 GWT, DCI를 기준으로 살펴보려고 합니다.

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
GWT와 DCI는 모두 나름의 장점을 가지고 있습니다. 
나름의 생각을 정리해보면 DCI의 경우 하나의 경우에 대해 여러 상태(성공, 실패, 보류 등)가 존재하는 경우 유용하게 사용할 수 있을 것 같고, GWT의 경우 하나의 경우에 대해 여러 상태(시작, 작업 중, 작업 끝)가 존재하는 경우 유용하게 사용할 수 있을 것 같습니다.

![[Untitled_Artwork.jpg]]

물론 생각이기 때문에 틀린 경우가 분명히 존재할 것이라고 생각합니다. 하지만 앞으로 테스트를 작성할 때에 위와 같은 기준을 가지면서 DCI와 GWT 테스트 코드를 작성해 보고 그에 따른 효과를 살펴보면서 개선 사항이 생긴다면 정리해서 공유하도록 하겠습니다.