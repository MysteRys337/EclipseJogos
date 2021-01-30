package graphics.sprite;

//Bibliotecas necessaria
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Spritesheet é uma classe criada para o usuário poder resgatar Sprites, seja a partir de um
 * diretório, ou de uma imagem.
 * 
 * @author MysteRys337
 * @version 0.2
 */
public class Spritesheet {
	
	//Variáveis
	private final String resourceFolder = "res";
	
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
		
		this.path = "spritesheet";
		
		setSpritesheet(path);
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
		
		setSpritesheet(path);
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
	 * Através de uma <code>String</code>, localiza o arquivo spritesheet e configura os sprites
	 * 
	 * Nessa função pode acontecer dois casos:
	 * 
	 * Caso 1: A "path" contém o endereço de uma pasta, o programa irá assumir que exista imagens dentro do diretório
	 * contendo os sprites. O Sprite será as imagens e o nomes dos sprites será o nome dos arquivos.
	 * 
	 * Caso 2: A "path" contém o endereço de uma imagem, o programa então irá resgatar essa imagem e usar a path para 
	 * encontrar um arquivo ".txt" com os nomes dos sprites. Depois disso, o código irá pesquisar por sub-imagens dentro
	 * da spritesheet.
	 * 
	 * @param path é a <code>String</code> contendo a localização do arquivo com o spritesheet
	 */
	private void setSpritesheet(String path) {
		
		try {
			File tmp = new File(resourceFolder + "/" + path);
			
			if( tmp.exists() && tmp.isDirectory() ) { //Caso 1 -> leia um diretório
				String contents[] = tmp.list();
				quickSort(contents,0,contents.length-1);
				sprites = new Sprite[contents.length];
				
				for(int i = 0; i < contents.length; i++) {
					if(contents[i].contains(".png")) {
						String name = contents[i].split("\\.")[0];
						BufferedImage b = ImageIO.read(new File(resourceFolder + "/" + tmp.getName() + "/" + contents[i]));
						sprites[i] = new Sprite(b, name);
					}
				}
				
			} else if (tmp.exists() && !tmp.isDirectory() ){ //Caso 2 -> leia um arquivo .png
				
				String nome = path.split("\\.")[0];
				
				this.spritesheet = ImageIO.read(getClass().getResource("/" + path));
				
				ArrayList<String> spriteNames = setSpritesNames(resourceFolder + "/" + nome + ".txt");
				this.sprites = new Sprite[spriteNames.size()];
				
				int index = 0;
				
				boolean noMoreSprites = false; //Variável para sinalizar quando os sprites acabaram
				
				for (int y = 0; y < spritesheet.getHeight(); y += SPRITE_SIZE) {
					for ( int x = 0; x < spritesheet.getWidth(); x += SPRITE_SIZE) {
						BufferedImage b = spritesheet.getSubimage(x,y,SPRITE_SIZE,SPRITE_SIZE);
						if(containsNonTransparentPixel(b)) {
							if(index < sprites.length) {
								sprites[index] = new Sprite(b, spriteNames.get(index));
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
			else {
				System.err.println("ERRO! Não foi encontrado nenhum spritesheet");
			}
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * Resgatar os nomes dos sprites dentro da spritesheet
	 * 
	 * @param path é o local onde o ".txt" com o nomes de todos os sprites está
	 */
	private ArrayList<String> setSpritesNames(String path) {

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
		
		return spriteNames;
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
    
    public void quickSort(String v[], int esquerda, int direita) {
		int esq = esquerda;
		int dir = direita;
		String pivo = v[(esq + dir) / 2];
		String troca;
	
		while (esq <= dir) {
			while (v[esq].compareTo(pivo) < 0) esq = esq + 1;
			
			while (v[dir].compareTo(pivo) > 0) dir = dir - 1;
			
			if (esq <= dir) {
				troca = v[esq];
				v[esq] = v[dir];
				v[dir] = troca;
				esq = esq + 1;
				dir = dir - 1;
			}
		}
		if (dir > esquerda) 
		quickSort(v, esquerda, dir);
		
		if(esq < direita)
		quickSort(v, esq, direita);
    }
}
