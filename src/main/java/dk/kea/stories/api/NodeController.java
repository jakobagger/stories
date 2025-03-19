package dk.kea.stories.api;

import dk.kea.stories.dto.NodeRequest;
import dk.kea.stories.dto.NodeResponse;
import dk.kea.stories.service.NodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/node")
@CrossOrigin
public class NodeController {
    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping("/{id}")
    public NodeResponse getNode(@PathVariable int id) {
        return nodeService.getNodeById(id);
    }

    @PostMapping
    public ResponseEntity<NodeResponse> createNode(@RequestBody NodeRequest body) {
        return nodeService.addNode(body);
    }

    @PutMapping("/{id}")
    public NodeResponse updateNode(@PathVariable int id, @RequestBody NodeRequest body) {
        return nodeService.editNode(body, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNode(@PathVariable int id) {
        return nodeService.deleteById(id);
    }

}
