package clonecoder.springLover.service;

import clonecoder.springLover.domain.*;
import clonecoder.springLover.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaService {
    private final QnaRepository qnaRepository;
    private final MemberService memberService;
    private final ProductService productService;

    @Transactional
    public Long saveQna(Qna qna) {
        Long savedId = qnaRepository.save(qna);
        return savedId;
    }

    public Qna findOne(Long id) {
        return qnaRepository.findOne(id);
    }

    public List<Qna> findProducts() {
        return qnaRepository.findAll();
    }

    public List<Qna> findProducts(int maxNum) {
        return qnaRepository.findAll(maxNum);
    }

    public Qna registerQna(HttpServletRequest request, String comment, Long productId) {
        Member member = memberService.checkValidity(request);
        Qna qna = new Qna();
        qna.setProduct(productService.findOne(productId));
        qna.setWriter(member);
        qna.setComment(comment);
        qna.setDate(new Timestamp(System.currentTimeMillis()));
        qnaRepository.save(qna);
        return qna;
    }
}
