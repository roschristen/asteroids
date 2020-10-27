import processing.core.PApplet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class asteroids2 extends PApplet{
	public void settings() {
		size(1000, 800);
	}

//VARIABLE DECLARATIONS
	//ship variables
	float xPos = 0;
	float yPos = 38;
	float speed = 1;
	float ang = 0;
	boolean keyPressRight = false;
	boolean keyPressLeft = false;
	boolean keyPressUp = false;
	boolean upReleased = false;
	boolean shipStop = true;
	float shipSpeed = 0;
	float turnSpeed = 6;
	boolean stillAlive = true;
	
	//torpedo variables
	float torpAng = 0;
	float torpXV = 0;
	float torpYV = 0;
	
	//asteroid variables
	float rockAng = 0;
	float rockSpeed = 0;
	float rockXV = 0;
	float rockYV = 0;
	float xPosR = 0;
	float yPosR = 0;
	boolean maxCap = false;
	float sidePick = random(4);
	int maxAst = 10;
	
	//particle variables
	float size = 10;
	int life = 255;

	//button variables
	int buttonR = 100;
	int buttonG = 100;
	int buttonB = 150;
	boolean hover = false;
	boolean hover2 = false;
	
	//score variables
	int score2 = 0;
	boolean enterName = false;
	boolean letterAdded = false;
	boolean dispHighScore = false;
	boolean dispName = false;
	String name = "";
	
	//timer variables
	int savedTime = 0;
	int savedTimeRocks = 0;
	boolean setTime = true;
	boolean setTimeRocks= true;
	boolean timePassed = true;
	boolean timePassedRocks = true;
	
	//arraylist declarations
	ArrayList<torpedo> torpList = new ArrayList<torpedo>();
	ArrayList<rocks> rockList = new ArrayList<rocks>();
	ArrayList<explosion> expList = new ArrayList<explosion>();
	ArrayList<String> nameLetters = new ArrayList<String>();
	ArrayList<String> highScores = new ArrayList<String>();
	ArrayList<String> nameHighScores = new ArrayList<String>();
	ArrayList<String> scores = new ArrayList<String>();
	
//KEYBOARD AND MOUSE INPUT
	public void keyPressed() {
		if(key == CODED) {
			//keychecks for movement code
			if(keyCode == RIGHT) {
				keyPressRight = true;
			}
			else if (keyCode == LEFT) {
				keyPressLeft = true;
			}
			if(keyCode == UP) {
				//variables for ship drift and movement
				keyPressUp = true;
				upReleased = false;
				shipSpeed = 4;
				shipStop = false;
			}
			
		}
		
		//torpedo fire check
		if(key == ' ') {
			if(timePassed && stillAlive) {
			//torpedo creation
			torpedo torp = new torpedo(xPos + width/2, yPos + height/2, ang, 20);
			torpList.add(torp);
			timePassed = false;
			}
			//spacebar restart
			if(!stillAlive && dispHighScore) {
				reset();
			}
		}	
		//torpedo timer call
		timer(50);
		
		if(key != CODED) {
		//backspace check to allow letter deletion
		if((key == BACKSPACE) && !(name.length() <= 0) ) {
			//remove letters from name array
			nameLetters.remove(nameLetters.size() - 1);
				}
		else {
			//check to append letters to name (still not sure how to make sure it only appends letters)
			if(enterName && key != ENTER) {
				//conversion of key char to string
				String keyS = String.valueOf(key);
				//addition to name array
				nameLetters.add(keyS);
				//boolean to combine letters 
				letterAdded = true;
				}
			}
		}
			//Enter key check to submit name and view high scores
			if(keyCode == ENTER && !stillAlive && !dispHighScore) {
				//display high scores boolean
				dispHighScore = true;
				//display name boolean
				dispName = false;
				//allow name editing boolean
				enterName = false;
				try {
					//writes new score to file
					score.writeHighScores(name, score2, "highScoreList.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					//reads and sorts high scores from file, then exports as an arraylist
					stringInventory inv = score.exportScores("highScoreList.txt");
					highScores = inv.scoreSort;
					nameHighScores = inv.nameSort;
					scores = highScores;
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
				
			}
	
	//key checks to allow torpedos to fire during motion
	public void keyReleased() {
		if(key == CODED) {
			if(keyCode == RIGHT) {
				keyPressRight = false;
			}
			if(keyCode == LEFT) {
				keyPressLeft = false;
			}
			if(keyCode == UP) {
				//booleans used for ship drift
				upReleased = true;
				shipStop = true;
			}
		}
		
	}
	
	//mouseClicked function
	public void mouseClicked() {
		//checks if button has been clicked on
		if(hover) {
			reset();
		}
		if(hover2) {
			if(scores == highScores) {
				scores = nameHighScores;
			}
			else {
				scores = highScores;
			}
		}
	}
	

//EXPLOSION METHODS	
	
//explosion variable and list generator
	public void expEffect(float xPos, float yPos) {
		//random color per explosion
		int r = (int)random(255);
		int g = (int)random(255);
		int b = (int)random(255);
		
		//particle vector magnitude(speed) calculation
		double xSquared = Math.pow((double)(rockXV + torpXV), (double) 2);
		double ySquared = Math.pow((double)(rockYV + torpYV), (double) 2);
		float exMag = sqrt((float)(xSquared + ySquared));
		
		//particle vector angle calculation
		float finalAngle = (float) Math.PI - (rockAng + ang);

		//main vector-based explosion
		explosion newexp = new explosion(20, xPos, yPos, life, size,finalAngle, (float)Math.PI/4, exMag, r, g, b);
		
		//off-vector randomness explosions
		explosion exp = new explosion(2, xPos, yPos, life-50, size, rockAng,(float) Math.PI/12,rockSpeed, r, g, b );
		explosion exp2 = new explosion(2, xPos, yPos, life, size, (radians(ang) + (float)Math.PI) + (float) Math.PI/8, (float) Math.PI/8, 1, r, g, b);
		explosion exp3 = new explosion(2, xPos, yPos, life, size, (radians(ang) + (float) Math.PI) - (float) Math.PI/8, (float) Math.PI/8, 1, r, g, b);
		explosion exp4 = new explosion(2, xPos, yPos, life, size, radians(ang), (float)Math.PI/4, 2, r, g, b);
		
		//explosion list additions
		expList.add(exp);
		expList.add(exp2);
		expList.add(exp3);
		expList.add(exp4);
		expList.add(newexp);
		
		//explosion particle generations
		exp.generate(this);
		exp2.generate(this);
		exp3.generate(this);
		exp4.generate(this);
		newexp.generate(this);
	}
	
//explosion list clear function
	public void expUpdate() {
		int expSize = expList.size() -1;
		for(int i = expSize; i >=0; i--) {
			explosion exp = expList.get(i);
			//checks to see if all particles have disappeared, then deletes explosion from list
			if(exp.partList.size() <=0) {
				expList.remove(i);
			}
		}
	}

	
//COLLISION METHODS
	
//torpedo-asteroid collision function
	public void collision() {
		
		int torpSize = torpList.size()-1;
		boolean collide = false;
		//checks to see if torpedo and asteroid have collided using trig
		//torpedo loop
		for(int i = torpSize; i >= 0; i --) {
			int rockSize = rockList.size()-1;
			torpedo torp = torpList.get(i);

			//asteroid loop
		for(int n = rockSize; n >= 0; n --) {
			rocks rock = rockList.get(n);
			//collision trig variables
			double xSquared = Math.pow((double)(rock.xPos - torp.xPos), (double) 2);
			double ySquared = Math.pow((double)(rock.yPos - torp.yPos), (double) 2);
			
			//collision check
			if((Math.sqrt(xSquared + ySquared) <= 40)) {
				collide= true;
				
				//asteroid variables for vector calculations
				rockAng = rock.ang;
				rockSpeed = rock.speed ;
				rockXV = rock.xV;
				rockYV = rock.yV;
				torpXV = torp.xV;
				torpYV = torp.yV;
				
				//torpedo variables for vector calculations
				torpAng = torp.ang;
				
				//asteroid deletion
				rockList.remove(n);
				
				//explosion call
				expEffect(rock.xPos, rock.yPos);
				
				//score concatenation
				score2 += 10;
			}
		}
		if(collide) {
			//torpedo deletion
			torpList.remove(i);
			collide = false;
		}
		}
		
	}
	
//ship collide function
	public void shipCollide() {
		if(stillAlive) {
		int rockSize = rockList.size() - 1;
		for(int i = rockSize; i>= 0; i--) {
			rocks rock = rockList.get(i);
			
			//trig-based collision equation
			double xSquaredShip = Math.pow((double)((xPos + 500) - rock.xPos), (double) 2);
			double ySquaredShip = Math.pow((double)((yPos + 400) - rock.yPos), (double) 2);
			
			//collision check
			if((Math.sqrt(xSquaredShip + ySquaredShip)<= 40)) {
				
				//name variables
				enterName = true;
				dispName = true;
				
				//ship deletion
				stillAlive = false;
				
				//asteroid deletion
				rockList.remove(i);
				
				//explosion calls-- these are not tailored vectors for the ship and asteroid, didn't have enough time. 
				//they follow the angle of the previous asteroid-torpedo collision, instead. Still look ok, though.
				
				expEffect(xPos + 500, yPos +400);
				expEffect(rock.xPos, rock.yPos);
				
			}
		}
	}
	}

//TIMER METHODS
	
	//torpedo timer
	public void timer(int length) {
		if(setTime) {
			savedTime = millis();
			setTime = false;
		}
		int passedTime = millis() - savedTime;
		if(passedTime >= length) {
			timePassed = true;
			setTime = true;
		}
	}
	
	//timer for generating asteroids
	public void timerRocks(int length) {
		
		if(setTimeRocks) {
			savedTimeRocks = millis();
			setTimeRocks = false;
		}
		int passedTime = millis() - savedTimeRocks;
		if(passedTime >= length) {
			timePassedRocks = true;
			setTimeRocks = true;
		}
	}
	
//GAME RESET METHOD
	public void reset() {
		//VARIABLE DECLARATIONS
		//ship variables
		xPos = 0;
		yPos = 38;
		speed = 1;
		ang = 0;
		keyPressRight = false;
		keyPressLeft = false;
		keyPressUp = false;
		upReleased = false;
		shipStop = true;
		shipSpeed = 0;
		turnSpeed = 6;
		stillAlive = true;
		
		//torpedo variables
		torpAng = 0;
		torpXV = 0;
		torpYV = 0;
		
		//asteroid variables
		rockAng = 0;
		rockSpeed = 0;
		rockXV = 0;
		rockYV = 0;
		xPosR = 0;
		yPosR = 0;
		maxCap = false;
		sidePick = random(4);
		maxAst = 10;
		
		//particle variables
		size = 10;
		life = 255;

		//button variables
		buttonR = 100;
		buttonG = 100;
		buttonB = 150;
		hover = false;
		hover2 = false;
		
		//score variables
		score2 = 0;
		enterName = false;
		letterAdded = false;
		dispHighScore = false;
		dispName = false;
		name = "";
		
		//timer variables
		savedTime = 0;
		savedTimeRocks = 0;
		setTime = true;
		setTimeRocks= true;
		timePassed = true;
		timePassedRocks = true;
		
		//Arraylist clearing
		torpList.clear();
		rockList.clear();
		nameLetters.clear();
		scores.clear();
		highScores.clear();
		//clears arraylists in score class because I couldn't find a good way to do it within the other functions, I'm aware this is messy.
		score.clearLists();
	}
	//ASTEROID CODE
	public void asteroidGen() {
		//asteroid coordinate generating code based on timer and max capacity
		
				if(timePassedRocks & !maxCap) {
					//side generator randomize call
					sidePick = random(4);
					
					//left side generator
					if(sidePick < 1) {
						xPosR = -25;
						yPosR = random(height);
					}
					
					//right side generator
					else if(sidePick > 1 && sidePick < 2) {
						xPosR = width + 25;
						yPosR = random(height);
					}
					
					//top side generator
					else if(sidePick > 2 && sidePick < 3) {
						yPosR = -25;
						xPosR = random(width);
					}
					
					//bottom side generator
					else if(sidePick > 3) {
						yPosR = height + 25;
						xPosR = random(width);
					}
					
				//angle calculator-- targets ship with random error margin added in
					float ang2 = (float) (Math.atan((double)((yPos + (height/2) + random(300)) - yPosR)/((xPos + width/2 + random(300)) - xPosR)));
					
					//angle adjustment if on right side of ship
					if(xPosR > xPos) {
						ang2 += Math.PI;
					}
					
				//random asteroid speed
					speed = random(2, 4);
					
					//asteroid creation and store to arraylist
					rocks rock = new rocks(xPosR, yPosR, speed, ang2);
					rockList.add(rock);
					timePassedRocks = false;
				}
				
				//asteroid cap
				if(rockList.size() >= maxAst) {
					maxCap = true;
				}
				else {
					maxCap = false;
				}
				
	}
	
	public void keyChecks() {
				if(!shipStop) {
					//slower turn if moving for sense of inertia
					turnSpeed = (float) 3.5;
				}
				if(shipStop) {
					//faster turn if stopped
					turnSpeed = 6;
				}
				
				//turn checks
				if(keyPressRight) {
					ang += turnSpeed;
				}
				if(keyPressLeft) {
					ang -= turnSpeed;
				}
				
				//move check
				if(keyPressUp) {
					xPos += shipSpeed* Math.cos(radians(ang));
					yPos += shipSpeed* Math.sin(radians(ang));
				}
				
				//ship drift calculation
				if(upReleased) {
					shipSpeed -= shipSpeed/30;
				}
				//end of ship drift
				if(shipSpeed <=0) {
					keyPressUp = false;
					upReleased = false;
				}
	}

//GAMEOVER METHOD
	public void gameOver() {
		//button draw
		button button = new button(340, 600, 115, 30, buttonR, buttonG, buttonB, "Restart");
		button.draw(this);
		//mouse over button check
		hover = button.hover(this);
		
		button sortButton = new button(505, 600, 170, 30, buttonR, buttonG, buttonB, "Sort Scores");
		sortButton.draw(this);
		hover2 = sortButton.hover(this);
		
		fill(255);
		
		
	//enter name code
		//arraylist concatenation to string
		if(letterAdded) {
		name = String.join("", nameLetters);
		}
		
		//name print
		if(dispName) {
			textSize(35);
			text("Game Over!", 390, 100); 
			textSize(26);
			text("Enter name:", 415, 150);
			text(name, 445, 200);
			textSize(15);
			text("Press ENTER to submit.", 405, 225);
			textSize(26);
		}
		
		//name length limiter
		if(name.length() >= 4) {
			enterName = false;
		}
		else {
			enterName = true;
		}
	
		//highscore display
		if(dispHighScore) {
			text("High Scores: ", 430, 150);
			int y = 200;
			//only displays top 15 scores(they are still stored to the text file, though)
			int scoreMax = scores.size() - 1;
			if(scoreMax > 15) {
				scoreMax = 15;
			}
			for(int i = 0; i < scoreMax; i ++) {
				String score = scores.get(i);
				text(score, 430, y);
				y += 25;
			}
		}
	}

///MAIN DRAW METHOD
	public void draw() {
		//write background color
		background(300);
		fill(255);
		//asteroid gen call
		asteroidGen();
		//asteroid timer call
		timerRocks(1000);
		//movement check call
		keyChecks();
		

//DRAW AND MOVE COMMANDS
	//torpedo draw and move
		for(torpedo torp:torpList) {
			torp.torpedoDraw(this);
			torp.move(this);
			
		}
	//asteroid draw and move
		for(rocks rock:rockList) {
			rock.draw(this);
			rock.move(this);
		//asteroid wrap checks
			//left side to right side wrap
			if(rock.xPos < -25) {
				rock.xPos += width + 50;
			}
			//right side to left side wrap
			if(rock.xPos > width + 25) {
				rock.xPos -= width+50;
			}
			//top to bottom wrap
			if(rock.yPos < -25) {
				rock.yPos += height + 50;
			}
			//bottom to top wrap
			if(rock.yPos > height + 25) {
				rock.yPos -= height + 50;
			}
		}
//explosion draw and move
		for(explosion exp : expList) {
			exp.display(this);
			exp.update(this);
		}
//explosion list clear function call
		expUpdate();
//collision check function call
		collision();
		shipCollide();
		//score draw
		textSize(26);
		fill(255);
		text("Score: " + score2, 50, 50);
		pushMatrix();
		//ship limiting checks
				if((xPos + 25) > width/2 ){
					xPos = width/2 - 25;
				}
				if((xPos - 25) < -width/2) {
					xPos = -width/2 + 25;
				}
				if((yPos + 38) > height/2 ) {
					yPos = height/2 - 38;
				}
				if((yPos -38 < -height/2)) {
					yPos = -height/2 + 38;
				}
	//shipDraw
		if(stillAlive) {
		ship.shipDraw(this, xPos, yPos, ang);
		}
	//Game over check
		if(!stillAlive) {
			gameOver();
		}
		popMatrix();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("asteroids2");
	}

}
