package street.pet.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import street.pet.domain.Chart;
import street.pet.domain.Pet;
import street.pet.domain.Vet;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChartRepository {

    private final EntityManager em;

    public void save(Chart chart) {
        em.persist(chart);
    }

    public Chart findOne(Long chartId) {
        return em.find(Chart.class, chartId);
    }

    public List<Chart> findAll() {
        return em.createQuery(
                        "select c from Chart c", Chart.class)
                .getResultList();
    }

    public List<Chart> findAllWithPetVet(int offset, int limit) {
        return em.createQuery(
                        "select c from Chart c" +
                                " join fetch c.pet p" +
                                " join fetch c.vet v", Chart.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Chart> findByIdWithPetVet(Long id) {
        return em.createQuery(
                        "select c from Chart c" +
                                " join fetch c.pet p" +
                                " join fetch c.vet v" +
                                " where c.id = :id", Chart.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Chart> findByVetWithPetVet(Long id) {
        return em.createQuery(
                        "select c from Chart c" +
                                " join fetch c.pet p" +
                                " join fetch c.vet v" +
                                " where c.vet.id = :id", Chart.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Chart> findByPetWithPetVet(Long id) {
        return em.createQuery(
                        "select c from Chart c" +
                                " join fetch c.pet p" +
                                " join fetch c.vet v" +
                                " where c.pet.id = :id", Chart.class)
                .setParameter("id", id)
                .getResultList();
    }
}
