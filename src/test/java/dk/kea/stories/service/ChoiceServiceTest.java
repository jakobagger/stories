package dk.kea.stories.service;

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

import java.util.Optional;

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
}