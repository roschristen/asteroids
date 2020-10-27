import processing.core.PApplet;
import java.util.ArrayList;

public class explosion extends PApplet{
	int partMax;
	float xPos;
	float yPos;
	float life;
	float size;
	int r;
	int g;
	int b;
	float spread;
	float ang;
	float speed;
	ArrayList<particle> partList = new ArrayList<particle>();
	
	public explosion(int partMax,float xPos, float yPos, float life, float size, float ang, float spread, float speed, int r, int g, int b) {
		this.partMax = partMax;
		this.xPos = xPos;
		this.yPos = yPos;
		this.life = life;
		this.size = size;
		this.r = r;
		this.g = g;
		this.b = b;
		this.spread = spread;
		this.ang = ang;
		this.speed = speed;
	}
	public void generate(PApplet Main) {
		float ang2 = ang;
		for(int i = 0; i <= partMax; i++) {
			double ran = Math.random();
			double angDiff = Math.random() * spread;
			float speed2 = (float)Math.random() * speed;
			if(speed2 < 0.75) {
				speed2 += 0.75;
			}
			particle part = new particle(xPos, yPos, life, size, ang2, r, g, b, speed2);
			partList.add(part);
			if(ran < 0.5) {
				angDiff *= -1;
			}
			ang2 = ang += angDiff;
		}
	}
	public void display(PApplet Main) {
		for(particle part : partList) {
			part.display(Main);
			part.move(Main);
		}
	}
	public void update(PApplet Main) {
		int partSize = partList.size() -1;
		for(int i = partSize; i >= 0; i --) {
			particle part = partList.get(i);
		
			if(part.life <= 0) {
				partList.remove(i);
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
