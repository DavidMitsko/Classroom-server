package by.mitsko.classroom.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode()
@Table(name = "reports")
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Column(name = "frequency", nullable = false)
    private Frequency frequency;

    public Report(User recipient, Frequency frequency) {
        this.recipient = recipient;
        this.frequency = frequency;
    }

    public Report() {
    }
}
