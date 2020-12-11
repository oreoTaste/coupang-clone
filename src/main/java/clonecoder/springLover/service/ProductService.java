package clonecoder.springLover.service;

import clonecoder.springLover.domain.Product;
import clonecoder.springLover.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

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
}
