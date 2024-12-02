package search.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "encounter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Encounter extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    private User patient;

    @ManyToOne
    private User practitioner;
}
