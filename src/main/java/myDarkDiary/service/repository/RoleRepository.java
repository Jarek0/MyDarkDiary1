
package myDarkDiary.service.repository;

import myDarkDiary.service.model.Role;
import myDarkDiary.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String rolename);
    
}
