package gui.dal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Arrays;

public class DataAccess {
    private static ObservableList<String> withinPlaylist = FXCollections.observableArrayList();

    private static ObservableList<String> withinPlaylistList = FXCollections.observableArrayList();

    private static ObservableList<String> songLibrary = FXCollections.observableArrayList();

    /**
     * Takes our file and creates a list of contents.
     */
    public static void viewPlaylist()
    {
        File directory = new File("MyTunes/src/gui/datasources/playlists/music");
        String[] inList;
        inList = directory.list();
        assert inList != null;
        withinPlaylist.addAll(Arrays.asList(inList));

    }
    /**
     * Takes our file and creates a list of contents.
     */
    public static void viewPlaylistList()
    {
        File directory = new File("MyTunes/src/gui/datasources/playlists");
        String[] inList;
        inList = directory.list();
        assert inList != null;
        withinPlaylistList.addAll(Arrays.asList(inList));

    }

    /**
     * Takes our file and creates a list of contents.
     */
    public static void viewSongLibrary()
    {
        File directory = new File("MyTunes/src/gui/datasources/songlibrary");
        String[] inList;
        inList = directory.list();
        assert inList != null;
        songLibrary.addAll(Arrays.asList(inList));

    }

    /**
     * Used by the main controller to initially fill our lists.
     */
    public void makeLibraries()
    {
        viewPlaylist();
        viewSongLibrary();
        viewPlaylistList();
    }

    /**
     *
     * @return returns our library as an observable list.
     */
    public ObservableList<String> returnLibraryView()
    {

        return songLibrary;
    }

    /**
     *
     * @return returns our playlist list as an observable list.
     */
    public ObservableList<String> returnPlaylistList()
    {

        return withinPlaylistList;
    }

    /**
     *
     * @return returns our playlist as an observable list.
     */
    public ObservableList<String> returnPlaylist()
    {

        return withinPlaylist;
    }
}
