테스트를 작성하는 이유

![[Pasted image 20230122141313.png]]

어떠한 서비스를 개발하든 간에 시간에 지남에 따라 서비스는 복잡해지고 규모는 커지게 된다.
그렇게 성장하다보면 어느 순간 개발자 1명이 모든 기능을 이해하기 어려울 정도가 되게 되고,
개발자가 기존의 기능을 수정하거나 새로운 기능을 추가했을 경우 코드가 서비스에 어떤 영향을 끼치는지 파악 조차 할 수 없게 된다.

그렇기에 개발자들이 그토록 서비스를 만드는 동안에 구현한 기능이 정상적으로 작동하는지  검증하는 테스트를 작성하게 된다.

그러한 테스트를 다른 개발자들이 이해할 수 없도록 작성하는 것은 코드를 이해하는 시간만 늘릴 뿐이니
가독성을 높이기 유효한 테스트를 작성하는 것이 중요하다.

유효한 테스트는 추후에 다뤄보기로 하고 가독성을 높일 수 있는 방법에 대해 공유하고자 한다.

GWT(given-when-then) 구조의 테스트 코드를 짜다보면 기능 검증을 위해 데이터를 준비하는 코드(given)가 실행(when)과 검증(then)을 하는 코드보다 길어질 때가 있습니다. 이런 경우 테스트 코드를 짜면서도 "배보다 배꼽이 더 큰게 아닌가" 라는 생각이 들게 됩니다.

이런 경우에 DCI(Describe Context It)구조를 적용해볼 수 있습니다.
DCI는 검증하고자 하는 함수, 테스트하고자 하는 상황, 검증으로 구조화하여 테스트를 진행하는 것을 말합니다.

예시에서도 확인 할 수 있지만 GWT 구조에서 고민하던 given 절의 코드가 길어지는 것을 방지하고, when과 then의 코드 검증에 집중될 수 있는 것을 확인할 수 있습니다.

### 랭킹보드 구현
랭킹 보드는 Redis를 사용하여 구현하였습니다. (랭킹보드 구현에 대해서는 설명하지 않습니다) [구현 바로가기](https://github.com/bekurin/blog_and_study/blob/main/Kotiln/kotlin-redis/src/main/kotlin/core/kotlinredis/repository/RedisRanking.kt)

## 테스트 코드 작성
- 테스트의 대상이 되는 클래스의 이름을 sut(System Unter Test)로 설정하였습니다. 
- 테스트 코드는 라인 수 관계로 랭키보드 생성에 대한 성공, 실패 케이스를 다룹니다.

### 하나의 클래스에 모든 케이스 구현
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

(단점) 통합 테스트는 외부 의존성에 대해서도 테스트를 진행하기 때문에 Redis, RDS와 같은 환경에 실제로 데이터가 변경되게 됩니다. 따라서 각 테스트는 사용이 끝난 리소스를 제거해줘야 하며 제거해주지 않으면 테스트 성능에 문제가 생길 수 있습니다.

(단점) GWT에서는 공유 변수 방식을 선택할 수 없다. 어떤 부분에서는 id가 3인 entity를 사용하고, 어떤 부분은 id가 4인 entity를 사용한다고 하였을 경우 AfterEach와 같은 함수에서 몇번 삭제해주기 어렵다.

(이점) GWT의 좋은 점은 given 절에서 생성한 객체의 수명이 해당 함수에서 종료되니 pk가 아닌 필드의 값을 자유롭게 변경하며 테스트를 할 수 있다.

(단점) 또한 테스트의 실행 순서가 보장되지 않기 때문에 몇 백가지의 테스트가 있다고 할 때 위의 결과처럼 save() 함수에 대한 결과만 찾기가 쉽지 않습니다.

#### @Nested 사용하여 상황 분리
```kotlin
@Nested  
@DisplayName("save()는 ")  
inner class Save() {  
    private val entity = subject()  
  
    @AfterEach  
    fun cleanUp() {  
        sut.delete(entity)  
    }  
  
    @Test  
    fun `성공적으로 저장하면 저장된 회원을 반환한다`() {  
	    //given
	    //when
        val save = sut.save(entity)  
        //then
        assertEquals(entity, save)  
    }  
  
    @Test  
    fun `저장에 실패하면 예외를 반환한다`() {  
	    //given
	    //when
        sut.save(entity)  
        //thenb
        assertThrows(RuntimeException::class.java) { sut.save(entity) }  
    }  
}
```

![[Screen Shot 2023-01-15 at 9.36.56 PM.png]]

일반적인 GWT 구조와 다른 점을 비교해보면 given 절의 코드가 없는 것을 확인할 수 있다. save 함수를 검증하기 위한 entity를 공유하기 때문이다.

여기서 테스트 코드를 작성하는 목적에 대한 생각을 이야기 해보자면 테스트 코드는 리팩토링, 요구사항 구현 등으로 발생한 코드가 올바르게 동작하고 있다는 것을 보증해주는 하나의 시스템이라고 생각한다. 그렇게 보았을 때 테스트에서 집중해야할 부분은 WT 즉, 실행과 실행을 결과로 발생한 상태를 검증하는 것이라고 생각한다.

이러한 관점으로 보면 위의 절충 DCI는 given 절을 공유할 수 있다는 것에서 이점이 있다.
-> 공유하기 때문에 GWT와는 다르게 다른 속성 값을 사용할 수는 없다.


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
![[Screen Shot 2023-01-15 at 11.47.04 AM.png]]

save()는 어떤 함수를 실행 할지에 대한 설명(Describe), 성공 또는 실패에 대한 상황(Context), 각 상황에서 반환되는 (결과, 상태) (It)으로 이뤄져있다.
위의 경우는 완전한 DCI의 구조로 작성된 테스트로 모든 단어를 이으면 말이 된다.


### 마무리
처음부터 DCI 구조로 코드를 작성하는 것은 과최적화라는 생각이 든다. 그렇다는 말은 구현에 시간이 꽤 걸린다는 것이다. 시간만 충분하다면 요구사항 구현, 테스트 코드 작성에 많은 시간을 투자하겠지만 현실의 세계는 그렇지 않다.

그에 반해 GWT는 직관적이고, 이해하기 쉽다.

DCI를 사용하면 분리된 context들에 대해 given 절을 통합하고, 실행과 검증에 몰입할 수 있다.

GWT와 DCI는 자세하게 보면 사실 구조는 비슷하게 되어 있다는 것을 눈치챌 수 있을 것이다.

따라서 앞으로 테스트 코드를 작성하게 된다면 DCI를 먼저 생각하는 것이 아니라 GWT로 테스트 코드를 작성하면서 given 절의 준비 코드가 실행과 검증보다 길어지고, 성공과 실패와 같이 나눠진 context에서 given절의 자원을 공유해도 문제가 될게 없다는 판단이 확고해지면 DCI로의 변환을 고려해볼 것 같다.

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





