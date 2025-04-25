package dk.kea.stories.service;

import dk.kea.stories.dto.StoryRequest;
import dk.kea.stories.dto.StoryResponse;
import dk.kea.stories.model.Story;
import dk.kea.stories.repository.StoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoryServiceTest {

    @Mock
    StoryRepository storyRepository;

    @InjectMocks
    StoryService storyService;

    private Story storyOne, storyTwo;

    @BeforeEach
    void setUp() {
        storyOne = Story.builder().id(1).title("Title One").description("Description One").build();
        storyTwo = Story.builder().id(2).title("Title Two").description("Description Two").build();
        storyOne.setCreated(LocalDateTime.now());
        storyTwo.setCreated(LocalDateTime.now());
    }

    @Test
    void shouldReturnAllStoriesWhenAnyExist() {
        when(storyRepository.findAll()).thenReturn(List.of(storyOne, storyTwo));

        List<StoryResponse> stories = storyService.getStories();
        assertEquals(2, stories.size());
    }

    @Test
    void shouldReturnStoryWhenIdExists() {
        when(storyRepository.findById(1)).thenReturn(Optional.of(storyOne));
        StoryResponse storyResponse = storyService.getStoryById(1);
        assertEquals("Title One", storyResponse.title());
    }

    @Test
    void shouldThrowNotFoundWhenStoryIdDoesNotExist() {
        when(storyRepository.findById(3)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () ->
            storyService.getStoryById(3));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void shouldSaveNewStoryCorrectly() {

        StoryRequest storyRequest = StoryRequest
                .builder()
                .title("Title Three")
                .description("Description Three")
                .build();

        when(storyRepository.save(any(Story.class))).thenReturn(new Story(storyRequest));

        storyService.addStory(storyRequest);

        verify(storyRepository, times(1)).save(any(Story.class));
    }

    @Test
    void shouldReturnStoryResponseAfterAddingStory() {

        StoryRequest storyRequest = StoryRequest.builder()
                .title("Title Four")
                .description("Description Four")
                .build();

        Story savedStory = new Story(storyRequest);
        savedStory.setId(4);
        savedStory.setCreated(LocalDateTime.now());

        when(storyRepository.save(any(Story.class))).thenReturn(savedStory);

        StoryResponse storyResponse = storyService.addStory(storyRequest);

        assertEquals(4, storyResponse.id());
        assertEquals("Title Four", storyResponse.title());
        assertEquals("Description Four", storyResponse.description());
        assertNull(storyResponse.startNodeId());
        assertEquals(0, storyResponse.numberOfNodes());
    }


    @Test
    void shouldUpdateStoryFieldsWhenEditingExistingStory() {
        int existingId = 1;
        StoryRequest request = StoryRequest
                .builder()
                .title("A New Title")
                .description("A New Description")
                .build();
        when(storyRepository.findById(existingId)).thenReturn(Optional.of(storyOne));

        Story updatedStory = Story.builder()
                .id(storyOne.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
        updatedStory.setCreated(storyOne.getCreated());
        when(storyRepository.save(any(Story.class))).thenReturn(updatedStory);

        StoryResponse response = storyService.editStory(request, existingId);

        assertEquals(storyOne.getId(), response.id());
        assertEquals("A New Title", response.title());
        assertEquals("A New Description", response.description());
    }

    @Test
    void shouldThrowNotFoundWhenEditingNonExistingStory() {
        int nonExistingId = -999;
        StoryRequest emptyRequest = StoryRequest.builder().build();
        when(storyRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () ->
                storyService.editStory(emptyRequest, nonExistingId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Story not found", exception.getReason());
    }

    @Test
    void shouldDeleteStoryWhenIdExists() {
        int existingId = 1;
        when(storyRepository.existsById(existingId)).thenReturn(true);

        ResponseEntity<String> response = storyService.deleteById(existingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldThrowNotFoundWhenDeletingNonExistingStory() {
        int nonExistingId = -999;
        when(storyRepository.existsById(nonExistingId)).thenReturn(false);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () ->
                storyService.deleteById(nonExistingId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void shouldReturnEmptyListWhenNoStoriesExist() {
        when(storyRepository.findAll()).thenReturn(Collections.emptyList());

        List<StoryResponse> emptyList = storyService.getStories();

        assertEquals(0, emptyList.size());
    }
}
