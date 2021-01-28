package scenes;

import entities.player.PlayerAction;
import graphics.Interface;

/**
 * Classe para gerenciar a visualização de informação na tela
 * @author MysteRys337
 * @version 0.1
 */
public class Scene extends Interface {
	
	protected PlayerAction action;
	
	/**
	 * Construtor: Definir os parâmetros da tela onde será renderizado
	 * os dados 
	 * @param width é o comprimento da tela
	 * @param height é a altura da tela
	 * @param scale é a escala na qual a tela é aumentada
	 */
	public Scene(int width, int height, int scale) {
		super(width, height, scale);
		this.action = new PlayerAction();
	}
	
	public void setAction(PlayerAction action) {
		this.action = action;
	}
	
	public void tick() {
		
	}
	
}
