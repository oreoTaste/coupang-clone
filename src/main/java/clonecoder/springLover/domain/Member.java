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
    private Long mainAddressId;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addressList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cart> cartList = new ArrayList<>();

    @OneToMany(mappedBy="member", cascade = CascadeType.ALL)
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

    public List<Address> getAddressList() {
        return this.addressList;
    }

    public boolean plusQuantityCart(Long productId, int quantity) {
        for(Cart cart : this.cartList) {
            if(cart.getProduct().getId() == productId) {
                cart.setCount(cart.getCount() + quantity);
                return true;
            }
        }
        return false;
    }

    public boolean adjustQuantityCart(Long productId, int quantity) {
        for(Cart cart : this.cartList) {
            if(cart.getProduct().getId() == productId) {
                cart.setCount(quantity);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emoticon='" + emoticon + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", addressList=" + addressList.size() +
                ", cartList=" + cartList.size() +
                ", orderList=" + orderList.size() +
                ", comments=" + comments +
                '}';
    }

}
