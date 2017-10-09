import java.awt.*;
public class TapNote extends Note {
	public static final int WIDTH = 85;
    public static final int HEIGHT = 20;
    //public static final int INIT_POS_X = 0;
    public static final int INIT_POS_Y = -HEIGHT;
    //public static final int INIT_VEL_X = 0;
    //public static final int INIT_VEL_Y = 8;
    private int time;

    private Color color;

    /**
    * Note that, because we don't need to do anything special when constructing a Square, we simply
    * use the superclass constructor called with the correct parameters.
    */
    public TapNote(int courtWidth, int courtHeight, Color color, char key, int time) {
        super(GameCourt.SPEED, 0, INIT_POS_Y, WIDTH, HEIGHT, 
        		key, courtWidth, courtHeight, false);
        this.time = time;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
    	//if (!this.isOut()){
    		g.setColor(this.color);
    		g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    	//}
    }
    
    public int getTime(){
		return this.time;
	}
	
    @Override
    public boolean handle(int ypos, boolean ispress){
    	if (ispress){
    	if (this.getPy() + (HEIGHT/2) > (ypos - 75) && this.getPy() + (HEIGHT/2) < (ypos + 25)){
    		//System.out.println(this.getPy() + (HEIGHT/2));
    		int add;
    		double mult = GameCourt.getCombo() * 0.1;
    		int diff = Math.abs(this.getPy() + (HEIGHT/2) - ypos);
    		if (diff < 15){
    			SongController.fx[GameCourt.getIndex(this.getKey())] = Scoring.PERFECT;
    			//System.out.println("Perfect");
    			//System.out.println(GameCourt.counter);
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
    			GameCourt.setCombo(0);
    			add = 0;
    		}
    		GameCourt.setScore(GameCourt.getScore() + 1 + add);
    		//System.out.println(GameCourt.getScore());
    		return true;
    	}
    	}
    	return false;
    }
}
