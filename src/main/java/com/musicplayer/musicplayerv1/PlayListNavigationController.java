package com.musicplayer.musicplayerv1;

import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class PlayListNavigationController {
    public static Stack<String> playlist = new Stack<>();
    private MediaPlayer mediaPlayer;

    PlayListNavigationController(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void updateStack(ArrayList<String> songNameList) {
        for (String song : songNameList.reversed()) {
            playlist.push(song);
        }
        System.out.println(playlist.peek());
    }

    public void updateStack(String songName) {
        System.out.println(playlist.peek());
    }

    public void NextSong(String songName) {
        //Song Name List example:
        // [
        //      Imagine Dragons - Believer.mp3                                       ,
        //      Jaden, Kid Cudi - On My Own.mp3                                      ,
        //      K'NAAN - Wavin' Flag.mp3             (+)                             ,
        //      OneRepublic - I Ain't Worried.mp3                                    ,
        //      file_example_WAV_1MG.wav                                             ,
        //      Spider_Man_No_Way_Home_2021_English_Full_Movie_HDRip.mp4
        // ]
        System.out.println(songName);
    }
}
