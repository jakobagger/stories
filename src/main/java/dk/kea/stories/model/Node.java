package dk.kea.stories.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Node extends DateTimeInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String text;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    @OneToMany(mappedBy = "fromNode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Choice> outgoingChoices;

    @OneToOne(mappedBy = "toNode", cascade = CascadeType.ALL, orphanRemoval = true)
    private Choice incomingChoice;

    public void addOutgoingChoice(Choice choice){
        if (outgoingChoices == null){
            outgoingChoices = new ArrayList<>();
        }
        outgoingChoices.add(choice);
    }
}
