package com.musicplayer.musicplayerv1;

import javafx.scene.media.MediaPlayer;

import java.util.*;

public class PlayListNavigationController {
    public static LinkedList<String> playlist = new LinkedList<>();
    private MediaPlayer mediaPlayer;

    PlayListNavigationController(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void updatePlayList(ArrayList<String> songName) {
        for (String song : songName) {
            playlist.add(song);
        }
    }

//    public String nextSongInPlayList(String currentSongName) {
//        ListIterator<String> litr = playlist.listIterator();
//        while (litr.hasNext()) {
////            (currentSongName.equals(litr.next())) ? return litr.next() : return "Playlist over";
//            if (currentSongName.equals(litr.next())) return litr.next();
//        }
//        return null;
//    }

    public String nextSongInPlayList(String currentSongName) {
        ListIterator<String> litr = playlist.listIterator();
        while (litr.hasNext()) {
            String nextSong = litr.next();
            if (currentSongName.equals(nextSong)) {
                if (litr.hasNext()) {
                    return litr.next();
                } else {
                    return "Playlist over";
                }
            }
        }
        return null; // Handle the case when the current song is not found in the playlist
    }

    public String previousSongInPlayList(String currentSongName) {
        ListIterator<String> litr = playlist.listIterator();
        while (litr.hasNext()) {
            String nextSong = litr.next();
            if (currentSongName.equals(nextSong)) {
                if (litr.hasPrevious()) {
                    System.out.println(litr.previous());
                    return litr.previous();
                } else {
                    System.out.println(litr.previous());
                    return "Playlist over";
                }
            }
        }
        return null; // Handle the case when the current song is not found in the playlist
    }
}
