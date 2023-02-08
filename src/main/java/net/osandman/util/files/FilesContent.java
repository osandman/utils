package net.osandman.util.files;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/*
Проход по дереву файлов
*/

public class FilesContent {
    public static void main(String[] args) {
        args = new String[]{"d:/tmp", "d:/tmp1/result.txt"};
        Path rootPath = Paths.get(args[0]);
        Path resultFileAbsolutePath = Paths.get(args[1]);
        Path destinationFile = Path.of(resultFileAbsolutePath.getParent().toString(), "/allFilesContent.txt");
        try {
            Files.deleteIfExists(destinationFile);
            writeFilesContent(walkDirTree(rootPath), destinationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void writeFilesContent(Set<Path> files, Path resultFile) throws IOException {
        for (Path file : files) {
            try (FileInputStream fileInputStream = new FileInputStream(file.toFile());
                 FileOutputStream fileOutputStream = new FileOutputStream(resultFile.toFile(), true)) {
                while (fileInputStream.available() > 0) {
                    fileOutputStream.write(fileInputStream.read());
                }
                fileOutputStream.write(System.lineSeparator().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("%s: %s | %d bytes\n", "writing from file", file.toAbsolutePath(), Files.size(file));
        }
        System.out.println("all files: " + files.size());
    }

    static Set<Path> walkDirTree(Path startDir) {
        Set<Path> resultFiles = new TreeSet<>();
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        try {
            Files.walkFileTree(startDir, options, Integer.MAX_VALUE, new GetSetOfFiles(resultFiles));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultFiles;
    }
}
