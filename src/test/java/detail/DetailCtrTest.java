package detail;

import com.teamtreehouse.Detail.DetailCtr;
import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.favorite.Favorite;
import com.teamtreehouse.login.LoginCtr;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.recipe.RecipeRepository;
import com.teamtreehouse.signUp.SignUpCtr;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class DetailCtrTest {
    private MockMvc mockMvc;

    @InjectMocks
    private DetailCtr controller;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    private Recipe recipe1;
    private User user1;
    private org.springframework.security.core.userdetails.User user2;

    //need:
    //recipe with list of favorites(UserRecipeProfiles), ingredient list and instruction list
    //userdetail
    //user with an id
    //recipeRepository.findOne(id);
    //userRepository.findByName(username);

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/html/view/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();



        recipe1 = mock(Recipe.class);
        recipe1.setInstructions(new ArrayList<>());
        recipe1.setIngredientsList(new ArrayList<>());


        when(recipe1.getId()).thenReturn(1L);

        user2 =
                new org.springframework.security.core.userdetails.User( "username", "password", AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN"));


        //the user we find using the username from user2

        //favorite needs a profile that needs a user with an id
        ArrayList<Favorite> recipeFavoriteUsers = new ArrayList<>();
        Favorite fav1 = new Favorite();
        fav1.setRecipe(recipe1);
        recipeFavoriteUsers.add(fav1);
        UserRecipesProfile usp1 = new UserRecipesProfile();
        usp1.setUser(user1);
        fav1.setProfile(usp1);
        ArrayList list = new ArrayList<UserRecipesProfile>();
        list.add(usp1);

        user1 = mock(User.class);
        user1.setName("user1");
        user1.setProfile(usp1);

        when(user1.getId()).thenReturn(1L);

        when(userRepository.findByName(any(String.class))).thenReturn(user1);
        when(recipeRepository.findOne(any(Long.class))).thenReturn(recipe1);
    }

    @Test
    public void detail_LoadsWithCorrectAttributes() throws Exception {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user2);


        // Arrange the mock behavior
        mockMvc.perform(get("/detail/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("ingredients"))
                .andExpect(model().attributeExists("instructions"))
                .andExpect(model().attributeExists("favorite"))
                .andExpect(view().name("detail"));
    }
}
