import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private ArrayList<Blob> blobs;
	
	public Player() {
		blobs = new ArrayList<Blob>();
		Blob bb = new Blob(90, 50, 30, Color.CYAN);
		addToBlobs(bb);
	}
	
	public Blob getBlob(int n) {
		return this.blobs.get(n);
	}
	
	public void addToBlobs(Blob b) {
		this.blobs.add(b);
	}
	
	public ArrayList<Blob> getBlobs() {
		return this.blobs;
	}
}
