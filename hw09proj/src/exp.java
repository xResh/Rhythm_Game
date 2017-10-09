import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

import org.junit.Test;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.AWTException;
import java.awt.Robot;

public class exp {
  
	  
	  @Test public void test1() throws IOException, SongParser.FormatException, UnsupportedAudioFileException, LineUnavailableException, AWTException  {
		  SongParser c = SongParser.make("ugh");
		  List<StoredNote> q = c.getSheet();
		  int n = c.getNum();
//		  File in = new File("ConceptOfReality.wav");
//	    	AudioInputStream ais = AudioSystem.getAudioInputStream(in);
//	    	Clip clip = AudioSystem.getClip();
//	    	clip.open(ais);
		  final GameCourt court = new GameCourt(new JLabel(""), new JLabel(""), null, n, q, 12);
		  court.instruct(new JButton(""));
		  court.reset();
		  //game setup
		  while (court.playing){
			  if(court.getTime() == 0){
				  //KeyEvent key = new KeyEvent(court, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_Q, 'Q');
				  //court.getKeyListeners()[0].keyPressed(key);
//				  Robot robot = new Robot();
//				  robot.keyPress(KeyEvent.VK_Q);
//				  assertEquals("p1", court.getPGOM(0), 0);
				  System.out.println("o");
			  }
		  }

	  }
}
