package clonecoder.springLover.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String big;
    private String medium;
    private String small;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;

}
