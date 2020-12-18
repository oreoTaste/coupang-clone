package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addressList = new ArrayList<>();

    @OneToMany(mappedBy="member")
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

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
        this.addressList.add(address);
        address.setMember(this);
    }

}
