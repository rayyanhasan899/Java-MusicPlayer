import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MusicPlayer {

    private Clip clip;
    private String currentPath;
    private int currentIndex;
    private final ArrayList<String> songs;

    public MusicPlayer(ArrayList<String> s) {
        songs = s;
        currentIndex = 0;
    }
    public void selectSong() {
        // ===== SONG SELECTION =====
        while (true) {
            Menu menu = new Menu();
            Scanner sc = new Scanner(System.in);

            System.out.println("Select The Song to play:");
            menu.showOptions(songs);

            int s = safeInt(sc);

            if (s == songs.size() + 1)
                break;

            if (s < 1 || s > songs.size()) {
                System.out.println("Invalid!");
                continue;
            }

            loadSong(s - 1);

            // ===== CONTROL MENU =====
            boolean back = false;

            while (!back) {

                menu.showControl(
                        getStatus());

                int c = safeInt(sc);

                switch (c) {

                    case 1:
                        play(); break;

                    case 2:
                        pause(); break;

                    case 3:
                        forward10(); break;

                    case 4:
                        back10(); break;

                    case 5:
                        reset(); break;

                    case 6:
                        next(); break;

                    case 7:
                        previous(); break;
                    case 8:
                        System.out.print("Enter Volume (0-100): ");
                        int vol = safeInt(sc);
                        setVolume(vol);
                        break;
                    case 9:
                        back = true;
                        pause();
                        reset();
                        break;

                    default:
                        System.out.println("Wrong!");
                }
            }
        }
    }

    // Load selected song
    public void loadSong(int index) {

        try {

            if (clip != null)
                clip.close();

            currentIndex = index;

            currentPath = "Songs/" + songs.get(currentIndex);

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(
                            new File(currentPath));

            clip = AudioSystem.getClip();
            clip.open(audio);

        } catch (Exception e) {
            System.out.println("Cannot Load Song: " + e.getMessage());
        }
    }

    public void play() {
        if (clip != null)
            clip.start();
    }
    public void pause() {
        if (clip != null)
            clip.stop();
    }
    public void reset() {
        if (clip != null)
            clip.setMicrosecondPosition(0);
    }
    // Jump +10 seconds
    public void forward10() {

        if (clip == null) return;

        long pos = clip.getMicrosecondPosition();
        clip.setMicrosecondPosition(pos + 10_000_000);
    }
    // Jump -10 seconds
    public void back10() {

        if (clip == null) return;

        long pos = clip.getMicrosecondPosition();

        pos -= 10_000_000;

        if (pos < 0) pos = 0;

        clip.setMicrosecondPosition(pos);
    }
    // Next song (modulo)
    public void next() {

        int next = (currentIndex + 1) % songs.size();
        loadSong(next);
        play();
    }
    // Previous song (modulo)
    public void previous() {

        int prev =
                (currentIndex - 1 + songs.size())
                        % songs.size();

        loadSong(prev);
        play();
    }
    // ===== SET VOLUME 0 - 100 =====
    public void setVolume(int v) {

        try {

            if (clip == null) {
                System.out.println("No song loaded!");
                return;
            }

            if (v < 0 || v > 100) {
                System.out.println("Volume must be 0-100");
                return;
            }

            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Convert 0-100 to decibels
            float min = gain.getMinimum();
            float max = gain.getMaximum();

            float volume = min + (max - min) * (v / 100.0f);

            gain.setValue(volume);

            System.out.println("Volume Set To: " + v);

        } catch (Exception e) {
            System.out.println(
                    "Volume Not Supported: " +
                            e.getMessage());
        }
    }


    public String getStatus() {

        if (clip == null)
            return "No Song Loaded";

        long total = clip.getMicrosecondLength();
        long current = clip.getMicrosecondPosition();

        long remain = (total - current) / 1_000_000;
        long elapsed = current / 1_000_000;

        return "Playing: " + currentPath +
                " | Elapsed: " + elapsed +
                "s | Remaining: " + remain + "s";
    }

    static int safeInt(Scanner sc) {

        try {
            return Integer.parseInt(sc.next());
        }
        catch (Exception e) {
            return -1;
        }
    }
}