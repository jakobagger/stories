package dk.kea.stories.repository;

import dk.kea.stories.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Integer> {

    List<Choice> findAllByFromNodeId(int id);
}
