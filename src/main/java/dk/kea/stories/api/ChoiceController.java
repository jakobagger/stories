package dk.kea.stories.api;

import dk.kea.stories.dto.ChoiceRequest;
import dk.kea.stories.dto.ChoiceResponse;
import dk.kea.stories.service.ChoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/choice")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ChoiceController {
    private final ChoiceService choiceService;
    Logger logger = LoggerFactory.getLogger(ChoiceController.class);

    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @PostMapping
    public ChoiceResponse addChoice(@RequestBody ChoiceRequest body) {
        logger.info("addChoice called");
        return choiceService.addChoice(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChoiceResponse> getChoiceById(@PathVariable int id) {
        ChoiceResponse choice = choiceService.getChoiceById(id);
        if (choice == null){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(choice, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ChoiceResponse updateChoice(@PathVariable int id, @RequestBody ChoiceRequest body) {
        return choiceService.updateChoice(body, id);
    }
}
