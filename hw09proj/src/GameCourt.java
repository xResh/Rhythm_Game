import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.*;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    public boolean playing = false; // whether the game is running 
    private JLabel score_status;
    private JLabel combo_status;
    private JButton playbutton;
    
    public static final String instructionsFile = "instructions.png";
    private static BufferedImage instructions;

    public static final int COURT_WIDTH = 640;
    public static final int COURT_HEIGHT = 360;
    public static int SPEED;
   
    private static int counter;
    private static int endtimer;
    private static boolean end;
    private static int score;
    private static int combo;
    private static int[] PGOM;
    
    private static List<StoredNote> NOTES;
    private static int tracker;
    private static int buffer = 70;
    private static int musicbuffer;
    private static Clip mainmusic;
    private static double SYNCFACTOR = 0.985031;//2 * (60 / 88.0) - 0.02; //0.985031;
    private static int numNotes;
    private static int playedNotes;
    
    Map<Integer, Queue<Note>> PLAYS = new TreeMap<Integer, Queue<Note>>();
    
    
    private SongController BAR;
    
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 25; //40 in a second
    
    public static void fill(Map<Integer, Queue<Note>> empty) {
    	empty.put((int)'q', new LinkedList<Note>());
    	empty.put((int)'w', new LinkedList<Note>());
    	empty.put((int)'e', new LinkedList<Note>());
    	empty.put((int)'i', new LinkedList<Note>());
    	empty.put((int)'o', new LinkedList<Note>());
    	empty.put((int)'p', new LinkedList<Note>());
    }
    
    public static int getIndex (char c) {
    	if (c == 'q') {return 0;}
    	else if (c == 'w') {return 1;}
    	else if (c == 'e') {return 2;}
    	else if (c == 'i') {return 3;}
    	else if (c == 'o') {return 4;}
    	else if (c == 'p') {return 5;}
    	else {return -1;}
    }
    
    public static int getScore() {
    	return score;
    }
    
    public static int getCombo() {
    	return combo;
    }
    
    public static int getTime() {
    	return counter;
    }
    
    public static int getPGOM(int i) {
    	return PGOM[i];
    }
    
    public static void setScore(int s) {
    	score = s;
    }
    
    public static void setCombo(int c) {
    	combo = c;
    }
    
    
    
    public static void incrPGOM(int i) {
    	PGOM[i]++;
    	playedNotes++;
    	if (playedNotes == numNotes){
    		end = true;
    	}
    }
    
    public GameCourt(JLabel sc, JLabel cb, Clip cl, int num, List<StoredNote> notes, int speed) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        fill(PLAYS);
        NOTES = notes;
        numNotes = num;
        mainmusic = cl;
        SPEED = speed;
        
        
        try {
            if (instructions == null) {
                instructions = ImageIO.read(new File(instructionsFile));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        
	//timer
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); //START THE TIMER!

        setFocusable(true);

        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                	SongController.pressed[1] = true;
                    if(!PLAYS.get((int)'w').isEmpty()){ // if something is in play in this column
                    	if (PLAYS.get((int)'w').peek().handle(BAR.getYpos(), true)) {
                    		PLAYS.get((int)'w').remove();
                    		SongController.timers[getIndex('w')] = 3;
                    	}
                    }  
                }
                else if (e.getKeyCode() == KeyEvent.VK_Q) {
                	SongController.pressed[0] = true;
                    if(!PLAYS.get((int)'q').isEmpty()){
                    	if (PLAYS.get((int)'q').peek().handle(BAR.getYpos(), true)) {
                    		PLAYS.get((int)'q').remove();
                    		SongController.timers[getIndex('q')] = 3;
                    	}
                    }  
                }
                else if (e.getKeyCode() == KeyEvent.VK_E) {
                	SongController.pressed[2] = true;
                    if(!PLAYS.get((int)'e').isEmpty()){
                    	if (PLAYS.get((int)'e').peek().handle(BAR.getYpos(), true)) {
                    		PLAYS.get((int)'e').remove();
                    		SongController.timers[getIndex('e')] = 3;
                    	}
                    }  
                }
                else if (e.getKeyCode() == KeyEvent.VK_I) {
                	SongController.pressed[3] = true;
                    if(!PLAYS.get((int)'i').isEmpty()){
                    	if (PLAYS.get((int)'i').peek().handle(BAR.getYpos(), true)) {
                    		PLAYS.get((int)'i').remove();
                    		SongController.timers[getIndex('i')] = 3;
                    	}
                    }  
                }
                else if (e.getKeyCode() == KeyEvent.VK_O) {
                	SongController.pressed[4] = true;
                    if(!PLAYS.get((int)'o').isEmpty()){
                    	if (PLAYS.get((int)'o').peek().handle(BAR.getYpos(), true)) {
                    		PLAYS.get((int)'o').remove();
                    		SongController.timers[getIndex('o')] = 3;
                    	}
                    }  
                }
                else if (e.getKeyCode() == KeyEvent.VK_P) {
                	SongController.pressed[5] = true;
                    if(!PLAYS.get((int)'p').isEmpty()){
                    	if (PLAYS.get((int)'p').peek().handle(BAR.getYpos(), true)) {
                    		PLAYS.get((int)'p').remove();
                    		SongController.timers[getIndex('p')] = 3;
                    	}
                    }  
                }
                
            }

            public void keyReleased(KeyEvent e) {
            	if (e.getKeyCode() == KeyEvent.VK_W) {
                	SongController.pressed[1] = false;
                	if(!PLAYS.get((int)'w').isEmpty()){ // if something is in play in this column
                    	if (PLAYS.get((int)'w').peek().handle(BAR.getYpos(), false)) {
                    		PLAYS.get((int)'w').remove();
                    	}
                    } 
                	}
            	else if (e.getKeyCode() == KeyEvent.VK_Q) {
            		SongController.pressed[0] = false;
            		if(!PLAYS.get((int)'q').isEmpty()){ // if something is in play in this column
                    	if (PLAYS.get((int)'q').peek().handle(BAR.getYpos(), false)) {
                    		PLAYS.get((int)'q').remove();
                    	}
                    } 
                	}
            	else if (e.getKeyCode() == KeyEvent.VK_E) {
            		SongController.pressed[2] = false;
            		if(!PLAYS.get((int)'e').isEmpty()){ // if something is in play in this column
                    	if (PLAYS.get((int)'e').peek().handle(BAR.getYpos(), false)) {
                    		PLAYS.get((int)'e').remove();
                    	}
                    } 
                	}
            	else if (e.getKeyCode() == KeyEvent.VK_I) {
            		SongController.pressed[3] = false;
            		if(!PLAYS.get((int)'i').isEmpty()){ // if something is in play in this column
                    	if (PLAYS.get((int)'i').peek().handle(BAR.getYpos(), false)) {
                    		PLAYS.get((int)'i').remove();
                    	}
                    } 
                	}
            	else if (e.getKeyCode() == KeyEvent.VK_O) {
            		SongController.pressed[4] = false;
                	if(!PLAYS.get((int)'o').isEmpty()){ // if something is in play in this column
                    	if (PLAYS.get((int)'o').peek().handle(BAR.getYpos(), false)) {
                    		PLAYS.get((int)'o').remove();
                    	}
                    } 
                	}
            	else if (e.getKeyCode() == KeyEvent.VK_P) {
            		SongController.pressed[5] = false;
            		if(!PLAYS.get((int)'p').isEmpty()){ // if something is in play in this column
                    	if (PLAYS.get((int)'p').peek().handle(BAR.getYpos(), false)) {
                    		PLAYS.get((int)'p').remove();
                    	}
                    } 
                	}
            }
        });

        this.score_status = sc;
        this.combo_status = cb;
    }
    //--------------------------

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
    	playbutton.setText("Retry");
    	if (mainmusic != null){
        	mainmusic.setFramePosition(0);
        	mainmusic.stop();
    	}
    	
    	BAR = new SongController(Color.WHITE, COURT_WIDTH);

    	PLAYS = new TreeMap<Integer, Queue<Note>>();
    	fill(PLAYS);
    	
    	counter = -buffer;
    	musicbuffer = counter - (int)Math.round(320.0 / SPEED);
    	tracker = 0;
    	endtimer = 60;
    	end = false;
    	score = 0;
    	combo = 0;
    	PGOM = new int[4];
    	playedNotes = 0;

        playing = true;
        score_status.setText("Score: "+ score);
        combo_status.setText("Combo: "+ combo);

        requestFocusInWindow();
    }
    
    public void instruct(JButton button){
    	this.playbutton = button;
    	repaint();
    }

    //EVERY TICK
    void tick() {
        if (playing) {
        	
            for (Integer q : PLAYS.keySet()){
	            for (Note s : PLAYS.get(q)) {
	            	s.move();
	            }
	            
	            if (!PLAYS.get(q).isEmpty() && PLAYS.get(q).peek().isOut()){
	            	if (!PLAYS.get(q).peek().isHold()){
	            		combo = 0;
	            		incrPGOM(3);
	            	}
	            	PLAYS.get(q).remove();
	            }
            }
            
            if (tracker < NOTES.size()) {
            	try {
	            while (NOTES.get(tracker).getStartTime()*SYNCFACTOR <= counter){ //IF THE TIME IS NOW
	            	char k = NOTES.get(tracker).getKey();
	            	Note s;
	            	if (NOTES.get(tracker).isHold()){
	            		s = new HoldNote(COURT_WIDTH, COURT_HEIGHT, Color.BLACK, k, 
	            				(int)(NOTES.get(tracker).getStartTime() * SYNCFACTOR), (int)(NOTES.get(tracker).getEndTime() * SYNCFACTOR));;
	            	}
	            	else {
	            		s = new TapNote(COURT_WIDTH, COURT_HEIGHT, Color.BLACK, k, NOTES.get(tracker).getStartTime());

	            	}
	            	PLAYS.get((int)k).add(s);
	            	tracker++;
	            }
            	} catch (Exception n){
            	}
            }
            score_status.setText("Score: "+ score);
            combo_status.setText("Combo: "+ combo);
            
            if (end) {
            	if (endtimer != 0){
            		endtimer--;
            	}
            	else {
            		playing = false;
            		score_status.setText("Final Score: " + score);
            		combo_status.setText("Perfect/Good/OK/Miss: " + PGOM[0] + " / "
									            				+ PGOM[1] + " / "
									            				+ PGOM[2] + " / "
									            				+ PGOM[3]);
            	}
            }
            counter ++;
            if (musicbuffer == 0 && mainmusic != null){
            	mainmusic.start();
            }
            else if (musicbuffer < 0){
            	musicbuffer++;
            }
            
            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (BAR == null){
        	g.drawImage(instructions,0, 0, COURT_WIDTH,COURT_HEIGHT, null);
        }
        else {
        for (Integer q : PLAYS.keySet()){
            for (Note s : PLAYS.get(q)) {
            	s.draw(g);
            }
        }
        	BAR.draw(g);
        }
    }
    

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}
