package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Order;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.domain.ProductSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final EntityManager em;

    public Long save(Product product) {
        em.persist(product);
        return product.getId();
    }

    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class)
                .getResultList();
    }

    public Product findOne(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAllByString(ProductSearch productSearch) {
        //language=JPAQL
        String jpql = "select p From Product p join p.category c";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (productSearch.getName() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.name = :name";
        }
        //회원 이름 검색
        if (StringUtils.hasText(productSearch.getSmallCategory())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " c.small like :small";
        }
        TypedQuery<Product> query = em.createQuery(jpql, Product.class)
                .setMaxResults(1000); //최대 1000건
        if (productSearch.getName() != null) {
            query = query.setParameter("name", productSearch.getName());
        }
        if (StringUtils.hasText(productSearch.getSmallCategory())) {
            query = query.setParameter("small", productSearch.getSmallCategory());
        }
        return query.getResultList();
    }

}
