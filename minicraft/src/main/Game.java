package main;


import javax.imageio.ImageIO;
import javax.swing.JFrame;

import controls.Control;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

import graphics.sprite.Spritesheet;
import scenes.*;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener{

    //Adicionando um "serial number" ao canvas
	private static final long serialVersionUID = 1L;

	//Declarando atributos da minha classe
	
	private JFrame frame; 			 		 // Janela do jogo
	
	private static boolean isRunning;		 // Variavel para manter o jogo ligado
	
	private final int WIDTH;  				 // Comprimento da janela a ser criada
	private final int HEIGHT;  	     		 // Altura da janela a ser criada
	public final int  SCALE;  	     		 // x vezes que a janela sera aumentada
	
	private static Thread thread;  		 	 // Criando threads
	
	public static BufferedImage layer; 		 // Imagem de fundo do meu jogo
	public static Spritesheet   spritesheet; // Spritesheet com todos os sprites

	//Variaveis para controlar as funcionalidades e cenas do jogo
	public static Gamestate gamestate;
	
	//Cenas do jogo
	private Normal   ingame;
	private Gameover gameover;
	private Cutcene  cutcene;
	
	public int getWIDTH() {
		return WIDTH;
	}
	
	//Metodo para pegar o valor e HEIGHT
	public int getHEIGHT() {
		return HEIGHT;
	}
	
	//Construtor do jogo
	public Game() {
		
		WIDTH  = 480;
		HEIGHT = 320;
		SCALE  = 2;
		
		gamestate = Gamestate.normal;
		
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		initFrame();
		
		this.addKeyListener(this);//Iniciando classe para permitir o controle do jogo
		this.addMouseListener(this);
		
		spritesheet = new Spritesheet();

		initScenes();
	}
	
	private void initScenes() {
		
		ingame   = new Normal(WIDTH,HEIGHT,SCALE);
		gameover = new Gameover(WIDTH,HEIGHT,SCALE);
		cutcene  = new Cutcene(WIDTH,HEIGHT,SCALE);
	}
	
	/**
	 * Método para iniciar a janela do jogo
	 */
	private void initFrame() {
		
		//Configurando janela do jogo
		this.frame = new JFrame("Minicraft"); 

		frame.setResizable(false); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.add(this);
		
		//frame.setUndecorated(true);
		
		frame.pack();
		
		//Configurando icone da janela
		Image icon = null;
		try {
			File tmp = new File("/icon.png");
			
			if( tmp.exists() && !tmp.isDirectory() ) {
				
				icon = ImageIO.read(getClass().getResource("/icon.png"));
				frame.setIconImage(icon);
				
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Image cursor    = toolkit.getImage(getClass().getResource("/icon.png"));
				Cursor c        = toolkit.createCustomCursor(cursor, new Point(0,0), "img");
				frame.setCursor(c);
			}
			
		} catch (Exception e) { e.printStackTrace(); }
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//Configurando a imagem de fundo
		layer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
	}
	
	//Primeira funcao a ser chamada no programa
	public static void main(String[] args) {
		
		//Iniciando o nosso jogo
		Game game = new Game();
		game.start();
	}
	
	public synchronized void start() {
		
		//Iniciando as minhas threads
		thread = new Thread(this);
		
		//Meu jogo foi iniciado, logo, sera igual a "true"
		isRunning = true;
		
		//Iniciando as Threads
		thread.start();
		
	}
	
	public void run() {
		
		//Fazendo com que o jogo rode a 60fps
		long lastTime        = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns            = 1000000000 / amountOfTicks;
		double delta         = 0.0;
		
		//Pedindo para que o canvas esteja em foco
		requestFocus();
		
		while(isRunning) {
			
			//Calculo do tempo até então decorrido
			long now = System.nanoTime();
			delta   += (now - lastTime) / ns;
			lastTime = now;
			
			//Ao passar um segundo ou mais
			if ( delta >= 1) {

				tick();
				render();
				delta--;
			}
			
		}
		
		//Parar o programa
		stop();

	}
	
	//Funcao que ira acontecer no fim do game
	public void stop(){
		
		try {
			isRunning = false;
			thread.join();
		
		} catch (Exception e) { e.printStackTrace();}
	}
	
	/**
	 * Verifica qual é a <code>Scene</code> atual na qual o jogo está rodando, e a envia
	 * @return a <code>Scene</code> que jogo está no atual momento
	 */
	public Scene getCurrentScene() {
		
		Scene currentScene;
		switch(gamestate) {
		
			case normal:
				
				currentScene = ingame;
				break;
				
			case gameover:
				
				currentScene = gameover;
				break;
				
			case cutcene:
				
				currentScene = cutcene;
				break;
			
			default:
				
				currentScene = null;
				break;
		}
		
		if(currentScene == null) {
			System.err.println("ERRO! A cena que foi carregada está vazia ou não foi iniciada!");
			System.exit(0);
		}
		
		return currentScene;
	}

	//Funcao que chama as acoes de todas as coisas a cada frame
	public void tick() {
		
		getCurrentScene().tick();
	}

	//Funcao para renderizar imagens na tela
	public void render() {
		
		//Iniciando buffer para renderizar imagem
		BufferStrategy bs = this.getBufferStrategy();
		if ( bs == null ) {

			this.createBufferStrategy(3);
			return;
		}
		
		//Criar um fundo primário que irá cobrir toda a tela
		Graphics g = layer.getGraphics();
		g.setColor(new Color(122,102,255));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		getCurrentScene().render(g);
		
		//Renderizar a imagem de fundo
		g.dispose();
		g = bs.getDrawGraphics();
		
		g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		bs.show();
		
	}
	
	//Funcoes que coordenam as coisas que sao apertadas
	
	/**
	 * Key Pressed = No momento que a tecla é pressionada
	 * @param e - tecla que foi pressionada
	 */
	public void keyPressed(KeyEvent e) {
		
		getCurrentScene().setAction(Control.pressed(e));
	}
	
	/**
	 * Key Released = No momento que a tecla é solta
	 * @param e - tecla que foi pressionada
	 */
	public void keyReleased(KeyEvent e) {
		
		getCurrentScene().setAction(Control.released(e));
	}

	//não utilizado
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		getCurrentScene().setAction(Control.mouse_pressed(e,SCALE));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		getCurrentScene().setAction(Control.mouse_released(e,SCALE));
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	
}