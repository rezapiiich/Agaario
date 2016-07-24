import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Blob extends JComponent{
	public int x;
    public int y;
    public int radius;
    public Color color;
    public static Blob blob = new Blob(800,100,40,Color.blue);
    private final int CONST = 5000;
    
    Blob(int x, int y, int radius, Color c){
        //x va y mokhtasate markaze blob ha hastand
    	this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = c;
    }
    
    //TODO now x and y s are correct. just use them
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval((int) x - (int) this.radius, (int) y - (int) this.radius, (int) this.radius * 2,
				(int) this.radius * 2);
    }
    
    double getVelocity() {
		return (CONST) / (Math.PI * this.radius * this.radius);
	}
}
