package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@ToString
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String emoticon;
    private String tel;
    private String email;
    private String password;
    private String type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private List<Address> addressList;

    @OneToMany(mappedBy="member")
    private List<Order> orderList;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

    // 생성메서드 : 신규회원
    public static Member createMember(String email, String name, String tel, String password) {
        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        member.setTel(tel);
        member.setPassword(password);
        return member;
    }

    // 연관관계 메서드
    public void setOrder(Order order) {
        this.orderList.add(order);
        order.setMember(this);
    }

    public void setAddress(Address address) {
        addressList.add(address);
    }
}
