package com.snakegame;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




public class main extends Snake {	  
	//Check file if it is valid by the parameters in the file
	public static boolean isFileValid(File file) throws FileNotFoundException {
		Scanner readFile = new Scanner(file);
		String readWidth = readFile.nextLine();
		String readHeight = readFile.nextLine();
	    int width = Integer.parseInt(readWidth);
	    int height = Integer.parseInt(readHeight);
	    List<String> checksize = new ArrayList<String>();
		 while(readFile.hasNextLine() ) {
		      String line = readFile.nextLine();
              checksize.add(line);
                if(checksize.size() == height && line.length() == width) {
                	readFile.close();
                	return true;
                }
		    }
     	readFile.close();
        System.out.println("Invalid field. Please enter valid field file path:");
		return false;
	    
	}
	public static void main(String[] args) throws FileNotFoundException, InterruptedException, IOException  {
		
		boolean isFileValid = false;
		while(!isFileValid) {
			System.out.println("Enter field file path:");
			Scanner sc = new Scanner(System.in);
			String filePath = sc.nextLine();
			File file = new File(filePath);
			if(file.exists()&&isFileValid(file)) {
				sc.close();
				if(isFileValid(file)) {
					Snake snake = new Snake();
					snake.playGame(filePath);
					isFileValid= true;
					 while(snake.isRestart()==true) {
						    snake = new Snake();
							new Field();
							snake.playGame(filePath);
				}
			}
		}
	
 }
	
	
}
}
	  		
	        	
	

