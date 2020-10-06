package by.mitsko.classroom.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode()
@Table(name = "users")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column
    private boolean raisedHand;

    @Column
    private boolean authorized;

    public User(String username) {
        this.username = username;
        this.raisedHand = false;
        this.authorized = true;
    }

    public User(Long id, String username, boolean raisedHand, boolean authorized) {
        this.id = id;
        this.username = username;
        this.raisedHand = raisedHand;
        this.authorized = authorized;
    }

    public User() {
    }
}
