package com.teamtreehouse.signUp;

import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import com.teamtreehouse.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SignUpCtr {
    @Autowired
    UserService userService;

    @RequestMapping(value= "/register", method = RequestMethod.GET)
    public String SignUp(Model model){
        System.out.println("signup page");
        RegistrationWrapper wrapper = new RegistrationWrapper("","","");
        model.addAttribute("wrapper",wrapper);
        return "signup";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String SignUp(@Valid RegistrationWrapper wrapper, BindingResult result, RedirectAttributes redirectAttributes){
        System.out.println("signup page");

        User user = new User(wrapper.getUsername(),new String [] {"user"},wrapper.getPassword());


        if(!wrapper.getcPassword().contentEquals(wrapper.getPassword())){
            System.out.println("passwords do not match");
            return "redirect:/register";
        }
        else if(userService.findUser(user)!=null){
            System.out.println("username taken");
            return "redirect:/register";
        }
        //complete registration with a new person guy thing majiger
        userService.RegisterUser(user);

        //need to set up cookies and session stuff....
        //just want to type some stuff. lol.


        //redirect to the index upon successful creation of user
        return "index";
    }
}
