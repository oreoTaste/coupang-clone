package clonecoder.springLover.controller;

import clonecoder.springLover.domain.Address;
import clonecoder.springLover.domain.AddressSearch;
import clonecoder.springLover.domain.Member;
import clonecoder.springLover.domain.Product;
import clonecoder.springLover.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final MemberService memberService;
    private final AddressService addressService;

    @GetMapping("register/address")
    public String registerForm(HttpServletRequest request) {
        memberService.checkValidity(request);
        return "address/registerForm";
    }

    @PostMapping("register/address")
    @ResponseBody
    public String register(HttpServletRequest request, AddressForm addressForm, Model model) {
        Member member = memberService.checkValidity(request);

        Address address = Address.createAddress(addressForm);
        addressService.save(address);
        member.setAddress(address);

//        AddressSearch addressSearch = new AddressSearch();
//        addressSearch.setMemberId(member.getId());
//        List<Address> addressList = addressService.findAddress(addressSearch);
//        model.addAttribute("addressList", addressList);
        return "주소를 정상적으로 등록했습니다";
    }

}
