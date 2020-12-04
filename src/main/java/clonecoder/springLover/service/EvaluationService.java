package clonecoder.springLover.service;

import clonecoder.springLover.domain.Comment;
import clonecoder.springLover.domain.Evaluation;
import clonecoder.springLover.domain.EvaluationSearch;
import clonecoder.springLover.repository.CommentRepository;
import clonecoder.springLover.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

    @Transactional
    public Long saveEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public Evaluation findOne(Long id) {
        return evaluationRepository.findOne(id);
    }

    public List<Evaluation> findComments() {
        return evaluationRepository.findAll();
    }

    public List<Evaluation> searchComments(EvaluationSearch evaluationSearch) {
        return evaluationRepository.findAllByString(evaluationSearch);
    }
}
