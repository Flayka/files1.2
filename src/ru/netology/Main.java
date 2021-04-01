package ru.netology;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        String path = File.separator + "Games" + File.separator +"savegames" + File.separator;
        String zipPath = File.separator + "Games" + File.separator +"savegames" + File.separator + "zipOutput.zip";

        GameProgress game1 = new GameProgress(100, 100, 1, 1);
        GameProgress game2 = new GameProgress(90, 150, 5, 17);
        GameProgress game3 = new GameProgress(50, 300, 10, 32);

        saveGame(path, game1);
        saveGame(path, game2);
        saveGame(path, game3);

        ArrayList<String> allFiles = new ArrayList<>(getFiles(path));
        zipFiles(allFiles, zipPath);

        cleanDir(path);
    }

    public static void saveGame(String path, GameProgress game) {
        String savePath = path + "game" + game.getCount() + ".dat";
        try (FileOutputStream fos = new FileOutputStream(savePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
            System.out.printf("Игра game%s сохранена\n", game.getCount());
        } catch (Exception e) {
            System.out.println("Ошибка, Игра не сохранена");
        }
    }

    public static void zipFiles(ArrayList<String> allFiles, String zipPath) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (int i = 0; i < allFiles.size(); i++) {
                try (FileInputStream fis = new FileInputStream(allFiles.get(i))) {
                    ZipEntry entry = new ZipEntry(i + 1 + "gamePacked.txt");
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                } catch (FileNotFoundException e) {
                    System.out.println("Ошибка. Файлы не заархивированы");
                }
            }
            System.out.println("Файлы заархивированы");
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка. Файлы не заархивированы");
        }
    }

    public static ArrayList<String> getFiles(String path) {
        File dir = new File(path);
        ArrayList<String> dirFiles = new ArrayList<>();
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                dirFiles.add(item.getAbsoluteFile().toString());
            }
        }
        return dirFiles;
    }

    public static void cleanDir(String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (!file.toString().substring(file.toString().lastIndexOf(".")).equals(".zip")) {
                    file.delete();
                }
            }
            System.out.println("Файлы вне архива удалены");
        }
    }
}
