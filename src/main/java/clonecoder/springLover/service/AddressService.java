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
}
