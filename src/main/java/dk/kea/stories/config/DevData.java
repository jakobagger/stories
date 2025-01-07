package dk.kea.stories.config;

import dk.kea.stories.model.Choice;
import dk.kea.stories.model.Node;
import dk.kea.stories.model.Story;
import dk.kea.stories.repository.StoryRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevData implements ApplicationRunner {

    private StoryRepository storyRepository;

    public DevData(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Create a story
        Story story = new Story();
        story.setTitle("Simple Tree Adventure");

// Create nodes
        Node rootNode = new Node();
        rootNode.setStory(story);
        rootNode.setText("You wake up in a forest.");
        story.addNode(rootNode);

        Node pathNode = new Node();
        pathNode.setStory(story);
        pathNode.setText("You find a path leading east.");
        story.addNode(pathNode);

        Node treasureNode = new Node();
        treasureNode.setStory(story);
        treasureNode.setText("You discover a hidden treasure!");
        story.addNode(treasureNode);

// Create choices
        Choice choice1 = new Choice();
        choice1.setFromNode(rootNode);
        choice1.setToNode(pathNode);
        choice1.setText("Follow the path.");

        Choice choice2 = new Choice();
        choice2.setFromNode(pathNode);
        choice2.setToNode(treasureNode);
        choice2.setText("Search for treasure.");

// Add relationships
        rootNode.addOutgoingChoice(choice1);
        pathNode.setIncomingChoice(choice1);
        pathNode.addOutgoingChoice(choice2);
        treasureNode.setIncomingChoice(choice2);

        storyRepository.save(story);

    }
}
