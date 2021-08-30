package street.pet.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import street.pet.domain.Pet;
import street.pet.domain.QOwner;
import street.pet.domain.QPet;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PetRepository {

    private final EntityManager em;

    public void save(Pet pet){
        em.persist(pet);
    }

    public Pet findOne(Long id){
        return em.find(Pet.class, id);
    }

    public List<Pet> findAll(PetSearch petSearch){
        JPAQueryFactory query = new JPAQueryFactory(em);
        QPet pet = QPet.pet;
        QOwner owner = QOwner.owner;

        return query
                .select(pet)
                .from(pet)
                .join(pet.owner, owner)
                .where(nameLike(petSearch.getName()))
                .limit(100)
                .fetch();
    }

    private BooleanExpression nameLike(String ownerName) {
        if(!StringUtils.hasText(ownerName)){
            return null;
        }
        return QOwner.owner.name.like(ownerName);
    }
}
