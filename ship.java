import processing.core.PApplet;

public class ship {
	public static void shipDraw(PApplet Main,float xPos, float yPos, float ang) {
		Main.fill(255);
		Main.translate(Main.width/2 + xPos, Main.height/2 + yPos);
		Main.rotate(PApplet.radians(ang));
		Main.triangle(-25, -38, - 25, 38, 25, 0);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
