WebClient는 HTTP 요청을 non-blocking, reactive로 수행하는 클라이언트입니다. 기존의 MVC로 구성된 프로젝트의 경우 RestTemplate을 사용하는 것도 고려할 수 있지만 [RestTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) 의 문서를 살펴보면 아래의 NOTE를 확인할 수 있습니다.

>**NOTE:** As of 5.0 this class is in maintenance mode, with only minor requests for changes and bugs to be accepted going forward. Please, consider using the `org.springframework.web.reactive.client.WebClient` which has a more modern API and supports sync, async, and streaming scenarios.

따라서, 이번 WebClient와 kotlin 확장 함수에서는 RestTemplate의 설명은 미뤄두고, WebClient를 중심으로 설명하겠습니다.

개념을 이해하기 위해서는 개선 전과 후를 비교하는 것이 좋은 방법 중 하나라고 생각합니다. 
이번 절에서는 다음의 요구사항을 기준으로 확장 함수를 사용하기 전, 후를 비교해보겠습니다.
 - Npc 단일 조회 -> Mono
 - Npc 배열 조회 -> Flux

### 확장 함수 적용 전 Controller
%%webClient를 사용하여 중복이 많게 정의된 코드%%

### WebClient와 확장 함수

```kotlin
inline fun <reified T> WebClient.doPostAsMono(body: Any, uri: String): Mono<T> {
    return this.post()
        .uri(uri)
        .bodyValue(body)
        .retrieve()
        .bodyToMono(T::class.java)
}

inline fun <reified T> WebClient.doPostAsFlux(body: Any, uri: String): Flux<T> {
    return this.post()
        .uri(uri)
        .bodyValue(body)
        .retrieve()
        .bodyToFlux(T::class.java)
}
```


### 확장 함수를 적용 후 Controller
%%확장 함수가 적용되어 간단하게 변경된 controller 코드%%


### 마무리
코틀린의 확장 함수는 변경하기 힘든 외부 의존성(CrudRepository, WebClient 등) 코드를 쉽게 확장 및 변경할 수 있습니다. 그만큼 확장함수를 잘 활용한다면 코드의 중복을 쉽게 해결하고, 요구사항을 쉽게 구현할 수 있습니다.

하지만, 때로는 지속 가능한 코드를 만들기 위해 확장 함수가 아닌 확장과 상속을 활용해야합니다.

따라서, WebClient, CrudRepository 등 외부 의존성의 코드를 변경해야할 경우라면 확장 함수를 우선적으로 고려하겠지만 프로젝트 내부 코드 또는 수정 가능한 코드라면 우선적으로 확장과 상속을 먼저 고려해볼 것 같습니다.
