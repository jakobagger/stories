package dk.kea.stories.service;

import dk.kea.stories.dto.NodeRequest;
import dk.kea.stories.dto.NodeResponse;
import dk.kea.stories.model.Node;
import dk.kea.stories.model.Story;
import dk.kea.stories.repository.NodeRepository;
import dk.kea.stories.repository.StoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NodeService {
    private final NodeRepository nodeRepository;
    private final StoryRepository storyRepository;
    public NodeService(NodeRepository nodeRepository, StoryRepository storyRepository) {
        this.nodeRepository = nodeRepository;
        this.storyRepository = storyRepository;
    }

    public NodeResponse addNode(NodeRequest body) {
        Story story = storyRepository.findById(body.getStoryId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No story with this id found, can not add node"));
        Node newNode =  Node.from(body, story);
        newNode = nodeRepository.save(newNode);
        return NodeResponse.from(newNode, true);
    }

    public NodeResponse getNodeById(int id) {
        Node node = nodeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found"));
        return NodeResponse.from(node, true);
    }

    public NodeResponse editNode(NodeRequest body, int id) {
        Node node = nodeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found"));
        node.setTitle(body.getTitle());
        node.setText(body.getText());
        nodeRepository.save(node);
        return NodeResponse.from(node, true);
    }

    public ResponseEntity<String> deleteById(int id) {
        if (!nodeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found");
        }
        nodeRepository.deleteById(id);
        return ResponseEntity.ok("{\"message\":\"Node successfully removed from database\"}");
    }

    public List<NodeResponse> getStoryNodes(int id) {
        if (!storyRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Story with ${id} not found");
        }
        return nodeRepository.findAllByStoryId(id)
                .stream()
                .map(node -> NodeResponse.from(node, true))
                .toList();
    }
}
