package dk.kea.stories.service;

import dk.kea.stories.dto.ChoiceRequest;
import dk.kea.stories.dto.ChoiceResponse;
import dk.kea.stories.model.Choice;
import dk.kea.stories.model.Node;
import dk.kea.stories.repository.ChoiceRepository;
import dk.kea.stories.repository.NodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChoiceServiceTest {

    @Mock
    private ChoiceRepository choiceRepository;

    @Mock
    private NodeRepository nodeRepository;

    @InjectMocks
    private ChoiceService choiceService;

    private Node nodeOne, nodeTwo;
    private Choice choiceOne;


    @BeforeEach
    void setUp() {
        nodeOne = Node.builder().id(1).title("Title One").text("Text One").build();
        nodeTwo = Node.builder().id(2).title("Title Two").text("Text Two").build();
        choiceOne = Choice.builder().id(1).text("Text One").build();
    }

    @Test
    void shouldReturnChoiceWhenIdExists() {
        int existingId = 1;
        choiceOne.setFromNode(nodeOne);
        choiceOne.setToNode(nodeTwo);
        when(choiceRepository.findById(existingId)).thenReturn(Optional.of(choiceOne));

        ChoiceResponse response = choiceService.getChoiceById(existingId);

        Assertions.assertEquals(choiceOne.getId(), response.id());
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        int nonExistingId = -999;
        when(choiceRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            choiceService.getChoiceById(nonExistingId);
        });
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void shouldAddChoiceWhenNodesExist() {
        int newChoiceId = 3;
        int fromNodeId = 1;
        int toNodeId = 2;
        ChoiceRequest request = ChoiceRequest.builder()
                .fromNodeId(fromNodeId)
                .toNodeId(toNodeId)
                .text("Text Three")
                .build();

        Choice newChoice = Choice.builder()
                .id(newChoiceId)
                .text("Text Three")
                .fromNode(nodeOne)
                .toNode(nodeTwo)
                .build();

        when(nodeRepository.findById(fromNodeId)).thenReturn(Optional.of(nodeOne));
        when(nodeRepository.findById(toNodeId)).thenReturn(Optional.of(nodeTwo));
        when(choiceRepository.save(any(Choice.class))).thenReturn(newChoice);

        ChoiceResponse response = choiceService.addChoice(request);

        Assertions.assertEquals(newChoiceId, response.id());
        Assertions.assertEquals(toNodeId, response.toNodeId());
        Assertions.assertEquals("Text Three", response.text());
    }
}