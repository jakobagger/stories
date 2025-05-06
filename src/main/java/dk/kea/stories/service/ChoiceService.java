package dk.kea.stories.service;

import dk.kea.stories.dto.ChoiceRequest;
import dk.kea.stories.dto.ChoiceResponse;
import dk.kea.stories.model.Choice;
import dk.kea.stories.model.Node;
import dk.kea.stories.repository.ChoiceRepository;
import dk.kea.stories.repository.NodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * ChoiceService handles business logic related to Choice objects
 *
 * @author Jakob Agger
 *
 */

@Service
public class ChoiceService {
    /**
     * The ChoiceRepository used by this service
     */
    private final ChoiceRepository choiceRepository;
    private final NodeRepository nodeRepository;
    Logger logger = LoggerFactory.getLogger(ChoiceService.class);

    /**
     * <p>ChoiceService Constructor
     * </p>
     * @param choiceRepository used to represent the choices of a user
     * @param nodeRepository used to represent the nodes of a story
     *
     * @since 1.0
     */
    public ChoiceService(ChoiceRepository choiceRepository, NodeRepository nodeRepository) {
        this.choiceRepository = choiceRepository;
        this.nodeRepository = nodeRepository;
    }


    /**
     * @param id, the id  of the choice
     * @return ChoiceResponse
     * @since 1.0
     */
    public ChoiceResponse getChoiceById(int id) {
        Choice choice = choiceRepository.findById(id).orElse(null);
                if (choice == null){
                    ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND);
                    logger.error(responseStatusException.getMessage(), responseStatusException);
                    throw responseStatusException;
                }
        return ChoiceResponse.from(choice);
    }

    public ChoiceResponse addChoice(ChoiceRequest body) {
        Node fromNode = nodeRepository.findById(body.getFromNodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No node with this id found, can not add choice"));
        Node toNode = nodeRepository.findById(body.getToNodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No node with this id found, can not add choice"));
        Choice newChoice = Choice.from(body, fromNode, toNode);
        newChoice = choiceRepository.save(newChoice);
        return ChoiceResponse.from(newChoice);
    }

    public ChoiceResponse updateChoice(ChoiceRequest body, int id) {
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Choice not found, can not update choice"));
        Node fromNode = nodeRepository.findById(body.getFromNodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No node with this id found, can not update choice because of missing from-node"));
        Node toNode = nodeRepository.findById(body.getToNodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No node with this id found, can not update choice because of missing to-node"));
        choice.setText(body.getText());
        choice.setFromNode(fromNode);
        choice.setToNode(toNode);
        Choice savedChoice = choiceRepository.save(choice);
        return ChoiceResponse.from(savedChoice);
    }

    public List<ChoiceResponse> getNodeChoices(int id) {
        List<Choice> choices = choiceRepository.findAllByFromNodeId(id);
        return choices.stream()
                .map(ChoiceResponse::from)
                .toList();
    }
}
