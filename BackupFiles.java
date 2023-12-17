import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class BackupFiles {
    int copysize;
    private final ArrayList<String> sources;
    private String destination;

    public BackupFiles() {

        //array list for the sources
        this.sources = new ArrayList<>();
    }

    // Getter
    public String getDestination() {
        return destination;
    }

    // Setter
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ArrayList<String> getSources() {
        return sources;
    }

    public void setSource(String source) {
        this.sources.add(source);
    }

    // Method to add a source directory using a file explorer
    public void addSourceFromDirectoryChooser() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setDialogTitle("Select a directory to backup");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String directoryPath = chooser.getSelectedFile().getAbsolutePath();
            sources.add(directoryPath);
            int copysize = sources.size();
            System.out.println("You have " + copysize + " sources to copy.");
        } else {
            System.out.println("No directory selected");
        }
    }

    // Method to choose the destination directory
    public void destinationDirectoryChooser() {
        JFileChooser choosedest = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        choosedest.setDialogTitle("Select a destination directory");
        choosedest.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        choosedest.setAcceptAllFileFilterUsed(false);

        if (choosedest.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String directoryPath = choosedest.getSelectedFile().getAbsolutePath();
            destination = directoryPath;
        } else {
            System.out.println("No directory selected");
        }
    }

    // Method to copy files from source to destination
    public void copyFiles() {
        for (String source : sources) {
            Path sourcePath = Path.of(source);
            Path destinationPath = Path.of(destination, sourcePath.getFileName().toString());

            try {
                Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Path targetFile = destinationPath.resolve(sourcePath.relativize(file));
                        Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File copied successfully from " + file + " to " + targetFile);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Path targetDir = destinationPath.resolve(sourcePath.relativize(dir));
                        Files.createDirectories(targetDir);
                        return FileVisitResult.CONTINUE;
                    }
                });
                System.out.println("Directory copied successfully from " + sourcePath + " to " + destinationPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
    }
    public void clearSources() {
        sources.clear();
    }
    public void clearDestination() {
        destination = null;
    }
}


