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

    public List<Product> findAll(int num) {
        TypedQuery<Product> query = em.createQuery("select p from Product p ORDER BY p.id DESC", Product.class);
        return query.setMaxResults(num).getResultList();
    }

    public Product findOne(Long id) {
        return em.find(Product.class, id);
    }

    public Long countAllByString(ProductSearch productSearch) {
        //language=JPQL
        String jpql = "select count(p) From Product p";

        boolean isFirstCondition = true;
        //제품명 검색
        if (productSearch.getName() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.name like :name";
        }

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        if (productSearch.getName() != null) {
            query = query.setParameter("name", "%" + productSearch.getName() + "%");
        }
        return query.getSingleResult();
    }

    public List<Product> findAllByString(ProductSearch productSearch) {
        //language=JPQL
        String jpql = "select p From Product p";

        boolean isFirstCondition = true;
        //제품명 검색
        if (productSearch.getName() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.name like :name";
        }

        TypedQuery<Product> query = em.createQuery(jpql, Product.class)
                .setMaxResults(1000); //최대 1000건
        if (productSearch.getName() != null) {
            query = query.setParameter("name", "%" + productSearch.getName() + "%");
        }
        return query.getResultList();
    }

}
