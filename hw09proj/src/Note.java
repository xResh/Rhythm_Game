import java.awt.Color;
import java.awt.Graphics;

public abstract class Note {
    private int px; 
    private int py;

    private int width;
    private int height;

    private int vy;
    private boolean hold;

    private int maxX;
    private int maxY;
    private char key;

    public Note(int vy, int px, int py, int width, int height, char key, int courtWidth,
        int courtHeight, boolean ishold) {
    	if (key == 'q') {this.px = 15;}
        else if (key == 'w') {this.px = 110;}
        else if (key == 'e') {this.px = 205;}
        else if (key == 'i') {this.px = 350;}
        else if (key == 'o') {this.px = 445;}
        else if (key == 'p') {this.px = 540;}
        this.vy = vy;
        this.py = py;
        this.width  = width;
        this.height = height;

        this.maxX = courtWidth;
        this.maxY = courtHeight;
        this.key = key;
        this.hold = ishold;
    }

    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }
    
    public int getVy() {
        return this.vy;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public char getKey(){
		return this.key;
	}
    
    public boolean isHold(){
		return this.hold;
	}

    public void setPx(int px) {
        this.px = px;
        //clip();
    }

    public void setPy(int py) {
        this.py = py;
        //clip();
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public boolean isOut() {
    	return (this.py > maxY);
    }

	//MOVE
    public void move() {
        this.py += this.vy;
    }

    public abstract void draw(Graphics g);
    
    public abstract boolean handle(int p, boolean isPress);
}
