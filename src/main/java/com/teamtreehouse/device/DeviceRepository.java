package com.teamtreehouse.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by scott on 6/19/2017.
 */
public interface DeviceRepository extends PagingAndSortingRepository<Device, Long> {
    @RestResource(rel = "name-contains", path="name")
    Page<Device> findByNameContaining(@Param("name")String name, Pageable page);
}
