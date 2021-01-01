package clonecoder.springLover.service;

import clonecoder.springLover.controller.ProductForm;
import clonecoder.springLover.domain.*;
import clonecoder.springLover.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberService memberService;
    private final StorageService storageService;

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

    @Transactional
    public Product register(MultipartFile description,
                         MultipartFile thumbnail,
                         ProductForm productForm,
                         HttpServletRequest request) throws Exception {

        Product product = new Product().create(productForm);
        saveProduct(product);
        Long productId = product.getId();
        String key = "product/" + productId;

        String[] descriptionSplit = description.getOriginalFilename().split("\\.");
        String descriptionKey = key + "/description/" + UUID.randomUUID() + "."
                + descriptionSplit[descriptionSplit.length - 1];

        String[] thumbnailSplit = thumbnail.getOriginalFilename().split("\\.");
        String thumbnailKey = key + "/thumbnail/" + UUID.randomUUID() + "."
                + thumbnailSplit[thumbnailSplit.length - 1];

        storageService.store(description, descriptionKey, request);
        storageService.store(thumbnail, thumbnailKey, request);

        product.setDescription("/upload/" + descriptionKey);
        product.setThumbnail("/upload/" + thumbnailKey);
        return product;
    }
}
