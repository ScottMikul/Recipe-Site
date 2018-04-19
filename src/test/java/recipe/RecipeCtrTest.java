package recipe;

import com.teamtreehouse.UserRecipes.UserRecipesProfile;
import com.teamtreehouse.ingredient.IngredientRepository;
import com.teamtreehouse.instruction.InstructionRepository;
import com.teamtreehouse.login.LoginCtr;
import com.teamtreehouse.profile.ProfileCtr;
import com.teamtreehouse.recipe.Recipe;
import com.teamtreehouse.recipe.RecipeCtr;
import com.teamtreehouse.recipe.RecipeRepository;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.omg.CORBA.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
@RunWith(MockitoJUnitRunner.class)
public class RecipeCtrTest {
    private MockMvc mockMvc;

    @InjectMocks
    private RecipeCtr controller;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UserRepository repository;

    @Mock
    IngredientRepository ingRepo;

    @Mock
    InstructionRepository instRepo;

    private User user1;
    private org.springframework.security.core.userdetails.User user2;
    private Recipe recipe1;
    private UserRecipesProfile urp;


    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/html/view/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();

        user2 = new org.springframework.security.core.userdetails.User( "username", "password", AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN"));

        recipe1 = mock(Recipe.class);
        recipe1.setInstructions(new ArrayList<>());
        recipe1.setIngredientsList(new ArrayList<>());


        user1 = spy(User.class);
        user1.setName("user1");

        urp = spy(UserRecipesProfile.class);
        ArrayList<Recipe> list = new ArrayList();
        list.add(recipe1);
        urp.setRecipeList(list);
        user1.setProfile(urp);


        when(user1.getId()).thenReturn(1L);

        when(repository.findByName(any(String.class))).thenReturn(user1);
        when(recipeRepository.findOne(any(Long.class))).thenReturn(recipe1);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user2);


    }

    @Test
    public void correctViewRenders() throws Exception {
        when(urp.ownsRecipe(recipe1)).thenReturn(true);

        mockMvc.perform(get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    public void doesNotOwnRecipe() throws Exception {
        when(urp.ownsRecipe(recipe1)).thenReturn(false);


        mockMvc.perform(get("/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

    @Test
    public void userMustOwnRecipeToDelete() throws Exception{
        when(urp.ownsRecipe(recipe1)).thenReturn(true);

        mockMvc.perform(get("/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));



    }

    @Test
    public void addRecipeRedirectsToEditRecipe() throws Exception{
        mockMvc.perform(get("/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Matchers.startsWith("redirect:/edit/")));
    }

}
