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
    private Long addressId;

    @OneToMany(mappedBy="member")
    private List<Order> orderList;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

}
