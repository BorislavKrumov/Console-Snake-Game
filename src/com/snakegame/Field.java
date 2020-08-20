package com.snakegame;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import com.googlecode.lanterna.graphics.TextGraphics;

public class Field {
	final char Brick = '#';
	final char Grass = ' ';
	final char Snake = '*';
	final char Apple = 'o';
    final char Pear = 'p';
    private LinkedList<Coordinates> bricks =  new LinkedList<Coordinates>();
	private LinkedList<Coordinates> grass = new LinkedList<Coordinates>();
	private LinkedList<Coordinates> snake = new LinkedList<Coordinates>() ;
    protected int width;
	protected int height;
	protected char [][] field;
	protected boolean hitBrick;
	protected boolean ReverseMovement = false;
	int copyX,copyY;
	protected int grassRandomIndex = 0;
	 

	 
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
void initializeSnake(char[][] field) {
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
				hitBrick = true;
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
	     protected void generateFruit(char[][] fruit) {
	    	
	    	     if(grassRandomIndex != 0) {
		    	    field[grass.get(grassRandomIndex).getY()][grass.get(grassRandomIndex).getX()] =Grass;

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
				/*
				 * switch(probability) { case 0: fruitch=Apple;break; case 1:
				 * fruitch=Apple;break; case 2: fruitch=Apple; break; case 3: fruitch=Pear;
				 * break; case 4: fruitch=Apple; break; case 5: fruitch=Apple; break; }
				 */
	    	   // field[grass.get(grassRandomIndex).getY()][grass.get(grassRandomIndex).getX()] = fruitch;
	    		//Generate fruit on random grass cordinates
	    	    Coordinates fruitCordinates = new Coordinates(grass.get(grassRandomIndex).getY(),grass.get(grassRandomIndex).getX());
	    		drawObject(field,fruitch,fruitCordinates);
	    	}
	    	
	    
	//Method for printing warning message
		protected void Printer(TextGraphics textGraphics, String string) {
		
			textGraphics.putCSIStyledString(0, height+1, string);

        
		}
		//Method for clearing the warning message
		protected void PrinterRelease(TextGraphics textGraphics, String string) {
                    
			String ch = " ";
		    	  for(int i = 0; i<string.length(); i++) {	
		    		  		    		  		 
		    		  		 textGraphics.putCSIStyledString(i, height+1, ch);
		    		}
		    	  		    	  
		}
		protected boolean isReverseMovement() {
			return this.ReverseMovement;
		}
		protected void setReverseMovement(boolean reverseMovement) {
			ReverseMovement = reverseMovement;
			
		}
}
