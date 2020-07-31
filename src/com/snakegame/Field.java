package com.snakegame;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

import com.googlecode.lanterna.graphics.TextGraphics;

public class Field {
	final char Brick = '#';
	final char Grass = ' ';
	final char Snake = '*';
	final char Apple = 'o';
    final char Pear = 'p';
    protected LinkedList<Cordinates> bricks =  new LinkedList<Cordinates>();
	protected LinkedList<Cordinates> grass = new LinkedList<Cordinates>();
    protected int width;
	protected int height;
	protected char [][] field;
	protected boolean hitBrick;
	protected boolean ReverseMovement = false;
	
	protected int grassRandomIndex = 0;
	 

	 
	   protected void loadField(TextGraphics textGraphics, String filename) {
	    	try {
				File file = new File(filename);
				Scanner readFile = new Scanner(file);
				String readWidth = readFile.nextLine();
				String readHeight = readFile.nextLine();
				//Reads first line for Height and 2nd line for width
			
			    width = Integer.parseInt(readWidth);
			    height = Integer.parseInt(readHeight);
			    field = new char[height][width];
			    //Draws map
				//Map = readFile.nextLine();    
			    while(readFile.hasNextLine()) {
			    	for(int i = 0; i<height;i++) {
			    	String d = readFile.nextLine();
			    	  for(int j = 0; j<width; j++) {
			    		field[i][j] = d.charAt(j);
			    		if(field[i][j]==Brick) {
			    			bricks.add(new Cordinates(i,j));
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

 
	   protected void printMap(TextGraphics textGraphics) {
			for (int row = 0; row < height; row++)
			{
			    for (int col = 0; col < width; col++)
			    {
			         textGraphics.putString(col, row, String.valueOf(field[row][col]));
			    }
			}
		}
	static int startx = 0;
	static int starty=0;
   protected void printSnake(LinkedList<Cordinates> snake) {
	   for(int i = 0; i<snake.size();i++) {		   
		   field[snake.get(i).getY()][snake.get(i).getX()] = Snake;
	   }
   }
	protected void snakeMove(LinkedList<Cordinates> snake,Cordinates cordinates,int movesCount,Boolean bool) {
		
		if(movesCount == 0) {
			int x = field[1].length/2;
			int y = field.length/2;
			startx=x;
			starty=y;
			//Head
	        field[y-1+cordinates.getY()][x+cordinates.getX()] = Snake;
			//Center of the Snake segment
			field[y+cordinates.getY()][x+cordinates.getX()] = Snake;	
			//Tail 
			field[y+1+cordinates.getY()][x+cordinates.getX()] = Snake;
			
	       for(int i=0; i<3;i++) {
	    	   snake.add(i, new Cordinates(y-1+i, x));
	       }
	       generateFruit(field);
		}
		
		else {	
			startx=snake.getFirst().getX();
			starty =snake.getFirst().getY();
			if(field[starty+cordinates.getY()][startx+cordinates.getX()] == Brick || field[starty+cordinates.getY()][startx+cordinates.getX()]==Snake ) {
		   
				hitBrick = true;
			}
			
			//head
			 if(movesCount%20==0) {
				 generateFruit(field);
			 }
			if(field[starty+cordinates.getY()][startx+cordinates.getX()]==Grass) {
				field[snake.getFirst().getY()+cordinates.getY()][snake.getFirst().getX()+cordinates.getX()] = '*';
				 //coordinates of head
				snake.add(0,new Cordinates(starty+cordinates.getY(),startx+cordinates.getX()));
				//Delete tail 
				field[snake.getLast().getY()][snake.getLast().getX()] = Grass;
				snake.removeLast();
			}
			if(field[starty+cordinates.getY()][startx+cordinates.getX()]==Apple) {
				field[starty+cordinates.getY()][startx+cordinates.getX()] =Snake;
				snake.add(0,new Cordinates(starty+cordinates.getY(),startx+cordinates.getX()));
				
			}
			if(field[starty+cordinates.getY()][startx+cordinates.getX()]==Pear) {
				field[starty+cordinates.getY()][startx+cordinates.getX()] =Snake;
				snake.add(0,new Cordinates(starty+cordinates.getY(),startx+cordinates.getX()));
				//Reverse the indexes of the LinkedList<Cordinates>snake 
				//The first index becomes the last and so on...
				Collections.reverse(snake);
				startx = snake.getFirst().getX();
				starty=snake.getFirst().getY();
				ReverseMovement=true;
				
			}
			
		
			 
			  
			 
			  
			

			
				
			
		}

		
	}
	
	
   

	     protected void generateFruit(char[][] fruit) {
	    	
	    	     if(grassRandomIndex != 0) {
		    	    field[grass.get(grassRandomIndex).getY()][grass.get(grassRandomIndex).getX()] =Grass;

	    		}
	    	     grass = new LinkedList<Cordinates>();
	 	    	for(int i = 0; i<height; i++) {
	 	    		for(int j = 0; j<width; j++) {
	 	    			if(field[i][j]==Grass) {
	 	    				grass.add(new Cordinates(i,j));
	 	    				
	 	    			}
	 	    			
	 	    		}
	 	    	}
	    		char fruitch =' ';
	    		Random random = new Random();
	    	    grassRandomIndex = random.nextInt(grass.size());
	    		int probability = random.nextInt(5);
	    		switch(probability) {
	    		case 0: fruitch=Apple;break;
	    		case 1: fruitch=Apple;break;
	    		case 2: fruitch=Apple; break;
	    		case 3: fruitch=Pear; break;
	    		case 4: fruitch=Apple; break;
	    		case 5: fruitch=Apple; break;
	    		}
	    	    field[grass.get(grassRandomIndex).getY()][grass.get(grassRandomIndex).getX()] = fruitch;
	    		
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
