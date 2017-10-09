import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Scanner;
import java.util.List;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Game implements Runnable {
	
	private Clip music;
	private int numNotes;
	private List<StoredNote> NOTES;
	private int speed;
	
	public Game (Clip music, SongParser sp, int speed){
		this.music = music;
		this.numNotes = sp.getNum();
		this.NOTES = sp.getSheet();
		this.speed = speed;
	}

    public void run() {
        final JFrame frame = new JFrame("Rhythm Game");
        frame.setResizable(false);
        frame.setLocation(300, 100);

        // Status panel
        final JPanel score_panel = new JPanel();
        score_panel.setBackground(Color.BLACK);
        GridLayout g = new GridLayout(2,1);
        g.setVgap(6);
        score_panel.setLayout(g);
        frame.add(score_panel, BorderLayout.SOUTH);
        final JLabel score = new JLabel("Score: 0");
        final JLabel combo = new JLabel("Combo: 0");
        score.setForeground(Color.WHITE);
        combo.setForeground(Color.WHITE);
        score.setHorizontalAlignment(JLabel.CENTER);
        combo.setHorizontalAlignment(JLabel.CENTER);
        score_panel.add(score);
        score_panel.add(combo);

        // Main playing area
        final GameCourt court = new GameCourt(score, combo, music, numNotes, NOTES, speed);
        court.setBackground(Color.GRAY);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        control_panel.setBackground(Color.BLACK);
        frame.add(control_panel, BorderLayout.NORTH);
	    
        final JButton reset = new JButton("Play");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        court.instruct(reset);
    }
    
	private static SongParser getParser(Scanner sc) {
		while (true) {
		      try {
		        String response = sc.nextLine();
		        SongParser c = SongParser.make(response);
		        return c;
		      } catch (Exception e) {
		        //something bad, prompt again
		      }
		      System.out.println("Invalid notemap. Try again!");
		    }
	  }
	
	private static Clip getMusic(Scanner sc) {
		while (true) {
			try {
				String response = sc.nextLine();
				if (response.equals("")){
					return null;
				}
	        	File in = new File(response);
		    	AudioInputStream ais = AudioSystem.getAudioInputStream(in);
		    	Clip clip = AudioSystem.getClip();
		    	clip.open(ais);
		    	return clip;
		      } catch (Exception e) {
		        //something bad, prompt again
		      }
		      System.out.println("Invalid music filename. Try again!");
		    }
	  }
	
	private static int getSpeed(Scanner sc) {
	    while (true) {
	      try {
	        int response = Integer.parseInt(sc.next());
	        if (response >= 6 && response <= 12) {
	          return response;
	        }
	      } catch (NumberFormatException ex) {
	        // Was not a number. Ignore and prompt again.
	      }
	      System.out.println("Invalid speed. Try again!");
	    }
	  }

    //main method
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
		System.out.println("Enter in the filename of your notemap (.txt) file: ");
		SongParser sp = getParser(sc);
		System.out.println("Enter in the filename of your song (.wav) file (Leave blank for silent play): ");
		Clip mus = getMusic(sc);
		System.out.println("Enter the speed you wish to play at (6 - 12)");
		int speed = getSpeed(sc);
        SwingUtilities.invokeLater(new Game(mus, sp, speed));
    }
}
