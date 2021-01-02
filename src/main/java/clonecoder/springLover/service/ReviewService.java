package clonecoder.springLover.service;

import clonecoder.springLover.domain.Review;
import clonecoder.springLover.domain.EvaluationSearch;
import clonecoder.springLover.domain.OrderProduct;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderProductService orderProductService;
    private final StorageService storageService;
    private final MemberService memberService;

    @Transactional
    public Long saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review findOne(Long id) {
        return reviewRepository.findOne(id);
    }

    public List<Review> findReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> searchReviews(EvaluationSearch evaluationSearch) {
        return reviewRepository.findAllByString(evaluationSearch);
    }

    @Transactional
    public Review register(Long orderProductId,
                           MultipartFile photo,
                           Review review,
                           HttpServletRequest request) throws Exception {
        OrderProduct orderProduct = orderProductService.findOne(orderProductId);
        Product product = orderProduct.getProduct();
        saveReview(review);
        orderProduct.setReview(review);
        Long reviewId = review.getId();

        String key = "productReview/" + product.getId();
        String[] photoSplit = photo.getOriginalFilename().split("\\.");
        String photoKey = key + UUID.randomUUID() + "."
                        + photoSplit[photoSplit.length - 1];
        storageService.store(photo, photoKey, request);
        review.setPhotoReview("/upload/" + photoKey);
        return review;
    }
}
