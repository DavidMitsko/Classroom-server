package by.mitsko.classroom.repository;

import by.mitsko.classroom.entity.Report;
import by.mitsko.classroom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Report getByRecipient(User user);

    void deleteByRecipient(User user);
}
