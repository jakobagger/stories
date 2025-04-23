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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            storyService.getStoryById(3);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void shouldSaveNewStoryCorrectly() {
        StoryRequest storyRequest = StoryRequest.builder().title("Title Three").description("Description Three").build();
        Story newStory = new Story(storyRequest);
        newStory.setCreated(LocalDateTime.now());
        newStory.setId(3);
        when(storyRepository.save(any(Story.class))).thenReturn(newStory);

        StoryResponse storyResponse = storyService.addStory(storyRequest);
        assertEquals(3, storyResponse.id());
        assertEquals("Title Three", storyResponse.title());
    }

    @Test
    void shouldReturnStoryResponseAfterAddingStory() {
    }

    @Test
    void shouldUpdateStoryFieldsWhenEditingExistingStory() {
    }

    @Test
    void shouldThrowNotFoundWhenEditingNonExistingStory() {
    }

    @Test
    void shouldReturnUpdatedStoryResponseAfterEdit() {
    }

    @Test
    void shouldDeleteStoryWhenIdExists() {
    }

    @Test
    void shouldThrowNotFoundWhenDeletingNonExistingStory() {
    }

    @Test
    void shouldReturnAllStoriesWhenTheyExist() {
    }

    @Test
    void shouldReturnEmptyListWhenNoStoriesExist() {
    }
}
