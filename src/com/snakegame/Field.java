package com.snakegame;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import com.googlecode.lanterna.graphics.TextGraphics;

public class Field {
	private final char Brick = '#';
	private final char Grass = ' ';
	private final char Snake = '*';
	private final char Apple = 'o';
    private final char Pear = 'p';
    private LinkedList<Coordinates> bricks =  new LinkedList<Coordinates>();
	private LinkedList<Coordinates> grass = new LinkedList<Coordinates>();
	private LinkedList<Coordinates> snake = new LinkedList<Coordinates>() ;
    private int width;
    public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	private int height;
	private char [][] field;
	private boolean hitBrick;
	private boolean ReverseMovement = false;
	private int copyX,copyY;
	private int grassRandomIndex = 0;
	 

	 
	   protected void loadField(TextGraphics textGraphics, String filename) {
	    	try {
				File file = new File(filename);
				Scanner readFile = new Scanner(file);
				String readWidth = readFile.nextLine();
				String readHeight = readFile.nextLine();			
			    width = Integer.parseInt(readWidth);
			    height = Integer.parseInt(readHeight);
			    field = new char[height][width]; 
			    while(readFile.hasNextLine()) {
			    	for(int i = 0; i<height;i++) {
			    	String d = readFile.nextLine();
			    	  for(int j = 0; j<width; j++) {
			    		field[i][j] = d.charAt(j);
			    		if(field[i][j]==Brick) {
			    			bricks.add(new Coordinates(i,j));
			    		}
			    	  }
			  	   }
			    }
		   readFile.close();
		}
			catch(Exception ex) {
			System.out.println("Error");
		}
	}

  void drawObject(char[][] field, char ch,Coordinates cordinates) {
	  field[cordinates.getY()][cordinates.getX()] = ch;
		/*
		 * if(ch==Snake) { snake.add(0,new
		 * Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX()));
		 * 
		 * }
		 */
  }
  boolean isObject(char ch, Coordinates cordinates) {
	  if(field[cordinates.getY()][cordinates.getX()] == ch) {
	   return true;
	  }
	  else {
		  return false;
	  }
  }
  boolean isObject(char ch,char ch2, Coordinates cordinates) {
	  if(field[cordinates.getY()][cordinates.getX()] == ch || field[cordinates.getY()][cordinates.getX()]==ch2) {
	   return true;
	  }
	  else {
		  return false;
	  }
  }
  void deleteSnakeTail(char[][]field,LinkedList<Coordinates> snake) {
		field[snake.getLast().getY()][snake.getLast().getX()] = Grass;
  }
	   protected void printMap(TextGraphics textGraphics) {
			for (int row = 0; row < height; row++)
			{
			    for (int col = 0; col < width; col++)
			    {
			         textGraphics.putString(col, row, String.valueOf(field[row][col]));
			    }
			}
		}
private void initializeSnake(char[][] field) {
	int x = field[1].length/2;
	int y = field.length/2;
	//Head
    field[y-1][x] = Snake;
	//Center of the Snake segment
	field[y][x] = Snake;	
	//Tail 
	field[y+1][x] = Snake;
	
   for(int i=0; i<3;i++) {
	   snake.add(i, new Coordinates(y-1+i, x));
   }
   generateFruit(field);
}
//TODO Simplify 
	protected void snakeMove(Coordinates cordinates,int movesCount,Boolean bool) {
		if(movesCount==0) {
			initializeSnake(field);
		}
		else {
			copyX=snake.getFirst().getX();
			copyY =snake.getFirst().getY();
			if(isObject(Brick,Snake,new Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX()))){
				setHitBrick(true);
			}
			
			if(movesCount%20==0) {
				 generateFruit(field);
			 }
			if (isObject(Grass,new Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX()))){
				drawObject(field,Snake,new Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX()));

				
				snake.add(0,new Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX()));
				deleteSnakeTail(field,snake);
				snake.removeLast();
			}
				if(isObject(Apple,new Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX())))
{
				drawObject(field,Snake,new Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX()));
				snake.add(0,new Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX()));
				
			}
			if(isObject(Pear,new Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX())))
				{
				drawObject(field,Snake,new Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX()));
				snake.add(0,new Coordinates(copyY+cordinates.getY(),copyX+cordinates.getX()));
				//Reverse the indexes of the LinkedList<Cordinates>snake 
				//The first index becomes the last and so on...
				Collections.reverse(snake);
				copyX = snake.getFirst().getX();
				copyY=snake.getFirst().getY();
				ReverseMovement=true;
				
			}		
			
		}
	}
	     private void generateFruit(char[][] fruit) {
	    	
	    	     if(grassRandomIndex != 0) {
	    	    	drawObject(field,Grass,new Coordinates(grass.get(grassRandomIndex).getY(),grass.get(grassRandomIndex).getX()));

	    		}
	    	     grass = new LinkedList<Coordinates>();
	 	    	for(int i = 0; i<height; i++) {
	 	    		for(int j = 0; j<width; j++) {
	 	    			if(field[i][j]==Grass) {
	 	    				grass.add(new Coordinates(i,j));
	 	    				
	 	    			}
	 	    			
	 	    		}
	 	    	}
	    		char fruitch =' ';
	    		Random random = new Random();
	    	    grassRandomIndex = random.nextInt(grass.size());
	    		int probability = random.nextInt(5);
	    		if (probability == 1) {
	    			fruitch = Pear;
	    		}
	    		else {
	    			fruitch = Apple;
	    		}
	    	    Coordinates fruitCordinates = new Coordinates(grass.get(grassRandomIndex).getY(),grass.get(grassRandomIndex).getX());
	    		drawObject(field,fruitch,fruitCordinates);
	    	}
	    	
	    
	
		protected boolean isReverseMovement() {
			return this.ReverseMovement;
		}
		protected void setReverseMovement(boolean reverseMovement) {
			ReverseMovement = reverseMovement;
			
		}

		public boolean isHitBrick() {
			return hitBrick;
		}

		public boolean setHitBrick(boolean hitBrick) {
			this.hitBrick = hitBrick;
			return hitBrick;
		}
}
