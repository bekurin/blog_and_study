package street.pet.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import street.pet.domain.Owner;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OwnerRepository {

    private final EntityManager em;

    public void save(Owner owner){
        em.persist(owner);
    }

    public Owner findOne(Long ownerId){
        return em.find(Owner.class, ownerId);
    }

    public List<Owner> findAll() {
        return em.createQuery(
                "select o from Owner o", Owner.class
        ).getResultList();
    }

    public List<Owner> findByName(String name){
        return em.createQuery(
                "select o from Owner o where o.name = :name", Owner.class)
                .setParameter("name", name)
                .getResultList();
    }
}
