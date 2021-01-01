package clonecoder.springLover.service;

import clonecoder.springLover.domain.Comment;
import clonecoder.springLover.domain.EvaluationSearch;
import clonecoder.springLover.domain.OrderProduct;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final OrderProductService orderProductService;
    private final StorageService storageService;
    private final MemberService memberService;

    @Transactional
    public Long saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findOne(Long id) {
        return commentRepository.findOne(id);
    }

    public List<Comment> findComments() {
        return commentRepository.findAll();
    }

    public List<Comment> searchComments(EvaluationSearch evaluationSearch) {
        return commentRepository.findAllByString(evaluationSearch);
    }

    @Transactional
    public Comment register(Long orderProductId,
                            MultipartFile photo,
                            Comment comment,
                            HttpServletRequest request) throws Exception {
        OrderProduct orderProduct = orderProductService.findOne(orderProductId);
        Product product = orderProduct.getProduct();
        saveComment(comment);
        orderProduct.setComment(comment);
        Long commentId = comment.getId();

        String key = "productReview/" + product.getId();
        String[] photoSplit = photo.getOriginalFilename().split("\\.");
        String photoKey = key + UUID.randomUUID() + "."
                        + photoSplit[photoSplit.length - 1];
        storageService.store(photo, photoKey, request);
        comment.setPhotoReview("/upload/" + photoKey);
        return comment;
    }
}
