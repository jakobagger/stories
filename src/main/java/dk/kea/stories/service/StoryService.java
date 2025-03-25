package dk.kea.stories.service;

import dk.kea.stories.dto.StoryRequest;
import dk.kea.stories.dto.StoryResponse;
import dk.kea.stories.model.Story;
import dk.kea.stories.repository.StoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StoryService {
    private final StoryRepository storyRepository;
    public StoryService(StoryRepository storyRepository){
        this.storyRepository = storyRepository;
    }

    public List<StoryResponse> getStories() {
        return storyRepository.findAll()
                .stream()
                .map(StoryResponse::from)
                .toList();
    }


    public StoryResponse getStoryById(int id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));
        return StoryResponse.from(story);
    }

    public StoryResponse addStory(StoryRequest body) {
        Story newStory = new Story(body);
        storyRepository.save(newStory);
        return StoryResponse.from(newStory);
    }

    public ResponseEntity<String> deleteById(int id) {
        if (!storyRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Story with this ID does not exist");
        }
        storyRepository.deleteById(id);
        return ResponseEntity.ok("{\"message\":\"Story successfully removed from database\"}");
    }

    public StoryResponse editStory(StoryRequest body, int id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));
        story.setTitle(body.getTitle());
        story.setDescription(body.getDescription());
        storyRepository.save(story);
        return StoryResponse.from(story);
    }
}
