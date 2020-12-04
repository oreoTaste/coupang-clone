package clonecoder.springLover.service;

import clonecoder.springLover.domain.Comment;
import clonecoder.springLover.domain.EvaluationSearch;
import clonecoder.springLover.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

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
}
