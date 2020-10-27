package zelda;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import world.World;

public class Menu {

	public String[] options = {"New Game","Load Game","Quit"};
	public static String[] menuOptions = {"New Game","Load Game","Quit"};
	public static String[] pauseOptions = {"Resume game","Load Game","Save Game", "Go to Menu"};
	
	public int currentOption = 0,saveFrames,encryptionValue = 15;
	
	public boolean down,up,exeCommand,gameInitiated = false;
	
	public static boolean pause = false,saveGame = false,noSaveFile = false;
	
	public void tick() throws IOException {
		
		if (up) {
			
			up = false;
			currentOption--;
			if (currentOption < 0) {
				
				currentOption = options.length -1;
			}
		}
		else if (down) {
			
			down = false;
			currentOption++;
			if (currentOption > options.length -1) {
				
				currentOption = 0;
			}
		} 
		else if (exeCommand) {
			
			exeCommand = false;
			if ( options[currentOption].equals(options[0]) ) {   // New Game / Resume Game
				
				Game.gameState = "NORMAL";
				if( !gameInitiated ) {
					
					Game.player.updateCamera();
					Game.gameState = "CUTCENE";
					File file = new File("Save.txt");
					file.delete();
					gameInitiated = true;
					options = pauseOptions;
				}
			}
			else if ( options[currentOption].equals(options[1]) ) { // Load game
				
				File file = new File("Save.txt");
				if (file.exists()) {
					String saver = loadGame(encryptionValue);
					applySave(saver);
				}
				else {
					noSaveFile = true;
				}
				
			}
			else if ( options[currentOption].equals(options[2]) ){
				
				if (options[currentOption].equals("Save Game") && !saveGame ) {
					
					String[] opt1 = {"level","health"};
					int [] opt2 = {Game.getCUR_LEVEL(),(int)Game.player.getLife()};
					Menu.saveGame(opt1,opt2,encryptionValue);
					
					saveGame = true;
					
				}
				else {
					
					System.exit(1);
				}
			}
			if ( options[currentOption].equals("Go to Menu")) {
				
				World.restartGame("level1.png");
				Game.gameState = "MENU";
				options = menuOptions;
				currentOption = 0;
				gameInitiated = false;
			}
		}
	}
	
	public static void applySave(String arg) throws IOException {
		
		String[] spl = arg.split("/");
		for(int i = 0; i < spl.length;i++) {
			
			String[] spl2 = spl[i].split(":");
			switch(spl2[0]) {
				
			case "level":
				World.restartGame("level"+spl2[1]+".png");
				Game.gameState = "NORMAL";
				pause = false;
				break;
			case "health":
				System.out.println(spl2[i]);
				Game.player.setLife(Integer.parseInt(spl2[i]));
				break;
			}
		}
	}
	
	public static String loadGame(int encode) throws IOException {
		
		String line = "";
		File saveFile = new File("Save.txt");
		if (saveFile.exists()) {
		
			String singleLine = null;
			BufferedReader reader = new BufferedReader(new FileReader("Save.txt"));
			
				while((singleLine = reader.readLine()) != null) {
					
					String[] trans = singleLine.split(":");
					char[] value = trans[1].toCharArray();
					trans[1] = "";
					for (int i = 0; i < value.length; i++) {
						
						value[i]-=encode;
						trans[1]+=value[i];
					}
					line+=trans[0];
					line+=":";
					line+=trans[1];
					line+="/";
				}
				reader.close();
		}
		
		return line;
		
	}
	
	public static void saveGame(String[] arg1, int[] arg2, int encode) throws IOException {
		
		BufferedWriter saveFile = new BufferedWriter(new FileWriter("Save.txt"));
		
		for (int i = 0; i < arg1.length; i++) {
			
			String current = arg1[i];
			
			current += ":";
			char[] value = Integer.toString(arg2[i]).toCharArray();
			for (int j = 0; j < value.length; j++) {
				
				value[j]+= encode;
				current += value[j];
			}
			
			saveFile.write(current);
			if ( i < arg1.length - 1) {
				
				saveFile.newLine();
			}
			
		}
		saveFile.flush();
		saveFile.close();
	}
	
	public void render(Graphics g) {
		
		//Renderizando o fundo
		
		if(!gameInitiated) { // Renderizar fundo preto
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Game.getWIDTH()*Game.SCALE, Game.getHEIGHT()*Game.SCALE);
		}
		else {				 // Renderizar fundo transparente
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, Game.getWIDTH()*Game.SCALE, Game.getHEIGHT()*Game.SCALE);
		}
		
		//Renderizar o titulo
		g.setColor(Color.RED);
		g.setFont(Game.pixelfont64);
		g.drawString("Rabbitmania", (Game.getWIDTH()*Game.SCALE)/2 - 450, (Game.getHEIGHT()*Game.SCALE)/2 - 250);
		
		g.setColor(Color.WHITE);
		g.setFont(Game.pixelfont48);
		
		if (saveGame) {
			
			saveFrames++;
			g.setColor(Color.GREEN);
			g.drawString("Game saved!", 20, 620);
			if (saveFrames >= 120) {
				
				saveGame = false;
				saveFrames = 0;
			}
		}
		
		if (noSaveFile) {
			
			saveFrames++;
			g.setColor(Color.RED);
			g.drawString("Error! No save file", 20, 620);
			if (saveFrames >= 120) {
				
				noSaveFile = false;
				saveFrames = 0;
			}
		}
		
		g.setColor(Color.WHITE);
		
		//Renderizar as opcoes do menu
		for ( int i = 0 ; i < options.length; i++) {
				g.drawString(options[i], (Game.getWIDTH()*Game.SCALE)/2 - 450, (Game.getHEIGHT()*Game.SCALE)/2 - 50 + (i*70));
		}
		
		//Renderizar a seta de selecao
		g.setColor(Color.BLUE);
		g.drawString(options[currentOption], (Game.getWIDTH()*Game.SCALE)/2 - 450, (Game.getHEIGHT()*Game.SCALE)/2 - 50 + (currentOption*70));
		
	}
	
	public void renderTutorial(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, Game.getWIDTH()*Game.SCALE, Game.getHEIGHT()*Game.SCALE);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Century",Font.BOLD,64));
		
		g.drawString("How to play this game:", 60, 120);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Century",Font.BOLD,30));
		g.drawString("Use WASD or the arrow keys to move the player.", 20, 230);
		g.drawString("Press \'SPACE\' to shoot(or use the mouse button) ", 20, 290);
		g.drawString("and \'R\' to reload your weapon.", 20, 325);
		g.drawString("Also, remember to use \'Q\' and \'E\' to switch", 20, 385);
		g.drawString("between weapons.", 20, 420);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Century",Font.BOLD,36));
		g.drawString("Press \'ENTER\' to begin the game", 160, 550);
		g.drawString("the game", 400, 600);
	}
}
