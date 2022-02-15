### 한달한권 | 클린코드 실습 프로젝트

<p align="center">
  <img src="https://github.com/piaochung/blog-and-study/blob/main/Spring-Boot/houseUtils/src/img/class%20diagram.png" width="70%"/>
</p>

- BrokeragePolicy: 임대와 매매 중개 수수료 계산을 다형성을 사용하여 풀어내기 위한 interface
- RentBrokeragePolicy: 임대일 때 건물 가격에 따른 BrokerageRule을 생성해주는 클래스
- PurchaseBrokeragePolicy: 매매일 때 건물 가격에 따른 BrokerageRule을 생성해주는 클래스
- BrokeragePolicyFactory: ActionType에 따른 RentBrokeragePolicy 또는 PurchaseBrokeragePolicy 클래스를 반환해주는 클래스
- ActionType:PURCHASE, RENT 값이 정의 되어 있는 enum 클래스
- BrokerageRule: 가격이 특정 범위일 때 상환효율과 상환금액을 가지는 클래스
