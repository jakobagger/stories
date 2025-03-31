package dk.kea.stories.dto;

import dk.kea.stories.model.Node;

import java.util.Collections;
import java.util.List;

public record NodeResponse(
        int id,
        String title,
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
                                .toList()
                        : Collections.emptyList()
        );
    }

}
