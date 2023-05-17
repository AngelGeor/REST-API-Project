import org.example.GitHubUser;
import org.junit.Assert;
import org.junit.Test;

public class GitHubUserTest {
    private GitHubUser user;

    @Test
    public void testSetAndGetId() {
        user = new GitHubUser();
        user.setId(123);
        Assert.assertEquals(123, user.getId());
    }

    @Test
    public void testSetAndGetName() {
        user = new GitHubUser();
        user.setName("Ivan Petrov");
        Assert.assertEquals("Ivan Petrov", user.getName());
    }

    @Test
    public void testSetAndGetEmail() {
        user = new GitHubUser();
        user.setEmail("ivan@gmail.com");
        Assert.assertEquals("ivan@gmail.com", user.getEmail());
    }


    @Test
    public void testToFreshDesk() throws Exception {
        user = new GitHubUser();
        user.setId(123453);
        user.setName("Ivan Petrov");
        user.setEmail("ivan@gmail.com");

        String expected = "{\"name\":\"Ivan Petrov\",\"unique_external_id\":\"123453\",\"email\":\"ivan@gmail.com\"}";

        String actual = user.toFreshDesk();
        Assert.assertEquals(expected, actual);
    }
}
