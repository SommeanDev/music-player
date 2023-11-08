package com.musicplayer.musicplayerv1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.awt.Desktop;

public class PageTwoController implements Initializable {
    @FXML
    public ListView<String> listView;
    public ArrayList<String> songList = new ArrayList<>();

    protected void findMusicMP3(){
        String command = "ls music/*.mp3";
        StringBuilder stringbuilder = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", command).directory(new File("C:\\Users\\HP"));
        try{
            int lineCount = 0;
            Process process = processBuilder.start();

            InputStream is = process.getInputStream();
            Scanner scanner = new Scanner(is);

            while (scanner.hasNextLine()){

                String line = scanner.nextLine();
                stringbuilder.append(line);
                songList.add(line);
                lineCount++;
            }
            String out = stringbuilder.toString();

            for (String song : songList) {
                if (songList.indexOf(song) >= 7 || songList.indexOf(song) == 5) {

                    String pattern = "^([-a]+)\\s+(\\d{2}-\\d{2}-\\d{4})\\s+(\\d{2}:\\d{2})\\s+(\\d+)\\s+(.*)$";

                    Pattern r = Pattern.compile(pattern);
                    Matcher m = r.matcher(song);

                    if (m.find()) {
                        String mode = m.group(1);
                        String lastWriteTime = m.group(2);
                        String time = m.group(3);
                        String length = m.group(4);
                        String name = m.group(5);

                        System.out.println("Mode: " + mode);
                        System.out.println("LastWriteTime: " + lastWriteTime);
                        System.out.println("Time: " + time);
                        System.out.println("Length: " + length);
                        System.out.println("Name: " + name);
                    }
                }

            }

            System.out.println("\nLine count: " + lineCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.getProperty("user.dir"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        findMusicMP3();
//        listView.getItems().addAll("song1", "song2", "song3", "song4");
        int listIndex = 1;
        for (String song : songList) {
            if (songList.indexOf(song) >= 7 || songList.indexOf(song) == 5) {
                String pattern = "^([-a]+)\\s+(\\d{2}-\\d{2}-\\d{4})\\s+(\\d{2}:\\d{2})\\s+(\\d+)\\s+(.*)$";

                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(song);

                if (m.find()) {
                    String name = m.group(5);
                    String listItem = listIndex + "\t" +  name;
                    listView.getItems().add(listItem);
                    listIndex++;
                }
            }
        }

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    String selectedSong = songList.get(selectedIndex);
                    playSong(selectedSong);
                }
            }
        });
    }
    private void playSong(String songFile) {
        try {
            File file = new File(songFile);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.err.println("File does not exist: " + songFile);
            }
        } catch (IOException e) {
            System.err.println("Error while opening the file: " + e.getMessage());
            e.printStackTrace();
        } catch (SecurityException e) {
            System.err.println("Security exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
