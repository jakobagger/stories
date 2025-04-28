package dk.kea.stories.service;

import dk.kea.stories.dto.NodeRequest;
import dk.kea.stories.dto.NodeResponse;
import dk.kea.stories.model.Node;
import dk.kea.stories.model.Story;
import dk.kea.stories.repository.NodeRepository;
import dk.kea.stories.repository.StoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NodeServiceTest {

    @Mock
    NodeRepository nodeRepository;

    @Mock
    StoryRepository storyRepository;

    @InjectMocks
    NodeService nodeService;

    private Node nodeOne, nodeTwo;

    private Story storyOne;

    @BeforeEach
    void setUp() {
        storyOne = Story.builder().id(1).title("Title One").description("Description One").build();
        storyOne.setCreated(LocalDateTime.now());
        nodeOne = Node.builder().id(1).title("Title One").text("Text One").story(storyOne).build();
        nodeTwo = Node.builder().id(2).title("Title Two").text("Text Two").story(storyOne).build();
        nodeOne.setCreated(LocalDateTime.now());
        nodeTwo.setCreated(LocalDateTime.now());
    }

    @Test
    void shouldAddNodeWhenStoryExists() {
        NodeRequest nodeRequest = NodeRequest.builder()
                .storyId(1)
                .title("Test Title")
                .text("Node Text")
                .build();

        Node newNode = Node.builder()
                .id(3)
                .story(storyOne)
                .title("Test Title")
                .text("Node Text")
                .build();

        when(storyRepository.findById(1)).thenReturn(Optional.of(storyOne));
        when(nodeRepository.save(any(Node.class))).thenReturn(newNode);

        NodeResponse response = nodeService.addNode(nodeRequest);

        Assertions.assertEquals(newNode.getId(), response.id());
        Assertions.assertEquals(newNode.getTitle(), response.title());
        Assertions.assertEquals(newNode.getText(), response.text());
    }

    @Test
    void shouldThrowNotFoundWhenAddingNodeToNonExistingStory() {
        int nonExistingStoryId = -999;
        when(storyRepository.findById(nonExistingStoryId)).thenReturn(Optional.empty());

        NodeRequest nodeRequest = NodeRequest.builder()
                .storyId(nonExistingStoryId)
                .title("Test Title")
                .text("Node Text")
                .build();

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () ->
                nodeService.addNode(nodeRequest));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

    }

    @Test
    void shouldReturnNodeWhenIdExists() {
        when(nodeRepository.findById(1)).thenReturn(Optional.of(nodeOne));
        NodeResponse response = nodeService.getNodeById(1);
        Assertions.assertEquals(nodeOne.getId(), response.id());
    }

    @Test
    void shouldThrowNotFoundWhenNodeIdDoesNotExist() {
        int nonExistingId = -999;
        when(nodeRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class, () -> nodeService.getNodeById(nonExistingId));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

    }

    @Test
    void shouldUpdateNodeFieldsWhenEditingExistingNode() {}

    @Test
    void shouldThrowNotFoundWhenEditingNonExistingNode() {}

    @Test
    void shouldDeleteNodeWhenIdExists() {}

    @Test
    void shouldThrowNotFoundWhenDeletingNonExistingNode() {}

    @Test
    void shouldReturnAllNodesForExistingStory() {}

    @Test
    void shouldThrowNotFoundWhenGettingNodesForNonExistingStory() {}

}