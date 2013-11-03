import static org.easymock.EasyMock.*;
  
import java.util.ArrayList;
  
import junit.framework.TestCase;
  
public class TestMp3Player extends TestCase {
  
    protected Mp3Player mp3;
    protected Mp3Player mp3Mock;
    protected ArrayList list = new ArrayList();
  
    public void setUp() {
        mp3Mock = createMock(Mp3Player.class);
        mp3 = mp3Mock;
  
        list = new ArrayList();
        list.add("Bill Chase -- Open Up Wide");
        list.add("Jethro Tull -- Locomotive Breath");
        list.add("The Boomtown Rats -- Monday");
        list.add("Carl Orff -- O Fortuna");
  
    }
  
    public void testPlay() {
        mp3Mock.loadSongs(list);
        expectLastCall();
        expect(mp3Mock.isPlaying()).andReturn(false).times(1);
        mp3Mock.play();
        expectLastCall();
        expect(mp3Mock.isPlaying()).andReturn(true).times(1);
        expect(mp3Mock.currentPosition()).andReturn(0.01).times(1);
        mp3Mock.pause();
        expectLastCall();
        expect(mp3Mock.currentPosition()).andReturn(0.01).times(1);
        mp3Mock.stop();
        expectLastCall();
        expect(mp3Mock.currentPosition()).andReturn(0.01).times(1);
        replay(mp3Mock);
  
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
        expect(mp3Mock.isPlaying()).andReturn(false).times(1);
        mp3Mock.play();
        expectLastCall();
        expect(mp3Mock.isPlaying()).andReturn(false).times(1);
        expect(mp3Mock.currentPosition()).andReturn(0.01).times(1);
        mp3Mock.pause();
        expectLastCall();
        expect(mp3Mock.currentPosition()).andReturn(0.01).times(1);
        expect(mp3Mock.isPlaying()).andReturn(false).times(1);
        mp3Mock.stop();
        expectLastCall();
        expect(mp3Mock.currentPosition()).andReturn(0.01).times(1);
        expect(mp3Mock.isPlaying()).andReturn(false).times(1);
        replay(mp3Mock);
  
        // Don't set the list up
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
        mp3Mock.loadSongs(list);
        expectLastCall();
        mp3Mock.play();
        expectLastCall();
        expect(mp3Mock.isPlaying()).andReturn(true).times(1);
        mp3Mock.prev();
        expectLastCall();
        expect(mp3Mock.currentSong()).andReturn((String) list.get(0)).times(1);
        expect(mp3Mock.isPlaying()).andReturn(true).times(1);
        mp3Mock.next();
        expectLastCall();
        expect(mp3Mock.currentSong()).andReturn((String) list.get(1)).times(1);
        mp3Mock.next();
        expectLastCall();
        expect(mp3Mock.currentSong()).andReturn((String) list.get(2)).times(1);
        mp3Mock.prev();
        expectLastCall();
        expect(mp3Mock.currentSong()).andReturn((String) list.get(1)).times(1);
        mp3Mock.next();
        expectLastCall();
        expect(mp3Mock.currentSong()).andReturn((String) list.get(2)).times(1);
        mp3Mock.next();
        expectLastCall();
        expect(mp3Mock.currentSong()).andReturn((String) list.get(3)).times(1);
        mp3Mock.next();
        expectLastCall();
        expect(mp3Mock.currentSong()).andReturn((String) list.get(3)).times(1);
        expect(mp3Mock.isPlaying()).andReturn(true).times(1);
        replay(mp3Mock);
          
  
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
