import static org.easymock.EasyMock.*;
  
import java.util.ArrayList;
  
import junit.framework.TestCase;
  
public class TestMp3Player extends TestCase {
  
    protected Mp3Player mp3;
    protected Mp3Player Mockmp3;
    protected ArrayList list = new ArrayList();
  
    public void setUp() {
        Mockmp3 = createMock(Mp3Player.class);
        mp3 = Mockmp3;
        
        //mp3 = new MockMp3Player();
  
        list = new ArrayList();
        list.add("Bill Chase -- Open Up Wide");
        list.add("Jethro Tull -- Locomotive Breath");
        list.add("The Boomtown Rats -- Monday");
        list.add("Carl Orff -- O Fortuna");
  
    }
  
    public void testPlay() {
        Mockmp3.loadSongs(list);
        expectLastCall();
        expect(Mockmp3.isPlaying()).andReturn(false).times(1);
        Mockmp3.play();
        expectLastCall();
        expect(Mockmp3.isPlaying()).andReturn(true).times(1);
        expect(Mockmp3.currentPosition()).andReturn(0.01).times(1);
        Mockmp3.pause();
        expectLastCall();
        expect(Mockmp3.currentPosition()).andReturn(0.01).times(1);
        Mockmp3.stop();
        expectLastCall();
        expect(Mockmp3.currentPosition()).andReturn(0.01).times(1);
        replay(Mockmp3);
  
        mp3.loadSongs(list);
        assertFalse(mp3.isPlaying());
        mp3.play();
        assertTrue(mp3.isPlaying());
        assertTrue(mp3.currentPosition() != 0.0);
        mp3.pause();
        assertTrue(mp3.currentPosition() != 0.0);
        mp3.stop();
        assertEquals(mp3.currentPosition(), 0.0, 0.1);
  
    }
  
    public void testPlayNoList() {
        expect(Mockmp3.isPlaying()).andReturn(false).times(1);
        Mockmp3.play();
        expectLastCall();
        expect(Mockmp3.isPlaying()).andReturn(false).times(1);
        expect(Mockmp3.currentPosition()).andReturn(0.01).times(1);
        Mockmp3.pause();
        expectLastCall();
        expect(Mockmp3.currentPosition()).andReturn(0.01).times(1);
        expect(Mockmp3.isPlaying()).andReturn(false).times(1);
        Mockmp3.stop();
        expectLastCall();
        expect(Mockmp3.currentPosition()).andReturn(0.01).times(1);
        expect(Mockmp3.isPlaying()).andReturn(false).times(1);
        replay(Mockmp3);
        
        
        assertFalse(mp3.isPlaying());
        mp3.play();
        assertFalse(mp3.isPlaying());
        assertEquals(mp3.currentPosition(), 0.0, 0.1);
        mp3.pause();
        assertEquals(mp3.currentPosition(), 0.0, 0.1);
        assertFalse(mp3.isPlaying());
        mp3.stop();
        assertEquals(mp3.currentPosition(), 0.0, 0.1);
        assertFalse(mp3.isPlaying());
    }
  
    public void testAdvance() {
        Mockmp3.loadSongs(list);
        expectLastCall();
        Mockmp3.play();
        expectLastCall();
        expect(Mockmp3.isPlaying()).andReturn(true).times(1);
        Mockmp3.prev();
        expectLastCall();
        expect(Mockmp3.currentSong()).andReturn((String) list.get(0)).times(1);
        expect(Mockmp3.isPlaying()).andReturn(true).times(1);
        Mockmp3.next();
        expectLastCall();
        expect(Mockmp3.currentSong()).andReturn((String) list.get(1)).times(1);
        Mockmp3.next();
        expectLastCall();
        expect(Mockmp3.currentSong()).andReturn((String) list.get(2)).times(1);
        Mockmp3.prev();
        expectLastCall();
        expect(Mockmp3.currentSong()).andReturn((String) list.get(1)).times(1);
        Mockmp3.next();
        expectLastCall();
        expect(Mockmp3.currentSong()).andReturn((String) list.get(2)).times(1);
        //Mockmp3.next();
        //expectLastCall();
        //expect(Mockmp3.currentSong()).andReturn((String) list.get(3)).times(1);
        Mockmp3.next();
        expectLastCall();
        expect(Mockmp3.currentSong()).andReturn((String) list.get(3)).times(1);
        expect(Mockmp3.isPlaying()).andReturn(true).times(1);
        replay(Mockmp3);
        
        
        mp3.loadSongs(list);
        mp3.play();
        assertTrue(mp3.isPlaying());
        mp3.prev();
        assertEquals(mp3.currentSong(), list.get(0));
        assertTrue(mp3.isPlaying());
        mp3.next();
        assertEquals(mp3.currentSong(), list.get(1));
        mp3.next();
        assertEquals(mp3.currentSong(), list.get(2));
        mp3.prev();
        assertEquals(mp3.currentSong(), list.get(1));
        mp3.next();
        assertEquals(mp3.currentSong(), list.get(2));
        mp3.next();
        assertEquals(mp3.currentSong(), list.get(3));
        mp3.next();
        assertEquals(mp3.currentSong(), list.get(3));
        assertTrue(mp3.isPlaying());
    }
  
} 
