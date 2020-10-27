import processing.core.PApplet;
public class torpedo {
	float xPos;
	float yPos;
	float ang;
	float speed;
	float xV;
	float yV;
	public torpedo(float xPos, float yPos, float ang, float speed) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.ang = ang;
		this.speed = speed;
	}
	public void torpedoDraw(PApplet Main) {
		Main.pushMatrix();
		Main.translate(xPos, yPos);
		Main.rotate(PApplet.radians(ang));
		Main.ellipse(0, 0, 30, 15);
		
		Main.popMatrix();
		
		
	}
	public void move(PApplet Main) {
		xV = speed*PApplet.cos(PApplet.radians(ang));
		yV = speed*PApplet.sin(PApplet.radians(ang));
		xPos += speed*PApplet.cos(PApplet.radians(ang));
		yPos += speed*PApplet.sin(PApplet.radians(ang));
	}
	public void draw() {
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
