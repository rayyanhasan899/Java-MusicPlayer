import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

class PlaylistManager {

    // Read list of playlists from playlists.txt
    public ArrayList<String> readPlaylists() {

        ArrayList<String> lists = new ArrayList<>();

        try {
            BufferedReader br =
                    new BufferedReader(
                            new FileReader("playlists.txt"));

            String line;

            while ((line = br.readLine()) != null) {

                if (!line.trim().isEmpty())
                    lists.add(line.trim());
            }

            br.close();

            if (lists.isEmpty())
                System.out.println("No playlists found!");

        } catch (Exception e) {
            System.out.println(
                    "Playlist file missing: " +
                            e.getMessage());
        }

        return lists;
    }

    // Create new playlist file and register it
    public String createPlaylist(String name) {

        try {

            String path = name + ".txt";

            File f = new File("Playlists/" + path);

            // If already exists
            if (f.exists()) {
                System.out.println("Playlist already exists!");
                return null;
            }

            // create empty file
            f.createNewFile();

            // Add path to playlists.txt
            FileWriter fw =
                    new FileWriter("playlists.txt", true);

            fw.write(path + "\n");
            fw.close();

            return path;

        } catch (Exception e) {
            System.out.println(
                    "Error creating playlist: " +
                            e.getMessage());

            return null;
        }
    }

    // Add selected song path into playlist file
    public void addSongToPlaylist( String playlistPath, String songPath) {

        try {

            FileWriter fw =
                    new FileWriter("Playlists/" + playlistPath, true);

            fw.write(songPath + "\n");

            fw.close();

            System.out.println("Added: " + songPath);

        } catch (Exception e) {
            System.out.println(
                    "Cannot add song: " +
                            e.getMessage());
        }
    }

    // Delete playlist: remove from playlists.txt AND delete file
    public void deletePlaylist(String path) {

        try {

            // ===== 1. DELETE ACTUAL FILE =====
            File f = new File("Playlists/" + path);
            if (f.exists())
                f.delete();
            // ===== 2. REMOVE ENTRY FROM playlists.txt =====

            File original = new File("playlists.txt");
            File temp = new File("temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(original));

            FileWriter fw = new FileWriter(temp);

            String line;

            while ((line = br.readLine()) != null) {

                // write all except deleted one
                if (!line.trim().equals(path))
                    fw.write(line + "\n");
            }

            br.close();
            fw.close();

            // replace old with new
            original.delete();
            temp.renameTo(original);

            System.out.println("Playlist Deleted Successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Delete Failed: " +
                            e.getMessage());
        }
    }

    public void renamePlaylist(String oldPath, String newName) {

        try {

            String newPath = newName + ".txt";

            File oldFile = new File("Playlists/" + oldPath);
            File newFile = new File("Playlists/" + newPath);

            // rename actual file
            oldFile.renameTo(newFile);

            // update playlists.txt
            File original = new File("playlists.txt");
            File temp = new File("temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(original));
            FileWriter fw = new FileWriter(temp);
            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().equals(oldPath))
                    fw.write(newPath + "\n");
                else
                    fw.write(line + "\n");
            }

            br.close();
            fw.close();

            original.delete();
            temp.renameTo(original);

            System.out.println("Renamed Successfully!");

        } catch (Exception e) {
            System.out.println(
                    "Rename Failed: " + e.getMessage());
        }
    }

    public void removeSong(String playlistPath, String songPath) {

        try {

            File original = new File("Playlists/" + playlistPath);
            File temp = new File("temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(original));

            FileWriter fw = new FileWriter(temp);

            String line;

            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(songPath))
                    fw.write(line + "\n");
            }

            br.close();
            fw.close();

            original.delete();
            temp.renameTo(original);

            System.out.println("Song Removed!");

        } catch (Exception e) {
            System.out.println(
                    "Remove Failed: " +
                            e.getMessage());
        }
    }



}
