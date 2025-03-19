package dk.kea.stories.dto;

import dk.kea.stories.model.Story;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record StoryResponse(
        int id,
        String title,
        String description,
        List<NodeResponse> nodes
) {

    public static StoryResponse from(Story story, boolean includeNodes) {
        List<NodeResponse> nodes = includeNodes ?
                story.getNodes().stream()
                        .map(node -> NodeResponse.from(node, true))
                        .collect(Collectors.toList())
                : Collections.emptyList();

        return new StoryResponse(story.getId(), story.getTitle(), story.getDescription(), nodes);
    }
}