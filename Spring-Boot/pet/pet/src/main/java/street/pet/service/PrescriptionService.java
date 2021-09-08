package street.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Chart;
import street.pet.domain.Prescription;
import street.pet.domain.Vet;
import street.pet.repository.ChartRepository;
import street.pet.repository.PrescriptionRepository;
import street.pet.repository.VetRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final ChartRepository chartRepository;
    private final VetRepository vetRepository;

    /**
     * 처방전 생성
     */
    @Transactional
    public Long save(Long chartId, Long vetId, String description) {
        // 엔티티 조회
        Chart chart = chartRepository.findOne(chartId);
        Vet vet = vetRepository.findOne(vetId);

        // 처방전 생성
        Prescription prescription = Prescription.createPrescription(description, chart, vet);

        // 처방전 저장
        prescriptionRepository.save(prescription);
        return prescription.getId();
    }

    /**
     * 처방전 조회
     */
    public List<Prescription> findPrescriptions(){
        return prescriptionRepository.findAll();
    }

    public List<Prescription> findByChart(Long chartId){
        Chart chart = chartRepository.findOne(chartId);
        return prescriptionRepository.findByChart(chart);
    }

    public Prescription findOne(Long id){
        return prescriptionRepository.findOne(id);
    }

    /**
     * 처방전 수정
     */
    @Transactional
    public void update(Long id, String description){
        Prescription prescription = prescriptionRepository.findOne(id);
        prescription.update(description);
    }
}
