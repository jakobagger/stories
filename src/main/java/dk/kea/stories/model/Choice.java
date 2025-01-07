package dk.kea.stories.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @JsonManagedReference("choice-toNode")
    private Node toNode;

    @ManyToOne
    @JoinColumn(name = "from_node_id", nullable = false)
    @JsonBackReference("node-choices")
    private Node fromNode;
}
