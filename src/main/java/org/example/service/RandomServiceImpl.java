package org.example.service;

import org.example.entity.Animal;
import org.example.entity.Cat;
import org.example.entity.Duck;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * @author duyenthai
 */
public class RandomServiceImpl implements RandomService {
    private final Random random = new SecureRandom();

    @Override
    public Animal[] randomArray(int numberRandom) {
        Animal[] res = new Animal[numberRandom];
        for (int index = -1; ++index < res.length; ) {
            int randomNumber = random.nextInt(1000);
            Animal animal = randomNumber % 2 == 0 ? new Cat() : new Duck();
            animal.setId(UUID.randomUUID().toString());
            res[index] = animal;
        }
        return res;
    }
}
