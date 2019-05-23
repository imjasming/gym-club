package com.gymclub.sso.oauth.github.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Xiaoming.
 * Created on 2019/05/21 01:17.
 */
public interface GithubEntityRepository extends JpaRepository<GithubEntity, Integer> {
    GithubEntity findGithubEntityById(Integer id);
}
