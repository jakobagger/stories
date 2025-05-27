package dk.kea.stories.api;

import dk.kea.stories.dto.NodeResponse;
import dk.kea.stories.dto.StoryRequest;
import dk.kea.stories.dto.StoryResponse;
import dk.kea.stories.service.NodeService;
import dk.kea.stories.service.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/story")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class StoryController {

    private final StoryService storyService;
    private final NodeService nodeService;

    public StoryController(StoryService storyService, NodeService nodeService) {
        this.storyService = storyService;
        this.nodeService = nodeService;
    }

    @GetMapping()
    public List<StoryResponse> getStories() {
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

    @PutMapping("/{id}")
    public StoryResponse updateStory (@RequestBody StoryRequest body, @PathVariable int id){
        return storyService.editStory(body, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStory(@PathVariable int id){
        return storyService.deleteById(id);
    }

    @GetMapping("/{id}/nodes")
    public List<NodeResponse> getStoryNodes(@PathVariable int id){
        return nodeService.getStoryNodes(id);
    }
}

