package street.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Vet;
import street.pet.repository.VetRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VetService {

    private final VetRepository vetRepository;

    @Transactional
    public Long saveVet(Vet vet){
        vetRepository.save(vet);
        return vet.getId();
    }

    @Transactional
    public void updateVet(Long id, String major){
        Vet findVet = vetRepository.findOne(id);
        findVet.setMajor(major);
    }

    public List<Vet> findVets() {
        return vetRepository.findAll();
    }

    public Vet findOne(Long id) {
        return vetRepository.findOne(id);
    }
}
