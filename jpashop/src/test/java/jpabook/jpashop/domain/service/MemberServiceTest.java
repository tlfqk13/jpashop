package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 테스트 끝나면 알아서 Rollback 시켜줌
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void join() throws Exception {
        //given
        Member member = new Member();
        member.setName("Son");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void checkDuplicateJoin() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("son");

        Member member2 = new Member();
        member2.setName("son");
        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야한다
/*
        try {
            memberService.join(member2); // 예외가 발생해야한다
        }catch (IllegalStateException e){
            return;
        }
*/
        //then
        fail("에외가 발생해야 한다.");
    }

}