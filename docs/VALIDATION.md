# Validation and limitations

## Observed locally — 6 November 2026

`javac 26` compiled the application and `tests/PlaybackTest.java` with `-Xlint:all`; `java -cp build PlaybackTest` passed. Checks cover empty libraries, missing WAV files, no-clip playback controls, and case-insensitive prefix search. This reports the tested JDK, not a claimed minimum version.

```powershell
javac -Xlint:all -d build Main.java Menu.java MusicPlayer.java PlaylistManager.java FileHandler.java tests/PlaybackTest.java
java -cp build PlaybackTest
```

These checks do not require audio hardware. Audible playback and mixer volume controls were not verified in this run. The existing demonstration recording is historical evidence, not a test of this revised commit.

Playlist persistence still uses the original file-rewrite approach. Error handling/atomicity and multiple `Scanner` instances warrant a separately tested follow-up; no data-loss-prone persistence rewrite is included here. The playback fix clears failed clips, closes input streams, clamps seeking, and avoids modulo-by-zero on empty libraries.
