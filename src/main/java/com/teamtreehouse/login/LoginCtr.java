package com.teamtreehouse.login;

import com.teamtreehouse.index.SignInWrapper;
import com.teamtreehouse.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by scott on 1/27/2018.
 */
@Controller
public class LoginCtr {

    @Autowired
    UserRepository mUserRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        SignInWrapper wrapper = new SignInWrapper();
        model.addAttribute("wrapper",wrapper);
        System.out.println("loginpage");
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginPage(@Valid SignInWrapper wrapper, BindingResult result, RedirectAttributes redirectAttributes){


        String enteredPass = wrapper.getPassword();
        System.out.println("Entered pass: "+ enteredPass);

        String actualPass =  mUserRepository.findByName(wrapper.getUsername()).getPassword();
        System.out.println("Actual pass: "+ actualPass);

        if(enteredPass.contentEquals(actualPass)){
            System.out.println("we can login now.");
            return "index";
        }

        return "redirect:/login";

    }
}
