package dk.kea.stories.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Choice extends DateTimeInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

    @OneToOne
    @JoinColumn(name = "to_node_id", nullable = false, unique = true)
    private Node toNode;

    @ManyToOne
    @JoinColumn(name = "from_node_id", nullable = false)
    private Node fromNode;
}
