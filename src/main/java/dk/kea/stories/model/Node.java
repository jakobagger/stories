package dk.kea.stories.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dk.kea.stories.dto.NodeRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Node extends DateTimeInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
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

    public static Node from(NodeRequest nodeRequest, Story story){
        Node newNode = new Node();

        newNode.title = nodeRequest.getTitle();
        newNode.text = nodeRequest.getText();
        newNode.story = story;
        newNode.story.addNode(newNode);
        return newNode;
    }
}
