package dk.kea.stories.api;

import dk.kea.stories.dto.StoryRequest;
import dk.kea.stories.dto.StoryResponse;
import dk.kea.stories.model.Story;
import dk.kea.stories.service.StoryService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public StoryResponse getStoryById(@PathVariable int id){
        return storyService.getStoryById(id);
    }

    @PostMapping
    public StoryResponse addStory (@RequestBody StoryRequest body){
        return storyService.addStory(body);
    }
}

