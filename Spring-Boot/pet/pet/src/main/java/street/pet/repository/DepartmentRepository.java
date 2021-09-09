package street.pet.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import street.pet.domain.Department;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepartmentRepository {

    private final EntityManager em;

    public void save(Department department) {
        em.persist(department);
    }

    public Department findOne(Long id) {
        return em.find(Department.class, id);
    }

    public List<Department> findAll() {
        return em.createQuery(
                        "select d from Department d", Department.class)
                .getResultList();
    }

    public List<Department> findByName(String name) {
        return em.createQuery(
                        "select d from Department d where d.name = :name", Department.class)
                .setParameter("name", name)
                .getResultList();
    }
}
