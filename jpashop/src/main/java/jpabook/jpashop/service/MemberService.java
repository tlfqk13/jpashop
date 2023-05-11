package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
//@RequiredArgsConstructor // final 붙은 애들만
public class MemberService {

/*    @Autowired // 필드 주입
    private MemberRepository memberRepository;*/

/*    @Autowired // 생성자 주입
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }*/

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        // db에 member의 name을 unique 제약 조건을 통해서 더 안전하게 한다
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다. ");
        }
        //예외처리

    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
        // 커멘드랑 쿼리는 철저히 분리한다
    }
}
