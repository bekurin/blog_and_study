package street.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Owner;
import street.pet.domain.Pet;
import street.pet.repository.OwnerRepository;
import street.pet.repository.PetRepository;
import street.pet.repository.PetSearch;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    /**
     * 조건 검색
     */
    public List<Pet> findPets(PetSearch petSearch){
        return petRepository.findAll(petSearch);
    }
}
