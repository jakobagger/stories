package dk.kea.stories.dto;

import dk.kea.stories.model.Story;

import java.util.List;
import java.util.stream.Collectors;

public record StoryResponse(
        int id,
        String title,
        String content,
        List<NodeResponse> nodes
) {
    // Static factory method to convert from entity
    public static StoryResponse from(Story story) {
        return new StoryResponse(
                story.getId(),
                story.getTitle(),
                story.getDescription(),
                story.getNodes().stream()
                        .map(NodeResponse::from)
                        .collect(Collectors.toList())
        );
    }
}