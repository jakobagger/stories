package dk.kea.stories.service;

import dk.kea.stories.dto.ChoiceRequest;
import dk.kea.stories.dto.ChoiceResponse;
import dk.kea.stories.model.Choice;
import dk.kea.stories.model.Node;
import dk.kea.stories.repository.ChoiceRepository;
import dk.kea.stories.repository.NodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ChoiceService {
    private final ChoiceRepository choiceRepository;
    private final NodeRepository nodeRepository;

    public ChoiceService(ChoiceRepository choiceRepository, NodeRepository nodeRepository) {
        this.choiceRepository = choiceRepository;
        this.nodeRepository = nodeRepository;
    }


    public ChoiceResponse getChoiceById(int id) {
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Choice not found"));
        return ChoiceResponse.from(choice);
    }

    public ResponseEntity<ChoiceResponse> addChoice(ChoiceRequest body) {
        Node fromNode = nodeRepository.findById(body.getFromNodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No node with this id found, can not add choice"));
        Node toNode = nodeRepository.findById(body.getToNodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No node with this id found, can not add choice"));
        Choice newChoice = Choice.from(body, fromNode, toNode);
        choiceRepository.save(newChoice);
        ChoiceResponse choiceResponse = ChoiceResponse.from(newChoice);
        return new ResponseEntity<>(choiceResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<ChoiceResponse> updateChoice(ChoiceRequest body, int id) {
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Choice not found, can not update choice"));
        Node fromNode = nodeRepository.findById(body.getFromNodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No node with this id found, can not update choice because of missing from-node"));
        Node toNode = nodeRepository.findById(body.getToNodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No node with this id found, can not update choice because of missing to-node"));
        choice.setText(body.getText());
        choice.setFromNode(fromNode);
        choice.setToNode(toNode);
        choiceRepository.save(choice);
        ChoiceResponse choiceResponse = ChoiceResponse.from(choice);
        return new ResponseEntity<>(choiceResponse, HttpStatus.OK);
    }
}
