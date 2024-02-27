package org.example.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.Animal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author duyenthai
 */
public class FileReadService implements ReadService {

    private static final Logger LOGGER = LogManager.getLogger("FileReadService");
    private final String fileNameSave = System.getProperty("user.dir") + File.separator + "content.json";
    private final FileService fileService = new FileServiceImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileReadService() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<Animal> read() {
        String fileContent = fileService.read(fileNameSave);
        if (!StringUtils.nonEmptyString(fileContent)) {
            return Collections.emptyList();
        }
        String[] arr = fileContent.split("\n");
        List<Animal> res = new ArrayList<>();
        for (String index : arr) {
            try {
                Animal o = objectMapper.readValue(index, Animal.class);
                res.add(o);
            } catch (Exception ex) {
                LOGGER.error("Transform error ", ex);
            }
        }
        return res;
    }
}
