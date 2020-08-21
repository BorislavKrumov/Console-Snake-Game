package com.snakegame;
import java.io.IOException;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Snake extends Field {
    private int movesCount = 0;
	private Terminal terminal;
	private Screen screen;
	private TextGraphics textGraphics;
	//
   // private LinkedList<Cordinates> snake = new LinkedList<Cordinates>() ;
	Coordinates begining = new Coordinates(0,0);
    private boolean boolReverse = false;
    private String file;
    private boolean restart = true;
    Field field = new Field();
    
        private void gameOverScreen(Screen screen, Terminal terminal) throws IOException {
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
		                	setHitBrick(false);
		                	exit = true;
		                	break;
						default:
							break;
						
		                
		                }
        		}
        	}
        }
        private void illegalMovePrint(Screen screen) {
        	Printer(textGraphics, "Illegal move try another direction after 2 seconds of waiting.");
     	   try {
			screen.refresh();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	   try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	   PrinterRelease(textGraphics,"Illegal move try another direction after 2 seconds of waiting." );
     	   try {
			screen.refresh();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
        private void reverseMovePrint(Screen screen) {
        	Printer(textGraphics, "Now the snake is reversing its direction.");
    		try {
				screen.refresh();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		PrinterRelease(textGraphics, "Now the snake is reversing its direction.");
    		try {
				screen.refresh();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	    protected void playGame(String file) throws InterruptedException, IOException {
	    	//Creating a new field
	    	
            setFile(file);
	        this.terminal = new DefaultTerminalFactory().createTerminal();
	        this.screen = new TerminalScreen(this.terminal);
	        this.textGraphics = screen.newTextGraphics();
	        screen.startScreen();


	        //Loading Field
	        field.loadField(textGraphics, file);
	        //Loading Snake at the center of the field
	        field.snakeMove(begining, movesCount, boolReverse);
	        while (!field.isHitBrick()) {
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
		                		begining = new Coordinates(0,-1);
		                    	field.snakeMove(begining, movesCount,boolReverse);
		                    	if(field.isReverseMovement() == true) {
		                    		reverseMovePrint(screen);
		                    	}
		                    	
	                    	}
	                    	else {
	                    		   illegalMovePrint(screen);

	                    	   
	                    	}
	                    	 
	                        break;
	                        
	                    case ArrowRight: 
	                    	if(begining.getX() != -1 || begining.getY() != 0) {
	                    		 movesCount++;
	     	                    begining = new Coordinates(0,1);
	     	                    field.snakeMove(begining, movesCount,boolReverse);
	     	                   if(field.isReverseMovement() == true) {
		                    		reverseMovePrint(screen);
		                    	}
	                    	}
	                    	else {
	                    		   illegalMovePrint(screen);
	                    	}  
	                        break;
	                   	                        
	                    case ArrowDown:
	                     	if(begining.getY()!= -1)
	                    	{
	                    		 movesCount++;
	 	 	                    begining = new Coordinates(1,0);
	 	 	                    field.snakeMove(begining, movesCount,boolReverse);
	 	 	              	screen.refresh();
	 	 	              if(field.isReverseMovement() == true) {
	                    		reverseMovePrint(screen);

	                    	}
	                    	}
	                    	else {
	                    		   illegalMovePrint(screen);

	                    	}
	                        break;
	                        
	                    case ArrowUp:
	                    	if(begining.getY()!= 1)
	                    	{
	                    		movesCount++;
		 	                    begining = new Coordinates(-1,0);
		 	                    field.snakeMove(begining, movesCount,boolReverse);
		 	                   if(field.isReverseMovement() == true) {
		                    		reverseMovePrint(screen);

		                    	}
		 	               	screen.refresh();

	                    	}
	                    	else {
	                    		   illegalMovePrint(screen);
	                    	}
	                    	
	 	                        break;
	                    	 
	                   default:
	                	   break;
	                        }
	                
 
	                        
	                }
	            
	        }
	        if(field.setHitBrick(true)) {
	        	gameOverScreen(screen,terminal);
		               
	        	    	
	        
	    }
	    
	        
     }
	  //Method for printing warning message
	  		private void Printer(TextGraphics textGraphics, String string) {
	  		
	  			textGraphics.putCSIStyledString(0, field.getHeight()+1, string);

	          
	  		}
	  		//Method for clearing the warning message
	  		private void PrinterRelease(TextGraphics textGraphics, String string) {
	                      
	  			String ch = " ";
	  		    	  for(int i = 0; i<string.length(); i++) {	
	  		    		  		    		  		 
	  		    		  		 textGraphics.putCSIStyledString(i, field.getHeight()+1, ch);
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
