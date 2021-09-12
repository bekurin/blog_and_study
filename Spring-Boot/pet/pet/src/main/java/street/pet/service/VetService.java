package street.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Department;
import street.pet.domain.Vet;
import street.pet.repository.VetRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VetService {

    private final VetRepository vetRepository;

    /**
     * 수의사 등록
     */
    @Transactional
    public Long saveVet(Vet vet) {
        validateOptions(vet);
        vetRepository.save(vet);
        return vet.getId();
    }

    private void validateOptions(Vet vet) {
        if(vet.getDepartment().getId() == null){
            throw new IllegalStateException("진료과목이 등록되지 않았습니다.");
        }

        List<Vet> findVets = vetRepository.findByName(vet.getName());
        if(!findVets.isEmpty()){
            throw new IllegalStateException("중복된 이름이 존재합니다.");
        }
    }

    /**
     * 수의사 조회
     */
    public Vet findOne(Long vetId){
        return vetRepository.findOne(vetId);
    }

    public List<Vet> findVets(){
        return vetRepository.findAll();
    }

    public List<Vet> findByDepartment(Department department) {
        return vetRepository.findByDepartment(department);
    }

    /**
     * 수의사 정보 수정
     */
    @Transactional
    public Long updateVet(Long id, String description, Department department){
        Vet vet = vetRepository.findOne(id);
        vet.update(description, department);

        return vet.getId();
    }
}
