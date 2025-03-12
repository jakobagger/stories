package dk.kea.stories.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryRequest {
    private int id;
    private String title;
    private String description;
}
