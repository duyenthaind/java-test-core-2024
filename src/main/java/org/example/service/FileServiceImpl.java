package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Scanner;

/**
 * @author duyenthai
 */
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LogManager.getLogger("FileService");

    @Override
    public void write(String fileName, String content) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception ex) {
                LOGGER.error("Cannot create regular file ", ex);
            }
        }
        if (file.exists()) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(content);
            } catch (Exception ex) {
                LOGGER.error("Write to file {} but triggered exception ", fileName, ex);
            }
        }
    }

    @Override
    public void write(String fileName, String content, boolean lock) {
        if (!lock) {
            write(fileName, content);
            return;
        }
        lockAndWrite(fileName, content);
    }

    @Override
    public String read(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return "";
        }
        try (Scanner scanner = new Scanner(file);) {
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
            return stringBuilder.toString();
        } catch (Exception ex) {
            LOGGER.error("Read file {} but trigger exception ", fileName, ex);
        }
        return "";
    }

    @Override
    public void append(String fileName, String content) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception ex) {
                LOGGER.error("Cannot create regular file ", ex);
            }
        }
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(content);
        } catch (Exception ex) {
            LOGGER.error("Write to file {} but triggered exception ", fileName, ex);
        }
    }

    private void lockAndWrite(String fileName, String content) {
        try {
            try (RandomAccessFile stream = new RandomAccessFile(fileName, "rw");
                 FileChannel channel = stream.getChannel()
            ) {
                writeFileWithLock(channel, stream, content);
            }
        } catch (Exception ex) {
            LOGGER.error("Try lock and write to file error, file {}", fileName, ex);
        }
    }

    public void writeFileWithLock(FileChannel channel, RandomAccessFile stream, String content) throws Exception {
        FileLock lock = channel.tryLock();
        try {
            stream.writeBytes(content);
        } finally {
            lock.release();
        }
    }
}
