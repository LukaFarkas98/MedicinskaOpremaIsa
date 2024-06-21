package LukaFarkas.MedOpremaBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PenalPoints")
public class PenalPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int points;
    private LocalDateTime timestamp;

    public PenalPoint(User user, int points, LocalDateTime timestamp) {
        this.user = user;
        this.points = points;
        this.timestamp = timestamp;
    }
}
