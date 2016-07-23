package agario;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Controller {
	
	ArrayList<Blob> blobs = new ArrayList<Blob>();
	private static int velocityConstant = 2000;
	public ArrayList<Dot> dots = new ArrayList<Dot>();
	Blob firstPlayer = new Blob(90, 50, 30, Color.CYAN);
	Blob secondPlayer = new Blob(20, 50, 30, Color.GREEN);
	MyFrame mf = new MyFrame("Agario");
	int mouseX = 0;
	int mouseY = 0;
	static int fScore = 30;
	static int sScore = 30;
	static int xDis = 0;
	static int yDis = 0;

	public static void main(String[] args) {
		new Controller().startGame();
	}

	public void startGame() {
		mf.addMouseMotionListener(new MyMouseMoveListener());
		Refresh rf = new Refresh();
		Thread t = new Thread(rf);
		t.start();
		while (true) {
			try {
				Random r = new Random();
				Thread.sleep(20);
				double dis = Math.sqrt(xDis * xDis + yDis * yDis);
				double easingAmount = velocityConstant / ((firstPlayer.size/2)*(firstPlayer.size/2)*Math.PI);
				if (easingAmount  < 2)
					easingAmount  = 2;
				if (dis > 5) {
					firstPlayer.x += easingAmount * xDis / dis;
					firstPlayer.y += easingAmount * yDis / dis;
					if (xDis > 0)
						xDis--;
					else if (xDis < 0)
						xDis++;
					if (yDis > 0)
						yDis--;
					else if (yDis<0)
						yDis++;
				}
				if (r.nextInt(10) == 0) {
					int randX = r.nextInt(600);
					int randY = r.nextInt(600);
					Dot d = new Dot(randX, randY);
					synchronized (dots) {
						dots.add(d);
					}
					System.out.println("firstPlayer score : " + fScore);
					System.out.println("secondPlayer score : " + sScore);
					mf.add(d);
					mf.repaint();
				}
			} catch (Exception e) {
			}
		}
	}

	class Refresh implements Runnable {
		public boolean intersect(Blob b, Dot d) {
			double dis = Math.sqrt(((b.x - d.x) * (b.x - d.x)) + ((b.y - d.y) * (b.y - d.y)));
			if (dis <= b.size - 10)
				return true;
			return false;
		}
		
		public boolean intersect(Blob a, Blob b) {
			double dis = Math.sqrt(((a.x - b.x) * (a.x - b.x)) + ((a.y - b.y) * (a.y - b.y)));
			if (dis <= Math.abs(a.size - b.size))
				return true;
			return false;
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(20);
				} catch (Exception e) {
					System.err.println("error");
				}
				synchronized (dots) {
					Iterator<Dot> i = dots.iterator();
					while (i.hasNext()) {
						Dot d = (Dot) i.next();
						if (this.intersect(firstPlayer, d)) {
							i.remove();
							firstPlayer.size += 1;
							fScore += 1;
						} else if (this.intersect(secondPlayer, d)) {
							i.remove();
							secondPlayer.size += 1;
							sScore += 1;
						} else if (this.intersect(secondPlayer, firstPlayer)) {
							if (secondPlayer.size > firstPlayer.size) {
								sScore += firstPlayer.size;
								fScore = 0;
								firstPlayer.size = 0;
							}
							else { 
								fScore += secondPlayer.size;
								sScore = 0;
								secondPlayer.size = 0;
							}
							//peighame bord biad o barname baste beshe
							System.exit(1);
							return;
						}
					}
				}
				mf.revalidate();
				mf.repaint();
			}
		}
	}

	class MyMouseMoveListener extends MouseMotionAdapter{
        public void mouseMoved(MouseEvent m){
            mouseX = m.getX();
            mouseY = m.getY();
            xDis = mouseX-firstPlayer.x;
            yDis = mouseY-firstPlayer.y;
        }
    }
	
//	class MyMouseMoveListener2 implements MouseMotionListener{
//		
//        public void mouseMoved(MouseEvent m){
//            mouseX = m.getX();
//            mouseY = m.getY();
//            xDis = mouseX-firstPlayer.x;
//            yDis = mouseY-firstPlayer.y;
//        }
//
//		@Override
//		public void mouseDragged(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//    }

	class MyFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		MyFrame(String s) {
			super(s);
			setBounds(0, 0, 900, 900);
			this.getContentPane().add(firstPlayer);
			this.getContentPane().add(secondPlayer);
			ArrowTest mainPanel = new ArrowTest();
			this.getContentPane().add(mainPanel);
			blobs.add(firstPlayer);
			blobs.add(secondPlayer);
			setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent e) {
				}
				
				@Override
				public void windowIconified(WindowEvent e) {
				}
				
				@Override
				public void windowDeiconified(WindowEvent e) {
				}
				
				@Override
				public void windowDeactivated(WindowEvent e) {
					e.getWindow().setVisible(false);
				}
				
				public void windowClosing(WindowEvent e) {
					
					if (fScore > sScore)
						JOptionPane.showMessageDialog(null, "Congratulations firstPlayer, you won " + fScore  + " on " + sScore);
					else if (fScore < sScore)
						JOptionPane.showMessageDialog(null, "Congratulations secondPlayer, you won " + fScore  + " on " + sScore);
					else
						JOptionPane.showMessageDialog(null, "It's draw... boring :(");
				}
				
				@Override
				public void windowClosed(WindowEvent e) {
				}
				
				@Override
				public void windowActivated(WindowEvent e) {
				}
			});
		}

		public void paint(Graphics g) {
			for (Blob b : blobs)
				b.paint(g);
			synchronized (dots) {
				for (Dot i : dots) {
					Dot d = (Dot) i;
					d.paint(g);
				}
			}
		}
	}

	private class MyArrowBinding extends AbstractAction {
		public MyArrowBinding(String text) {
			super(text);
			putValue(ACTION_COMMAND_KEY, text);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if (actionCommand.equals("Up"))
				secondPlayer.y--;
			else if (actionCommand.equals("Down"))
				secondPlayer.y++;
			else if (actionCommand.equals("Left"))
				secondPlayer.x--;
			else if (actionCommand.equals("Right"))
				secondPlayer.x++;
		}
	}
	
	public class ArrowTest extends JPanel {
		   private static final int PREF_W = 400;
		   private static final int PREF_H = PREF_W;

		   public ArrowTest() {
		      ActionMap actionMap = getActionMap();
		      int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		      InputMap inputMap = getInputMap(condition);

		      for (Direction direction : Direction.values()) {
		         inputMap.put(direction.getKeyStroke(), direction.getText());
		         actionMap.put(direction.getText(), new MyArrowBinding(direction.getText()));
		      }
		   }

		   private class MyArrowBinding extends AbstractAction {
		      public MyArrowBinding(String text) {
		         super(text);
		         putValue(ACTION_COMMAND_KEY, text);
		      }

		      @Override
		      public void actionPerformed(ActionEvent e) {
		         String actionCommand = e.getActionCommand();
		         if (actionCommand.equals("Up"))
						secondPlayer.y -= 5;
					else if (actionCommand.equals("Down"))
						secondPlayer.y += 5;
					else if (actionCommand.equals("Left"))
						secondPlayer.x -= 5;
					else if (actionCommand.equals("Right"))
						secondPlayer.x += 5;
		      }
		   }

		   @Override
		   public Dimension getPreferredSize() {
		      return new Dimension(PREF_W, PREF_H);
		   }	

	}
}

enum Direction {
	UP("Up", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0)), DOWN("Down",
			KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0)), LEFT("Left",
					KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0)), RIGHT("Right",
							KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));

	Direction(String text, KeyStroke keyStroke) {
		this.text = text;
		this.keyStroke = keyStroke;
	}

	private String text;
	private KeyStroke keyStroke;

	public String getText() {
		return text;
	}

	public KeyStroke getKeyStroke() {
		return keyStroke;
	}

	@Override
	public String toString() {
		return text;
	}
}
