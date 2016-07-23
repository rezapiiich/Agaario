package agario;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Random;

public class Blob extends Component{
    
	public int x;
    public int y;
    public int size;
    public Color color;
    public static Blob blob = new Blob(800,100,40,Color.blue);
    
    Blob(int x, int y, int size, Color c){
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = c;
    }
    
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

}