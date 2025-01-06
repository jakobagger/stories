package dk.kea.stories.service;

import dk.kea.stories.model.Story;
import dk.kea.stories.repository.StoryRepository;
import org.springframework.stereotype.Service;

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
}
