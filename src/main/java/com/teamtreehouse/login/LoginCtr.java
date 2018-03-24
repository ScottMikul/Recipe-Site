package com.teamtreehouse.login;

import com.teamtreehouse.core.BaseUserController;
import com.teamtreehouse.index.SignInWrapper;
import com.teamtreehouse.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
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
public class LoginCtr extends BaseUserController{

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        //get username from session.
        String username = getSessionUsername();
        model.addAttribute("username", username);

        SignInWrapper wrapper = new SignInWrapper();
        model.addAttribute("wrapper",wrapper);
        System.out.println("loginpage");
        return "login";
    }

}
