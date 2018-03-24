package com.teamtreehouse.index;

import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.core.BaseUserController;
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
public class IndexCtr extends BaseUserController {
    @Autowired
    UserRepository mUserRepository;

    @RequestMapping("/index")
    public String loginPage(Model model) {
        //get username from session.
        String username = getSessionUsername();
        model.addAttribute("username", username);


        SignInWrapper wrapper = new SignInWrapper();
        model.addAttribute("wrapper",wrapper);

        return "index";
    }
}
