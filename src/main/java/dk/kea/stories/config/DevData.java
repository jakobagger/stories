package dk.kea.stories.config;

import dk.kea.stories.model.Story;
import dk.kea.stories.repository.StoryRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DevData implements ApplicationRunner {

    private StoryRepository storyRepository;

    public DevData(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Story testStory = new Story();
        testStory.setTitle("Test title");
        System.out.println(testStory.getTitle());
        Story testStory2 = new Story();
        testStory2.setTitle("Test title 2");
        System.out.println(testStory2.getTitle());
        List stories = new ArrayList(){};
        stories.add(testStory);
        stories.add(testStory2);
        storyRepository.saveAll(stories);
    }
}
