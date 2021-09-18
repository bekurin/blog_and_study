package street.pet.web.api;

import javassist.NotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.*;
import street.pet.web.api.chart.ChartApiController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class ChartApiControllerTest extends ApiDocumentationTest {

    @Autowired
    private ChartApiController chartApiController;

    @Test
    public void getCharts() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        em.persist(member);

        Pet petA = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);
        Pet petB = Pet.createPet("태양", LocalDate.of(2019, 8, 13), member);

        Department department = Department.createDepartment("안과");
        em.persist(department);

        Vet vetA = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);
        Vet vetB = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", department);
        em.persist(vetA);
        em.persist(vetB);

        Chart chartA = Chart.createChart(vetA, petA);
        Chart chartB = Chart.createChart(vetA, petB);
        em.persist(chartA);
        em.persist(chartB);

        Prescription prescriptionA = Prescription.createPrescription("처방 테스트 A.", chartA, vetB);
        Prescription prescriptionB = Prescription.createPrescription("처방 테스트 B.", chartB, vetA);
        em.persist(prescriptionA);
        em.persist(prescriptionB);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/charts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.data[0].status").value(ChartStatus.READY.toString()))
                .andExpect(jsonPath("$.data[0].patient").value(petA.getName()))
                .andExpect(jsonPath("$.data[0].specialist").value(vetA.getName()))
                .andDo(document("GET-charts",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void getChart() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        em.persist(member);

        Pet pet = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);

        Department department = Department.createDepartment("안과");
        em.persist(department);

        Vet vetA = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);
        Vet vetB = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", department);
        em.persist(vetA);
        em.persist(vetB);

        Chart chart = Chart.createChart(vetA, pet);
        em.persist(chart);

        Prescription prescriptionA = Prescription.createPrescription("처방 테스트 A.", chart, vetB);
        Prescription prescriptionB = Prescription.createPrescription("처방 테스트 B.", chart, vetA);
        em.persist(prescriptionA);
        em.persist(prescriptionB);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/chart/" + chart.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.data[0].specialist").value(vetA.getName()))
                .andExpect(jsonPath("$.data[0].patient").value(pet.getName()))
                .andDo(document("GET-chart",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
    
    @Test
    public void getByPets() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        em.persist(member);

        Pet petA = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);

        Department department = Department.createDepartment("안과");
        em.persist(department);

        Vet vetA = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);
        Vet vetB = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", department);
        em.persist(vetA);
        em.persist(vetB);

        Chart chartA = Chart.createChart(vetA, petA);
        Chart chartB = Chart.createChart(vetB, petA);
        em.persist(chartA);
        em.persist(chartB);

        Prescription prescriptionA = Prescription.createPrescription("처방 테스트 A.", chartA, vetB);
        Prescription prescriptionB = Prescription.createPrescription("처방 테스트 B.", chartB, vetA);
        em.persist(prescriptionA);
        em.persist(prescriptionB);
        
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/chart/pet/" + petA.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.data[0].specialist").value(vetA.getName()))
                .andExpect(jsonPath("$.data[1].specialist").value(vetB.getName()))
                .andDo(document("GET-chart-pet",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void getByVet() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        em.persist(member);

        Pet petA = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);
        Pet petB = Pet.createPet("구름", LocalDate.of(2019, 8, 13), member);

        Department department = Department.createDepartment("안과");
        em.persist(department);

        Vet vet = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);
        em.persist(vet);

        Chart chartA = Chart.createChart(vet, petA);
        Chart chartB = Chart.createChart(vet, petB);
        em.persist(chartA);
        em.persist(chartB);

        Prescription prescriptionA = Prescription.createPrescription("처방 테스트 A.", chartA, vet);
        em.persist(prescriptionA);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/chart/vet/" + vet.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.data[0].patient").value(petA.getName()))
                .andExpect(jsonPath("$.data[1].patient").value(petB.getName()))
                .andDo(document("GET-chart-vet",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void getChartFailed() throws Exception {
        //when
        Assertions.assertThrows(NotFoundException.class,
                () -> chartApiController.chartsV1(0,10));
        //then
    }

    @Test
    public void postChart() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        em.persist(member);

        Pet pet = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);
        em.persist(pet);

        Department department = Department.createDepartment("안과");
        em.persist(department);

        Vet vet = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);
        em.persist(vet);

        HashMap<String, Object> map = new HashMap<>();
        map.put("vetId", vet.getId());
        map.put("petId", pet.getId());

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/chart")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(map))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.vetName").value(vet.getName()))
                .andExpect(jsonPath("$.petName").value(pet.getName()))
                .andDo(document("POST-chart",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("vetId").type(JsonFieldType.NUMBER).description("수의사 id"),
                                fieldWithPath("petId").type(JsonFieldType.NUMBER).description("반려동물 id")
                        )));
    }

    @Test
    public void putChart() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        em.persist(member);

        Pet pet = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);
        em.persist(pet);

        Department department = Department.createDepartment("안과");
        em.persist(department);

        Vet vet = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);
        em.persist(vet);

        Chart chart = Chart.createChart(vet, pet);
        em.persist(chart);

        Map<String, Object> map = new HashMap<>();
        map.put("status", ChartStatus.ACCESS);

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/chart/" + chart.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(map))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(chart.getId()))
                .andExpect(jsonPath("$.status").value(ChartStatus.ACCESS.toString()))
                .andDo(document("PUT-chart",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("차트 상태")
                        )));
    }
}