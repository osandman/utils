package net.osandman.util.files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class GetSetOfFiles extends SimpleFileVisitor<Path> {
    private String partOfName;
    private String partOfContent;
    private long minSize;
    private long maxSize = Long.MAX_VALUE;
    private final List<Path> foundFiles;

    public GetSetOfFiles(List<Path> foundFiles) {
        this.foundFiles = foundFiles;
    }

    /**
     * переопределяем метод {@code visitFile}<p>
     * при выполнении заполняет Set значениями Path файлов, имеющих размер менее 50 байт
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes basicFileAttributes) throws IOException {
        if (partOfName != null && file.getFileName().toString().contains(partOfName)) {
            long fileSize = Files.size(file);
            if (fileSize >= minSize && fileSize <= maxSize) {
                String fileContent = new String(Files.readAllBytes(file));
                if (partOfContent != null && fileContent.contains(partOfContent)) {
                    foundFiles.add(file);
                }
            }
        }
        return super.visitFile(file, basicFileAttributes);
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }


    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(long minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }
}
