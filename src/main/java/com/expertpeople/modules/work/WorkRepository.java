package com.expertpeople.modules.work;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface WorkRepository extends JpaRepository<Work,Long> {

    boolean existsByPath(String path);
}
