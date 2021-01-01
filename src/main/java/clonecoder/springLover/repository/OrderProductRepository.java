package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Order;
import clonecoder.springLover.domain.OrderProduct;
import clonecoder.springLover.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderProductRepository {
    private final EntityManager em;
    public OrderProduct findOne(Long id) {
        return em.find(OrderProduct.class, id);
    }

}
