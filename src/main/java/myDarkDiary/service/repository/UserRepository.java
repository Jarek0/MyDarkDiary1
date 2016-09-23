
package myDarkDiary.service.repository;

import java.util.List;
import myDarkDiary.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(Long id);
    @Override
    List<User> findAll();
}
