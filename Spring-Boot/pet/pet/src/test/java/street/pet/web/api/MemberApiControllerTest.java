package street.pet.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.domain.Address;
import street.pet.domain.Member;
import street.pet.domain.Pet;
import street.pet.repository.MemberRepository;
import street.pet.service.MemberService;
import street.pet.service.PetService;
import street.pet.web.dto.MemberResponseDto;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberApiControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Autowired
    private MemberService memberService;

    @Autowired
    private PetService petService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 전체 조회")
    public void testMVC() throws Exception {
        //given
        Address address = new Address("서울", "테스트", "123-4");
        Member member1 = Member.createMember("박성수", "010-2356-5432", address);
        Member member2 = Member.createMember("이기자", "010-5462-6562", address);
        memberService.join(member1);
        memberService.join(member2);

        //when
        List<Member> members = memberRepository.findAll();

        List<MemberResponseDto> memberResponseDtoList = members.stream()
                .map(member -> new MemberResponseDto(member))
                .collect(Collectors.toList());

        ResultActions result = this.mockMvc.perform(get("/api/v1/members/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberResponseDtoList))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andDo(document("members"));
    }
}