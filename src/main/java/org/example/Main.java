package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.Animal;
import org.example.repository.AnimalRepository;
import org.example.repository.DbAnimalRepository;
import org.example.repository.FileRepository;
import org.example.service.*;
import org.example.worker.PersistentWorker;
import org.example.worker.ReadWorker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author duyenthai
 */
public class Main {

    private static final Logger LOGGER = LogManager.getLogger("LOGGER");

    public static void main(String[] args) throws InterruptedException {
        ApplicationService applicationService = new ApplicationServiceImpl();
        applicationService.process();

        RandomService randomService = new RandomServiceImpl();
        Animal[] animals = randomService.randomArray(10);
        LOGGER.info("Successfully random list animals");

        CountDownLatch latch = new CountDownLatch(2);
        AnimalRepository dbRepository = new DbAnimalRepository();
        AnimalRepository fileRepository = new FileRepository();

        PersistentWorker dbWorker = new PersistentWorker(latch, dbRepository, animals);
        PersistentWorker fileWorker = new PersistentWorker(latch, fileRepository, animals);

        dbWorker.start();
        fileWorker.start();
        latch.await();

        LOGGER.info("Done");

        CountDownLatch readLatch = new CountDownLatch(2);
        ReadService dbReadService = new DbReadService();
        ReadService fileReadService = new FileReadService();
        List<Animal> fromDb = new ArrayList<>();
        List<Animal> fromFile = new ArrayList<>();
        ReadWorker readDbWorker = new ReadWorker(dbReadService, fromDb, readLatch);
        ReadWorker readFileWorker = new ReadWorker(fileReadService, fromFile, readLatch);
        readDbWorker.start();
        readFileWorker.start();
        readLatch.await();

        LOGGER.info("Read Done");

        fromDb.sort(Comparator.comparing(Animal::getId));
        fromFile.sort(Comparator.comparing(Animal::getId));

        List<Animal> inFileNotDb =  fromFile.stream().filter(val -> !fromDb.contains(val)).toList();
        List<Animal> inDbNotFile = fromDb.stream().filter(val -> !fromFile.contains(val)).toList();
        if(inDbNotFile.isEmpty() && inFileNotDb.isEmpty()) {
            LOGGER.info("2 arrays is equals");
        }
    }
}