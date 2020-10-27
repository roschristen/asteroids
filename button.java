import processing.core.PApplet;
public class button {
	float xPos;
	float yPos;
	float width;
	float height;
	float r;
	float g;
	float b;
	String name;
	public button(float xPos, float yPos, float width, float height, float r, float g,float b, String name) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.r = r;
		this.g = g;
		this.b = b;
		this.name = name;
	}
	public void draw(PApplet Main) {
		Main.fill(r,g,b);
		Main.rect(xPos, yPos, width, height);
		Main.fill(255);
		Main.text(name, xPos + 12, yPos + (height-5));
	}
	public boolean hover(PApplet Main) {
		//checks to see if mouse is hovering over button, then returns a boolean
		if(Main.mouseX <= (xPos + width) && Main.mouseX >= xPos && Main.mouseY <=(yPos + height) && Main.mouseY >= yPos) {
			return true;
		}
		else {
		return false;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
