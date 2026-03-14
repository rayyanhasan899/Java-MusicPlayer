import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        FileHandler fh = new FileHandler();
        Menu menu = new Menu();

        ArrayList<String> songs = fh.readSongs("songs.txt");

        if (songs.isEmpty())
            return;

        MusicPlayer player = new MusicPlayer(songs);

        int ch = 0;
        while (ch != 5) {
            menu.showMain();

            ch = safeInt(sc);

            if (ch == 1) {
                player.selectSong();
            }
            else if (ch == 2) {

                PlaylistManager pm = new PlaylistManager();

                boolean backMain = false;

                while (!backMain) {


                    System.out.println("Select Playlist:");
                    menu.showPlaylistMenu();

                    int pch = safeInt(sc);

                    switch (pch) {

                        case 1:   // ===== SELECT PLAYLIST =====

                            ArrayList<String> pls = pm.readPlaylists();

                            if (pls.isEmpty())
                                break;

                            menu.showOptions(pls);

                            int sel = safeInt(sc);

                            if (sel == pls.size()+1)
                                break;

                            if (sel < 1 || sel > pls.size()) {
                                System.out.println("Invalid");
                                break;
                            }

                            // Read songs of that playlist
                            ArrayList<String> psongs = fh.readSongs("Playlists/" + pls.get(sel-1));

                            if (psongs.isEmpty())
                                break;
                            //

                            // ===== USE SAME PLAYER MENU =====
                            MusicPlayer pPlayer =
                                    new MusicPlayer(psongs);
                            pPlayer.selectSong();
                            break;

                        case 2:
                            // ===== CREATE PLAYLIST =====

                            System.out.print("Enter Playlist Name: ");

                            String name = sc.next();

                            String newPath =
                                    pm.createPlaylist(name);

                            if (newPath == null)
                                break;

                            // Now add songs one by one
                            boolean done = false;

                            while (!done) {

                                System.out.println(
                                        "Add Songs To Playlist: " +
                                                newPath);

                                menu.showOptions(songs);

                                int s = safeInt(sc);

                                if (s == songs.size() + 1) {
                                    done = true;
                                    break;
                                }

                                if (s < 1 || s > songs.size()) {
                                    System.out.println("Invalid!");
                                    continue;
                                }

                                // Get full path from songs.txt list
                                String selectedSong =
                                        songs.get(s - 1);

                                // Append into playlist file
                                pm.addSongToPlaylist(
                                        newPath,
                                        selectedSong);
                            }
                            break;


                        case 3:
                            // ===== UPDATE PLAYLIST =====

                            ArrayList<String> lists =
                                    pm.readPlaylists();

                            if (lists.isEmpty())
                                break;

                            System.out.println("Select Playlist To Update:");

                            menu.showOptions(lists);

                            int u = safeInt(sc);

                            if (u == lists.size() + 1)
                                break;

                            if (u < 1 || u > lists.size()) {
                                System.out.println("Invalid");
                                break;
                            }

                            String path = lists.get(u - 1);

                            boolean back = false;

                            while (!back) {


                                System.out.println("Update: " + path);

                                System.out.println("1. Change Playlist Name");
                                System.out.println("2. Add Songs");
                                System.out.println("3. Remove Songs");
                                System.out.println("4. Back");

                                int op = safeInt(sc);

                                switch (op) {

                                    case 1:
                                        System.out.print("New Name: ");
                                        String nn = sc.next();

                                        pm.renamePlaylist(path, nn);

                                        path = nn + ".txt";
                                        break;

                                    case 2:
                                        // SAME AS CREATE LOGIC BUT APPEND
                                        done = false;

                                        while (!done) {

                                            menu.showOptions(songs);

                                            int s = safeInt(sc);

                                            if (s == songs.size() + 1) {
                                                done = true;
                                                break;
                                            }

                                            if (s < 1 || s > songs.size()) {
                                                System.out.println("Invalid");
                                                continue;
                                            }

                                            pm.addSongToPlaylist(path, songs.get(s - 1));
                                        }
                                        break;

                                    case 3:
                                        // ===== REMOVE SONG =====

                                        ArrayList<String> inside = fh.readSongs("Playlists/" + path);

                                        if (inside.isEmpty())
                                            break;

                                        menu.showOptions(inside);

                                        int r = safeInt(sc);

                                        if (r == inside.size() + 1)
                                            break;

                                        if (r < 1 || r > inside.size()) {
                                            System.out.println("Invalid");
                                            break;
                                        }

                                        pm.removeSong(path, inside.get(r - 1));

                                        break;

                                    case 4:
                                        back = true;
                                        break;

                                    default:
                                        System.out.println("Wrong");
                                }
                            }
                            break;


                        case 4:
                            // ===== DELETE PLAYLIST =====

                            ArrayList<String> all = pm.readPlaylists();

                            if (all.isEmpty())
                                break;

                            System.out.println("Select Playlist To Delete:");

                            menu.showOptions(all);

                            int d = safeInt(sc);

                            if (d == all.size() + 1)
                                break;

                            if (d < 1 || d > all.size()) {
                                System.out.println("Invalid");
                                break;
                            }

                            String delPath = all.get(d - 1);

                            pm.deletePlaylist(delPath);
                            break;

                        case 5:
                            backMain = true;
                            break;

                        default:
                            System.out.println("Invalid");
                    }
                }
            }
            else if (ch == 3) {
                PlaylistManager pm = new PlaylistManager();
                String path = "favourites.txt";

                boolean back = false;

                while (!back) {

                    System.out.println("1. Play Favourites");
                    System.out.println("2. Add Songs");
                    System.out.println("3. Remove Songs");
                    System.out.println("4. Back");

                    int op = safeInt(sc);

                    switch (op) {

                        case 1:
                            ArrayList<String> psongs = fh.readSongs("Playlists/" + path);

                            if (psongs.isEmpty())
                                break;
                            //

                            // ===== USE SAME PLAYER MENU =====
                            MusicPlayer pPlayer = new MusicPlayer(psongs);
                            pPlayer.selectSong();
                            break;
                        case 2:
                            // SAME AS CREATE LOGIC BUT APPEND
                           boolean done = false;

                            while (!done) {

                                menu.showOptions(songs);

                                int s = safeInt(sc);

                                if (s == songs.size() + 1) {
                                    done = true;
                                    break;
                                }

                                if (s < 1 || s > songs.size()) {
                                    System.out.println("Invalid");
                                    continue;
                                }

                                pm.addSongToPlaylist(path, songs.get(s - 1));
                            }
                            break;

                        case 3:
                            // ===== REMOVE SONG =====

                            ArrayList<String> inside = fh.readSongs("Playlists/" + path);

                            if (inside.isEmpty())
                                break;

                            menu.showOptions(inside);

                            int r = safeInt(sc);

                            if (r == inside.size() + 1)
                                break;

                            if (r < 1 || r > inside.size()) {
                                System.out.println("Invalid");
                                break;
                            }

                            pm.removeSong(path, inside.get(r - 1));

                            break;

                        case 4:
                            back = true;
                            break;

                        default:
                            System.out.println("Wrong");
                    }
                }
            }
            else if (ch == 4) {

                System.out.println("=== SEARCH SONGS ===");
                System.out.println("Type letters to search, # to exit");
                System.out.println();
                menu.showOptions(songs);
                ArrayList<String> result = songs;

                while (true) {

                    System.out.print("\nInput: ");
                    String in = sc.next();

                    try {

                        int num = Integer.parseInt(in);
                        if (num == result.size() + 1)
                            break;

                        if (num >= 1 && num <= result.size()) {

                            MusicPlayer sp = new MusicPlayer(result);

                            sp.loadSong(num-1);
                            sp.selectSong();
                        }

                    } catch (Exception e) {
                        // not a number → treat as new search
                    }

                    // ---- FILTER BY STARTS WITH ----
                    result = fh.prefixSearch(in, songs);

                    System.out.println(
                            "Results Starting With: " + in);
                    System.out.println();
                    menu.showOptions(result);
                }
            }
            else if (ch == 5)
                System.out.println("Exit...");
        }
    }

    // Safe integer input handler
    static int safeInt(Scanner sc) {

        try {
            return Integer.parseInt(sc.next());
        }
        catch (Exception e) {
            return -1;
        }
    }
}
