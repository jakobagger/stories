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

    private final StoryRepository storyRepository;

    public DevData(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    public void run(ApplicationArguments args){

        Story story = new Story();
        story.setTitle("Simple Tree Adventure");
        story.setDescription("This is a simple tree adventure.");

        Node rootNode = new Node();
        rootNode.setTitle("Forest");
        rootNode.setText("You wake up in a forest.");
        rootNode.setStory(story);
        story.addNode(rootNode);

        Node pathNode = new Node();
        pathNode.setTitle("Path");
        pathNode.setText("You find a path leading east.");
        pathNode.setStory(story);
        story.addNode(pathNode);

        Node treasureNode = new Node();
        treasureNode.setTitle("Treasure");
        treasureNode.setText("You discover a hidden treasure!");
        treasureNode.setStory(story);
        story.addNode(treasureNode);

        Choice choice1 = new Choice();
        choice1.setText("Follow the path.");
        choice1.setFromNode(rootNode);
        choice1.setToNode(pathNode);

        Choice choice2 = new Choice();
        choice2.setText("Search for treasure.");
        choice2.setFromNode(pathNode);
        choice2.setToNode(treasureNode);

        rootNode.addOutgoingChoice(choice1);
        pathNode.addOutgoingChoice(choice2);

        storyRepository.save(story);
    }
}
