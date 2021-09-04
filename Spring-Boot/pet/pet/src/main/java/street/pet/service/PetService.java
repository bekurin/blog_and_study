package street.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Member;
import street.pet.domain.Pet;
import street.pet.repository.MemberRepository;
import street.pet.repository.PetRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    /**
     * 반려동물 등록
     */
    @Transactional
    public Long join(Pet pet) {
        validateJoinedMember(pet);
        petRepository.save(pet);
        return pet.getId();
    }

    private void validateJoinedMember(Pet pet) {
        List<Member> members = memberRepository.findByName(pet.getMember().getName());
        if(members.isEmpty()){
            throw new IllegalStateException("등록된 회원만 반려동물을 등록할 수 있습니다.");
        }
    }

    /**
     * 반려동물 조회
     */
    public List<Pet> findPets() {
        return petRepository.findAll();
    }

    public Pet findOne(Long petId) {
        return petRepository.findOne(petId);
    }

    /**
     * 반려동물 정보 수정
     */
    @Transactional
    public void update(Long petId, String name) {
        Pet pet = petRepository.findOne(petId);
        pet.update(name);
    }
}
