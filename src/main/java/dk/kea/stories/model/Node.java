package dk.kea.stories.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    private String title;
    private String text;

    @ManyToOne
    @JoinColumn(name = "story_id")
    @JsonBackReference("story-nodes")
    private Story story;

    @OneToMany(mappedBy = "fromNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("node-choices")
    private List<Choice> outgoingChoices;

    @OneToOne(mappedBy = "toNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("choice-toNode")
    private Choice incomingChoice;

    public void addOutgoingChoice(Choice choice){
        if (outgoingChoices == null){
            outgoingChoices = new ArrayList<>();
        }
        outgoingChoices.add(choice);
    }
}
