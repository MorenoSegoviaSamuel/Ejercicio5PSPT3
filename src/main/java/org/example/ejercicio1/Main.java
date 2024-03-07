package org.example.ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        URLList urlList = new URLList();
        Downloader downloader = new Downloader();

        // Agregar el observador
        urlList.addObserver(downloader);

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
    private List<String> urls = new ArrayList<>();
    private List<URLListObserver> observers = new ArrayList<>();

    public void addObserver(URLListObserver observer) {
        observers.add(observer);
    }

    public void addURL(String url) {
        urls.add(url);
        notifyObservers(url);
    }

    private void notifyObservers(String url) {
        for (URLListObserver observer : observers) {
            observer.onURLAdded(url);
        }
    }
}

interface URLListObserver {
    void onURLAdded(String url);
}

class Downloader implements URLListObserver {
    @Override
    public void onURLAdded(String url) {
        System.out.println("Se ha iniciado la descarga del archivo " + url);
        // Aquí podrías iniciar la descarga del archivo correspondiente a la URL
    }
}

