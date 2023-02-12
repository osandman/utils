package net.osandman.util.file;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Проход по дереву файлов, запись содержимого найденных файлов в один заданный файл
 */

public class ConcatFilesContent {
    private Path rootPath;
    private Path targetFile;
    private boolean isSorted;

    public static void main(String[] args) throws IOException {
        Path rootPath = Paths.get("d:/tmpZip");
        Path targetFile = Paths.get(rootPath.resolve("result").toUri());
        ConcatFilesContent concatFilesContent = new ConcatFilesContent(rootPath, targetFile, true);
        FoundFiles fileVisitor = new FoundFiles("zip");
        List<Path> foundFiles = walkDirTree(rootPath, 1, fileVisitor);
        concatFilesContent.writeFilesContent(foundFiles, targetFile);
    }

    public ConcatFilesContent(Path rootPath, Path targetFile, boolean isSorted) {
        this.rootPath = rootPath;
        this.targetFile = targetFile;
        this.isSorted = isSorted;
    }

    /**
     * записывает содержимое файлов {@code files} в файл {@code resultFile} <p>
     * если находим файл с таким же именем, то создаем новый с добавлением текущей даты
     */
    void writeFilesContent(List<Path> files, Path resultFile) throws IOException {
        if (isSorted) {
            files.sort(Comparator.naturalOrder());
        }
        int currentDirOnly = 1;
        if (walkDirTree(resultFile.getParent(), currentDirOnly, new FoundFiles()).contains(resultFile)) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("d_MM_u_Hms");
            String suffix = "_" + LocalDateTime.now().format(format);
            String fileName = resultFile.getFileName().toString();
            int where = fileName.lastIndexOf(".") > 0 ? fileName.lastIndexOf(".") : fileName.length();
            String newFileName = fileName.substring(0, where) + suffix + fileName.substring(where);
            resultFile = Paths.get(resultFile.getParent().toString(), newFileName);
        }
        for (Path file : files) {
            try (BufferedOutputStream fileOutputStream =
                         new BufferedOutputStream(new FileOutputStream(resultFile.toFile(), true))) {
                Files.copy(file, fileOutputStream);
//                fileOutputStream.write(System.lineSeparator().getBytes());
                System.out.printf("%s: %s | %d bytes\n", "writing from file", file.toAbsolutePath(), Files.size(file));
            }
        }
        System.out.printf("%d file(s) writing to %s, %d bytes\n", files.size(), resultFile, Files.size(resultFile));
    }

    /**
     * обход дерева каталогов начиная с {@code startDir} <p>
     * возвращает List найденых файлов в соответствие с параметрами заданными в {@code FoundFiles}<p>
     * который наследуется от SimpleFileVisitor и переопределяет его методы
     */
    static List<Path> walkDirTree(Path startDir, int maxDepth, FoundFiles fileVisitor) {
        List<Path> resultFiles = fileVisitor.getFoundFiles();
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        try {
            Files.walkFileTree(startDir, options, maxDepth, fileVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultFiles;
    }
}
