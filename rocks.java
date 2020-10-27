import processing.core.PApplet;

public class rocks {
	float xPos;
	float yPos;
	float speed;
	float ang;
	float xV;
	float yV;
	public rocks(float xPos, float yPos, float speed, float ang) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;
		this.ang = ang;
	}
	public void draw(PApplet Main) {
		Main.ellipse(xPos, yPos, 50, 50);
	}
	public void move(PApplet Main) {
		xV = speed * PApplet.cos(ang);
		yV = speed * PApplet.sin(ang);
		xPos += speed * PApplet.cos(ang);
		yPos += speed * PApplet.sin(ang);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
