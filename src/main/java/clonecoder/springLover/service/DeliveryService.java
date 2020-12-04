package clonecoder.springLover.service;

import clonecoder.springLover.domain.Delivery;
import clonecoder.springLover.domain.Evaluation;
import clonecoder.springLover.domain.EvaluationSearch;
import clonecoder.springLover.repository.DeliveryRepository;
import clonecoder.springLover.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public Long saveDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Delivery findOne(Long id) {
        return deliveryRepository.findOne(id);
    }

    public List<Delivery> findDeliveries() {
        return deliveryRepository.findAll();
    }

    public List<Delivery> searchDeliveries(Delivery delivery) {
        return deliveryRepository.findAllByString(delivery);
    }

}
