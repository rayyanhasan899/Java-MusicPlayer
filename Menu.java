import java.util.ArrayList;

class Menu {

    public void showMain() {

        System.out.println("=== Terminal Music App ===");
        System.out.println("1. Play Songs");
        System.out.println("2. Playlists");
        System.out.println("3. Favourites");
        System.out.println("4. Search");
        System.out.println("5. Exit");
    }

    public void showPlaylistMenu() {

        System.out.println("=== PLAYLIST MENU ===");
        System.out.println("1. Select Playlist");
        System.out.println("2. Create Playlist");
        System.out.println("3. Update Playlist");
        System.out.println("4. Delete Playlist");
        System.out.println("5. Back To Main Menu");
    }

    public void showOptions(ArrayList<String> p) {


        for (int i = 0; i < p.size(); i++)
            System.out.println((i+1) + ". " + p.get(i));

        System.out.println((p.size()+1) + ". Back");
    }


    public void showControl(String status) {


        System.out.println("==== PLAYER ====");
        System.out.println(status);
        System.out.println();

        System.out.println("1. Play");
        System.out.println("2. Pause");
        System.out.println("3. Go Ahead 10 Seconds");
        System.out.println("4. Go Back 10 Seconds");
        System.out.println("5. Reset");
        System.out.println("6. Next Song");
        System.out.println("7. Previous Song");
        System.out.println("8. Set Volume");
        System.out.println("9. Back To Select Songs");
    }

}