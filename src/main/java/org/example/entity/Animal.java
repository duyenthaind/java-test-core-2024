package org.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author duyenthai
 */
@Entity
@Table(name = "animal")
public class Animal implements Serializable {
    private String id;
    private String name;
    private int type;
    private int numberOfLeg;

    public Animal() {
    }

    @Id
    public String getId() {
        return id;
    }

    @Column(name = "name")

    public String getName() {
        return name;
    }

    @Column(name = "type")
    public int getType() {
        return type;
    }

    @Column(name = "number_of_leg")
    public int getNumberOfLeg() {
        return numberOfLeg;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setNumberOfLeg(int numberOfLeg) {
        this.numberOfLeg = numberOfLeg;
    }

    protected void makeNoise() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal animal)) return false;
        return type == animal.type && numberOfLeg == animal.numberOfLeg && Objects.equals(id, animal.id) && Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, numberOfLeg);
    }
}
