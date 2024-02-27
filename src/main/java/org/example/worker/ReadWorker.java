package org.example.worker;

import org.example.entity.Animal;
import org.example.service.ReadService;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author duyenthai
 */
public class ReadWorker extends Thread {
    private final ReadService readService;
    private final List<Animal> listAnimals;
    private final CountDownLatch latch;

    public ReadWorker(ReadService readService, List<Animal> listAnimals, CountDownLatch latch) {
        this.readService = readService;
        this.listAnimals = listAnimals;
        this.latch = latch;
    }

    @Override
    public void run() {
        List<Animal> list = readService.read();
        if (!list.isEmpty()) {
            listAnimals.addAll(list);
        }
        latch.countDown();
    }
}
