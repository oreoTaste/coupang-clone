package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Address;
import clonecoder.springLover.domain.AddressSearch;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final MemberService memberService;
    private final AddressService addressService;
    private final EntityManager em;

    @GetMapping("delete/address/{id}")
    @ResponseBody
    public String deleteAddress(@PathVariable Long id,
                                HttpServletRequest request) {
        Member member = memberService.checkValidity(request);
        if(addressService.removeAddress(member, id)) {
            return "OK";
        }
        return "Not OK";
    }


    @GetMapping("change/address/{id}")
    public String changeForm(@PathVariable Long id,
                             HttpServletRequest request,
                             Model model) {
        Member member = memberService.checkValidity(request);
        List<Address> addressList = member.getAddressList();
        for(Address address : addressList) {
            if(address.getId().equals(id)) {
                Address realAddress = addressService.getAddress(id);
                model.addAttribute("address", realAddress);
                break;
            }
        }
        return "address/changeForm";
    }

    @PostMapping("change/address")
    @ResponseBody
    public String changeAddress(HttpServletRequest request, Address address, Model model) {
        Member member = memberService.checkValidity(request);
        if(addressService.changeAddress(member, address)) {
            return "OK";
        }
        return "Not OK";
    }

    @GetMapping("register/address")
    public String registerForm(HttpServletRequest request) {
        memberService.checkValidity(request);
        return "address/registerForm";
    }

    @PostMapping("register/address")
    @Transactional
    @ResponseBody
    public String register(HttpServletRequest request, AddressForm addressForm, Model model) {
        Member member = memberService.checkValidity(request);

        Address address = new Address(addressForm);
        addressService.save(address);
        member.setAddress(address);

        if(member.getMainAddressId() == null) {
            member.setMainAddressId(address.getId());
        }

//        AddressSearch addressSearch = new AddressSearch();
//        addressSearch.setMemberId(member.getId());
//        List<Address> addressList = addressService.findAddress(addressSearch);
//
//        model.addAttribute("addressList", addressList);
//        return "address/showList";
        return "OK";
    }

    @GetMapping("address/list")
    @Transactional
    public String listAddress(HttpServletRequest request,
                              Model model) {
        List<Address> myAddress = addressService.getMyAddress(request);
        model.addAttribute("addressList", myAddress);
        return "address/list";
    }


}
