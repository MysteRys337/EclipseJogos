package world;

import scenes.Gamestate;

/**
 * Classe para gerenciar o contexto na qual o jogo está (Informações da tela, estado de jogo e outros)
 * @author MysteRys337
 *
 */
public class Context {

	private int screenWIDTH;
	private int screenHEIGHT;
	
	private int worldWIDTH;
	private int worldHEIGHT;
	
	private Gamestate currentGameState;
	
	/**
	 * Construtor do contexto
	 * @param screenWIDTH  largura da tela do jogo
	 * @param screenHEIGHT altura da tela do jogo
	 * @param worldWIDTH   largura do mundo na qual o jogo está
	 * @param worldHEIGHT  altura do mundo na qual o jogo está
	 * @param currentGameState estado na qual o jogo está
	 */
	public Context(int screenWIDTH, int screenHEIGHT, int worldWIDTH, int worldHEIGHT, Gamestate currentGameState) {
		
		this.screenWIDTH  = screenWIDTH;
		this.screenHEIGHT = screenHEIGHT;
		
		this.worldWIDTH  = worldWIDTH;
		this.worldHEIGHT = worldHEIGHT;
		
		this.currentGameState = currentGameState;
	}
	
	public void setScreenWIDTH(int screenWIDTH) {
		this.screenWIDTH = screenWIDTH;
	}
	
	public void setScreenHEIGHT(int screenHEIGHT) {
		this.screenHEIGHT = screenHEIGHT;
	}
	
	public void setWorldWIDTH(int worldWIDTH) {
		this.worldWIDTH = worldWIDTH;
	}
	
	public void setWorldHEIGHT(int worldHEIGHT) {
		this.worldHEIGHT = worldHEIGHT;
	}
	
	public void setCurrentGameState(Gamestate currentGameState) {
		this.currentGameState = currentGameState;
	}
	
	public int getScreenWIDTH() {
		return this.screenWIDTH;
	}
	
	public int getScreenHEIGHT() {
		return this.screenHEIGHT;
	}
	
	public int getWorldWIDTH() {
		return this.worldWIDTH;
	}
	
	public int getWorldHEIGHT() {
		return this.worldHEIGHT;
	}
	
	public Gamestate getCurrentGameState() {
		return this.currentGameState;
	}
	
	/**
	 * Armazena todos os valores das variáveis dentro de uma String e retorna
	 * @return uma String com os valores da tela, mundo e o contexto atual
	 */
	public String getContext() {
		return "Screen Width      = " + getScreenWIDTH()      + "\n" + 
			   "Screen Height     = " + getScreenHEIGHT()     + "\n" + 
			   "World Width       = " + getWorldWIDTH()       + "\n" + 
			   "World Height      = " + getWorldHEIGHT()      + "\n" + 
			   "Current Gamestate = " + getCurrentGameState() + "\n" ;
	}
}
