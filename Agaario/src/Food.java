import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

public class Food extends JComponent{
	public int x;
    public int y;
    public Color c;
    public int radius = 5;
    
    Food(int x, int y){
        this.x = x;
        this.y = y;
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        this.c = new Color(r,g,b);
    }
    
    public void paint(Graphics g){
        g.setColor(c);
        g.fillOval(this.x - 5, this.y -5, 10, 10);
    }
}
