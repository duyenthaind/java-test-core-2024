package org.example.worker;

import org.example.entity.Animal;
import org.example.repository.AnimalRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author duyenthai
 */
public class PersistentWorker extends Thread {
    private final CountDownLatch latch;
    private final AnimalRepository repository;
    private final Animal[] animals;

    public PersistentWorker(CountDownLatch latch, AnimalRepository repository, Animal[] animals) {
        this.latch = latch;
        this.repository = repository;
        this.animals = animals;
    }

    @Override
    public void run() {
        List<Animal> list = Arrays.stream(animals).toList();
        List<Animal> toSave = new CopyOnWriteArrayList<>();
        for (Animal animal : list) {
            if (toSave.size() == 50) {
                repository.batchSave(toSave);
                toSave.clear();
            }
            toSave.add(animal);
        }
        if (!toSave.isEmpty()) {
            repository.batchSave(toSave);
        }
        latch.countDown();
    }
}
