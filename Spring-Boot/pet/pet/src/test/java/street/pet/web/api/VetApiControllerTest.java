package street.pet.web.api;

import javassist.NotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Department;
import street.pet.domain.Vet;
import street.pet.web.api.vet.VetApiController;

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
public class VetApiControllerTest extends ApiDocumentationTest {

    @Autowired
    private VetApiController vetApiController;

    @Test
    public void getVets() throws Exception {
        //given
        Department departmentA = Department.createDepartment("성형외과");
        Department departmentB = Department.createDepartment("피부과");
        em.persist(departmentA);
        em.persist(departmentB);

        Vet vetA = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", departmentA);
        Vet vetB = Vet.createVet("안녕하세요 아무개입니다.", "아무개", departmentA);
        Vet vetC = Vet.createVet("안녕하세요 김밭기입니다.", "김밭기", departmentB);
        em.persist(vetA);
        em.persist(vetB);
        em.persist(vetC);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/vets")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(3))
                .andDo(document("GET-vets",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void getFailed() throws Exception {
        //when
        Assertions.assertThrows(NotFoundException.class,
                () -> vetApiController.vetsV1());
    }

    @Test
    public void getById() throws Exception {
        //given
        Department department = Department.createDepartment("성형외과");
        em.persist(department);

        Vet vet = Vet.createVet("안녕하세요 스타로드입니다.", "스타로드", department);
        em.persist(vet);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/vet/" + vet.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.data[0].doctor").value(vet.getName()))
                .andDo(document("GET-vet",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void postVet() throws Exception {
        //given
        Department department = Department.createDepartment("정신과");
        em.persist(department);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "김수한무");
        map.put("description", "안녕하세요! 김수한무입니다.");
        map.put("departmentId", department.getId());

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/vet/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("김수한무"))
                .andDo(document("POST-vet",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("수의사 이름"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("수의사 소개"),
                                fieldWithPath("departmentId").type(JsonFieldType.NUMBER).description("진료과목 ID")
                        )));
    }
    
    @Test
    public void putVet() throws Exception {
        //given
        Department departmentA = Department.createDepartment("정형외과");
        Department departmentB = Department.createDepartment("방사선과");
        em.persist(departmentA);
        em.persist(departmentB);

        Vet vet = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", departmentA);
        em.persist(vet);

        Map<String, Object> map = new HashMap<>();
        map.put("description", "방사선과로 이동하게 된 홍길동입니다.");
        map.put("departmentId", departmentB.getId());

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/vet/" + vet.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(vet.getId()))
                .andExpect(jsonPath("$.departmentName").value(departmentB.getName()))
                .andExpect(jsonPath("$.description").value(map.get("description")))
                .andDo(document("PUT-vet",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("description").type(JsonFieldType.STRING).description("수의사 소개"),
                                fieldWithPath("departmentId").type(JsonFieldType.NUMBER).description("진료과목 Id")
                        )));
    }
}