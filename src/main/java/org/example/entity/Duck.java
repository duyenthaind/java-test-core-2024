package org.example.entity;

import javax.persistence.Entity;
import java.util.UUID;

/**
 * @author duyenthai
 */
@Entity
public class Duck extends Animal {

    public Duck() {
        this.setNumberOfLeg(2);
        this.setType(2);
        this.setName("Duck");
    }

    @Override
    protected void makeNoise() {
        System.out.println("Quack quack");
    }
}
