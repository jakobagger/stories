package dk.kea.stories;

import dk.kea.stories.service.ChoiceServiceTest;
import dk.kea.stories.service.NodeServiceTest;
import dk.kea.stories.service.StoryServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({StoryServiceTest.class, NodeServiceTest.class, ChoiceServiceTest.class})
public class JUnitTestSuite {
}
