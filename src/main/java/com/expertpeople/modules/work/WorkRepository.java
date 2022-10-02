package com.expertpeople.modules.work;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface WorkRepository extends JpaRepository<Work,Long> {

    boolean existsByPath(String path);
   @EntityGraph(attributePaths = {"jobs","zones","managers","members"})
    Work findByPath(String path);
}
