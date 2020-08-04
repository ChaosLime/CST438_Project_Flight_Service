package CST438.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import CST438.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
  User findbyEmail(String email);


  @Query("SELECT user FROM User user")
  List<User> findAllUsers();



}
