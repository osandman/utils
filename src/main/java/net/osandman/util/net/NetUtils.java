package net.osandman.util.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class NetUtils {
    public static void main(String[] args) throws IOException {
        Path fileFromURL = downloadFileFromURL("https://ya.ru/index.html",
                Paths.get("D:/MyDownloads"));
        System.out.println("writing to file: " + fileFromURL);
        for (String line : Files.readAllLines(fileFromURL)) {
            System.out.println(line);
        }
    }

    /**
     * записывает содержимое файла из URL {@code urlString} в файл на диск в директорию {@code downloadDirectory}
     */
    public static Path downloadFileFromURL(String urlString, Path downloadDirectory) throws IOException {
        URL url = new URL(urlString);
        if (Files.notExists(downloadDirectory)) {
            Files.createDirectory(downloadDirectory);
        }
        Path dest = downloadDirectory.resolve(Paths.get(url.getFile()).getFileName());
        try (InputStream urlInputStream = url.openStream()) {
            Path tempFile = Files.createTempFile(dest.getParent(), "download", ".tmp");
            Files.copy(urlInputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(dest);
            Files.move(tempFile, dest);
        }
        return dest;
    }
}
