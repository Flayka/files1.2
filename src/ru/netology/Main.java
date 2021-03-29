package ru.netology;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        String pathZip = "C://Games/savegames/zipOutput.zip";
        String path = "C://Games/savegames/";
        String pathGame = "C://Games/savegames/1gamePacked.txt";

        openZip(pathZip, path);
        openProgress(pathGame);

        /*
        Zip zip = new Zip("C://Games/savegames/zipOutput.zip");
        GameProgress game1 = new GameProgress(100, 100, 1, 1);
        GameProgress game2 = new GameProgress(90, 150, 5, 17);
        GameProgress game3 = new GameProgress(50, 300, 10, 32);

        zip.saveGame(path, game1);
        zip.saveGame(path, game2);
        zip.saveGame(path, game3);

        ArrayList<String> allFiles = new ArrayList<>(zip.getFiles(path));
        zip.zipFiles(allFiles, zip.getPath());

        zip.cleanDir(path);
*/
    }

    public static void openZip(String pathZip, String path) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(pathZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName(); // получим название файла
                // распаковка
                FileOutputStream fos = new FileOutputStream(path + name);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fos.write(c);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
            System.out.println("Файлы разархивированы");
        } catch (Exception e) {
            System.out.println("Ошибка. Файлы не разархивированы");
        }
    }

    public static void openProgress(String pathGame) {
        GameProgress game = null;
        try (FileInputStream fis = new FileInputStream(pathGame);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            game = (GameProgress) ois.readObject();
            System.out.println("Файл десерилиазован");
        } catch (Exception e) {
            System.out.println("Ошибка. Не удалось десерилиазовать файл");
        }
        System.out.println(game);
    }
}
