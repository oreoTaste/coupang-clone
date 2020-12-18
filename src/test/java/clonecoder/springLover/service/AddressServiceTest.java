package clonecoder.springLover.service;

import clonecoder.springLover.domain.Address;
import clonecoder.springLover.domain.AddressSearch;
import clonecoder.springLover.domain.Member;
import org.junit.Test;
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
public class AddressServiceTest {

    @Autowired AddressService addressService;
    @Autowired MemberService memberService;
    @Autowired EntityManager em;

    @Test
    @Commit
    public void 주소등록() throws Exception {
        // given
        Address address = new Address();
        address.setCity("Seoul");
        address.setStreet("my street");
        address.setDetail("100-200");
        address.setReceiverName("receiver");
        address.setZipcode("111-222");
        
        // when
        Long addressId = addressService.save(address);
        Address foundAddress = addressService.getAddress(addressId);

        // then
        assertEquals("등록된 정보와 입력한 정보가 같습니다.", address.getCity(), foundAddress.getCity());
        assertEquals("등록된 정보와 입력한 정보가 같습니다.", address.getDetail(), foundAddress.getDetail());
    }

    @Test
    @Commit
    public void 멤버관련_주소등록() throws Exception {
        // given
        Member member = Member.createMember("email", "name", "tel", "password");
        Long memberId = memberService.join(member);

        Address address = new Address();
        address.setCity("Seoul");
        address.setStreet("my street");
        address.setDetail("100-200");
        address.setReceiverName("receiver");
        address.setZipcode("111-222");

        Long addressId = addressService.save(address);

        // when
        member.setAddress(address);
        AddressSearch addressSearch = new AddressSearch();
        addressSearch.setMemberId(member.getId());
        List<Address> addressList = addressService.findAddress(addressSearch);

        // then
        assertEquals("등록된 정보와 입력한 정보가 같습니다.", member.getAddressList().get(0).getStreet(), address.getStreet());
        assertEquals("등록된 정보와 입력한 정보가 같습니다.", address.getStreet(), addressList.get(0).getStreet());
        assertEquals("검색된 주소가 1건입니다.", 1, addressList.size());
    }

}