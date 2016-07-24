import java.util.Random;

public class GameThread implements Runnable {
	GameFrame p = null;

	@Override
	public void run() {
		System.err.println("game tread started");
		Player p1 = new Player();
		p = new GameFrame("PlayGround", 400, 600);
		// TODO try to add another. dont worry :D keyboard listener has been added to the code. just add player and enjoy
		p.addToPlayers(p1);
		p.getContentPane().revalidate();
		p.getContentPane().repaint();

		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Random r = new Random();
			if (r.nextInt(20) == 0) {
				// TODO generate random x and y . it should be in the frame ,
				// not outside of it
				Food food = new Food(r.nextInt(500), r.nextInt(500));
				p.addToFoods(food);
			}
			// TODO add splitters. make new blobs and add them to player.
			// remember to add them to your game frame too :D

			// TODO check if foods are eaten , remove them , and update player
			// as you wish we had it in previous version. i think you cant copy paste it ( and ofcourse a lot of rename :D )

			// TODO what if we have more than one blob. we should have for loop
			// instead
			int xDis = p.getMouseX() - p.getPlayer(0).getBlob(0).x;
			int yDis = p.getMouseY() - p.getPlayer(0).getBlob(0).y;
			double distance = Math.sqrt((xDis) * (xDis) + (yDis) * (yDis));
			if (distance > 3) {
				
				//TODO it is too fast :/ i really don't know why !
				p.getPlayer(0).getBlob(0).x += (p.getPlayer(0).getBlob(0).getVelocity() * xDis / distance) ;
				p.getPlayer(0).getBlob(0).y += (p.getPlayer(0).getBlob(0).getVelocity() * yDis / distance) ;
			}
			p.getContentPane().revalidate();
			p.getContentPane().repaint();
		}

	}

}
