package com.expertpeople.modules.work;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface WorkRepositoryExtension {
    List<Work>findByKeyword(String keyword);
}
