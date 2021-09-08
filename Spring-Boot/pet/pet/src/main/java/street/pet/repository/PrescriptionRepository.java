package street.pet.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import street.pet.domain.Chart;
import street.pet.domain.Prescription;
import street.pet.domain.Vet;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PrescriptionRepository {

    private final EntityManager em;

    public void save(Prescription prescription) {
        em.persist(prescription);
    }

    public List<Prescription> findAll() {
        return em.createQuery(
                "select p from Prescription p", Prescription.class)
                .getResultList();
    }

    public Prescription findOne(Long id){
        return em.find(Prescription.class, id);
    }

    public List<Prescription> findByChart(Chart chart){
        return em.createQuery(
                "select p from Prescription p where p.chart = :chart", Prescription.class)
                .setParameter("chart", chart)
                .getResultList();
    }

    public List<Prescription> findAllWithChartVet(int offset, int limit) {
        return em.createQuery(
                "select p from Prescription p" +
                        " join fetch p.chart c" +
                        " join fetch p.vet v", Prescription.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
