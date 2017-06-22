package com.teamtreehouse.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by scott on 6/19/2017.
 */
public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Room save(Room entity);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Iterable save(Iterable entities);
}
