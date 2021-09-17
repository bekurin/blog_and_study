### 동물병원 진료 프로그램

### 활동 개요
- 테이블 설계[블로그 링크](https://hangjastar.tistory.com/223)
- CRUD API 설계 
- Spring Rest Docs를 사용하여 API 문서화
- Service, Controller, Entity 성공, 실패 테스트 코드 작성 (약 90% 커버)

### 프로젝트 진행 시 발생한 문제점
#### 조회 API n+1 문제 발생
XXXToOne -> fetch join, XXXToMany -> 지연로딩 사용
jpa.properties.hibernate.default_batch_fetch_size = 100 설정하여 100개 단위로 in절에 부모키를 같이 넘겨주어주는 것으로 해결하였습니다.

---

#### API 테스트 코드 작성 시 parameter 전달 문제
POST, PUT, DELETE의 경우에 http 요청 시 데이터를 함께 전달하여야한다. request, response를 위해 만들어두었던 클래스에 생성자를 만들어서 값을 전달하려고 할 때 오류 발생
Map<String, Object>를 만들어서 json 데이터의 이름과 값을 넣어주어서 ObjectMapper를 사용하여 Mapping하는 것으로 해결하였습니다.

---

## Dependencies
### Main
- spring-boot-starter-validation
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- lombok
- h2 database

### Test
- Junit4
- spring-restdocs-mockmvc
- spring-boot-starter-test
- spring-boot-devtools

### Documentation
- asciidoctor.jvm.convert
