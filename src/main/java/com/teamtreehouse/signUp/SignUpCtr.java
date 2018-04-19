package com.teamtreehouse.signUp;

import com.teamtreehouse.core.BaseUserController;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import com.teamtreehouse.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by scott on 1/27/2018.
 */
@Controller
public class SignUpCtr extends BaseUserController{
    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager mAuthenticationManager;

    @RequestMapping(value= "/register", method = RequestMethod.GET)
    public String SignUp(Model model){
        //get username from session.
        String username = getSessionUsername();
        model.addAttribute("username", username);

        System.out.println("signup page");
        RegistrationWrapper wrapper = new RegistrationWrapper("","","");
        model.addAttribute("wrapper",wrapper);
        return "signup";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String SignUp(@Valid RegistrationWrapper wrapper, BindingResult result, RedirectAttributes redirectAttributes){
        System.out.println("signup page");

        User user = new User(wrapper.getUsername(),new String [] {"ROLE_USER"},wrapper.getPassword());


        if(!wrapper.getcPassword().contentEquals(wrapper.getPassword())){
            System.out.println("passwords do not match");
            return "redirect:/register";
        }
        else if(userService.findUser(user)!=null){
            System.out.println("username taken");
            return "redirect:/register";
        }

        //complete registration
        userService.RegisterUser(user);


        //TODO create session with new user on registration completion
        //Authentication authentication = mAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(wrapper.getUsername(),wrapper.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles())));
        //SecurityContextHolder.getContext().setAuthentication(authentication);
        //return "redirect:/index";

        return "redirect:/login";
    }
}
