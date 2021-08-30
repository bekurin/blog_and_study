package street.pet.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import street.pet.domain.Vet;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class VetRepository {

    private final EntityManager em;

    public void save(Vet vet) {
        if (vet.getId() == null) {
            em.persist(vet);
        } else {
            em.merge(vet);
        }
    }

    public Vet findOne(Long id) {
        return em.find(Vet.class, id);
    }

    public List<Vet> findAll() {
        return em.createQuery(
                "select v from Vet v", Vet.class)
                .getResultList();
    }
}
