package favorite;


import com.teamtreehouse.Detail.DetailCtr;
import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.favorite.Favorite;
import com.teamtreehouse.favorite.FavoriteCtr;
import com.teamtreehouse.favorite.FavoriteRepository;
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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class FavoriteCtrTest {
    private MockMvc mockMvc;

    @InjectMocks
    private FavoriteCtr controller;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    private Recipe recipe1;
    private User user1;
    private org.springframework.security.core.userdetails.User user2;

    //need:
    //userdetail
    //user with an id
    //userRepository.findByName(username);
    //recipeRepository.findOne(id);
    //favoriteRepository.delete(favoritedId);
    //favoriteRepository.save(favorite);


    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/html/view/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();



        recipe1 = spy(Recipe.class);
        recipe1.setInstructions(new ArrayList<>());
        recipe1.setIngredientsList(new ArrayList<>());


        when(recipe1.getId()).thenReturn(1L);

        user2 =
                new org.springframework.security.core.userdetails.User( "username", "password", AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN"));


        //user stuff
        user1 = spy(User.class);
        user1.setName("user1");

        UserRecipesProfile urp = new UserRecipesProfile();
        Favorite favorite = spy(Favorite.class);
        favorite.setRecipe(recipe1);
        favorite.setProfile(urp);
        when(favorite.getId()).thenReturn(1L);

        ArrayList<Favorite> favs = new ArrayList<>();
        favs.add(favorite);
        urp.setFavoriteRecipesList(favs);

        user1.setProfile(urp);

        when(userRepository.findByName(any(String.class))).thenReturn(user1);
        when(user1.getId()).thenReturn(1L);

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
        mockMvc.perform(get("/favorite/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/detail/1"))
                .andExpect(view().name("redirect:/detail/1"));

     }
}

