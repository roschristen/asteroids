import processing.core.PApplet;

public class particle {
	float xPos;
	float yPos;
	float life;
	float size;
	int r;
	int g;
	int b;
	float ang;
	float speed;
	public particle(float xPos, float yPos, float life, float size, float ang, int r, int g, int b, float speed) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.life = life;
		this.size = size;
		this.r = r;
		this.g = g;
		this.b = b;
		this.ang = ang;
		this.speed = speed;
	}
	public void move(PApplet Main) {
		xPos += speed * PApplet.cos(ang);
		yPos += speed * PApplet.sin(ang);
	}
	public void display(PApplet Main) {
		Main.fill(r, g, b, life);
		Main.noStroke();	
		Main.ellipse(xPos, yPos, size, size);
		life -= 2;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
