package user;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.teamtreehouse.Application;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DatabaseSetup("classpath:testdb.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class UserDaoTest {
    @Autowired
    private UserRepository dao;

    @Test
    public void findUserTest() throws Exception {
        assertThat(dao.findByName("scotty"),notNullValue());
    }

    @Test
    public void saveUserTest() throws Exception {
        User user2 = new User("Bob",new String[]{"ROLE_USER","ROLE_ADMIN"} , "password");
        dao.save(user2);
        assertThat(dao.findByName("Bob"),notNullValue());
    }

}

