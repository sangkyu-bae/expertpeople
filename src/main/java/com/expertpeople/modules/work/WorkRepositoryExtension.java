package com.expertpeople.modules.work;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface WorkRepositoryExtension {
   //Page<Work> findByKeyword(String keyword, Pageable pageable);

    List<Work> findByKeyword(String keyword);
}
