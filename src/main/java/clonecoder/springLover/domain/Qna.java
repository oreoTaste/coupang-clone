package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Qna {

    @Id
    @GeneratedValue
    @Column(name = "qna_id")
    private Long id;

    private String comment;

    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    @OneToMany(mappedBy = "superQna", cascade = CascadeType.ALL)
    private List<Qna> qnaList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_qna_id")
    private Qna superQna;


}
