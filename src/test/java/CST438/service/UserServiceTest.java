package CST438.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import CST438.domain.User;

@SpringBootTest
public class UserServiceTest {

  @MockBean
  private UserService userService;

  @Test
  public void validGetAccountInfo() {
    String testEmail = "test@email.test";
    User testUser = new User(1, testEmail, "creation_date", "last_name", "first_name");

    given(userService.getAccountInfo(testEmail)).willReturn(testUser);

    User userResult = userService.getAccountInfo(testEmail);

    User expectedUserResult =
        new User(1, "test@email.test", "creation_date", "last_name", "first_name");

    assertThat(userResult.getId()).isEqualTo(expectedUserResult.getId());
    assertThat(userResult.getEmail()).isEqualTo(expectedUserResult.getEmail());
    assertThat(userResult.getCreate_date()).isEqualTo(expectedUserResult.getCreate_date());
    assertThat(userResult.getLast_name()).isEqualTo(expectedUserResult.getLast_name());
    assertThat(userResult.getFirst_name()).isEqualTo(expectedUserResult.getFirst_name());

  }

  @Test
  public void inValidGetAccountInfo() {
    String testEmail = "test@email.test";
    User testUser = new User(1, testEmail, "creation_date", "last_name", "first_name");
    given(userService.getAccountInfo(testEmail)).willReturn(testUser);

    User userResult = userService.getAccountInfo("different@email.test");
    User expectedResult = null;

    assertThat(userResult).isEqualTo(expectedResult);
  }

  @Test
  public void validGetAllUsers() {
    List<User> userList = new ArrayList<>();

    User testUser1 = new User(1, "test1@email.test", "creation_date", "last_name", "first_name");
    userList.add(testUser1);

    User testUser2 = new User(2, "test2@email.test", "creation_date", "last_name", "first_name");
    userList.add(testUser2);

    User testUser3 = new User(3, "test3@email.test", "creation_date", "last_name", "first_name");
    userList.add(testUser3);

    User testUser4 = new User(4, "test4@email.test", "creation_date", "last_name", "first_name");
    userList.add(testUser4);

    // Since the result of the UserList should be the same as the expected result,
    // there is no need to generate a new list with the same test data.
    given(userService.getAllUsers()).willReturn(userList);

    List<User> userListResult = userService.getAllUsers();

    assertThat(userListResult).isEqualTo(userList);
  }

  @Test
  public void inValidGetAllUsers() {
    List<User> userList = new ArrayList<>();

    User testUser1 = new User(1, "test1@email.test", "creation_date", "last_name", "first_name");
    userList.add(testUser1);

    User testUser2 = new User(2, "test2@email.test", "creation_date", "last_name", "first_name");
    userList.add(testUser2);

    User testUser3 = new User(3, "test3@email.test", "creation_date", "last_name", "first_name");
    userList.add(testUser3);

    User testUser4 = new User(4, "test4@email.test", "creation_date", "last_name", "first_name");
    userList.add(testUser4);

    List<User> outOfDateUserList = new ArrayList<User>();

    User diffTestUser1 =
        new User(1, "test1@email.test", "creation_date", "last_name", "first_name");
    outOfDateUserList.add(diffTestUser1);

    User diffTestUser2 =
        new User(2, "test2@email.test", "creation_date", "last_name", "first_name");
    outOfDateUserList.add(diffTestUser2);

    List<User> emptyUserList = new ArrayList<User>();

    // Service call given mock data
    given(userService.getAllUsers()).willReturn(userList);
    List<User> userListResult = userService.getAllUsers();

    given(userService.getAllUsers()).willReturn(outOfDateUserList);
    List<User> expectedOutOfDateUserList = userService.getAllUsers();

    given(userService.getAllUsers()).willReturn(emptyUserList);
    List<User> expectedEmptyUserList = userService.getAllUsers();

    // check if a list is not the same as the out of date results
    assertThat(userListResult).isNotEqualTo(expectedOutOfDateUserList);

    // Checking an empty list, and if so if they are not equal.
    assertThat(expectedEmptyUserList).isEmpty();
    assertThat(userListResult).isNotEqualTo(expectedEmptyUserList);

  }

  @Test
  public void validSaveIntoDatabase() {
    System.out.println("validSave test");
    User testUser = new User(1, "test@email.test", "creation_date", "last_name", "first_name");

    // Boolean result = true;

    given(userService.saveIntoDatabase(testUser)).willReturn(true);
    // when(userService.saveIntoDatabase(testUser)).thenReturn(true);
    Boolean result = userService.saveIntoDatabase(testUser);
    System.out.println("Valid result[ " + result + "]");
    Boolean expectedResult = true;
    System.out.println("Valid expectedResult[" + expectedResult + "]");


    assertThat(result).isEqualTo(expectedResult);

  }

  @Test
  public void inValidSaveIntoDatabase() {
    System.out.println("inValidSave test");

    User testUser = new User();
    given(userService.saveIntoDatabase(testUser)).willReturn(false);

    Boolean result = userService.saveIntoDatabase(testUser);
    System.out.println("Invalid result[ " + result + "]");
    Boolean expectedResult = false;
    System.out.println("Invalid expectedResult[" + expectedResult + "]");

    assertThat(result).isEqualTo(expectedResult);

  }



}
