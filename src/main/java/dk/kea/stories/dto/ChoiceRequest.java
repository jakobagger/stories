package dk.kea.stories.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoiceRequest {
    private int fromNodeId;
    private int toNodeId;
    private String text;
}
