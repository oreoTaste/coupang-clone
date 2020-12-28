package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Cart;
import clonecoder.springLover.domain.Order;
import clonecoder.springLover.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepository {
    private final EntityManager em;

    @Transactional
    public Long save(Cart cart) {
        em.persist(cart);
        return cart.getId();
    }

    public Cart findOne(Long id) {
        return em.find(Cart.class, id);
    }

    public List<Cart> findAllByString (Long memberId){
        //language=JPAQL
        String jpql = "select c From Cart c join c.member m"
                    + " where c.member.id = :id";

        TypedQuery<Cart> query = em.createQuery(jpql, Cart.class)
                .setMaxResults(1000); //최대 1000건

        query = query.setParameter("id", memberId);
        return query.getResultList();
    }

    public void delete(Cart cart){
        em.remove(cart);
    }

}
