package street.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/** 멤버 2명 생성
 *  조창석 - userA
 *     - 하늘 - petA
 *     - 구름 - petB
 *  정민 - userB
 *     - 잔디 - petC
 *
 *  수의사 2명 생성
 *  김길도 - vetA, 신경외과 - departmentA
 *  박수아 - vetB, 정형외과 - departmentB
 *
 *   차트 2개 생성
 *   잔디, 김길도 - chartA
 *   하늘, 박수아 - chartB
 *
 *   처방전 5개 생성
 *   테스트A - prescriptionA, 김길도, ChartA
 *   테스트B - prescriptionB, 박수아, ChartA
 *   테스트C - prescriptionC, 김길도, ChartA
 *   테스트D - prescriptionD, 박수아, ChartB
 *   테스트E - prescriptionE, 김길도, ChartB
 */
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        Map<String, Long> vetIds = initService.dbVet();
        initService.dbInit1(vetIds);
        initService.dbInit2(vetIds);
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public Map<String, Long> dbVet() {
            Department departmentA = Department.createDepartment("신경외과");
            em.persist(departmentA);

            Department departmentB = Department.createDepartment("정형외과");
            em.persist(departmentB);

            Vet vetA = Vet.createVet("안녕하세요!", "김길도", departmentA);
            em.persist(vetA);

            Vet vetB = Vet.createVet("안녕하세요!", "박수아", departmentB);
            em.persist(vetB);

            Map<String, Long> vetIds = new HashMap<>();

            vetIds.put("vetA", vetA.getId());
            vetIds.put("vetB", vetB.getId());
            return vetIds;
        }

        public void dbInit1(Map<String, Long> vetIds) {
            Member member = createMember("조창석", "010-1324-4432", "서울", "테스트", "123-4");
            em.persist(member);

            Pet petA = createPet("하늘", LocalDate.of(2019, 8, 13), member);
            em.persist(petA);

            Pet petB = createPet("구름", LocalDate.of(2015, 6, 10), member);
            em.persist(petB);

            Vet vetA = getVet(vetIds, "vetA");
            Vet vetB = getVet(vetIds, "vetB");

            Chart chart = createChart(vetB, petA);
            em.persist(chart);

            Prescription testA = createPrescription("테스트A", chart, vetA);
            em.persist(testA);

            Prescription testB = createPrescription("테스트B", chart, vetB);
            em.persist(testB);

            Prescription testC = createPrescription("테스트C", chart, vetA);
            em.persist(testC);
        }

        public void dbInit2(Map<String, Long> vetIds) {
            Member member = createMember("정민", "010-7654-1298", "인천", "테스트", "654-1");
            em.persist(member);

            Pet petC = createPet("잔디", LocalDate.of(2017, 12, 18), member);
            em.persist(petC);

            Vet vetA = getVet(vetIds, "vetA");
            Vet vetB = getVet(vetIds, "vetB");

            Chart chart = createChart(vetB, petC);
            em.persist(chart);

            Prescription testD = createPrescription("테스트D", chart, vetB);
            em.persist(testD);

            Prescription testE = createPrescription("테스트E", chart, vetA);
            em.persist(testE);
        }

        private Prescription createPrescription(String description, Chart chart, Vet vet) {
            return Prescription.createPrescription(description, chart, vet);
        }

        private Chart createChart(Vet vet, Pet pet) {
            return Chart.createChart(vet, pet);
        }

        private Vet getVet(Map<String, Long> vetIds, String key) {
            Long vetId = vetIds.get(key);
            Vet vet = em.find(Vet.class, vetId);
            return vet;
        }

        private Pet createPet(String name, LocalDate birthDate, Member member) {
            return Pet.createPet(name, birthDate, member);
        }

        private Member createMember(String name, String phone, String city, String street, String zipcode) {
            return Member.createMember(name, phone, new Address(city, street, zipcode));
        }
    }
}
