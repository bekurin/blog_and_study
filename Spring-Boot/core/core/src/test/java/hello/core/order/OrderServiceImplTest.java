package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {

    @Test
    public void createOrder() throws Exception {
        //given
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());

        memberRepository.save(new Member(1L, "userA", Grade.VIP));

        //when
        Order order = orderService.createOrder(1L, "itemA", 10000);

        //then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
