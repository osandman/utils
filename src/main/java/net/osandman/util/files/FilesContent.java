package net.osandman.util.files;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Проход по дереву файлов, запись содержимого найденных файлов в один файл
 */

public class FilesContent {
    public static void main(String[] args) {
        args = new String[]{"d:/tmp", "result.txt"};
        Path rootPath = Paths.get(args[0]);
        Path destinationFile = Paths.get(rootPath.resolve(args[1]).toUri());
        try {
            Files.deleteIfExists(destinationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FoundFiles fileVisitor = new FoundFiles();
        fileVisitor.setMinSize(10);
        fileVisitor.setMaxSize(200);
        fileVisitor.setPartOfContent("");
        fileVisitor.setPartOfName("txt");

        writeFilesContent(walkDirTree(rootPath, fileVisitor), destinationFile);
    }

    /**
     * записывает содержимое файлов {@code files} в файл {@code resultFile}
     */
    static void writeFilesContent(List<Path> files, Path resultFile) {
        for (Path file : files) {
            try (FileInputStream fileInputStream = new FileInputStream(file.toFile());
                 FileOutputStream fileOutputStream = new FileOutputStream(resultFile.toFile(), true)) {
                if (Files.isSameFile(file, resultFile)) continue; //не записываем содержимое исходного файла в себя
                while (fileInputStream.available() > 0) {
                    fileOutputStream.write(fileInputStream.read());
                }
                fileOutputStream.write(System.lineSeparator().getBytes());
                System.out.printf("%s: %s | %d bytes\n", "writing from file", file.toAbsolutePath(), Files.size(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.printf("%d files writing to %s\n", files.size(), resultFile);
    }

    /**
     * обход дерева каталогов начиная с {@code startDir} <p>
     * возвращает List найденых файлов в соответствие с параметрами заданными в {@code FoundFiles}<p>
     * который наследуется от SimpleFileVisitor и переопределяет его методы
     */
    static List<Path> walkDirTree(Path startDir, FoundFiles fileVisitor) {
        List<Path> resultFiles = fileVisitor.getFoundFiles();
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        try {
            Files.walkFileTree(startDir, options, Integer.MAX_VALUE, fileVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultFiles;
    }
}
