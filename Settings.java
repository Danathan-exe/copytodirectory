import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.time.LocalDate;

public class Settings {
    private final LocalDate date = LocalDate.now();

    private String saveSource;
    private String savesettingsource;
    private String saveDestination;

    private final BackupFiles backupFiles = new BackupFiles();

    // Setters

    public void setSaveSource(String saveSource) {
        saveSource = saveSource.substring(1, saveSource.length() - 1);
        this.saveSource = saveSource;
    }

    public void setSaveDestination(String destination) {
        this.saveDestination = destination;
    }


    // Getters
    public String getSaveSource() {
        return saveSource;
    }

    public String getSaveDestination() {
        return saveDestination;
    }


    // Methods
    public void choosesavesettingswindow() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setDialogTitle("Select where you would like to save your settings");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String directoryPath = chooser.getSelectedFile().getAbsolutePath();
            savesettingsource = directoryPath;
        } else {
            System.out.println("No directory selected");
        }
    }

    public void saveSettings() {
        if (savesettingsource == null) {
            System.out.println("Please choose a settings save location.");
            return; // Abort the save if savesettingsource is not set
        }

        try {
            FileWriter writer = new FileWriter(savesettingsource + "\\Settings" + date + ".txt");
            writer.write("Source: " + getSaveSource() + "\n");
            writer.write("Destination: " + getSaveDestination() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void importsettings(BackupFiles backupFiles) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Settings File");

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            setBackupFileSettings(selectedFile, backupFiles);
        }
    }

    private static void setBackupFileSettings(File file, BackupFiles backupFiles) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Source:")) {
                    backupFiles.setSource(line.substring(8).trim());
                } else if (line.startsWith("Destination:")) {
                    backupFiles.setDestination(line.substring(13).trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




