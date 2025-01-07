package dk.kea.stories.dto;

import dk.kea.stories.model.Choice;

public record ChoiceResponse(
        int id,
        String text,
        int toNodeId  // Just store the ID instead of the full node
) {
    public static ChoiceResponse from(Choice choice) {
        return new ChoiceResponse(
                choice.getId(),
                choice.getText(),
                choice.getToNode().getId()
        );
    }
}