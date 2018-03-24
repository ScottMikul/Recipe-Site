package com.teamtreehouse.ingredient;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.teamtreehouse.core.BaseEntity;

import javax.persistence.Entity;

/**
 * Created by scott on 3/22/2018.
 */

@Entity
public class Ingredient extends BaseEntity{
    String name;
    String condition;
    int quantity;

    public Ingredient() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
