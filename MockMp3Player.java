import java.util.ArrayList;
 
 
public class MockMp3Player implements Mp3Player{
 
    private ArrayList songs;
    // Points to the current song 
    private int csptr = 0;
    // Boolean variable to see if song playing or not
    private boolean isPlaying = false;
    // Number of seconds played in the current song
    private double songseconds = 0.0;
 
    public MockMp3Player(){
        this.songs = new ArrayList<String>();
    }
 
    @Override
    public void play() {
        if(this.songs.size() > 0){
            this.isPlaying = true;
            songseconds += 0.01;
        }
    }
 
    @Override
    public void pause() {
        if(this.songs.size() > 0){
            this.isPlaying = false;
        }
    }
 
    @Override
    public void stop() {
        if(this.songs.size() > 0){
            this.isPlaying = false;
            this.songseconds = 0.0;
        }
    }
 
    @Override
    public double currentPosition() {
        return this.songseconds;
    }
 
 
    @Override
    public String currentSong() {
        return String.valueOf(this.songs.get(csptr));
    }
 
    @Override
    public void next() {
 
        if(this.csptr < this.songs.size() - 1) 
        {
          this.csptr++;
        }
        
        this.songseconds = 0;
 
    }
 
    @Override
    public void prev() {
        if(this.csptr > 0) 
        this.csptr--;
        this.songseconds = 0;
    }
 
    @Override
    public boolean isPlaying() {
        return this.isPlaying;
    }
 
    @Override
    public void loadSongs(ArrayList songlist) {
        for(Object song : songlist){
            this.songs.add(song);
        }
    }
 
}
