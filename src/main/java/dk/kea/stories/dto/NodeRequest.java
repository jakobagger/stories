package dk.kea.stories.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeRequest {
    private int storyId;
    private String title;
    private String text;
}
