import java.awt.Color;
import java.awt.Graphics;

public abstract class Note {
	/*
     * Current position of the object (in terms of graphics coordinates)
     *  
     * Coordinates are given by the upper-left hand corner of the object. This position should
     * always be within bounds.
     *  0 <= px <= maxX 
     *  0 <= py <= maxY 
     */
    private int px; 
    private int py;

    /* Size of object, in pixels. */
    private int width;
    private int height;

    /* Velocity: number of pixels to move every time move() is called. */
    private int vy;
    private boolean hold;

    /* 
     * Upper bounds of the area in which the object can be positioned. Maximum permissible x, y
     * positions for the upper-left hand corner of the object.
     */
    private int maxX;
    private int maxY;
    private char key;

    /**
     * Constructor
     */
    public Note(int vy, int px, int py, int width, int height, char key, int courtWidth,
        int courtHeight, boolean ishold) {
    	if (key == 'q') {this.px = 15;}
        else if (key == 'w') {this.px = 110;}
        else if (key == 'e') {this.px = 205;}
        else if (key == 'i') {this.px = 350;}
        else if (key == 'o') {this.px = 445;}
        else if (key == 'p') {this.px = 540;}
        this.vy = vy;
        //this.px = px;
        this.py = py;
        this.width  = width;
        this.height = height;

        // take the width and height into account when setting the bounds for the upper left corner
        // of the object.
        this.maxX = courtWidth;
        this.maxY = courtHeight;
        this.key = key;
        this.hold = ishold;
    }

    /*** GETTERS **********************************************************************************/
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

    /*** SETTERS **********************************************************************************/
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

    /*** UPDATES AND OTHER METHODS ****************************************************************/

    /**
     * Prevents the object from going outside of the bounds of the area designated for the object.
     * (i.e. Object cannot go outside of the active area the user defines for it).
     */ 
//    private void clip() {
//        this.px = Math.min(Math.max(this.px, 0), this.maxX);
//        this.py = Math.min(Math.max(this.py, 0), this.maxY);
//    }
    public boolean isOut() {
    	return (this.py > maxY);
    }

    /**
     * Moves the object by its velocity.  Ensures that the object does not go outside its bounds by
     * clipping.
     */
    public void move() {
        this.py += this.vy;
    }

    public abstract void draw(Graphics g);
    
    public abstract boolean handle(int p, boolean isPress);
}
