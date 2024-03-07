package org.example.ejercicio2;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        URLList urlList = new URLList();

        // Pedir al usuario que ingrese las URLs
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Por favor ingresa una URL (o escribe 'exit' para salir): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            urlList.addURL(input);
        }
    }
}

class URLList {
    private CompletableFuture<Void> downloadFuture = CompletableFuture.completedFuture(null);

    public void addURL(String url) {
        downloadFuture = downloadFuture.thenComposeAsync(ignore ->
                CompletableFuture.runAsync(() -> {
                    try {
                        // Descargar la página web y guardarla en un archivo
                        Path filePath = Paths.get(getFileNameFromURL(url));
                        downloadWebPage(url, filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    private String getFileNameFromURL(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        if (!fileName.contains(".")) {
            fileName += ".html"; // Agregar extensión .html si no está presente en la URL
        }
        return fileName;
    }

    private void downloadWebPage(String url, Path filePath) throws Exception {
        try (InputStream in = new URL(url).openStream();
             FileOutputStream out = new FileOutputStream(filePath.toFile())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}

