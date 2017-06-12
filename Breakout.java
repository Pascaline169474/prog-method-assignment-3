/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	private static final int TIME_DELAY = 30;
	
	private GRect paddle;
	
	private GOval ball;
	
	private double vx,vy;
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	private int brickCounter=100;
	

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		for (int i =0; i< NTURNS; i++){
			buildGame();
			playGame();
			if (brickCounter == 0){
				removeAll();
			
			}
		}
		if(brickCounter> 0);
			printGameOver();
	
	}
	private void buildGame(){
		setSize(WIDTH, HEIGHT);
		buildBricks(0,BRICK_Y_OFFSET);
		buildPaddle();
		buildBall();
    	}
	 private void buildBricks(double bx, double by) {
		for (int row = 0; row < NBRICK_ROWS ; row++){
		for (int column = 0; column < NBRICKS_PER_ROW; column++){
		
	 double y = by + (BRICK_HEIGHT + BRICK_SEP) * row;
	 double x = bx + (BRICK_WIDTH + BRICK_SEP) * column;
	 
	 GRect brick = new GRect (x,y, BRICK_WIDTH , BRICK_HEIGHT);
	 brick.setFilled(true);
	 
	 switch(row) {
	 case 0: brick.setColor(Color.RED); break;
	 case 1: brick.setColor(Color.RED); break;
	 case 2: brick.setColor(Color.ORANGE); break;
	 case 3: brick.setColor(Color.ORANGE); break;
	 case 4: brick.setColor(Color.YELLOW); break;
	 case 5: brick.setColor(Color.YELLOW); break;
	 case 6: brick.setColor(Color.GREEN); break;
	 case 7: brick.setColor(Color.GREEN); break;
	 case 8: brick.setColor(Color.CYAN); break;
	 case 9: brick.setColor(Color.CYAN); break;
	 default: break;
	 }
     
	 add (brick);
		}
		}
	 }
	 
 
	    private void buildPaddle (){
	    	double x = getWidth()/2 - PADDLE_WIDTH/2;
	    	double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
	    	
	    	paddle = new GRect (x,y, PADDLE_WIDTH, PADDLE_HEIGHT);
	    	paddle.setFilled(true);
	    	paddle.setColor(Color.BLACK);
	    	add (paddle);
    	    addMouseListeners();
	    	
	     }
	    	
	    
	    public void mouseMoved(MouseEvent e){
	    	double X = e.getX();
	    	if (e.getX () >= 0 && e.getX () < (WIDTH-PADDLE_WIDTH)){
	    		 paddle.setLocation(e.getX(),getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	    	} else if (e.getX ()>= WIDTH) {
	    		paddle.setLocation (WIDTH - PADDLE_WIDTH, HEIGHT * PADDLE_Y_OFFSET);
	    	}
	    }
	    
	    private void buildBall(){
	    	ball = new GOval(WIDTH/2 - BALL_RADIUS, HEIGHT/2 - BALL_RADIUS,BALL_RADIUS, BALL_RADIUS);
	    	ball.setFilled(true);
	    	ball.setColor(Color.BLACK);
	    	add(ball);
	    	
	    	
	    }
        
	    private void playGame(){
	    	waitForClick();
	    	getBallVelocity();
	    	while (true) {	
	    		moveBall();
	    		if (ball.getY() >= getHeight()){
	    			break;
	    		}
	    		if (brickCounter == 0) {
	    			break;
	    		}
	    	}
	    	getCollidingObject();
	    	checkWalls();
	    	
	    	
	    }  
	    	
	   private void getBallVelocity(){
		   vy=5.0;
		   vx=rgen.nextDouble(1.0,3.0);
		   if(rgen.nextBoolean(0.5))vx=-vx;
	   }
		   
	     private void moveBall(){
			   ball.move(vx, vy);
			   checkWalls();
	
		   GObject collider= getCollidingObject();
			   
			   
			if (collider==paddle){
				vy=-vy;
			}else if (collider!= null && collider != paddle){
				
				remove(collider);
				brickCounter--;
				
			}
			pause(TIME_DELAY);
	      }
	    	
	   	private void checkWalls(){
	   		if(ball.getX()  <=0){
	   			vx=-vx;	   		
	   		}else if (ball.getX() + 2* BALL_RADIUS >= WIDTH){
	   			vx=-vx;
	   		}else if (ball.getY() <=0){
	   			vy=-vy;
	   		}
	   		
	   	}
	   	
	    
		private GObject getCollidingObject() {
			if (getElementAt (ball.getX(), ball.getY())!=null){
				return getElementAt(ball.getX(), ball.getY());
			}else if (getElementAt((ball.getX()+ 2*BALL_RADIUS), ball.getY()) != null){
				return getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY());
			}else if(getElementAt(ball.getX(), ball.getY()+ 2*BALL_RADIUS) != null){
				return (getElementAt(ball.getX(), ball.getY()+ 2*BALL_RADIUS));
			}else if(getElementAt(ball.getX()+2*BALL_RADIUS,(ball.getY()+2*BALL_RADIUS))!=null){
				return getElementAt(ball.getX()+2*BALL_RADIUS,ball.getY()+(2*BALL_RADIUS));
			}
			
			return null;
		}
		
		
		private void printWinner(){
			GLabel Winner = new GLabel ("Winner!!", getWidth()/2, getHeight()/2);
			Winner.move(-Winner.getWidth()/2,- Winner.getHeight());
			Winner.setColor(Color.BLUE);
			add(Winner);
		}
	    
	    
	    private void printGameOver(){
	    	GLabel gameOver = new GLabel ("Game Over!!", getWidth()/2, getHeight()/2);
			gameOver.move(-gameOver.getWidth()/2,- gameOver.getHeight());
			gameOver.setColor(Color.RED);
			add(gameOver);
	    }
	    

	    }

	    
	 
     
