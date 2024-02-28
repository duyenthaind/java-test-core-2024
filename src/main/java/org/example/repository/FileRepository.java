package org.example.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dto.BaseResponse;
import org.example.entity.Animal;
import org.example.service.FileService;
import org.example.service.FileServiceImpl;

import java.io.File;
import java.util.List;

/**
 * @author duyenthai
 */
public class FileRepository implements AnimalRepository {

    private static final Logger LOGGER = LogManager.getLogger("FileRepository");

    private final String fileNameSave = System.getProperty("user.dir") + File.separator + "content.json";
    private final FileService fileService = new FileServiceImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Animal save(Animal animal) {
        try {
            fileService.append(fileNameSave, objectMapper.writeValueAsString(animal) + "\n");
            return animal;
        } catch (Exception ex) {
            LOGGER.error("Save error ", ex);
        }
        return null;
    }

    @Override
    public BaseResponse batchSave(List<Animal> animals) {
        BaseResponse res = new BaseResponse();
        res.setR(0);
        res.setMsg("ok");

        StringBuilder contentSave = new StringBuilder();
        for (Animal a : animals) {
            try {
                contentSave.append(objectMapper.writeValueAsString(a)).append("\n");
            } catch (Exception ex) {
                LOGGER.error("Batch save error ", ex);
                res.setR(-1);
                res.setMsg("Failed");
                return res;
            }
        }
        fileService.append(fileNameSave, contentSave.toString());
        return res;
    }
}
