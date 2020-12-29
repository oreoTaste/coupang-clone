package clonecoder.springLover.service;

import clonecoder.springLover.controller.AddressForm;
import clonecoder.springLover.domain.Address;
import clonecoder.springLover.domain.AddressSearch;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.repository.AddressRepository;
import clonecoder.springLover.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    public Address findAddress(HttpServletRequest request, Long addressId) {
        Member member = memberService.checkValidity(request);
        Hibernate.initialize(member.getAddressList());
        List<Address> addressList = member.getAddressList();
        for(Address address : addressList) {
            if(address.getId().equals(addressId)) {
                return addressRepository.findOne(addressId);
            }
        }
        return null;
    }

    public List<Address> findMyAddress(HttpServletRequest request) {
        Member member = memberService.checkValidity(request);
        Hibernate.initialize(member.getAddressList());
//        member.getAddressList().size(); 이런식으로도 가능
        List<Address> addressList = member.getAddressList();
        return addressList;
    }

    public List<Address> searchAddress(AddressSearch addressSearch) {
        return addressRepository.findAllByString(addressSearch);
    }

    @Transactional
    public boolean changeAddress(HttpServletRequest request, Address address) {
        Address foundAddress = findAddress(request, address.getId());
        if(foundAddress != null) {
            foundAddress.setAddress(address);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean removeAddress(HttpServletRequest request, Long id) {
        Member member = memberService.checkValidity(request);
        Long mainAddressId = member.getMainAddressId();

        List<Address> myAddress = findMyAddress(request);
        for(Address foundAddress : myAddress) {
            if(foundAddress.getId().equals(id)) {
                if(mainAddressId != null && mainAddressId.equals(foundAddress.getId())) {
                    member.setMainAddressId(null);
                }
                myAddress.remove(foundAddress);
                if(foundAddress.getDelivery() == null) {
                    addressRepository.delete(foundAddress);
                } else {
                    foundAddress.setMember(null);
                }
                return true;
            }
        }
        return false;
    }

    public boolean registerAddress(HttpServletRequest request, AddressForm addressForm) {
        Member member = memberService.checkValidity(request);
//        Hibernate.initialize(member.getAddressList());
        Address address = new Address(addressForm);
        save(address);
        member.setAddress(address);

        if(member.getMainAddressId() == null) {
            member.setMainAddressId(address.getId());
        }
        return true;
    }
}
