import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class GameFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Player> players;
	private ArrayList<Food> foods;
	private MouseMovement mouseMove = null;
	public GameFrame(String s, int x, int y) {
		super(s);
		setBounds(0, 0, x, y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		players = new ArrayList<Player>();
		foods = new ArrayList<Food>();
		setVisible(true);
		KeyboardTest keyListener = new KeyboardTest();
		mouseMove = new MouseMovement();
		this.addMouseMotionListener(mouseMove);
		this.getContentPane().add(keyListener);
	}
	
	public void addToPlayers(Player p) {
		if (!this.players.contains(p))
			this.players.add(p);
		for (Blob b : p.getBlobs()) {
			this.getContentPane().add(b);
		}
	}
	
	public void addToFoods(Food f) {
		this.foods.add(f);
		this.getContentPane().add(f);
	}
	
	public int getMouseX(){
		return this.mouseMove.mouseX;
	}
	
	public int getMouseY(){
		return this.mouseMove.mouseY;
	}
	
	public Player getPlayer(int n) {
		return this.players.get(n);
	}
	
	class KeyboardTest extends JPanel {

		private static final long serialVersionUID = 1L;
		private static final int PREF_W = 400;
		private static final int PREF_H = PREF_W;

		public KeyboardTest() {
			ActionMap actionMap = getActionMap();
			int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
			InputMap inputMap = getInputMap(condition);

			for (Direction direction : Direction.values()) {
				inputMap.put(direction.getKeyStroke(), direction.getText());
				actionMap.put(direction.getText(), new KeyBoardMoving(direction.getText()));
			}
		}

		private class KeyBoardMoving extends AbstractAction {

			private static final long serialVersionUID = 1L;

			public KeyBoardMoving(String text) {
				super(text);
				putValue(ACTION_COMMAND_KEY, text);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO what if we have more than one blob. what should we do. for loop is awfullt bad for performance.
				//     what else we can do ?
				//TODO speed ?! 
				String actionCommand = e.getActionCommand();
				if (actionCommand.equals("UP"))
					players.get(1).getBlob(0).y -= players.get(1).getBlob(0).getVelocity();
				else if (actionCommand.equals("DOWN"))
					players.get(1).getBlob(0).y += players.get(1).getBlob(0).getVelocity();
				else if (actionCommand.equals("LEFT"))
					players.get(1).getBlob(0).x -= players.get(1).getBlob(0).getVelocity();
				else if (actionCommand.equals("RIGHT"))
					players.get(1).getBlob(0).x += players.get(1).getBlob(0).getVelocity();
				else if (actionCommand.equals("UP_RIGHT")) {
					players.get(1).getBlob(0).x += players.get(1).getBlob(0).getVelocity();
					players.get(1).getBlob(0).y -= players.get(1).getBlob(0).getVelocity();
				} else if (actionCommand.equals("DOWN_RIGHT")) {
					players.get(1).getBlob(0).x += players.get(1).getBlob(0).getVelocity();
					players.get(1).getBlob(0).y += players.get(1).getBlob(0).getVelocity();
				} else if (actionCommand.equals("UP_LEFT")) {
					players.get(1).getBlob(0).x -= players.get(1).getBlob(0).getVelocity();
					players.get(1).getBlob(0).y -= players.get(1).getBlob(0).getVelocity();
				} else if (actionCommand.equals("DOWN_LEFT")) {
					players.get(1).getBlob(0).x -= players.get(1).getBlob(0).getVelocity();
					players.get(1).getBlob(0).y += players.get(1).getBlob(0).getVelocity();
				}
			}
		}
	}
	
	class MouseMovement  implements MouseMotionListener{
		public int mouseX ;
		public int mouseY ;

		@Override
		public void mouseMoved(MouseEvent m) {
			mouseX = m.getX();
			mouseY = m.getY();
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			
		}
	}
}


enum Direction {
	
	UP("UP", KeyStroke.getKeyStroke(KeyEvent.VK_W, 0)), 
	UP_LEFT("UP_LEFT",KeyStroke.getKeyStroke(KeyEvent.VK_Q,0)),
	LEFT("LEFT",KeyStroke.getKeyStroke(KeyEvent.VK_A, 0)), 
	DOWN_LEFT("DOWN_LEFT",KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0)),
	DOWN("DOWN",KeyStroke.getKeyStroke(KeyEvent.VK_S, 0)),
	DOWN_RIGHT("DOWN_RIGHT",KeyStroke.getKeyStroke(KeyEvent.VK_C, 0)),
	RIGHT("RIGHT",KeyStroke.getKeyStroke(KeyEvent.VK_D, 0)),
	UP_RIGHT("UP_RIGHT",KeyStroke.getKeyStroke(KeyEvent.VK_E, 0)),;

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


