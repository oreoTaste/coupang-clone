package clonecoder.springLover.service;

import clonecoder.springLover.domain.*;
import clonecoder.springLover.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberService memberService;

    @Transactional
    public Long saveProduct(Product product) {
        Long savedId = productRepository.save(product);
        return savedId;
    }

    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }

    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    public List<Product> findProducts(int maxNum) {
        return productRepository.findAll(maxNum);
    }

    public Long countByKeyword(String keyword) {
        ProductSearch productSearch = new ProductSearch();
        productSearch.setName(keyword);
        return productRepository.countAllByString(productSearch);
    }

    public List<Product> findByKeyword(String keyword) {
        ProductSearch productSearch = new ProductSearch();
        productSearch.setName(keyword);
        return productRepository.findAllByString(productSearch);
    }

    public List<OrderProduct> findMyProduct(HttpServletRequest request) {
        Member member = memberService.checkValidity(request);
        if(member != null) {
            Hibernate.initialize(member.getOrderList());
            List<Order> orderList = member.getOrderList();
            List<OrderProduct> orderProductList = new ArrayList<>();

            orderList.forEach((e) -> {
                orderProductList.addAll(e.getOrderProductList());
            });
            return orderProductList;
        }
        return null;
    }
}
