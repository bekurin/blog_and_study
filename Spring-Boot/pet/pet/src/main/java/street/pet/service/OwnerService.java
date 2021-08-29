package street.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Owner;
import street.pet.repository.OwnerRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Owner owner) {
        validateDuplicatedOwner(owner);
        ownerRepository.save(owner);
        return owner.getId();
    }

    private void validateDuplicatedOwner(Owner owner) {
        List<Owner> findOwner = ownerRepository.findByName(owner.getName());

        if(!findOwner.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 조회
     */
    public List<Owner> findOwners() {
        return ownerRepository.findAll();
    }

    public Owner findOne(Long ownerId) {
        return ownerRepository.findOne(ownerId);
    }

    /**
     * 회원 이름 변경
     */
    @Transactional
    public void update(Long id, String name){
        Owner owner = ownerRepository.findOne(id);
        owner.setName(name);
    }
}
