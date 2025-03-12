package dk.kea.stories.service;

import dk.kea.stories.dto.StoryRequest;
import dk.kea.stories.dto.StoryResponse;
import dk.kea.stories.model.Story;
import dk.kea.stories.repository.StoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StoryService {
    private StoryRepository storyRepository;
    public StoryService(StoryRepository storyRepository){
        this.storyRepository = storyRepository;
    }

    public List<Story> getStories() {
        return storyRepository.findAll();
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
}
