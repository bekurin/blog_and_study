
https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#flatMap-java.util.function.Function-

![[Screen Shot 2023-01-13 at 5.54.08 PM.png]]

FlatMap의 동작을 살펴본다.
순서,
1. flatMap 예제 실행
2. 올바르지 않은 순서 마치 collection과 같이 동작한다.
3. 그림을 살펴보면 들어오는대로 이벤트를 처리하는 것을 알 수 있다.
4. delayElements를 사용하면 sequence처럼 동작한다.
5. 이렇게 동작하는 이유
이벤트를 발행하는 속도가 너무 빨라서 flatMap을 동작하기 전에는 동기적으로 결과 collection을 모으는 것 처럼 보인다.

이벤트 발행 속도가 예제처럼 빠를 일은 없으니 실제 예제 처럼 delay를 걸어주면 그림과 같이 원소마다 순서대로 모든 처리를 진행하는 것을 알 수 있다. 이때, Thread가 어떻게 변하는지 살펴보는 것도 좋을 것 같다.