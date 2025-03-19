package dk.kea.stories.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryRequest {
    private String title;
    private String description;
}
