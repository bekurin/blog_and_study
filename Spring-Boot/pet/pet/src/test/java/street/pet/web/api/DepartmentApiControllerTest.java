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
import street.pet.web.api.department.DepartmentApiController;

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
public class DepartmentApiControllerTest extends ApiDocumentationTest {

    @Autowired
    DepartmentApiController departmentApiController;

    @Test
    public void getDepartments() throws Exception {
        //given
        Department departmentA = Department.createDepartment("테스트 진려과목 1");
        Department departmentB = Department.createDepartment("테스트 진려과목 2");
        em.persist(departmentA);
        em.persist(departmentB);

        Vet vetA = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", departmentA);
        Vet vetB = Vet.createVet("안녕하세요 아무개입니다.", "아무개", departmentA);
        em.persist(vetA);
        em.persist(vetB);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/departments/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.data[0].name").value(departmentA.getName()))
                .andExpect(jsonPath("$.data[1].name").value(departmentB.getName()))
                .andDo(document("GET-departments",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void getDepartment() throws Exception {
        //given
        Department department = Department.createDepartment("테스트 진려과목 1");
        em.persist(department);

        Vet vet = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", department);
        em.persist(vet);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/department/" + department.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(department.getName()))
                .andDo(document("GET-department",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void getDepartmentFailed() throws Exception {
        //when
        Assertions.assertThrows(NotFoundException.class,
                () -> departmentApiController.departmentsV1());
        //then
    }

    @Test
    public void postDepartment() throws Exception {
        //given
        Map<String, String> map = new HashMap<>();
        map.put("name", "테스트 진료과목1");

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/department")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(map))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(map.get("name")))
                .andDo(document("POST-department",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("진려과목 이름")
                        )));
    }

    @Test
    public void putDepartment() throws Exception {
        //given
        Department department = Department.createDepartment("테스트 진료과목");
        em.persist(department);

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "수정 후 진료과목");

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/department/" + department.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(map))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(map.get("name")))
                .andDo(document("PUT-department",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("수정할 진료과목 이름")
                        )));
    }
}