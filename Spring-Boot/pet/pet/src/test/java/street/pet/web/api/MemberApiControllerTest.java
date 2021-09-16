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
import street.pet.web.api.member.MemberApiController;

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
public class MemberApiControllerTest extends ApiDocumentationTest {

    @Autowired
    private MemberApiController memberApiController;

    @Test
    public void GET_멤버_전체_조회() throws Exception {
        //given
        Address address = new Address("서울", "테스트", "123-4");
        Member member1 = Member.createMember("박성수", "010-2356-5432", address);
        Member member2 = Member.createMember("이기자", "010-5462-6562", address);
        em.persist(member1);
        em.persist(member2);

        Pet petA = Pet.createPet("태양", LocalDate.of(2012, 5, 23), member2);
        Pet petB = Pet.createPet("하늘", LocalDate.of(2018, 3, 13), member2);
        em.persist(petA);
        em.persist(petB);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/members/")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("GET-members",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void GET_멤버_Id_조회() throws Exception {
        //given
        Address address = new Address("서울", "테스트", "123-4");
        Member member = Member.createMember("박성수", "010-2356-5432", address);
        em.persist(member);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/member/" + member.getId())
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("GET-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void 등록된_멤버가_없으면_NotFoundException() throws Exception {
        //then
        Assertions.assertThrows(NotFoundException.class,
                () -> memberApiController.membersV1());
    }

    @Test
    public void POST_멤버_등록() throws Exception {
        //given
        Address address = new Address("경기도", "테스트", "544-54");
        Member member = Member.createMember("홍길동", "010-5424-6542", address);

        Map<String, Object> map = new HashMap<>();
        map.put("address", member.getAddress());
        map.put("phone", member.getPhone());
        map.put("name", member.getName());

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/member/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(map)));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(member.getName()))
                .andDo(document("POST-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("멤버의 이름"),
                                fieldWithPath("phone").type(JsonFieldType.STRING).description("멤버의 핸드폰 번호"),
                                fieldWithPath("address.city").type(JsonFieldType.STRING).description("멤버의 도시"),
                                fieldWithPath("address.street").type(JsonFieldType.STRING).description("멤버의 도로명"),
                                fieldWithPath("address.zipcode").type(JsonFieldType.STRING).description("멤버의 zipcode")
                        )));
    }

    @Test
    public void PUT_멤버_수정() throws Exception {
        //given
        Address addressA = new Address("경기도", "테스트", "53-123");
        Member member = Member.createMember("아무개", "010-6541-9815", addressA);
        em.persist(member);

        //when
        Address addressB = new Address("서울", "테스트", "53-123");
        Map<String, Object> map = new HashMap<>();
        map.put("address", addressB);
        map.put("phone", "010-1111-2222");

        ResultActions result = mockMvc.perform(put("/api/v1/member/" + member.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(map)));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(member.getId()))
                .andExpect(jsonPath("$.phone").value(member.getPhone()))
                .andExpect(jsonPath("$.address.city").value(member.getAddress().getCity()))
                .andExpect(jsonPath("$.address.street").value(member.getAddress().getStreet()))
                .andExpect(jsonPath("$.address.zipcode").value(member.getAddress().getZipcode()))
                .andDo(document("PUT-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("phone").type(JsonFieldType.STRING).description("멤버의 핸드폰 번호"),
                                fieldWithPath("address.city").type(JsonFieldType.STRING).description("멤버의 도시"),
                                fieldWithPath("address.street").type(JsonFieldType.STRING).description("멤버의 도로명"),
                                fieldWithPath("address.zipcode").type(JsonFieldType.STRING).description("멤버의 zipcode")
                        )));
    }
}