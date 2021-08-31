package street.pet.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Chart;
import street.pet.domain.Pet;
import street.pet.domain.Vet;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class PetRepository {

    private final EntityManager em;
    private final VetRepository vetRepository;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public void save(Pet pet){
        em.persist(pet);
    }

    public Pet findOne(Long id){
        return em.find(Pet.class, id);
    }

//    public List<Pet> findAll(PetSearch petSearch){
//        JPAQueryFactory query = new JPAQueryFactory(em);
//        QPet pet = QPet.pet;
//        QOwner owner = QOwner.owner;
//
//        return query
//                .select(pet)
//                .from(pet)
//                .join(pet.owner, owner)
//                .where(nameLike(petSearch.getName()))
//                .limit(100)
//                .fetch();
//    }

//    private BooleanExpression nameLike(String ownerName) {
//        if(!StringUtils.hasText(ownerName)){
//            return null;
//        }
//        return QOwner.owner.name.like(ownerName);
//    }

    /**
     * 진료 접수
     */
    @Transactional
    public Long chart(Long petId, Long vetId, String diseaseName, String description){
        // 엔티티 조회
        Pet pet = petRepository.findOne(petId);
        Vet vet = vetRepository.findOne(vetId);

        // 진료 생성
        Chart.createChart(pet, vet, diseaseName, description);

        petRepository.save(pet);
        return pet.getId();
    }

    /**
     * 진료 취소
     */
    @Transactional
    public void cancelChart(Long petId){
        Pet pet = petRepository.findOne(petId);
        pet.cancel();
    }
}
