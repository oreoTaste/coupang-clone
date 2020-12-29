package clonecoder.springLover.service;

import clonecoder.springLover.domain.*;
import clonecoder.springLover.repository.DeliveryRepository;
import clonecoder.springLover.repository.MemberRepository;
import clonecoder.springLover.repository.OrderRepository;
import clonecoder.springLover.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final DeliveryService deliveryService;

    @Transactional
    public Long depositCheckout(Long memberId,
                             List<Long> productId,
                             List<Integer> count,
                             Address address) throws Exception{
        return checkout(OrderStatus.ORDER, memberId, productId, count, address);
    }

    @Transactional
    public Long cardCheckout(Long memberId,
                         List<Long> productId,
                         List<Integer> count,
                         Address address) throws Exception{
        return checkout(OrderStatus.PAID, memberId, productId, count, address);
    }

    @Transactional
    public Long checkout(OrderStatus orderStatus,
                         Long memberId,
                         List<Long> productId,
                         List<Integer> count,
                         Address address) throws Exception{
        // 엔티티조회
        Member member = memberRepository.findOne(memberId);

        // 배송관련
        Delivery delivery = new Delivery();
        delivery.setAddress(address);
        delivery.setStatus(DeliveryStatus.READY);

//        Hibernate.initialize(address);
        address.setDelivery(delivery);

        // 주문관련
        List<OrderProduct> orderProductList = new ArrayList<>();
        for(int i = 0; i < productId.size(); i++) {
            Product product = productRepository.findOne(productId.get(i));
            orderProductList.add(OrderProduct.createOrderProduct(product, product.getPrice(), count.get(0)));
        }

        Order order = null;
        if(orderStatus == OrderStatus.ORDER) {
            order = Order.createDefaultOrder(member, delivery, orderProductList.stream().toArray(OrderProduct[]::new));
        } else if(orderStatus == OrderStatus.PAID) {
            order = Order.createPayedOrder(member, delivery, orderProductList.stream().toArray(OrderProduct[]::new));
        }
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
