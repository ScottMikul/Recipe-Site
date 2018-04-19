package index;

import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.favorite.Favorite;
import com.teamtreehouse.index.IndexCtr;
import com.teamtreehouse.login.LoginCtr;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.recipe.RecipeRepository;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class IndexCtrTest {
    private MockMvc mockMvc;

    @InjectMocks
    private IndexCtr controller;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    private Recipe recipe1;
    private Recipe recipe2;


    private User user1;
    private org.springframework.security.core.userdetails.User user2;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/html/view/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();



        recipe1 = mock(Recipe.class);
        recipe1.setName("R1name");

        recipe2 = mock(Recipe.class);
        recipe2.setName("R2name");

        when(recipe1.getId()).thenReturn(1L);
        when(recipe2.getId()).thenReturn(2L);

        user1 = new User("user1",new String[]{"ROLE_USER"} , "password");

        user2 =
                new org.springframework.security.core.userdetails.User( "username", "password", AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN"));
        UserRecipesProfile usp1 = new UserRecipesProfile();
        ArrayList<Favorite> favorites = new ArrayList<>();
        Favorite fav1 = new Favorite();
        fav1.setRecipe(recipe2);
        favorites.add(fav1);
        usp1.setFavoriteRecipesList(favorites);
        usp1.setRecipeList(new ArrayList<>());
        user1.setProfile(usp1);
    }

    @Test
    public void index_pageLoadsWithCorrectAttributes() throws Exception {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user2);


        // Arrange the mock behavior
        //recipeRepository.findAll();
        ArrayList<Recipe> recipes = new ArrayList<>(Arrays.asList(
                recipe1,
                recipe2
        ));

        when(recipeRepository.findAll()).thenReturn(recipes);

        // User user = mUserRepository.findByName(username);
        when(userRepository.findByName(any(String.class))).thenReturn(user1);
        // List<Favorite> favorites = user.getProfile().getFavoriteRecipesList();




        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("searchform"))
                .andExpect(model().attributeExists("allRecipes"))
                .andExpect(view().name("index"));
    }

}
