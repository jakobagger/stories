package dk.kea.stories.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dk.kea.stories.dto.StoryRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor

@Entity
public class Story extends DateTimeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    @JsonManagedReference("story-nodes")
    private List<Node> nodes;

    public void addNode(Node node) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        nodes.add(node);
    }

    public Story(StoryRequest body) {
        this.title = body.getTitle();
        this.description = body.getDescription();
    }
}
