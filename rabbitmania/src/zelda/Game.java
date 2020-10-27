package zelda;

//Importando bibliotecas do proprio java
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.io.IOException;
import java.io.InputStream;

//Bibliotecas que eu criei
import entities.*;
import graficos.*;
import world.*;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

    //Adicionando um "serial number" ao canvas
	private static final long serialVersionUID = 1L;

	//Declarando atributos da minha classe
	
	private JFrame frame; 							// Janela do meu jogo
	
	private static boolean isRunning; 				// Variavel para manter o jogo ligado
	
	private final static int WIDTH = 240; 			// Comprimento da janela a ser criada
	private final static int HEIGHT = 160; 			// Altura da janela a ser criada
	public final static int SCALE = 4; 			    // x vezes que a janela sera aumentada
	private static Thread thread;					// Criando threads
	
	public static BufferedImage layer; 					// Imagem de fundo do meu jogo
	public static ArrayList<Entity> entities;		// array dinamico contendo todos as entidades
	public static ArrayList<Enemy> enemies;		    // array dinamico contendo todos os inimigos
	public static ArrayList<Bullet> bullets;		// array dinamico de balas
	public static Spritesheet spritesheet;  		// Spritesheet com todos os sprites
	
	public static World world;						// Objeto contendo o mapa do mundo
	
	public static Player player;					// Objeto player
	
	public static Random gerador;					// Gerador de numeros aleatorios
	public static Sound sound;
	
	public static UI ui;							// Interface de usuario
	public static Menu menu;						// Menu do jogo
	public static Lightmap lightmap;				// Sistema de luz		
	
	//Variaveis de manipulacao do jogo
	private static int CUR_LEVEL = 1,MAX_LEVEL = 3;
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true,restartGame = false,skipCutcene = false;
	private int framesGameOver = 0;
	
	//Carregar Fonte personalizada
	public static InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
	public static InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
	public static Font pixelfont64,pixelfont48;
	
	//Mouse motion listener
	//public int mx,my;
	
	//Metodo para pegar o valor em WIDTH
	public static int getWIDTH() {
		return WIDTH;
	}
	
	//Metodo para pegar o valor e HEIGHT
	public static int getHEIGHT() {
		return HEIGHT;
	}
	
	public static int getCUR_LEVEL() {
		return CUR_LEVEL;
	}
	
	//Construtor do meu jogo
	public Game() throws IOException, FontFormatException {
		
		sound = new Sound();
		//Criando o gerador de numeros aleatorio
		gerador = new Random();
		
		ui = new UI();
		
		//Configurando as dimensoes da minha tela
		
		//Tela cheia
		//this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		
		//Em janela
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		//Configurando as preferencias da minha janela
		initFrame();
		
		//Iniciando classe para permitir o controle do jogo
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		initObjects();
		
	}
	
	public static void initObjects() throws IOException, FontFormatException {
		
		pixelfont64 = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(64f);
		pixelfont48 = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(48f);
		
		menu = new Menu();
		
		//Criando um array flexivel para todas as entidades e inimigos do jogo
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		
		//Criando o objeto que contem o spritesheet do jogo
		spritesheet = new Spritesheet("/spritesheet.png");
		
		//Criando o player, e adicionando ele na ArrayList
		player = new Player(0,0,10,15,null);
		entities.add(player);
		
		//Iniciando o objeto mundo;
		world = new World("/level"+CUR_LEVEL+".png");
		//ui.setMinimap(20, 20);
		
	}
	
	//Metodo para iniciar a janela
	private void initFrame() throws IOException {
		
		//Configurando janela do jogo
		this.frame = new JFrame("Rabbitmania"); 
		frame.setResizable(false); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.add(this);
		//frame.setUndecorated(true);
		frame.pack();
		
		//Configurando icone da janela
		Image icon = ImageIO.read(getClass().getResource("/icon.png"));
		frame.setIconImage(icon);
		
		//Configurando imagem do mouse
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursor = toolkit.getImage(getClass().getResource("/icon.png"));
		Cursor c = toolkit.createCustomCursor(cursor, new Point(0,0), "img");
		frame.setCursor(c);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//Configurando a imagem de fundo
		layer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		lightmap = new Lightmap(WIDTH,HEIGHT);
		
	}
	
	//Primeira funcao a ser chamada no programa
	public static void main(String[] args) throws IOException, FontFormatException {
		
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
	
	//Funcao loop que irá rodar durante toda a duracao do game
	public void run() {
		
		//Fazendo com que o jogo rode a 60fps
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0.0;
		
		//Pedindo para que o canvas esteja em foco
		requestFocus();
		
		while(isRunning) {
			
			//Calculo do tempo até então decorrido
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			//Ao passar um segundo ou mais
			if ( delta >= 1) {
				try {
					tick();
				} catch (IOException e) {
					e.printStackTrace();
				}
				render();
				delta--;
			}
			
		}
		//Parar o programa
		try {
			stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Funcao que ira acontecer no fim do game
	public void stop() throws InterruptedException {
		
		isRunning = false;
		thread.join();
	}

	//Funcao que chama as acoes de todas as coisas a cada frame
	public void tick() throws IOException {
		
		if ( gameState.equals("NORMAL")) {
			
			normalTick();
		}
		else if ( gameState.equals("GAME OVER")) {
			
			gameOverTick();
		}
		else if ( gameState.equals("MENU") || gameState.equals("PAUSE")) {
			
			menu.tick();
		}
		else if ( gameState.equals("CUTCENE")) {
			
			cutceneTick();
		}
		
	}
	
	public void normalTick() throws IOException {
		

		for (int i = 0; i < entities.size(); i++) {
			
			Entity e = entities.get(i);
			e.tick();
		}
		
		for (int i = 0; i < bullets.size(); i++) {
			
			bullets.get(i).tick();
		}
		
		if ( enemies.size() == 0 ) {
			
			CUR_LEVEL++;
			if ( CUR_LEVEL > MAX_LEVEL ) {
				
				CUR_LEVEL = 1;
			}
			String newWorld = "level"+CUR_LEVEL+".png";
			World.restartGame(newWorld);
		}
	}
	
	public void gameOverTick() throws IOException {
		
		framesGameOver++;
		if( framesGameOver == 90) {
			
			framesGameOver = 0;
			if (showMessageGameOver) {
				
				showMessageGameOver = false;
			}
			else {
				
				showMessageGameOver =  true;
			}
		}
		if (restartGame) {
			
			restartGame = false;
			
			CUR_LEVEL = 1;
			String newWorld = "level"+CUR_LEVEL+".png";
			World.restartGame(newWorld);
		}
	}
	
	public void cutceneTick() {
		
		if(skipCutcene) {
			
			gameState = "NORMAL";
		}
	}
	
	/*
	public void drawRectangleExample(int xoff,int yoff) {
		
		for (int x = 0; x < 32 ; x++) {
			
			for (int y = 0; y < 32; y++) {
				
				int xOff = x + xoff,yOff = y + xoff;
				if ( xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT) {
					
					continue;
				}
				pixels[xOff +(yOff*WIDTH)] = 0xffff00;
			}
		}
	}
	*/

	//Funcao para renderizar imagens na tela
	public void render() {
		
		//Iniciando buffer para renderizar imagem
		BufferStrategy bs = this.getBufferStrategy();
		if ( bs == null ) {
			this.createBufferStrategy(3);
			return;
		}
		
		//Renderizar um fundo preto
		Graphics g = layer.getGraphics();
		
		//Renderizando o mapa
		world.render(g);
		
		Collections.sort(entities, Entity.entitySorter);
		
		//Renderizar todas as entidades na tela
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		//Renderizando todas as balas na tela
		for (int i = 0; i < bullets.size(); i++) {
			
			bullets.get(i).render(g);
		}
		
		//lightmap.render();
		ui.renderPlayerStats(g);
		//ui.renderMinimap(g);
		ui.renderReload(g);
		
		//Renderizar a imagem de fundo
		g.dispose();
		g = bs.getDrawGraphics();
		
		//drawRectangleExample(90,30);
		
		g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		//g.drawImage(layer, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		if (gameState.equals("GAME OVER")) {
			
			ui.renderGameOver(g,showMessageGameOver);
		}
		else if (gameState.equals("MENU") || gameState.equals("PAUSE")) {
			
			menu.render(g);
		}
		else if(gameState.equals("CUTCENE")) {
			
			menu.renderTutorial(g);
		}
		
		/*
		 * Rotacao de objeto usando Graphics2D
		 * 
		Graphics2D g2 = (Graphics2D) g;
		double angleMouse = Math.atan2(225 - my, 225 - mx);
		g2.rotate(angleMouse,225,225);
		g.setColor(Color.red);
		g.fillRect(200, 200, 50, 50);
		*/
		
		//Mostrar tudo que foi pedido para ser renderizado
		
		bs.show();
		
	}
	
	//Funcoes que coordenam as coisas que sao apertadas
	public void keyPressed(KeyEvent e) {
		
		if ( gameState.equals("NORMAL")) {
			
			playerControl(e);
		}
		else if ( gameState.equals("MENU") || gameState.equals("PAUSE")) {
			
			menuControl(e);
		}
		else {
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER && gameState.equals("GAME OVER")) {
				
				restartGame = true;
			}
			else if ( e.getKeyCode() == KeyEvent.VK_ENTER && gameState.equals("CUTCENE")) {
				
				skipCutcene = true;
			}
		}
		
	}
	
	public void playerControl(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
		   e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
		        e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
	    }
		if(e.getKeyCode() == KeyEvent.VK_UP ||
		   e.getKeyCode() == KeyEvent.VK_W) {
			
			player.up = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
			e.getKeyCode() == KeyEvent.VK_S) {
			
			player.down = true;
	    }		
		if(e.getKeyCode() == KeyEvent.VK_SPACE  && player.getWeapon().delay >= player.getWeapon().maxDelay) {
			
			player.isShooting = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			
			gameState = "PAUSE";
		}
		if(e.getKeyCode() == KeyEvent.VK_R && player.getAmmo() < 13 && player.getReserveAmmo() > 0) {
			
			if(!player.isReloading) {
				
				Game.sound.play(Sound.pistolReload);
			}
			player.isReloading = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_E) {
			
			player.weaponUp = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_Q) {
			
			player.weaponDown = true;
		}
		/*
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			
			player.jump = true;
		}
		*/
		
	}

	public void menuControl(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP ||
		   e.getKeyCode() == KeyEvent.VK_W) {
			
			menu.up = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
		        e.getKeyCode() == KeyEvent.VK_S) {
			
			menu.down = true;
		}	
		if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
			
			menu.exeCommand = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
		if (gameState.equals("NORMAL")) {
			
			movementRelease(e);
		}
	}
	
	public void movementRelease(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
			e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = false;			
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			
				player.left = false;					
		}
		if(e.getKeyCode() == KeyEvent.VK_UP ||
		   e.getKeyCode() == KeyEvent.VK_W) {
			
				player.up = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
			    e.getKeyCode() == KeyEvent.VK_S) {
			
				player.down = false;						
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE ) {
			
				player.isShooting = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER && gameState.equals("GAME OVER")) {
			
				restartGame = false;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		
		player.isShootingMouse = true;
		player.mx = (m.getX()/SCALE) ;
		player.my = (m.getY()/SCALE) ;
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		player.isShootingMouse = false;
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent m) {
		
		//this.mx = m.getX();
		//this.my = m.getY();
	}
	
}