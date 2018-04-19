package login;

import com.teamtreehouse.login.LoginCtr;
import com.teamtreehouse.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginPageTest {
    private MockMvc mockMvc;

    @InjectMocks
    private LoginCtr controller;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/html/view/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new LoginCtr())
                .setViewResolvers(viewResolver)
                .build();

        User user2 = new User("scotty",new String[]{"ROLE_USER"} , "password");
        Authentication auth = new UsernamePasswordAuthenticationToken(user2.getName(),user2.getPassword(), AuthorityUtils.createAuthorityList(user2.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @Test
    public void login_ControllerViewResolved() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}