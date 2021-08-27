package street.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Address;
import street.pet.domain.Owner;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
//        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Owner ownerA = createOwner("ownerA", "서울", "test1", "24564");
            em.persist(ownerA);

            Owner ownerB = createOwner("ownerB", "경기도", "test2", "12553");
            em.persist(ownerB);
        }

        private Owner createOwner(String name, String city, String street, String zipcode) {
            Address address = Address.builder()
                    .city(city)
                    .street(street)
                    .zipcode(zipcode)
                    .build();

            Owner owner = Owner.builder()
                    .name(name)
                    .address(address)
                    .build();

            return owner;
        }
    }
}
