package street.pet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.domain.Address;
import street.pet.domain.Owner;
import street.pet.repository.OwnerRepository;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerServiceTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

    private final OwnerService ownerService = ac.getBean(OwnerService.class);
    private final OwnerRepository ownerRepository = ac.getBean(OwnerRepository.class);

    @Test
    @DisplayName("Join() 테스트")
    public void join() throws Exception {
        //given
        Address address = Address.builder().city("seoul").street("test").zipcode("12345").build();

        Owner owner = Owner.builder().name("userA").address(address).build();

        //when
        Long ownerId = ownerService.join(owner);

        //then
        assertThat("userA").isEqualTo(ownerService.findOne(ownerId).getName());
    }

    @Test
    @DisplayName("중복 이름 회원 가입 시 X")
    public void validate() throws Exception {
        //given
        Address address1 = Address.builder().city("seoul").street("test").zipcode("12345").build();
        Address address2 = Address.builder().city("inchon").street("test").zipcode("12345").build();

        Owner ownerA = Owner.builder().name("userA").address(address1).build();
        Owner ownerB = Owner.builder().name("userA").address(address2).build();

        //when
        ownerService.join(ownerA);

        //then
        Assertions.assertThrows(IllegalStateException.class,
                () -> ownerService.join(ownerB));
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
        Address address = Address.builder().city("seoul").street("test").zipcode("12345").build();
        Owner ownerA = Owner.builder().name("userA").address(address).build();

        //when
        Long ownerId = ownerService.join(ownerA);
        ownerService.update(ownerId, "userB");

        //then
        assertThat(ownerRepository.findOne(ownerId).getName()).isEqualTo("userB");
    }
}