package net.osandman.util.files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

public class GetSetOfFiles extends SimpleFileVisitor<Path> {
    private Set<Path> result;

    public GetSetOfFiles(Set<Path> result) {
        this.result = result;
    }

    /**
     * переопределяем метод {@code visitFile}<p>
     * заполняет Set значениями File, файлов, имеющих размер менее 50 байт
     */
    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        if (!Files.isDirectory(path) && Files.size(path) <= 50 && Files.size(path) != 0) {
            result.add(path);
        }
        return super.visitFile(path, basicFileAttributes);
    }
}
