CREATE TABLE payment_event
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    buyer_id        INT          NOT NULL COMMENT '구매자 ID',
    order_name      VARCHAR(255) NOT NULL COMMENT '주문 이름',
    order_id       VARCHAR(255) NOT NULL COMMENT '주문 유일 키',
    payment_key     VARCHAR(255) COMMENT '결제 키',
    payment_type    ENUM ('NORMAL') COMMENT '결제 유형, 실제 값은 일반 결제',
    payment_method  ENUM ('EASY_PAY') COMMENT '결제 방법, 실제 값은 간편 결제',
    approved_at     TIMESTAMP COMMENT '승인 시간, nullable',
    is_payment_done BOOLEAN      NOT NULL COMMENT '결제 완료 여부',
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시간'
);

CREATE TABLE payment_order
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    payment_event_id  INT          NOT NULL COMMENT '결제 이벤트 ID',
    seller_id         INT          NOT NULL COMMENT '판매자 ID',
    product_id        INT          NOT NULL COMMENT '제품 ID',
    order_id         VARCHAR(255) NOT NULL COMMENT '주문 키',
    amount            BIGINT       NOT NULL COMMENT '금액',
    payment_status    ENUM ('NOT_STARTED', 'EXECUTING', 'SUCCESS', 'FAILURE', 'UNKNOWN') COMMENT '결제 상태, 실제 값은 결제 처리 시작 전, 결제 처리 중, 결제 처리 성공, 결제 처리 실패, 알 수 없는 결제 상태',
    is_ledger_updated BOOLEAN      NOT NULL COMMENT '원장 업데이트 여부',
    is_wallet_updated BOOLEAN      NOT NULL COMMENT '지갑 업데이트 여부',
    created_at        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시간'
);


