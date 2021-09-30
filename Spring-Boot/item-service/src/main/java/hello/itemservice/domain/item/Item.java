package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

    /**
     * Integer's default value = null
     * int's default value = 0
     * 따라서 가격과 수량의 값이 0이면 의미가 중복될 수 있으므로
     * Integer를 사용하여 값이 입력되지 않았을 경우 null로 처리
     */
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
