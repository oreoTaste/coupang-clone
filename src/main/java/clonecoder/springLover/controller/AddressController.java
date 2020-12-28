package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Address;
import clonecoder.springLover.domain.AddressSearch;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
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
        if(addressService.removeAddress(request, id)) {
            return "OK";
        }
        return "Not OK";
    }


    @GetMapping("change/address/{id}")
    public String changeForm(@PathVariable Long id,
                             HttpServletRequest request,
                             Model model) {
        addressService.findAddress(request, id);
        return "address/changeForm";
    }

    @PostMapping("change/address")
    @ResponseBody
    public String changeAddress(HttpServletRequest request, Address address, Model model) {
        if(addressService.changeAddress(request, address)){
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
        if(addressService.registerAddress(request, addressForm)) {
            return "OK";
        }
        return "Not OK";
    }

    @GetMapping("address/list")
    public String listAddress(HttpServletRequest request,
                              Model model) {
        List<Address> myAddress = addressService.findMyAddress(request);
        model.addAttribute("addressList", myAddress);
        return "address/list";
    }


}
