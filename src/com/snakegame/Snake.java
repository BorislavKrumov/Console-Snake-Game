package com.snakegame;
import java.io.IOException;
import java.util.LinkedList;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Snake extends Field {
	static int movesCount = 0;
	private Terminal terminal;
	private Screen screen;
	private TextGraphics textGraphics;
    private LinkedList<Cordinates> snake = new LinkedList<Cordinates>() ;
	Cordinates begining = new Cordinates(0,0);
    boolean boolReverse = false;
    private String file;
    private boolean restart = true;
    Field field = new Field();
	    public void playGame(String file) throws InterruptedException, IOException {
	    	//Creating a new field
	    	
            setFile(file);
	        this.terminal = new DefaultTerminalFactory().createTerminal();
	        this.screen = new TerminalScreen(this.terminal);
	        this.textGraphics = screen.newTextGraphics();
	        screen.startScreen();


	        //Loading Field
	        field.loadField(textGraphics, file);
	        //Loading Snake at the center of the field
	        field.snakeMove(snake, begining, movesCount, boolReverse);
	        while (!field.hitBrick) {
	            KeyStroke keyStroke = terminal.pollInput();
	            //Initialize next move
	            field.printMap(textGraphics);
            	screen.refresh();
            	
               if (keyStroke != null) {
	            	
	            	if(field.isReverseMovement()==true ) {
	            		begining.setX(begining.getX()*(-1));
	            		begining.setY(begining.getY()*(-1));
	            		field.setReverseMovement(false);
	            	}
	            
	                switch (keyStroke.getKeyType()) {
	                
	                    case ArrowLeft:
	                    	 //Check for current direction state
	                        //If snake is already moving right, it cannot move left
	                    	if(begining.getX() != 1 || begining.getY() != 0 ) {
	                    		movesCount++;
		                		begining = new Cordinates(0,-1);
		                    	field.snakeMove(snake, begining, movesCount,boolReverse);
		                    	if(field.isReverseMovement() == true) {
		                    		field.Printer(textGraphics, "Now the snake is reversing its direction.");
		    	            		screen.refresh();
		    	            		Thread.sleep(1000);
		    	            		field.PrinterRelease(textGraphics, "Now the snake is reversing its direction.");
		    	            		screen.refresh();
		                    	}
		                    	
	                    	}
	                    	else {
	                    	   field.Printer(textGraphics, "Illegal move try another direction after 2 seconds of waiting.");
	                    	   screen.refresh();
	                    	   Thread.sleep(2000);
	                    	   field.PrinterRelease(textGraphics,"Illegal move try another direction after 2 seconds of waiting." );
	                    	   screen.refresh();
	                    	   
	                    	}
	                    	 
	                        break;
	                        
	                    case ArrowRight: 
	                    	if(begining.getX() != -1 || begining.getY() != 0) {
	                    		 movesCount++;
	     	                    begining = new Cordinates(0,1);
	     	                    field.snakeMove(snake, begining, movesCount,boolReverse);
	     	                   if(field.isReverseMovement() == true) {
		                    		field.Printer(textGraphics, "Now the snake is reversing its direction.");
		    	            		screen.refresh();
		    	            		Thread.sleep(1000);
		    	            		field.PrinterRelease(textGraphics, "Now the snake is reversing its direction.");
		    	            		screen.refresh();
		                    	}
	                    	}
	                    	else {
	                    		 field.Printer(textGraphics, "Illegal move try another direction after 2 seconds of waiting.");
		                    	   screen.refresh();
		                    	   Thread.sleep(2000);
		                    	   field.PrinterRelease(textGraphics,"Illegal move try another direction after 2 seconds of waiting." );
		                    	   screen.refresh();
	                    	}  
	                        break;
	                   	                        
	                    case ArrowDown:
	                     	if(begining.getY()!= -1)
	                    	{
	                    		 movesCount++;
	 	 	                    begining = new Cordinates(1,0);
	 	 	                    field.snakeMove(snake, begining, movesCount,boolReverse);
	 	 	              	screen.refresh();
	 	 	              if(field.isReverseMovement() == true) {
	                    		field.Printer(textGraphics, "Now the snake is reversing its direction.");
	    	            		screen.refresh();
	    	            		Thread.sleep(1000);
	    	            		field.PrinterRelease(textGraphics, "Now the snake is reversing its direction.");
	    	            		screen.refresh();
	                    	}

	                    	}
	                    	else {
	                    		 field.Printer(textGraphics, "Illegal move try another direction after 2 seconds of waiting.");
		                    	   screen.refresh();
		                    	   Thread.sleep(2000);
		                    	   field.PrinterRelease(textGraphics,"Illegal move try another direction after 2 seconds of waiting." );
		                    	   screen.refresh();
	                    	}
	                        break;
	                        
	                    case ArrowUp:
	                    	if(begining.getY()!= 1)
	                    	{
	                    		movesCount++;
		 	                    begining = new Cordinates(-1,0);
		 	                    field.snakeMove(snake, begining, movesCount,boolReverse);
		 	                   if(field.isReverseMovement() == true) {
		                    		field.Printer(textGraphics, "Now the snake is reversing its direction.");
		    	            		screen.refresh();
		    	            		Thread.sleep(1000);
		    	            		field.PrinterRelease(textGraphics, "Now the snake is reversing its direction.");
		    	            		screen.refresh();
		                    	}
		 	               	screen.refresh();

	                    	}
	                    	else {
	                    		   field.Printer(textGraphics, "Illegal move try another direction after 2 seconds of waiting.");
	                    		   screen.refresh();
		                    	   Thread.sleep(2000);
		                    	   field.PrinterRelease(textGraphics,"Illegal move try another direction after 2 seconds of waiting." );
		                    	   screen.refresh();
	                    	}
	                    	
	 	                        break;
	                    	 
	                   default:
	                	   break;
	                        }
	                
 
	                        
	                }
	            
	        }
	        if(field.hitBrick=true) {
	        	screen.clear();
	        	textGraphics.clearModifiers();
	        	textGraphics.putCSIStyledString(0, 0, "Game over.");
	        	textGraphics.putCSIStyledString(0, 1, "Press 'End' for exit.");
	        	textGraphics.putCSIStyledString(0, 2, "Press 'Enter' for a new game.");
	        	screen.refresh();
	        	boolean exit = false;
	        	while(exit == false) {
	            KeyStroke key =terminal.pollInput();
	        		if(key != null) {
	        			 switch (key.getKeyType()) {
			                
			                case End: 
			                	screen.close();
			                	exit = true;
			                	restart =false;
			                	break;
			                case Enter:
			                	setRestart(true);
			                	screen.close();
			                	movesCount = 0;
			                	hitBrick = false;
			                	exit = true;
			                	break;
							default:
								break;
							
			                
			                }
	        		}
		               
	        	}    	
	        
	    }
	    
	        
     }
		public String getFile() {
			return file;
		}
		public void setFile(String file) {
			this.file = file;
		}
		public boolean isRestart() {
			return restart;
		}
		public void setRestart(boolean restart) {
			this.restart = restart;
		}
	   
	
}
