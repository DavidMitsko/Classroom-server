package by.mitsko.classroom.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@EqualsAndHashCode()
@Table(name = "log")
@Entity
@Data
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "action", nullable = false)
    private Action action;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private User user;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public Log(Action action, User user) {
        this.action = action;
        this.user = user;
        this.date = LocalDateTime.now(ZoneId.of("UTC+3"));
    }

    public Log() {
    }
}
