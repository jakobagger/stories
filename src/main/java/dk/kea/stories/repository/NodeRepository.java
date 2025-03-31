package dk.kea.stories.repository;

import dk.kea.stories.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, Integer> {
    List<Node> findAllByStoryId(int storyId);
}
