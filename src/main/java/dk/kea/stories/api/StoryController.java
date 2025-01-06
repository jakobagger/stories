package dk.kea.stories.api;

import dk.kea.stories.model.Story;
import dk.kea.stories.service.StoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/story")
@CrossOrigin
public class StoryController {

    private StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping()
    public List<Story> getStories() {
        return storyService.getStories();
    }
}