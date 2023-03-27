
보통 spring-jpa의 트랜잭션 어노테이션을 사용할 때 "runtimeException의 경우 롤백을 하고, checkedException의 경우 롤백을 하지 않는다"라고 알고 있을 것입니다.

그렇다면 만약 runtimeException이 발생해도 롤백을 하지 말아야하는 경우가 있다면 어떻게 해야할까요?

간단한 예시로 데이터 적재가 성공할 때 알림을 받고 싶은 경우를 들 수 있습니다. 
알림 전송이 실패한 경우 데이터 롤백을 해야할까요?

위 질문에 "아니오"라고 결정이 된다면 RuntimeException이 발생해도 롤백이 되지 않도록 구현해야합니다. 어떻게 할 수 있을까요?

바로 Transactional 어노테이션의 noRollbackFor 옵션을 사용하면 됩니다. 비슷한 옵션으로 RollbackFor도 존재하는데요. 2가지가 어떤 동작을 하는지 간략하게 알아보겠습니다.
- noRollbackFor: 배열로 넘겨진 throwable에 대해 예외가 발생해도 롤백하지 않는다.
- rollbackFor: 배열로 넘겨진 throwable에 대해 예외가 발생하면 롤백한다.

어떠한 예외도 롤백을 할 수 있고, 안 할 수 있게 되기 때문에 기존의 "runtimeException의 경우 롤백을 하고, checkedException의 경우 롤백을 하지 않는다"은 잘못된 사실이라는 것을 알 수 있습니다. 

결국 롤백에 대한 결정은 이해관계자들의 합의에 따라 변경될 수 있다는 것을 알 수 있습니다.

간단한 예시로 활용법에 대해 알아보겠습니다.
![](https://blog.kakaocdn.net/dn/cbPw6d/btr4Ds5K10r/DLKkk1k074MTsFVAI04rk1/img.png)

예시로 만든 SlackSendFailException 클래스를 noRollbackFor에 넘겨주게 되면
데이터가 롤백되지 않은 것을 알 수 있습니다.

하지만 runtimeException이 발생하면 데이터가 적재되지 않는 것을 알 수 있습니다.

![](https://blog.kakaocdn.net/dn/LgEUZ/btr4v9l1SYt/6e5VkX4j7rVd6zWKJfjqDk/img.png)