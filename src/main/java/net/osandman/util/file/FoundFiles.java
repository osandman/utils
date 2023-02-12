package net.osandman.util.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * нахождение файлов в соответствие с заданными параметрами
 */

public class FoundFiles extends SimpleFileVisitor<Path> {
    private String partOfName = "";
    private String partOfContent = "";
    private long minSize = 0;
    private long maxSize = Long.MAX_VALUE;
    private boolean withoutSearch = false;

    private final List<Path> foundFiles = new ArrayList<>();

    public FoundFiles() {
        withoutSearch = true;
    }

    public FoundFiles(String partOfName) {
        this.partOfName = partOfName;
    }

    public FoundFiles(String partOfName, String partOfContent) {
        this.partOfName = partOfName;
        this.partOfContent = partOfContent;
    }

    public FoundFiles(long minSize, long maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public FoundFiles(String partOfName, String partOfContent, long minSize, long maxSize) {
        this.partOfName = partOfName;
        this.partOfContent = partOfContent;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    /**
     * переопределяем метод {@code visitFile}<p>
     * при выполнении заполняет Set значениями Path файлов, имеющих размер менее 50 байт
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes basicFileAttributes) throws IOException {
        if (withoutSearch) {
            foundFiles.add(file);
        } else {
            if (partOfName != null && file.getFileName().toString().contains(partOfName)) {
                long fileSize = basicFileAttributes.size();
                if (fileSize >= minSize && fileSize <= maxSize) {
                    if (isContains(file)) {
                        foundFiles.add(file);
                    }
                }
            }
        }
        return super.visitFile(file, basicFileAttributes);
    }

    //TODO поиск содержимого с учетом больших файлов
    private boolean isContains(Path file) throws IOException {
        String fileContent = new String(Files.readAllBytes(file));
        if (partOfContent != null && fileContent.contains(partOfContent)) {
            return true;
        }
        return false;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }
}
