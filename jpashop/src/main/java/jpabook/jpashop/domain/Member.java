package jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 나는 주인이 아니에요 거울이에요, orderTable에 member를 비추는
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
