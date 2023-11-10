package com.musicplayer.musicplayerv1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageTwoController implements Initializable {
    @FXML
    public ListView<String> listView;
    public ArrayList<String> songList = new ArrayList<>(), songNameList = new ArrayList<>();
    private static int playPauseCount = 0;

    public MediaPlayer mediaPlayer;
    public Label songTitle;
    public Slider songProgressBar;
    public Button playButton;

    private Timeline progressBarUpdateTimeLine;
    private double userPlayBackValue = 0.0;
    private Map<Media, String> mediaNameMap;

    protected void findMusicMP3(){
        String command = "ls music/*.mp3, music/*.wav, music/*.mp4";
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
                System.out.println(song);

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
                        songNameList.add(name);
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
        mediaNameMap = new HashMap<>();
        for (String song : songNameList) {
            String listItem = listIndex + "\t" + song;
            listView.getItems().add(listItem);
            listIndex++;
        }

        findAndPlaySong(listView.getItems().get(0).split("\t")[1], false);

        listView.setStyle("-fx-background-color: black;");
        System.out.println(listView.getStyle());
//        for (String listItem : listView.getItems().stream().toList()) {
//            String[] listItemContents = listItem.split("\t");
//            System.out.println(listItemContents[1]);
//        }

        listView.setOnMouseClicked(event -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                playPauseCount = 0;
                playButton.setText("Play");
            }
            if (selectedIndex >= 0) {
                String selectedSong = songNameList.get(selectedIndex);
                findAndPlaySong(selectedSong, true);
            }
        });
    }
    private void findAndPlaySong(String songName, boolean playSong) {
        try {
            File file = new File("C:\\Users\\HP\\music\\" + songName);
            if (file.exists()) {
                Media media = new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setOnReady(() -> {
                    songTitle.setText(file.getName());
                });
                MediaView mediaView = new MediaView(mediaPlayer);
//                Desktop.getDesktop().open(file);

                mediaNameMap.put(media, songName);
                if (playSong) {
                    mediaPlayer.play();
                    playButton.setText("Pause");
                    playPauseCount++;
                }

                progressBarUpdateTimeLine = new Timeline(
                        new KeyFrame(
                                Duration.seconds(1),
                                event -> {
                                    if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                                        double totalSongDuration = mediaPlayer.getTotalDuration().toMillis();
                                        songProgressBar.setMax(totalSongDuration);
                                        songProgressBar.setValue(mediaPlayer.getCurrentTime().toMillis());
                                    }
                                }
                        )
                );

                progressBarUpdateTimeLine.setCycleCount(Timeline.INDEFINITE);
                mediaPlayer.currentTimeProperty().addListener(((observable, oldValue, newValue) -> {
                    songProgressBar.setMax(mediaPlayer.getTotalDuration().toMillis());
                    songProgressBar.setValue(newValue.toMillis());
                }));

                songProgressBar.valueProperty().addListener(((observable, oldValue, newValue) -> {
                    userPlayBackValue = newValue.doubleValue();
                    if (Math.abs(newValue.doubleValue() - oldValue.doubleValue()) > 300) {
                        mediaPlayer.seek(Duration.millis(userPlayBackValue));
                    }
                    System.out.println("Observable value: " + observable.getValue().doubleValue() + "\tOld value: " +  oldValue.doubleValue() + "\tNew value: " + newValue.doubleValue());
                }));
            } else {
                System.err.println("File does not exist: " + songName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: FXML PART
    @FXML
    public void previousSong(MouseEvent mouseEvent) {

    }

    @FXML
    public void playSong(MouseEvent mouseEvent) {
        if (playPauseCount == 0) {
//            if (userPlayBackValue != 0.0) {
//                System.out.println(userPlayBackValue);
//                mediaPlayer.seek(Duration.seconds(userPlayBackValue));
//                userPlayBackValue = 0.0;
//            }
            mediaPlayer.play();
            playButton.setText("Pause");
            playPauseCount++;
        } else {
            playButton.setText("Play");
            mediaPlayer.pause();
            playPauseCount = 0;
        }
    }

    @FXML
    public void NextSong(MouseEvent mouseEvent) {
        PlayListNavigationController controller = new PlayListNavigationController(mediaPlayer);
        controller.updateStack(songNameList);
        controller.NextSong(mediaNameMap.get(mediaPlayer.getMedia()));
    }

}
