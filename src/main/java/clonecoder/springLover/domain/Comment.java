package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id; //댓글id

    @OneToOne(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private OrderProduct orderProduct; // 주문제품 테이블 관리용
    private String comment;//후기
    private int stars;//별점
    private String photoReview; // 사진평
    private String shortComment; //한줄후기

}
