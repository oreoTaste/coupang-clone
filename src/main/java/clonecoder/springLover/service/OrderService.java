package clonecoder.springLover.service;

import clonecoder.springLover.domain.*;
import clonecoder.springLover.repository.DeliveryRepository;
import clonecoder.springLover.repository.MemberRepository;
import clonecoder.springLover.repository.OrderRepository;
import clonecoder.springLover.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long checkout(Long memberId, Long productId, int count, Address address) throws Exception{
        // 엔티티조회
        Member member = memberRepository.findOne(memberId);
        Product product = productRepository.findOne(productId);

        // 배송관련
        Delivery delivery = new Delivery();
        delivery.setAddress(address);
        delivery.setStatus(DeliveryStatus.READY);

        // 주문관련
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderProduct);
        orderRepository.save(order);
        return order.getId();
    }

    public Order findOne(Long id) {
        return orderRepository.findOne(id);
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }


    public void cancel(Long orderId) {
        Order foundOrder = orderRepository.findOne(orderId);
        foundOrder.cancel();
    }
}
