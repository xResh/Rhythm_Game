import java.awt.*;

public class HoldNote extends Note{
	public static final int WIDTH = 85;
    public static final int HEIGHT = 20;
    public static final int INIT_VEL_X = 0;
    private int starttime;
    private int endtime;
    private int startPy = -HEIGHT;
    private int cw;
    private int ch;
    public boolean hit = false;
    private int lockedPy;
    private boolean awardedpointsforhold  = false;

    private Color color;

    public HoldNote(int courtWidth, int courtHeight, Color color, char key, int start, int end) {
        super(GameCourt.SPEED, 0, -calc(GameCourt.SPEED, (end - start)) , WIDTH, HEIGHT, 
        		key, courtWidth, courtHeight, true);
        this.starttime = start;
        this.endtime = end;
        this.color = color;
        this.cw = courtWidth;
        this.ch = courtHeight;
    }
    
    public static int calc(int speed, int duration){
    	return speed * duration;
    }
    
    @Override
    public void move() {
        this.setPy(this.getPy() + this.getVy());
        startPy = startPy + this.getVy();
    }
    
    @Override
    public void draw(Graphics g) {
    		g.setColor(this.color);
    		if (startPy + HEIGHT > ch && !hit){
    			if (color != Color.DARK_GRAY){
    				this.color = Color.DARK_GRAY;
    				GameCourt.incrPGOM(3);
    				GameCourt.incrPGOM(3);
        			GameCourt.setCombo(0);
    			}
    		}
    		int y = Math.max(this.getPy(),0);
    		int h = Math.min(startPy + HEIGHT,ch) - y;
    		if (hit) {
    			h = Math.max(lockedPy - y,0);
    			if (h == 0){
    				this.setPy(ch);
    				awardedpointsforhold = true;
    				GameCourt.incrPGOM(0);
    				GameCourt.setCombo(GameCourt.getCombo()+1);
        			double add = GameCourt.getCombo() * 0.1;
        			GameCourt.setScore(GameCourt.getScore() + 1 + (int)add);
    			}
    		}
    		g.fillRect(this.getPx(), y, this.getWidth(), h);
    }
    
    public int getStartTime(){
		return this.starttime;
	}
    
    public int getEndTime(){
		return this.endtime;
	}
    
    @Override
    public boolean handle(int ypos, boolean ispress){
    	if (!hit && ispress){
    	if (startPy + (HEIGHT/2) > (ypos - 75) && startPy + (HEIGHT/2) < (ypos + 25)){
    		lockedPy = startPy + HEIGHT;
    		hit = true;
    		SongController.timers[GameCourt.getIndex(this.getKey())] = 3;
    		color = Color.WHITE;
    		int add;
    		double mult = GameCourt.getCombo() * 0.1;
    		int diff = Math.abs(startPy + (HEIGHT/2) - ypos);
    		if (diff < 15){
    			SongController.fx[GameCourt.getIndex(this.getKey())] = Scoring.PERFECT;
    			GameCourt.incrPGOM(0);
    			GameCourt.setCombo(GameCourt.getCombo()+1);
    			add = (int)(mult * 2);
    		}
    		else if (diff < 25){
    			SongController.fx[GameCourt.getIndex(this.getKey())] = Scoring.GOOD;
    			GameCourt.incrPGOM(1);
    			GameCourt.setCombo(GameCourt.getCombo()+1);
    			add = (int)(mult * 1.5);
    		}
    		else if (diff < 40){
    			SongController.fx[GameCourt.getIndex(this.getKey())] = Scoring.OK;
    			GameCourt.incrPGOM(2);
    			GameCourt.setCombo(GameCourt.getCombo()+1);
    			add = (int)(mult * 1);
    		}
    		else {
    			SongController.fx[GameCourt.getIndex(this.getKey())] = Scoring.MISS;
    			GameCourt.incrPGOM(3);
    			GameCourt.incrPGOM(3);
    			GameCourt.setCombo(0);
    			add = 0;
    			return true;
    		}
    		GameCourt.setScore(GameCourt.getScore() + 1 + add);
    		return false;
    	}
    	}
    	else if (hit && !ispress){
    		//RELEASE EVENT BEFORE ITS REMOVED COMPLETELY
    		int diff = Math.abs(this.getPy() - lockedPy);//distance from where u first hit
    		int diff2 = Math.abs(this.getPy() - ypos);//distance from line
    		diff = Math.min(diff, diff2);//mercy in case you hit under line
    		if (diff < 80) {
    			if (!awardedpointsforhold){
    			GameCourt.incrPGOM(0); //get a perfect as long as you release close to end
    			GameCourt.setCombo(GameCourt.getCombo()+1);
    			double add = GameCourt.getCombo() * 0.1;
    			GameCourt.setScore(GameCourt.getScore() + 1 + (int)add);
    			}
    		}
    		else {
    			GameCourt.incrPGOM(3);
    			GameCourt.setCombo(0);
    		}
    		return true;
    	}
    	return false;
    }
    	
}
