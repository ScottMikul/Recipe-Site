package com.teamtreehouse.core;

import javax.persistence.*;

/**
 * Created by scott on 6/19/2017.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected final Long id;
    @Version
    private Long version;

    protected BaseEntity() {
        id = null;
    }

    public Long getId() {
        return id;
    }

}