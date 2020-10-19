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

    @Column
    private String email;

    @Column
    private Role role;

    public User(String username, Role role) {
        this.username = username;
        this.raisedHand = false;
        this.authorized = true;
        this.role = role;
    }

    public User(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.raisedHand = false;
        this.authorized = true;
    }

    public User(Long id, String username, boolean raisedHand, boolean authorized, Role role) {
        this.id = id;
        this.username = username;
        this.raisedHand = raisedHand;
        this.authorized = authorized;
        this.role = role;
    }

    public User() {
    }
}
