package clonecoder.springLover.repository;

import clonecoder.springLover.domain.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class DeliveryRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired DeliveryRepository deliveryRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원등록() throws Exception {
        // given
        Member member = new Member();
        member.setEmail("email@email.com");
        member.setName("YK");
        Long memberId = memberRepository.save(member);

        Order order = new Order();
        order.setMember(member);
        order.setStatus(OrderStatus.ORDER);
        Long orderId = orderRepository.save(order);
        em.flush();

        Address address = new Address();
        address.setCity("Seoul");

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setAddress(address);
        delivery.setStatus(DeliveryStatus.READY);

        // when
        Long deliveryId = deliveryRepository.save(delivery);
        Delivery savedDelivery = deliveryRepository.findOne(deliveryId);
        em.flush();

        // then
        Assertions.assertEquals(savedDelivery.getAddress(), delivery.getAddress());
        Assertions.assertEquals(savedDelivery.getOrder(), delivery.getOrder());
        Assertions.assertEquals(savedDelivery.getStatus(), delivery.getStatus());
    }

    @Test
    @Transactional
    public void 회원검색() throws Exception {
        // given
        Member member = new Member();
        member.setEmail("email@email.com");
        member.setName("YK");
        Long memberId = memberRepository.save(member);

        Order order = new Order();
        order.setMember(member);
        order.setStatus(OrderStatus.ORDER);
        Long orderId = orderRepository.save(order);

        Address address = new Address();
        address.setCity("Seoul");

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setAddress(address);
        delivery.setStatus(DeliveryStatus.READY);
        Long deliveryId = deliveryRepository.save(delivery);
        order.setDelivery(delivery); // delivery를 뒤에 등록했기 때문에 order를 업데이트 해줘야함

        // when
        Delivery savedDelivery = deliveryRepository.findOne(deliveryId);
        Delivery search = new Delivery();
        search.setOrder(order);
        search.setStatus(DeliveryStatus.READY);
        List<Delivery> deliveryList = deliveryRepository.findAllByString(search);
        Delivery foundDelivery = deliveryList.get(0);

        // then
        Assertions.assertEquals(savedDelivery.getAddress(), foundDelivery.getAddress());
        Assertions.assertEquals(savedDelivery.getOrder(), foundDelivery.getOrder());
        Assertions.assertEquals(savedDelivery.getStatus(), foundDelivery.getStatus());
    }
}