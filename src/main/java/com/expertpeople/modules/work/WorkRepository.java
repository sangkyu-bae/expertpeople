package com.expertpeople.modules.work;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface WorkRepository extends JpaRepository<Work,Long>, QuerydslPredicateExecutor<Work>,WorkRepositoryExtension {

    boolean existsByPath(String path);
   @EntityGraph(attributePaths = {"jobs","zones","managers","members"})
    Work findByPath(String path);

   @EntityGraph(attributePaths = {"jobs","managers"})
    Work findWorkWithJobsByPath(String path);

   @EntityGraph(attributePaths = {"zones","managers"})
    Work findWorkWithZonesByPath(String path);
    @EntityGraph(attributePaths = {"managers"})
    Work findWorkWithManagersByPath(String path);
    @EntityGraph(attributePaths = {"members"})
    Work findWorkWithMembersByPath(String path);

    @EntityGraph(attributePaths = {"jobs","zones"})
    Work findWorkWithJobAndZoneById(Long id);

    @EntityGraph(attributePaths = {"managers","members"})
    Work findWorkWithManagersAndMembersById(Long id);

    Page<Work> findByKeyword(String keyword, Pageable pageable);
}
