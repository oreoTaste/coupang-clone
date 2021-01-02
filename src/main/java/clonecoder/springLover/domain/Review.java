package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id; //댓글id

    @OneToOne(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private OrderProduct orderProduct; // 주문제품 테이블 관리용
    private String review;//후기
    private int stars;//별점
    private String photoReview; // 사진평
    private String shortReview; //한줄후기

}
