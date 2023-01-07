Spring Security In Action을 공부하려고 하는데 깃헙 프로젝트가 70개 정도된다. 70개의 프로젝트르 하나하나 만들어서 관리하는 것보다 하나의 프로젝트 core가 공통 dependency를 관리하는 구조가 되면 좋을 것 같다.

ex)
core (Securty + Web)
chapter02 
- exercise01
- exercise02
- exercise03
chapter03 (+ H2 Database)
- exercise01
- exercise02
chapter04 (+ Oauth2)
- exercise01
- exercise01
- exercise01

현재 시도를 하고 있는 중이고, build가 성공해도 mainClassName을 찾지 못하는 Error가 발생하고 있다.

위와 같이 챕터마다 필요한 의존성을 관리하여 하위 프로젝트에 전파를 하고 싶다.
kotlin 예제가 별로 없어서 어떻게 할 수 있을지 고민을 좀 해봐야할 것 같다.

