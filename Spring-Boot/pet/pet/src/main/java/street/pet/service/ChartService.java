package street.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Chart;
import street.pet.domain.Pet;
import street.pet.domain.Prescription;
import street.pet.domain.Vet;
import street.pet.repository.ChartRepository;
import street.pet.repository.PetRepository;
import street.pet.repository.VetRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChartService {

    private final ChartRepository chartRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;

    /**
     * 차트 생성
     */
    @Transactional
    public Long Chart(Long petId, Long vetId) {
        //엔티티 조회
        Pet pet = petRepository.findOne(petId);
        Vet vet = vetRepository.findOne(vetId);

        //차트 생성
        Chart chart = Chart.createChart(vet, pet);
        chartRepository.save(chart);
        return chart.getId();
    }

    /**
     * 차트 조회
     */
    public Chart findOne(Long chartId){
        return chartRepository.findOne(chartId);
    }

    /**
     * 차트 취소
     */
    @Transactional
    public void cancelChart(Long chartId){
        Chart chart = chartRepository.findOne(chartId);
        chart.cancel();
    }
}
