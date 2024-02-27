package org.example.service;

/**
 * @author duyenthai
 */
public interface FileService {
    void write(String fileName, String content);

    void write(String fileName, String content, boolean lock);

    String read(String fileName);

    void append(String fileName, String content);
}
