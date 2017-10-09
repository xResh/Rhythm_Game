import static org.junit.Assert.*;
	import java.io.*;

import org.junit.Test;
import java.util.*;

public class SongParserTest {
  
	  @Test public void test1() throws IOException, SongParser.FormatException {
	    // Here's how to test expecting an exception...
	    try {
	      new SongParser(null);
	      fail("Expected an IllegalArgumentException - cannot create SongParser with null.");
	    } catch (IllegalArgumentException f) {    
	      //Do nothing. It's supposed to throw an exception
	    }
	  }
	  
	  @Test public void test2() throws IOException, SongParser.FormatException  {
		  SongParser c = SongParser.make("song");
		  List<StoredNote> q = c.getSheet();
		    assertEquals("try1", q.get(0).getKey(), 'q');
		    assertEquals("try2", q.get(0).getStartTime(), 0);
		    //q.remove();
		    assertEquals("try3", q.get(1).getKey(), 'o');
		    assertEquals("try4", q.get(1).getStartTime(), 60);
		    //q.remove();
		    assertEquals("try5", q.get(2).getKey(), 'w');
		    assertEquals("try6", q.get(2).getStartTime(), 100);
		    
		    assertEquals("num", c.getNum(), 19);
		  }
}
