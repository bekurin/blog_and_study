package street.pet.web.api;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import street.pet.AutoAppConfig;
import street.pet.domain.Address;
import street.pet.domain.Member;
import street.pet.domain.Pet;
import street.pet.service.MemberService;
import street.pet.service.PetService;
import street.pet.web.api.member.MemberApiController;

import java.time.LocalDate;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberApiControllerTest extends ApiDocumentationTest{

    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    private final MemberService memberService = ac.getBean(MemberService.class);
    private final PetService petService = ac.getBean(PetService.class);
    private final MemberApiController memberApiController = ac.getBean(MemberApiController.class);

    @Test
    @DisplayName("[GET] 멤버 전체 조회")
    @Transactional
    public void getMembers() throws Exception {
        //given
        Address address = new Address("서울", "테스트", "123-4");
        Member member1 = Member.createMember("박성수", "010-2356-5432", address);
        Member member2 = Member.createMember("이기자", "010-5462-6562", address);
        memberService.join(member1);
        memberService.join(member2);

        Pet petA = Pet.createPet("태양", LocalDate.of(2012, 5, 23), member2);
        Pet petB = Pet.createPet("하늘", LocalDate.of(2018, 3, 13), member2);
        petService.join(petA);
        petService.join(petB);

        //when
        BaseApiController.Result membersV1 = memberApiController.membersV1();

        ResultActions result = this.mockMvc.perform(get("/api/v1/members/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(membersV1))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("members",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("[POST] 멤버 생성")
    public void createMember() throws Exception {
        //given


        //when

        //then
    }
}