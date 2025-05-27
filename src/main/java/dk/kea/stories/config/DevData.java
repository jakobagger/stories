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
    public void run(ApplicationArguments args) {

        Story story = new Story();
        story.setTitle("Adventure");
        story.setDescription("Choose your path");

        //── ROOT ───────────────────────────────────────────────────────────────────
        Node forest = new Node();
        forest.setTitle("Forest");
        forest.setText("You wake up in a dense forest with two visible paths.");
        forest.setStory(story);
        story.addNode(forest);

        //── FIRST BRANCH ────────────────────────────────────────────────────────────
        // Path A: Eastward path
        Node pathEast = new Node();
        pathEast.setTitle("Eastward Path");
        pathEast.setText("You follow the well-trodden path to the east.");
        pathEast.setStory(story);
        story.addNode(pathEast);

        // Path B: Dark Cave
        Node darkCave = new Node();
        darkCave.setTitle("Dark Cave");
        darkCave.setText("You squeeze through a narrow cave entrance. It’s pitch black inside.");
        darkCave.setStory(story);
        story.addNode(darkCave);

        // Choices from the root
        Choice toEast = new Choice();
        toEast.setText("Take the eastward path.");
        toEast.setFromNode(forest);
        toEast.setToNode(pathEast);
        forest.addOutgoingChoice(toEast);

        Choice toCave = new Choice();
        toCave.setText("Enter the dark cave.");
        toCave.setFromNode(forest);
        toCave.setToNode(darkCave);
        forest.addOutgoingChoice(toCave);

        //── SECOND BRANCH ON EAST PATH ──────────────────────────────────────────────
        // Treasure on the path
        Node treasure = new Node();
        treasure.setTitle("Hidden Treasure");
        treasure.setText("Behind a shrub you uncover a chest of gold!");
        treasure.setStory(story);
        story.addNode(treasure);

        // River along the path
        Node riverbank = new Node();
        riverbank.setTitle("Riverbank");
        riverbank.setText("You come across a flowing river with a rickety bridge.");
        riverbank.setStory(story);
        story.addNode(riverbank);

        Choice eastToTreasure = new Choice();
        eastToTreasure.setText("Search for treasure among the bushes.");
        eastToTreasure.setFromNode(pathEast);
        eastToTreasure.setToNode(treasure);
        pathEast.addOutgoingChoice(eastToTreasure);

        Choice eastToRiver = new Choice();
        eastToRiver.setText("Head toward the sound of running water.");
        eastToRiver.setFromNode(pathEast);
        eastToRiver.setToNode(riverbank);
        pathEast.addOutgoingChoice(eastToRiver);

        //── SECOND BRANCH IN THE CAVE ──────────────────────────────────────────────
        // Bat swarm scene
        Node bats = new Node();
        bats.setTitle("Swarm of Bats");
        bats.setText("Your torchlight awakens a great swarm of bats—do you flee or stand your ground?");
        bats.setStory(story);
        story.addNode(bats);

        // Underground pool
        Node pool = new Node();
        pool.setTitle("Underground Pool");
        pool.setText("Deeper in, you find a serene underground pool.");
        pool.setStory(story);
        story.addNode(pool);

        Choice caveToBats = new Choice();
        caveToBats.setText("Venture deeper, ignoring the squeaks.");
        caveToBats.setFromNode(darkCave);
        caveToBats.setToNode(bats);
        darkCave.addOutgoingChoice(caveToBats);

        Choice caveToPool = new Choice();
        caveToPool.setText("Tiptoe along the left wall hoping for open space.");
        caveToPool.setFromNode(darkCave);
        caveToPool.setToNode(pool);
        darkCave.addOutgoingChoice(caveToPool);

        //── OPTIONAL TERTIARY BRANCH AT THE RIVERBANK ──────────────────────────────
        Node bridge = new Node();
        bridge.setTitle("Rickety Bridge");
        bridge.setText("The bridge looks unsafe, but it’s the only way across.");
        bridge.setStory(story);
        story.addNode(bridge);

        Node ford = new Node();
        ford.setTitle("Shallow Ford");
        ford.setText("You can wade through—if you don’t mind getting your feet wet.");
        ford.setStory(story);
        story.addNode(ford);

        Choice riverToBridge = new Choice();
        riverToBridge.setText("Cross the rickety bridge.");
        riverToBridge.setFromNode(riverbank);
        riverToBridge.setToNode(bridge);
        riverbank.addOutgoingChoice(riverToBridge);

        Choice riverToFord = new Choice();
        riverToFord.setText("Wade through the shallow ford.");
        riverToFord.setFromNode(riverbank);
        riverToFord.setToNode(ford);
        riverbank.addOutgoingChoice(riverToFord);

        //─ SAVE EVERYTHING ─────────────────────────────────────────────────────────
        storyRepository.save(story);
    }
}
