import java.util.ArrayList;
import java.util.List;
public class PlaybackTest {
    public static void main(String[] args) {
        MusicPlayer empty = new MusicPlayer(new ArrayList<>());
        empty.next(); empty.previous(); empty.loadSong(0);
        if (!empty.getStatus().equals("No Song Loaded")) throw new AssertionError("Empty library state");
        MusicPlayer missing = new MusicPlayer(new ArrayList<>(List.of("missing-test-file.wav")));
        missing.loadSong(0);
        missing.play(); missing.pause(); missing.forward10(); missing.back10(); missing.reset();
        if (!missing.getStatus().equals("No Song Loaded")) throw new AssertionError("Failed load leaves stale state");
        FileHandler handler = new FileHandler();
        var matches = handler.prefixSearch("AL",new ArrayList<>(List.of("alpha.wav","beta.wav","ALBUM.wav")));
        if (matches.size()!=2) throw new AssertionError("Case-insensitive prefix search");
        System.out.println("Playback/file-search regression checks passed");
    }
}
