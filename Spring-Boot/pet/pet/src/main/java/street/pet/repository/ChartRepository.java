package street.pet.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import street.pet.domain.*;

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

    public List<Chart> findAllWithSearch(ChartSearch chartSearch){
        JPAQueryFactory query = new JPAQueryFactory(em);
        QChart chart = QChart.chart;
        QPet pet = QPet.pet;
        QVet vet = QVet.vet;

        return query
                .select(chart)
                .from(chart)
                .join(chart.pet, pet)
                .fetchJoin()
                .join(chart.vet, vet)
                .fetchJoin()
                .where(petNameLike(chartSearch.getPetName()), chartStatusEq(chartSearch.getStatus()))
                .limit(1000)
                .fetch();
    }

    private BooleanExpression chartStatusEq(ChartStatus status) {
        if (status == null) {
            return null;
        }
        return QChart.chart.status.eq(status);
    }

    private BooleanExpression petNameLike(String petName) {
        if(!StringUtils.hasText(petName)) {
            return null;
        }
        return QPet.pet.name.like(petName);
    }
}
