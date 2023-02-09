package net.osandman.util.files;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Проход по дереву файлов, запись содержимого найденных файлов в один файл
 */

public class FilesContent {
    public static void main(String[] args) {
        args = new String[]{"d:/tmp", "d:/tmp1/result.txt"};
        Path rootPath = Paths.get(args[0]);
        Path resultFileAbsolutePath = Paths.get(args[1]);
        Path destinationFile = Path.of(resultFileAbsolutePath.getParent().toString(), "/allFilesContent.txt");
        try {
            Files.deleteIfExists(destinationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeFilesContent(walkDirTree(rootPath), destinationFile);
    }

    /**
     * записывает содержимое файлов {@code files} в файл {@code resultFile}
     */
    static void writeFilesContent(List<Path> files, Path resultFile) {
        for (Path file : files) {
            try (FileInputStream fileInputStream = new FileInputStream(file.toFile());
                 FileOutputStream fileOutputStream = new FileOutputStream(resultFile.toFile(), true)) {
                while (fileInputStream.available() > 0) {
                    fileOutputStream.write(fileInputStream.read());
                }
                fileOutputStream.write(System.lineSeparator().getBytes());
                System.out.printf("%s: %s | %d bytes\n", "writing from file", file.toAbsolutePath(), Files.size(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("all files: " + files.size());
    }

    /**
     * обход дерева каталогов начиная с {@code startDir} <p>
     * возвращает TreeSet найденых файлов в соответствие с параметрами заданными в {@code GetSetOfFiles}<p>
     * который наследуется от SimpleFileVisitor и переопределяет его методы
     */
    static List<Path> walkDirTree(Path startDir) {
        List<Path> resultFiles = new ArrayList<>();
        GetSetOfFiles fileVisitor = new GetSetOfFiles(resultFiles);
        fileVisitor.setMinSize(7);
        fileVisitor.setMaxSize(78);
        fileVisitor.setPartOfContent("ir");
        fileVisitor.setPartOfName("read");

        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        try {
            Files.walkFileTree(startDir, options, Integer.MAX_VALUE, fileVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultFiles;
    }
}
