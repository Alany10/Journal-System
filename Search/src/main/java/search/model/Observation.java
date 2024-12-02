package search.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "observation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Observation extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String description;

    @ManyToOne
    private Diagnos diagnos;

    @ManyToOne
    private Encounter encounter;

    @ManyToOne
    private User patient;

    @ManyToOne
    private User practitioner;
}
