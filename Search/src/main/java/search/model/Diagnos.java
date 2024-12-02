package search.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "diagnos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diagnos extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "diagnos_status")
    private DiagnosStatus diagnosStatus;

    @NotNull
    private String name;

    @ManyToOne
    private User patient;

    @ManyToOne
    private User practitioner;
}

