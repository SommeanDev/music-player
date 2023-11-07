package com.musicplayer.musicplayerv1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageTwoController implements Initializable {
    @FXML
    public ListView<String> listView;
    public ArrayList<String> songList = new ArrayList<>();

    protected void findMusicMP3(){
        String command = "dir music/*.mp3";
        ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", command);
        try{
            int lineCount = 0;
            Process process = processBuilder.start();

            InputStream is = process.getInputStream();
            Scanner scanner = new Scanner(is);

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                songList.add(line);
                lineCount++;
            }

            for (String song : songList) {
                if (songList.indexOf(song) >= 7 || songList.indexOf(song) == 5) {
//                    System.out.println(song);
//                    System.out.println(Arrays.toString(song.split(" ")));
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
    }
}
