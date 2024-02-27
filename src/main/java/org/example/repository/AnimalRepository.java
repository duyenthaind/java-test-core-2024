package org.example.repository;

import org.example.dto.BaseResponse;
import org.example.entity.Animal;

import java.util.List;

/**
 * @author duyenthai
 */
public interface AnimalRepository {
    Animal save(Animal animal);

    BaseResponse batchSave(List<Animal> animals);
}
