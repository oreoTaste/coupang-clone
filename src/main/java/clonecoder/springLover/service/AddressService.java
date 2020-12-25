package clonecoder.springLover.service;

import clonecoder.springLover.domain.Address;
import clonecoder.springLover.domain.AddressSearch;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.repository.AddressRepository;
import clonecoder.springLover.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {
    private final AddressRepository addressRepository;
    private final MemberService memberService;

    @Transactional
    public Long save(Address address) {
        return addressRepository.save(address);
    }

    public Address getAddress(Long addressId) {
        return addressRepository.findOne(addressId);
    }

    public List<Address> findAddress(AddressSearch addressSearch) {
        return addressRepository.findAllByString(addressSearch);
    }

    @Transactional
    public boolean changeAddress(Member member, Address address) {
        Long addressId = address.getId();

        List<Address> addressList = member.getAddressList();
        for(Address foundAddress : addressList) {
            if(foundAddress.getId().equals(addressId)) {
                Address addressFromId = getAddress(addressId);
                addressFromId.setAsk(address.getAsk());
                addressFromId.setCity(address.getCity());
                addressFromId.setDelivery(address.getDelivery());
                addressFromId.setDetail(address.getDetail());
                addressFromId.setReceiverName(address.getReceiverName());
                addressFromId.setReceiverTel(address.getReceiverTel());
                addressFromId.setStreet(address.getStreet());
                addressFromId.setZipcode(address.getZipcode());
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean removeAddress(Member member, Long id) {
        List<Address> addressList = member.getAddressList();
        for(Address foundAddress : addressList) {
            if(foundAddress.getId().equals(id)) {
                Address address = getAddress(id);
                addressRepository.delete(address);
                addressList.remove(foundAddress);
                return true;
            }
        }
        return false;
    }
}
