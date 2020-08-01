package CST438.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import CST438.domain.User;
import CST438.domain.UserRepository;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public List<User> getAccountInfo(String email) {
    List<User> user = userRepository.findbyEmail(email);
    return user;
  }

  public List<User> getAllUsers() {
    List<User> user = userRepository.findAllUsers();
    return user;
  }

  public void saveIntoDatabase(User user) {
    userRepository.save(user);
  }

}
