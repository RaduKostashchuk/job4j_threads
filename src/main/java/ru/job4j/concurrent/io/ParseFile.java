package ru.job4j.concurrent.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = new File(file.getAbsolutePath());
    }

    public String getContent() {
        return content(c -> true);
    }

    public String getContentWithoutUnicode() {
        return content(c -> c < 0x80);
    }

    private String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in =
                     new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}