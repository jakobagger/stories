package dk.kea.stories.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dk.kea.stories.model.Node;
import dk.kea.stories.model.Story;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StoryResponse(
        int id,
        String title,
        String description,
        Integer startNodeId,
        int numberOfNodes
) {

    public static StoryResponse from(Story story) {
        List<Node> nodes = story.getNodes();

        if (nodes.isEmpty()) {
            return new StoryResponse(
                    story.getId(),
                    story.getTitle(),
                    story.getDescription(),
                    null,
                    0
            );
        }

        Node startNode = nodes.stream()
                .filter(node -> node.getIncomingChoice() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No start node found"));

        return new StoryResponse(
                story.getId(),
                story.getTitle(),
                story.getDescription(),
                startNode.getId(),
                nodes.size()
        );
    }

}