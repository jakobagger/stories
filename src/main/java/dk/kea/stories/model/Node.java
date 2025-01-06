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
    private String description;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    @OneToMany(mappedBy = "fromNode", cascade = CascadeType.ALL)
    private List<Choice> choices;

    @OneToOne(mappedBy = "toNode")
    private Node toNode;

    public void addChoice(Choice choice){
        if (choices == null){
            choices = new ArrayList<>();
        }
        choices.add(choice);
    }
}
