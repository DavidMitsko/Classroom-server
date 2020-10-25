package by.mitsko.classroom.repository;

import by.mitsko.classroom.entity.Action;
import by.mitsko.classroom.entity.Log;
import by.mitsko.classroom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> getAllByUser(User student);

    List<Log> getAllByActionIsInAndUser(List<Action> actions, User user);

    List<Log> getAllByDateAfter(LocalDateTime date);
}
