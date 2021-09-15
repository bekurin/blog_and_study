package street.pet.web.api;

import javassist.NotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Address;
import street.pet.domain.Member;
import street.pet.domain.Pet;
import street.pet.web.api.pet.PetApiController;

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
public class PetApiControllerTest extends ApiDocumentationTest {

    @Autowired
    private PetApiController petApiController;

    @Test
    public void GET_반려동물_조회() throws Exception {
        //given
        Address address = new Address("서울", "테스트", "123-4");
        Member member = Member.createMember("박성수", "010-2356-5432", address);
        em.persist(member);

        Pet petA = Pet.createPet("한라봉", LocalDate.of(2012, 5, 23), member);
        Pet petB = Pet.createPet("용과", LocalDate.of(2018, 3, 13), member);
        Pet petC = Pet.createPet("무화과", LocalDate.of(2016, 2, 21), member);
        em.persist(petA);
        em.persist(petB);
        em.persist(petC);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/pets/")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("GET-pets",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void 등록된_반려동물이_없으면_NotFoundException() throws Exception {
        //when
        Assertions.assertThrows(NotFoundException.class,
                () -> petApiController.petsV1());
    }

    @Test
    public void GET_반려동물_id_조회() throws Exception {
        //given
        Address address = new Address("서울", "테스트", "123-4");
        Member member = Member.createMember("박성수", "010-2356-5432", address);
        em.persist(member);

        Pet pet = Pet.createPet("한라봉", LocalDate.of(2012, 5, 23), member);
        em.persist(pet);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/pet/" + pet.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("GET-pet",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void POST_반려동물_등록() throws Exception {
        //given
        Address address = new Address("서울", "테스트", "123-4");
        Member member = Member.createMember("박성수", "010-2356-5432", address);
        em.persist(member);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "케밥");
        map.put("memberId", member.getId());
        map.put("birthDate", LocalDate.of(2021, 9, 1));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/pet/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)));

        //then
        result.andExpect(status().isOk())
                .andDo(document("POST-pet",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("반려동물 이름"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버의 id"),
                                fieldWithPath("birthDate").type(JsonFieldType.STRING).description("반려동물 출생년일")
                        )));
    }

    @Test
    public void PUT_반려동물_수정() throws Exception {
        //given
        Address address = new Address("서울", "테스트", "123-4");
        Member member = Member.createMember("박성수", "010-2356-5432", address);
        em.persist(member);

        Pet pet = Pet.createPet("한라봉", LocalDate.of(2012, 5, 23), member);
        em.persist(pet);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "자몽");

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/pet/" + pet.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("자몽"))
                .andExpect(jsonPath("$.id").value(pet.getId()))
                .andDo(document("PUT-pet",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("반려동물 이름")
                        )));
    }
}