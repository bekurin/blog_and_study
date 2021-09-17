### 동물병원 진료 프로그램

### 활동 개요
- 테이블 설계 및 요구사항 제작 [(블로그 링크)](https://hangjastar.tistory.com/223)
- 모든 테이블에 있어 CRUD API 개발
- Spring Rest Docs를 사용하여 API 문서화
- Service, Controller, Entity 성공, 실패 테스트 코드 작성 (약 90% 커버)

### 프로젝트 진행 시 발생한 문제점
#### 조회 API n+1 문제 발생
XXXToOne -> fetch join, XXXToMany -> 지연로딩 사용
jpa.properties.hibernate.default_batch_fetch_size = 100 설정하여 100개 단위로 in절에 부모키를 같이 넘겨주어주는 것으로 해결하였습니다.

---

#### API 테스트 코드 작성 시 parameter 전달 문제
POST, PUT, DELETE의 경우에 http 요청 시 데이터를 함께 전달해야해서 API 스펙 유지를 위한 만들었던 request, response 클래스에 생성자를 만들어서 값을 전달하려고 하였지만 오류 발생하였습니다. 따라서 Map<String, Object>를 만들어서 json 데이터의 이름과 값을 넣어주어서 ObjectMapper를 사용하여 Mapping하는 것으로 해결하였습니다.

---

#### API 테스트 속도 문제
ApiController 테스트 진행 시 클래스마다 objectMapper, EntityManager, restDocumentation, webApplicationContext들을 생성 해야해서 ApiDocumentationTest라는 API 테스트용 부모 클래스를 만들어서 해당 기능들을 상속받는 구조로 개선하여 40% 정도 속도를 개선하였습니다.

---

#### Chart 조회 API에서 동적인 검색 퀴리 개발
chart는 반려동물과 수의사의 정보를 가지고 있습니다. 따라서 반려동물과 수의사의 이름, chart의 상태에 따른 검색 기능이 필요한데 정적인 퀴리를 만들어서 사용하면 유지보수 및 확장성에 있어 좋지 못하기 때문에 Querydsl을 사용하여 동적인 퀴리를 만들었습니다.

---

### 해결 중인 문제
- 연관관계의 주인이 아닌 테이블에서 정보를 삭제하고 싶을 때 (반려동물과 멤버는 삭제하지만 chart의 기록은 남도록)

## 사용 기술
### Main
- spring-boot-starter-validation
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- lombok
- h2 database
- querydsl

### Test
- Junit4
- spring-restdocs-mockmvc
- spring-boot-starter-test
- spring-boot-devtools

### Documentation
- asciidoctor.jvm.convert
