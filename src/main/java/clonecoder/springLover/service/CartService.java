package clonecoder.springLover.service;

import clonecoder.springLover.domain.*;
import clonecoder.springLover.repository.CartRepository;
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
public class CartService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Transactional
    public void save(Long memberId, Long productId, int count) throws Exception{
        // 엔티티조회
        Member member = memberRepository.findOne(memberId);
        Product product = productRepository.findOne(productId);

        // 이미 카트에 있는 경우, 상품 개수만 늘리기
        if(!member.adjustCart(productId, count)) {
            // 카드에 없는 경우, 추가하기
            Cart savedCart = Cart.createCart(member, product, count);
            cartRepository.save(savedCart);
        }

//        return savedCart.getId();
    }

    public Cart findOne(Long cartId) {
        return cartRepository.findOne(cartId);
    }

    public List<Cart> findMyCart(Long memberId) {
        return cartRepository.findAllByString(memberId);
    }

    @Transactional
    public void clear(Cart cart) {
        cartRepository.delete(cart);
    }

    @Transactional
    public void setQuantity(Long cartId, int quantity) {
        Cart cart = cartRepository.findOne(cartId);
        cart.setCount(quantity);
    }
}
