package dk.kea.stories.api;

import dk.kea.stories.dto.ChoiceRequest;
import dk.kea.stories.dto.ChoiceResponse;
import dk.kea.stories.service.ChoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/choice")
@CrossOrigin
public class ChoiceController {
    private final ChoiceService choiceService;

    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @PostMapping
    public ResponseEntity<ChoiceResponse> addChoice(@RequestBody ChoiceRequest body) {
        return choiceService.addChoice(body);
    }

    @GetMapping("/{id}")
    public ChoiceResponse getChoiceById(@PathVariable int id) {
        return choiceService.getChoiceById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChoiceResponse> updateChoice(@PathVariable int id, @RequestBody ChoiceRequest body) {
        return choiceService.updateChoice(body, id);
    }
}
