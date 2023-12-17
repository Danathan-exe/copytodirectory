import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Gui {
    BackupFiles backupFiles = new BackupFiles();
    JFrame frame = new JFrame("Backup Files");
    JPanel panel = new JPanel();
    //JButtons
//create a progress bar and set initial value to 0


    JButton addSource = new JButton("Add Sources");
    JButton copy = new JButton("Copy Files");
    JButton Destination = new JButton("Destination");
    JButton saveSettings = new JButton("Export Settings");
    JButton importSettings = new JButton("Import Settings");
    JButton clear = new JButton("Clear all");

    //JTextareas
    JTextArea source = new JTextArea(5,20);
    
    //JProgressBars
    JProgressBar pb = new JProgressBar(0, 100);
    public Gui() {
        addActionListeners();
        //add to panel
        panel.add(addSource);
        panel.add(copy);
        panel.add(Destination);
        panel.add(source);
        panel.add(saveSettings);
        panel.add(importSettings);
        panel.add(clear);
        panel.add(pb);
        panel.setLayout(new GridLayout(3, 2));

        //add to frame
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
//add action listeners
    private void addActionListeners() {
        addSource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backupFiles.addSourceFromDirectoryChooser();
                update();
            }
        });

        Destination.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backupFiles.destinationDirectoryChooser();
                update();
            }
        });



        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backupFiles.copyFiles();
                //add in a progress bar to track copy progress
                progressBar.setValue(0);
                progressBar.setStringPainted(true);
                int i = 0;
                while (i <= 100) {
                    progressBar.setValue(i);
                    i++;
                    try {
                        Thread.sleep(100);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        saveSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Settings exportSettings = new Settings();
                exportSettings.choosesavesettingswindow();
                exportSettings.setSaveSource(backupFiles.getSources().toString());
                exportSettings.setSaveDestination(backupFiles.getDestination());
                exportSettings.saveSettings();

            }
        });
        importSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Settings importSettings = new Settings();
                importSettings.importsettings(backupFiles);
            }
        });
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backupFiles.clearSources();
                backupFiles.clearDestination();
                update();
            }
        });
    }

    public void update() {
        source.setText("");
        source.append("Source: " + backupFiles.getSources() + "\n" + "Destination: " + backupFiles.getDestination() + "\n");
    }
}