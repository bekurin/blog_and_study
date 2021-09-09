package street.pet.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import street.pet.domain.Department;
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

    public Vet findOne(Long vetId) {
        return em.find(Vet.class, vetId);
    }

    public List<Vet> findAll() {
        return em.createQuery(
                        "select v from Vet v", Vet.class)
                .getResultList();
    }

    public List<Vet> findByName(String name) {
        return em.createQuery(
                        "select v from Vet v where v.name = :name", Vet.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Vet> findByDepartment(Department department){
        return em.createQuery(
                "select v from Vet v where v.department = :department", Vet.class)
                .setParameter("department", department)
                .getResultList();
    }

    public List<Vet> findAllWithDepartment(){
        return em.createQuery(
                "select v from Vet v" +
                        " join fetch v.department d", Vet.class)
                .getResultList();
    }

    public List<Vet> findByIdWithDepartment(Long id) {
        return em.createQuery(
                "select v from Vet v" +
                        " join fetch v.department d" +
                        " where v.id = :id")
                .setParameter("id", id)
                .getResultList();
    }
}
