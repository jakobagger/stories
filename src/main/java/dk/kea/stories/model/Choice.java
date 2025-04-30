package dk.kea.stories.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dk.kea.stories.dto.ChoiceRequest;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

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

    public static Choice from(ChoiceRequest choiceRequest, Node fromNode, Node toNode) {
        Choice choice = new Choice();
        choice.text = choiceRequest.getText();
        choice.fromNode = fromNode;
        choice.fromNode.addOutgoingChoice(choice);
        choice.toNode = toNode;
        return choice;
    }
}
