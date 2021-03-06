package by.mitsko.classroom.repository;

import by.mitsko.classroom.entity.Role;
import by.mitsko.classroom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUsername(String username);

    User getById(Long id);

    List<User> getAllByRoleAndAuthorizedIsTrueOrderByIdAsc(Role role);

    List<User> getAllByRole(Role role);
}
