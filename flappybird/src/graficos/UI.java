package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import flappybird.Game;

/**
 * Classe para gerenciar a visualização de informação na tela
 * @author MysteRys337
 * @version 0.1
 */
public class UI {
	
	private int width;
	private int height;
	private int scale;
	
	//Variaveis para armazenar a divisão entre a altura, comprimento com a escala da tela
	private final int dws;
	private final int dhs;
	
	//Variaveis para armazenar a multiplicacao entre a altura, comprimento com a escala da tela
	private final int mws;
	private final int mhs;
	
	/**
	 * Construtor: Definir os parâmetros da tela onde será renderizado
	 * os dados 
	 * @param width é o comprimento da tela
	 * @param height é a altura da tela
	 * @param scale é a escala na qual a tela é aumentada
	 */
	public UI(int width,int height,int scale) {
		this.width  = width;
		this.height = height;
		this.scale  = scale;
		
		this.dws = width/scale;
		this.dhs = height/scale;
		
		this.mws = width*scale;
		this.mhs = height*scale;
		
	}
	
	/**
	 * Renderiza a pontuação do player
	 * @param g é o objeto que irá renderizar os objetos na tela
	 */
	public void render(Graphics g) {
		
		g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString("Pontos: " + Game.player.getPontos(), 0, 8);
	}
	
	/**
	 * Renderiza a tela de pausa 
	 * @param g é o objeto que irá renderizar os objetos na tela
	 * @param showMessage é a condicional se o programa pode mostrar a mensagem
	 * na tela
	 */
	public void renderPause(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, mws, mhs);
		
		g.setColor(Color.green);
		g.setFont(new Font("arial", Font.BOLD, 15));
		g.drawString("Pausado", 55, 67);
	}
	
	/**
	 * Renderiza a tela de game over
	 * @param g é o objeto que irá renderizar os objetos na tela
	 * @param showMessage é a condicional se o programa pode mostrar a mensagem
	 * na tela
	 */
	public void renderGameOver(Graphics g, boolean showMessage) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, mws, mhs);
		
		g.setColor(Color.red);
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString("Você Perdeu!", dws%dhs*scale/3, height/3);
		
		if (showMessage) 
			pressioneEnterParaContinuar(g);
			
	}
	
	public void pressioneEnterParaContinuar(Graphics g) {
		g.setColor(Color.green);
		g.setFont(new Font("arial", Font.BOLD, 20));
		
		int soma = dws + dhs;
		
		g.drawString("Pressione", 60, soma);
		g.drawString("\'Enter\'", 80, soma + (scale * 4));
		g.drawString("para continuar", 35, soma + (scale * 4) * 2);
	}
}
