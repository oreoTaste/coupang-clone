package clonecoder.springLover.service;

import clonecoder.springLover.domain.*;
import clonecoder.springLover.repository.DeliveryRepository;
import clonecoder.springLover.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final MemberService memberService;
    
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

    public Integer countCurrentDeliveries(HttpServletRequest request) {
        Member member = memberService.checkValidity(request);
        Hibernate.initialize(member.getOrderList());
        List<Order> orderList = member.getOrderList();
        int cnt = 0;
        for(Order order: orderList) {
            Delivery delivery = order.getDelivery();
            if(delivery.getStatus().equals(DeliveryStatus.READY) ||
            delivery.getStatus().equals(DeliveryStatus.INDELIVERY)) {
                cnt += 1;
            }
        }
        return cnt;
    }
}
