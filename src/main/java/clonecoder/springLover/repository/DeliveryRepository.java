package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Delivery;
import clonecoder.springLover.domain.Order;
import clonecoder.springLover.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryRepository {
    private final EntityManager em;

    public Long save(Delivery delivery) {
        em.persist(delivery);
        return delivery.getId();
    }

    public Delivery findOne(Long id) {
        return em.find(Delivery.class, id);
    }

    public List<Delivery> findAll() {
        return em.createQuery("select d from Delivery d", Delivery.class).getResultList();
    }

    public List<Delivery> findAllByString (Delivery delivery){
        //language=JPAQL
        String jpql = "select d From Delivery d join d.address a join d.order";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (delivery.getOrder() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " d.order.id = :id";
        }
        //회원 이름 검색
        if (delivery.getStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " d.status like :status";
        }
        TypedQuery<Delivery> query = em.createQuery(jpql, Delivery.class)
                .setMaxResults(1000); //최대 1000건
        if (delivery.getOrder() != null) {
            query = query.setParameter("id", delivery.getOrder().getId());
        }
        if (delivery.getStatus() != null) {
            query = query.setParameter("status", delivery.getStatus());
        }
        return query.getResultList();
    }}
