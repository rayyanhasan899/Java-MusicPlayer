import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FileHandler {

    // Read all song paths from txt file
    public ArrayList<String> readSongs(String file) {

        ArrayList<String> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {

                if (!line.trim().isEmpty())
                    list.add(line.trim());
            }

            br.close();

            if (list.isEmpty())
                System.out.println("Song file is empty!");

        } catch (Exception e) {
            System.out.println("Error Reading File: " + e.getMessage());
        }

        return list;
    }

    // Prefix based search (starts with)
    public ArrayList<String> prefixSearch(String prefix, ArrayList<String> all) {

        ArrayList<String> res =
                new ArrayList<>();

        prefix = prefix.toLowerCase();

        for (String s : all) {

            // extract only file name not path
            String name = new File(s).getName().toLowerCase();

            if (name.startsWith(prefix))
                res.add(s);
        }

        return res;
    }



}
