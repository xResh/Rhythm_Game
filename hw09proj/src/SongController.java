import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SongController {
	public static int WIDTH;
    public static int HEIGHT = 2;
    private static int YPOS = 300;
	private Color color;
	public static boolean pressed[] = new boolean[6];
	public static Scoring fx[] = new Scoring[6];
	private static int xvals[] = new int[]{10,105,200,345,440,535};
	public static int timers[] = new int[6];
	
	private static BufferedImage img_white;
	private static BufferedImage img_pink;
	private static BufferedImage img_purple;
	private static BufferedImage img_red;
	
	public SongController (Color c, int stageWidth){
		WIDTH = stageWidth;
		this.color = c;
		for (int i = 0; i <6; i++) {
			fx[i] = Scoring.EMPTY;
		}
		try {
            if (img_white == null) {
                img_white = ImageIO.read(new File("note_white.png"));
            }
            if (img_pink == null) {
                img_pink = ImageIO.read(new File("note_pink.png"));
            }
            if (img_purple == null) {
                img_purple = ImageIO.read(new File("note_purple.png"));
            }
            if (img_red == null) {
                img_red = ImageIO.read(new File("note_red.png"));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
	}
	
	public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(0, YPOS, WIDTH, HEIGHT);
        //keylights
        for (int i = 0; i < 6;i++){
        	if (pressed[i]){
        		g.fillRect(xvals[i], YPOS, 95, 10);
        	}
        	else {
        		g.drawRect(xvals[i], YPOS, 95, 10);
        	}
        }
        //hitlights
        for (int i = 0; i < 6;i++){
        	switch (fx[i]){
        	case PERFECT: if (timers[i] != 0){
        					g.drawImage(img_white, xvals[i]+5, YPOS-30, 85, 30, null);
        					timers[i]--;
				        	}
				        	else{
				        		fx[i] = Scoring.EMPTY;
				        	}
        					break;
        	case GOOD: if (timers[i] != 0){
			        		g.drawImage(img_pink, xvals[i]+5, YPOS-30, 85, 30, null);
			        		timers[i]--;
			        	}
			        	else{
			        		fx[i] = Scoring.EMPTY;
			        	} 
			  			break;
        	case OK: if (timers[i] != 0){
			        		g.drawImage(img_purple, xvals[i]+5, YPOS-30, 85, 30, null);
			        		timers[i]--;
			        	}
			        	else{
			        		fx[i] = Scoring.EMPTY;
			        	} 
  						break;
        	case MISS: if (timers[i] != 0){
			        		g.drawImage(img_red, xvals[i]+5, YPOS-30, 85, 30, null);
			        		timers[i]--;
			        	}
			        	else{
			        		fx[i] = Scoring.EMPTY;
			        	}
  						break;	
        	case EMPTY: break;
        	}
        }
    }
	
	public int getYpos(){
		return YPOS;
	}
}
