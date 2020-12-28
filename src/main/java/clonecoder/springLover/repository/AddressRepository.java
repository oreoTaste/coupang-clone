package clonecoder.springLover.repository;

import clonecoder.springLover.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AddressRepository {
    private final EntityManager em;

    public Long save(Address address) {
        em.persist(address);
        return address.getId();
    }

    @Transactional
    public void delete(Address address){
        System.out.println("++++++++++++++++++++++++++++");
        System.out.println("delete repo");
        System.out.println(address);
        System.out.println("++++++++++++++++++++++++++++");
        em.remove(address);
        em.flush();
        em.clear();
    }

    public Address findOne(Long id) {
        return em.find(Address.class, id);
    }

    public List<Address> findAllByString (AddressSearch addressSearch){
        //language=JPAQL
        String jpql = "select a From Address a"
                + " left join a.member m";
//                + " on a.id = m.addressList.id";

        // 딜러버리 정보(주문정보)가 있는 경우
        if (addressSearch.getDeliveryId() != null) {
            jpql += " left join a.delivery d";
//                 + " on a.id = d.address.id";
        }

        jpql += " where m.id = :memberId";
        // 딜러버리 정보(주문정보)가 있는 경우
        if (addressSearch.getDeliveryId() != null) {
            jpql += " and d.id = :deliveryId";
        }

        TypedQuery<Address> query = em.createQuery(jpql, Address.class)
                .setMaxResults(1000); //최대 1000건

        query = query.setParameter("memberId", addressSearch.getMemberId());
        // 딜러버리 정보(주문정보)가 있는 경우
        if (addressSearch.getDeliveryId() != null) {
            query = query.setParameter("deliveryId", addressSearch.getDeliveryId());
        }

        return query.getResultList();
    }



}
