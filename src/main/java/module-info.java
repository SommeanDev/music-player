module com.musicplayer.musicplayerv1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;

    opens com.musicplayer.musicplayerv1 to javafx.fxml;
    exports com.musicplayer.musicplayerv1;
}