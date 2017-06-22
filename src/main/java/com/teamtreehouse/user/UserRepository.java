package com.teamtreehouse.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by scott on 6/19/2017.
 */
@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User,Long> {
    User findByName(String name);

}