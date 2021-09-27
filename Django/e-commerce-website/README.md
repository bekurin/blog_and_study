# alistyle-e-commerce-with-django
### django를 공부하기 위한 목적으로 제작한 웹사이트입니다.

### 구현 항목
- custom user model 생성 (일반 회원, super 회원 구분)
- Proxy Model을 사용하여 Status에 따라 아이템 목록 구분
- Subcategory 기능 구현 아이템 확인 시 현재 유저가 접속한 위치 확인 가능
- Grid, Large 템플릿 구분하여 아이템 확인 시 원하는 디자인 선택 가능
- Q와 icontains 사용하여 검색 기능 구현
- Cart, CartItem 모델 생성 후 장바구니 기능 구현
- Like 모델 생성 후 좋아요 기능 구현
- context_processors.py를 사용하여 장바구니 수와 좋아요 수 navbar에 표현
- Session key를 사용하여 Cart 모델 id 설정
- Paginator 사용하여 하나의 화면에 보여줄 아이템의 개수 제한
- 장바구니에서 증가, 감소, 제거 기능 구현
- 장바구니 추가 시에 상품, 색상, 사이즈 등의 정보가 모두 같은 아이템이 존재한다면 개수를 올려주는 것으로 동작하게 기능 구현
- 로그인 하지 않은 회원의 경우 주문을 하지 못하도록 기능 구현
- 로그인 하지 않은 상태에서 장바구니에 넣은 물건을 로그인 시 기존의 장바구니에 추가해주는 기능 구현
- 회원가입 시 사용 가능한 이메일만 회원가입이 가능하도록 하기 위해서 메일을 보내 활성화 여부를 체크함 (SMTP 사용)
- 비밀번호를 잃어버렸을 경우에 대비하여 비밀번호 재설정 기능 추가 (SMTP 사용)
- 장바구니 화면에서 check out 버튼 클릭 시 주문하기 화면에 정보 연결
- PayPal sandbox를 사용하여 결제 기능 테스트
- 결제 후 주문 정보 데이터베이스에 저장
- 사용자 Dashboard 기능 추가 (My Orders, Edit Profile, Change Password)
- AWS Lightsail사용하여 웹 배포 테스트
