package example.houseUtils.policy;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 가격이 특정 범위일 때 상환효율과 상환금액을 가지는 클래스
 */
@Getter
@AllArgsConstructor
public class BrokerageRule {
    private Long lessThan;
    private Double brokeragePercent;
    private Long limitAmount;

    public BrokerageRule(Long lessThan, Double brokeragePercent) {
        this(lessThan, brokeragePercent, Long.MAX_VALUE);
    }

    public Long calcMaxBrokerage(Long price) {
        return Math.min(multiplyPercent(price), limitAmount);
    }

    private Long multiplyPercent(Long price) {
        return Double.valueOf(Math.floor(brokeragePercent / 100 * price)).longValue();
    }
}

