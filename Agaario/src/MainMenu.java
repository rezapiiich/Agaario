import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainMenu extends JFrame implements ActionListener{
	
	
	public static void main(String[] args) {
		MainMenu m = new MainMenu(500, 500);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int width, height;
	JButton play = null;
	
	public MainMenu(int width, int height) {
		
		this.width = width;
		this.height = height;
		this.play = new JButton("Play as guest");
		addButtons();
		setSize(width, height);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setTitle("MainMenu");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		requestFocus();
	}
	
	private void addButtons() {
		play.addActionListener(this);
		add(play);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();
		if (source == play) {
			this.dispose();
			GameThread c = new GameThread();
			Thread t = new Thread(c);
			t.start();
		} 
	}
}
