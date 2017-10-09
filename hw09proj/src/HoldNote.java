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
    //THIS.GETPY = TOPRIGHT CORNER

    private Color color;

    /**
    * Note that, because we don't need to do anything special when constructing a Square, we simply
    * use the superclass constructor called with the correct parameters.
    */
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
    				//System.out.println("3");
    				GameCourt.incrPGOM(3);
    				//System.out.println("4");
        			GameCourt.setCombo(0);
    			}
    		}
    		int y = Math.max(this.getPy(),0);
    		int h = Math.min(startPy + HEIGHT,ch) - y;
    		if (hit) {
    			h = Math.max(lockedPy - y,0);
    			if (h == 0){
    				this.setPy(ch);
    				//System.out.println("ew");
    				awardedpointsforhold = true;
    				GameCourt.incrPGOM(0);
    				GameCourt.setCombo(GameCourt.getCombo()+1);
        			double add = GameCourt.getCombo() * 0.1;
        			GameCourt.setScore(GameCourt.getScore() + 1 + (int)add);
    			}
    		}
    		g.fillRect(this.getPx(), y, this.getWidth(), h);
    		//System.out.println(this.getPy());
    	//}
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
    		//System.out.println(startPy + (HEIGHT/2));
    		lockedPy = startPy + HEIGHT;
    		hit = true;
    		SongController.timers[GameCourt.getIndex(this.getKey())] = 3;
    		color = Color.WHITE;
    		int add;
    		double mult = GameCourt.getCombo() * 0.1;
    		int diff = Math.abs(startPy + (HEIGHT/2) - ypos);
    		if (diff < 15){
    			SongController.fx[GameCourt.getIndex(this.getKey())] = Scoring.PERFECT;
    			//System.out.println("Perfect");
    			GameCourt.incrPGOM(0);
    			GameCourt.setCombo(GameCourt.getCombo()+1);
    			add = (int)(mult * 2);
    		}
    		else if (diff < 25){
    			SongController.fx[GameCourt.getIndex(this.getKey())] = Scoring.GOOD;
    			//System.out.println("Good");
    			GameCourt.incrPGOM(1);
    			GameCourt.setCombo(GameCourt.getCombo()+1);
    			add = (int)(mult * 1.5);
    		}
    		else if (diff < 40){
    			SongController.fx[GameCourt.getIndex(this.getKey())] = Scoring.OK;
    			//System.out.println("OK");
    			GameCourt.incrPGOM(2);
    			GameCourt.setCombo(GameCourt.getCombo()+1);
    			add = (int)(mult * 1);
    		}
    		else {
    			SongController.fx[GameCourt.getIndex(this.getKey())] = Scoring.MISS;
    			//System.out.println("MISS");
    			GameCourt.incrPGOM(3);
    			GameCourt.incrPGOM(3);
    			//System.out.println("1");
    			GameCourt.setCombo(0);
    			add = 0;
    			return true;
    		}
    		GameCourt.setScore(GameCourt.getScore() + 1 + add);
    		//System.out.println(GameCourt.getScore());
    		return false;
    	}
    	}
    	else if (hit && !ispress){
    		//RELEASE EVENT BEFORE ITS REMOVED COMPLETELY
    		//System.out.println("ew");
    		int diff = Math.abs(this.getPy() - lockedPy);//distance from where u first hit
    		int diff2 = Math.abs(this.getPy() - ypos);//distance from line
    		diff = Math.min(diff, diff2);//mercy in case you hit under line
    		if (diff < 80) {
    			//System.out.println("handle");
    			if (!awardedpointsforhold){
    			GameCourt.incrPGOM(0); //get a perfect as long as you release close to end
    			GameCourt.setCombo(GameCourt.getCombo()+1);
    			double add = GameCourt.getCombo() * 0.1;
    			GameCourt.setScore(GameCourt.getScore() + 1 + (int)add);
    			}
    		}
    		else {
    			GameCourt.incrPGOM(3);
    			//System.out.println("2");
    			GameCourt.setCombo(0);
    			//System.out.println(diff);
    		}
    		return true;
    	}
    	return false;
    }
    	
}
