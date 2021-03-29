package ru.netology;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
    private String path;

    public Zip(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void saveGame(String path, GameProgress game) {
        String savePath = path + "game" + game.getCount() + ".dat";
        try (FileOutputStream fos = new FileOutputStream(savePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
            System.out.printf("Игра game%s сохранена\n", game.getCount());
        } catch (Exception e) {
            System.out.println("Ошибка, Игра не сохранена");
        }
    }

    public void zipFiles(ArrayList<String> allFiles, String zipPath) throws IOException {
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

    public ArrayList<String> getFiles(String path) {
        File dir = new File(path);
        ArrayList<String> dirFiles = new ArrayList<>();
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                dirFiles.add(item.getAbsoluteFile().toString());
            }
        }
        return dirFiles;
    }

    public void cleanDir(String path) {
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
