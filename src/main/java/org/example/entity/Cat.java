package org.example.entity;

import javax.persistence.Entity;
import java.util.UUID;

/**
 * @author duyenthai
 */
@Entity
public class Cat extends Animal {

    public Cat() {
        this.setNumberOfLeg(4);
        this.setType(1);
        this.setName("Cat");
    }

    @Override
    protected void makeNoise() {
        System.out.println("Meow meow");
    }
}
