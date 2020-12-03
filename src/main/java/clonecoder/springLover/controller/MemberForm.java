package clonecoder.springLover.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberForm {
    private String name;
    private String password;
    private String email;
    private String tel;
}
