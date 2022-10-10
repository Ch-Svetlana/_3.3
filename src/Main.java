import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        openZip("C://Games//savegames//zip.zip", "C://Games//savegames//");
        File dir = new File("C://Games//savegames");
        File[] files = dir.listFiles();
        int i = 1;
        for (File file : files) {
            System.out.println(openProgress("C://Games//savegames//packed_game_progress" + i + ".txt"));
            i++;
        }
    }

    public static void openZip(String roadToFile, String roadToDir) {

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(roadToFile))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                try (FileOutputStream fout = new FileOutputStream(roadToDir + name)) {
                    for (int i = zin.read(); i != -1; i = zin.read()) {
                        fout.write(i);
                    }
                    fout.flush();
                    zin.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String roadToSaveGame) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(roadToSaveGame);
             ObjectInputStream ios = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ios.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}