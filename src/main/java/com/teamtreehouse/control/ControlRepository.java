package com.teamtreehouse.control;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by scott on 6/19/2017.
 */

public interface ControlRepository extends PagingAndSortingRepository<Control, Long> {
}
