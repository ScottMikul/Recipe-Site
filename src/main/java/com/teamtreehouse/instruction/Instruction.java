package com.teamtreehouse.instruction;

import com.teamtreehouse.core.BaseEntity;

import javax.persistence.Entity;

/**
 * Created by scott on 3/22/2018.
 */
@Entity
public class Instruction extends BaseEntity{
    String detail;


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Instruction() {

    }

    public Instruction(String detail) {
        this.detail = detail;
    }
}
