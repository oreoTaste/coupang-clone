package clonecoder.springLover.repository;

import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Order;
import clonecoder.springLover.domain.OrderStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderRepositoryTest {
    @Autowired OrderRepository orderRepository;
    @Autowired EntityManager em;

    @Test
    public void 주문등록() throws Exception {
        // given
        Order order = new Order();
        order.setStatus(OrderStatus.ORDER);
        
        Member member = new Member();
        member.setEmail("email@email.com");
        order.setMember(member);

        // when
        Long orderId = orderRepository.save(order);
        em.flush();
        Order savedOrder = orderRepository.findOne(orderId);

        // then
        Assert.assertEquals("아이디가 같아야함", savedOrder.getId(), order.getId());
        Assert.assertEquals("이메일주소가 같아야함", savedOrder.getMember().getEmail(), order.getMember().getEmail());
    }
}