package street.pet.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Address;
import street.pet.domain.Owner;
import street.pet.repository.OwnerRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OwnerServiceTest {

    @Autowired
    OwnerService ownerService;
    @Autowired
    OwnerRepository ownerRepository;

    @Test
    @DisplayName("Join() 테스트")
    public void join() throws Exception {
        //given
        Address address = Address.builder().city("seoul").street("test").zipcode("12345").build();

        Owner owner = Owner.builder().name("userA").address(address).build();

        //when
        Long ownerId = ownerService.join(owner);

        //then
        assertThat(owner).isEqualTo(ownerService.findOne(ownerId));
    }

    @Test
    @DisplayName("전체 주인 조회하기")
    public void findAllOwners() throws Exception {
        //given
        Address address1 = Address.builder().city("seoul").street("test").zipcode("12345").build();
        Address address2 = Address.builder().city("inchon").street("test").zipcode("12345").build();

        Owner ownerA = Owner.builder().name("userA").address(address1).build();
        Owner ownerB = Owner.builder().name("userB").address(address2).build();

        //when
        ownerService.join(ownerA);
        ownerService.join(ownerB);

        //then
        assertThat(ownerService.findOwners().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("주인 이름 수정하기")
    public void updateOwnerName () throws Exception {
        //given

        //when

        //then
    }
}