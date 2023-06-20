package com.example.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestFileUtils {

    public static String getContent(String path) throws IOException {
        final File jsonFile = new ClassPathResource(path).getFile();

        return Files.readString(jsonFile.toPath());
    }
}
