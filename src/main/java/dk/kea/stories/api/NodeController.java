package dk.kea.stories.api;

import dk.kea.stories.dto.ChoiceResponse;
import dk.kea.stories.dto.NodeRequest;
import dk.kea.stories.dto.NodeResponse;
import dk.kea.stories.service.ChoiceService;
import dk.kea.stories.service.NodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/node")
@CrossOrigin
public class NodeController {
    private final NodeService nodeService;
    private final ChoiceService choiceService;

    public NodeController(NodeService nodeService, ChoiceService choiceService) {
        this.nodeService = nodeService;
        this.choiceService = choiceService;
    }

    @GetMapping("/{id}")
    public NodeResponse getNode(@PathVariable int id) {
        return nodeService.getNodeById(id);
    }

    @PostMapping
    public ResponseEntity<NodeResponse> createNode(@RequestBody NodeRequest body) {
         NodeResponse response = nodeService.addNode(body);
         return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public NodeResponse updateNode(@PathVariable int id, @RequestBody NodeRequest body) {
        return nodeService.editNode(body, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNode(@PathVariable int id) {
        return nodeService.deleteById(id);
    }

    @GetMapping("{id}/choices")
    public List<ChoiceResponse> getNodeChoices(@PathVariable int id) {
        return choiceService.getNodeChoices(id);
    }
}
