package graphics.sprite;

//Bibliotecas necessaria
import java.awt.image.BufferedImage;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Classe usada para resgatar uma imagem @BufferedImage , para então
 * usar como base para resgatar sub-imagens @Sprite
 * 
 * @author MysteRys337
 * @version 0.1
 */
public class Spritesheet {
	
	//Variáveis
	private BufferedImage spritesheet;
	private Sprite[]      sprites;
	private final byte    SPRITE_SIZE = 16;
	private final String  path;
	
	/**
	 * <em>Construtor vazio</em>:<p>
	 *  
	 * Nenhum caminho específico é determinado, o programa irá assumir que na path 
	 * de resource irá conter um <strong>"spritesheet.png"</strong>. <p> 
	 * 
	 * Além disso, ele também irá verificar pela existência de um <strong>"spritesheet.txt"</strong>
	 * que será o arquivo que irá conter o nome dos sprites dentro da @spritesheet <p> 
	 * <strong> Atenção! </strong> Ambos arquivos precisam existir no diretório, 
	 * caso contrário, o programa irá alertar um erro.
	 */
	public Spritesheet() {
		
		this.path = "/spritesheet";
		setSpritesheet(path + ".png");
		
		setSprites("res" + path + ".txt");
	}
	
	/**
	 * <em>Construtor com @String path</em>: <p>
	 *  
	 * A path é especifícada, logo ele irá procurar pelo <strong>"{path}.png"</strong>. <p> 
	 * 
	 * Além disso, ele também irá verificar pela existência de um <strong>"{path}.txt"</strong> 
	 * que será o arquivo que irá conter o nome dos sprites dentro da @spritesheet <p> 
	 * 
	 * <strong> Atenção! </strong> Ambos arquivos precisam existir no diretório, caso contrário, 
	 * o programa irá alertar um erro.
	 * 
	 * @param path é a <code>String</code> que será utilizada para pesquisar pela imagem
	 * e pelo ".txt"
	 */
	public Spritesheet(String path) {
	
		this.path = path;
		setSpritesheet(path + ".png");
		
		setSprites("res" + path + ".txt");
	}
	
	//Funções 'get' para resgatar conteúdos da classe
	
	/**
	 * <em> getSprite 1 </em> : <p>
	 * 
	 * Resgata um sprite, a partir das posições <strong>x</strong> e 
	 * <strong>y</strong> no spritesheet <p>
	 * 
	 * <strong>Atenção!</strong> o tamanho do sprite será correspondente ao valor da variável <code>SPRITE_SIZE</code> 
	 * 
	 * @param x é a posição no eixo das abscissas onde a captura da imagem irá começar
	 * @param y é a posição no eixo das ordenadas onde a captura da imagem irá começar
	 * @return um <code>Sprite</code> com o conteúdo da subimagem resgatada do @spritesheet
	 */
	public Sprite getSprite(int x,int y) {
		return new Sprite(spritesheet.getSubimage(x,y,SPRITE_SIZE,SPRITE_SIZE), "null");
	}
	
	/**
	 * <em>getSprite 2</em> : <p>
	 * 
	 * Resgata um sprite, a partir da posição <strong>i</strong> no array @sprites
	 * 
	 * @param index é a posição na qual o sprite será resgatado
	 * @return o <code>Sprite</code> resgatado na posição do @index
	 */
	public Sprite getSprite(int index) {
		return sprites[index];
	}
	
	public Sprite getSprite(String s) {
		Sprite resp = null;
		for(Sprite spr: this.sprites) {
			
			if(spr != null && spr.getName().equals(s)) {
				resp = spr;
				break;
			}
		}
		
		return resp;
	}
	
	/**
	 * <em>getSprite 3</em> : <p>
	 * 
	 * Resgata uma série de sprites(<code>Sprite[]</code>), a partir de uma <code>String</code> <strong>S</strong> no array @sprites. <par>
	 * 
	 * Essa <code>String</code> contém um prefixo, que será vasculhado pela spritesheet, e que ao encontrar o prefixo, ele irá acumular no
	 * <code>ArrayList</code> e então retornar.
	 * 
	 * @param S é a <code>String</code> com o prefixo que deverá ser procurado
	 * @return o <code>Sprite</code> resgatado na posição do @index
	 */
	public Sprite[] getSprites(String s) {
		
		ArrayList<Sprite> sprites = new ArrayList<>();
		for(Sprite spr: this.sprites) {
			
			if(spr != null && spr.getName().contains(s)) {
				sprites.add(spr);
			}
		}
		
		return toArray(sprites);
	}
	
	// Funções 'set'
	
	/**
	 * Através de uma <code>String</code>, localiza o arquivo spritesheet 
	 * 
	 * @param path é a <code>String</code> contendo a localização do arquivo com o spritesheet
	 */
	private void setSpritesheet(String path) {
		
		try {
			this.spritesheet = ImageIO.read(getClass().getResource(path));
			
		} catch (Exception e ) {e.printStackTrace();} 
		
		if(this.spritesheet == null) {
			System.err.println("ERRO! " + path + " não é um caminho para um spritesheet");
			
		}
	}
	
	/**
	 * Pegar todos os sprites dentro da Spritesheet e colocar dentro de um array
	 * 
	 * @param path é o local onde o ".txt" com o nomes de todos os sprites está
	 */
	private void setSprites(String path) {

		// 1# passo: Pegar todos os nomes dos sprites
		// Dessa maneira, é também possível determinar quantos sprites tem no spritesheet
		ArrayList<String> spriteNames = new ArrayList<>();
		String line                   = "";
		
		RandomAccessFile names = null;
		try {
			
			names = new RandomAccessFile(path,"r");
		} catch(Exception e) {e.printStackTrace();}
		
		if(names == null) {
			System.err.println("ERRO! " + path + " não foi encontrado");
			
		}
		
		try {
			
			line = names.readLine();
			while(line != null) {
				
				spriteNames.add(line);
				line = names.readLine();
			}
			names.close();
			
		} catch (Exception e) {e.printStackTrace();}
	
		sprites = new Sprite[spriteNames.size()];
		
		// 2# Passo: Pegar todos os sprites
		int index = 0;
		
		boolean noMoreSprites = false; //Variável para sinalizar quando os sprites acabaram
		
		for (int y = 0; y < spritesheet.getHeight(); y += SPRITE_SIZE) {
			for ( int x = 0; x < spritesheet.getWidth(); x += SPRITE_SIZE) {
				BufferedImage tmp = spritesheet.getSubimage(x,y,SPRITE_SIZE,SPRITE_SIZE);
				if(containsNonTransparentPixel(tmp)) {
					if(index < spriteNames.size()) {
						sprites[index] = new Sprite(tmp, spriteNames.get(index));
						index++;
						
					}
				} else {
					
					//Se não possui mais sprites a ser lido( não foi detectado um sprite no primeiro tile da linha)
					if(x == 0)
						noMoreSprites = true;
					break;
				}
			}
			if(noMoreSprites) 
				break;
		}
		
	}
	
	/**
	 * Verifica se uma imagem está vazia
	 * 
	 * @param tmp é o <code>BufferedImage</code> com a imagem a ser verificada
	 * @return true ou false
	 */
    private boolean containsNonTransparentPixel(BufferedImage image){
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (!isTransparent(image, j, i)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica se um pixel(localizado em <strong>(x,y)</strong>) é 
     * transparente
     * 
     * @param image é o <code>BufferedImage</code> com a imagem
     * @param x é <code>integer</code> com o ponto no eixo das abscissas
     * @param y é <code>integer</code> com o ponto no eixo das ordenadas
     * @return um <code>boolean</code> se o ponto é transparente ou não.
     */
    public boolean isTransparent(BufferedImage image, int x, int y ) {
        int pixel = image.getRGB(x,y);
        return (pixel>>24) == 0x00;
    }
    
    /**
     * Converte um <code>ArrayList</code> para <code>Sprite[]</code>
     * 
     * @param array é o <code>ArrayList</code> contendo os sprites a serem convertidos
     * @return um <code>Sprite[]</code> com o conteúdo do <code>ArrayList</code>
     */
    public Sprite[] toArray(ArrayList<Sprite> array) {
    	Sprite[] resp = new Sprite[array.size()];
    	
    	for(int i = 0 ; i < array.size(); i++) 
    		resp[i] = array.get(i);
    	
    	return resp;
    }
}
