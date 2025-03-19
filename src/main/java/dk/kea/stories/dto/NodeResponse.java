package dk.kea.stories.dto;

import dk.kea.stories.model.Node;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record NodeResponse(
        int id,
        String name,
        String text,
        List<ChoiceResponse> outgoingChoices
) {
    public static NodeResponse from(Node node, boolean includeChoices) {
        return new NodeResponse(
                node.getId(),
                node.getTitle(),
                node.getText(),
                (includeChoices && node.getOutgoingChoices() != null) ?
                        node.getOutgoingChoices().stream()
                                .map(ChoiceResponse::from)
                                .collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }

}
