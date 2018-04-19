package profile;

import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.favorite.Favorite;
import com.teamtreehouse.index.IndexCtr;
import com.teamtreehouse.login.LoginCtr;
import com.teamtreehouse.profile.ProfileCtr;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.recipe.RecipeRepository;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class ProfileCtrTest {
    private MockMvc mockMvc;

    @InjectMocks
    private ProfileCtr controller;


    @Mock
    private RecipeRepository repository;

    @Mock
    private UserRepository userRepository;

    private Recipe recipe1;
    private User user1;

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

        User user2 = new User("scotty",new String[]{"ROLE_USER"} , "password");
        Authentication auth = new UsernamePasswordAuthenticationToken(user2.getName(),user2.getPassword(), AuthorityUtils.createAuthorityList(user2.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        recipe1 = mock(Recipe.class);
        recipe1.setInstructions(new ArrayList<>());
        recipe1.setIngredientsList(new ArrayList<>());

        //userrecipe profile needs a favorite recipe list and a list of owned things

        user1 = spy(User.class);

        UserRecipesProfile usp1 = new UserRecipesProfile();

        ArrayList<Favorite> recipeFavoriteUsers = new ArrayList<>();
        Favorite fav1 = new Favorite();
        fav1.setRecipe(recipe1);
        fav1.setProfile(usp1);
        recipeFavoriteUsers.add(fav1);
        usp1.setFavoriteRecipesList(recipeFavoriteUsers);

        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        usp1.setRecipeList(recipes);

        usp1.setUser(user1);
        user1.setProfile(usp1);

        when(userRepository.findByName(any(String.class))).thenReturn(user1);
        when(recipe1.getId()).thenReturn(1L);


    }

    @Test
    public void correctViewRenders() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }

}