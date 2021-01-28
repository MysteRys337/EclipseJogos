package attributes.matriz;

/**
 * Classe para geração de uma matriz bidimensional de forma flexível
 * @author MysteRys337 (GLR)
 * @version 0.1
 */
public class Matriz<T> {

	/**
	 * Classe interna Celula, para gerenciar os dados dentro da Matriz,
	 * assim como o elemento T
	 * @author MysteRys337 (GLR)
	 * @version 0.1
	 */
	private class Celula {
		
		//Celulas
		public Celula superior;
		public Celula inferior;
		public Celula direita;
		public Celula esquerda;
		
		//Elemento
		private T elemento;
		
		/**
		 * Construtor 1
		 */
		public Celula() {
			this.superior = null;
			this.inferior = null;
			this.direita  = null;
			this.esquerda = null;
			this.elemento = null;
		}
		
		/**
		 * Pegar o elemento da Célula
		 * @return <code>T</code> que pertence a Célula
		 */
		public T getElemento() {
			return this.elemento;
		}
		
		/**
		 * Configurar o elemento <code>T</code> da célula
		 * @param elemento <code>T</code> que vai ser o novo elemento da a Célula
		 */
		public void setElemento(T elemento) {
			this.elemento = elemento;
		}
	}
	
	//Elementos da Matriz
	private Celula primeira;       //primeiro elemento da matriz
	private Celula ultimaInserida; //ultimo elemento inserido

	private int L; //Numero de linhas
	private int C; //Numero de colunas
	
	/**
	 * Construtor padrão da Matriz
	 * número de linhas:   3
	 * números de colunas: 3
	 */
	public Matriz() {
		this.L = 3;
		this.C = 3;
		this.gerarMatriz();
	}
	
	/**
	 * Construtor 1 da Matriz:
	 * números de linhas:  x
	 * números de colunas: x
	 * @param x é o número de linhas e colunas da matriz
	 * @exception x se for menor que 3, o programa irá re-escrever
	 * 
	 */
	public Matriz(int x) {
		if(x < 3) 
			x = 3;
		
		this.L = x;
		this.C = x;
		this.gerarMatriz();
	}
	
	/**
	 * Construtor 2 da Matriz:
	 * números de linhas:  L
	 * números de colunas: C
	 * @param L é o número de linhas da matriz
	 * @param C é o número de colunas da matriz
	 */
	public Matriz(int L, int C) {
		if(L < 3 ) 
			L = 3;
		if(C < 3) 
			C = 3;

		this.L = L;
		this.C = C;
		this.gerarMatriz();
	}

	public int getLinhas() {
		return this.L;
	}

	public int getColunas() {
		return this.C;
	}

	/**
	 * Função para limpar a matriz
	 */
	public void clear() {
		gerarMatriz();
	}
	
	/**
	 * Gerar a matriz 
	 */
	private void gerarMatriz() {
		
		this.primeira       = new Celula();
		this.ultimaInserida = this.primeira;

		Celula aux = this.primeira;
		
		for(int i = 0; i < this.L - 1; i++) {
			Celula tmp = new Celula();
			aux.direita = tmp;
			tmp.esquerda = aux;
			
			aux = aux.direita;
		}
		
		aux = this.primeira;
		for(int i = 0; i < this.C - 1; i++) {
			
			Celula tmp = new Celula();
			aux.inferior = tmp;
			tmp.superior = aux;
			for(int j = 0; j < this.L - 1; j++) {
				
				Celula tmp2 = new Celula();
				tmp.direita   = tmp2;
				tmp2.esquerda = tmp;
				tmp.superior.direita.inferior = tmp2;
				tmp2.superior = tmp.superior.direita;
				
				tmp = tmp.direita;
			}
			aux = aux.inferior;
		}
	}

	/**
	 * Adicionar elemento a matriz
	 * @param t é o elemento a ser adicionado
	 * @return <code>true</code> ou <code>false</code> se a adição foi sucedida
	 */
	public boolean add(T t) {
		boolean resp = false;
		if(this.ultimaInserida == null) 
			return resp;

		this.ultimaInserida.setElemento(t);
		resp = true;

		if(this.ultimaInserida.direita != null) {
			this.ultimaInserida = this.ultimaInserida.direita;
		}
		else if(this.ultimaInserida.inferior != null) {
			this.ultimaInserida = this.ultimaInserida.inferior;
			while(this.ultimaInserida.esquerda != null) 
				this.ultimaInserida = this.ultimaInserida.esquerda;
		}
		else {
			this.ultimaInserida = null;
		}
		
		return resp;
	}
	
	/**
	 * Procurar o x elemento na matriz como se fosse um array, e retornar
	 * @param x a posição do elemento na matriz como se fosse um array
	 * @return o elemento que foi encontrado
	 * @exception x se a posição for maior ou menor do que o número de elementos na matriz
	 */
	public T get(int x) {
		if(x >= this.C * this.L || x < 0) 
			return null;
		
		int c = x / this.L;
		int l = x % this.L;
		
		return search(l,c);
	}
	
	/**
	 * Procurar o elemento nas coordenadas x,y
	 * @param x posição onde o elemento está no eixo das abscissas
	 * @param y posiçãp onde o elemento está no eixo das ordenadas
	 * @return o elemento que foi encontrado
	 * @exception x se for maior do que o número de elementos no eixo das abscissas
	 * @exception y se for maior do que o número de elementos no eixo das ordenadas
	 */
	public T get(int x, int y) {

		if(x > this.L || y > this.C || x <= 0 || y <= 0) 
			return null;
		
		return search(x,y);
	}

	/**
	 * Função usada pelas funções <code>get</code> para atravessar a matriz procurando pelo o elemento
	 * @param x posição onde o elemento está no eixo das abscissas
	 * @param y posiçãp onde o elemento está no eixo das ordenadas
	 * @return o elemento que foi encontrado
	 */
	private T search(int x, int y) {
		Celula resp = this.primeira;
		
		for(int i = 0; i < y;i++, resp = resp.inferior);
		for(int i = 0; i < x;i++, resp = resp.direita);
		
		return resp.getElemento();
	}

	/**
	 * Armazenar os dados da matriz dentro de uma String e retornar ao usuário
	 * @return uma <code>String</code> contendo informações sobre a String
	 */
	public String print() {
		String resp = "";
		for(Celula i = this.primeira; i != null; i = i.inferior) {
			for(Celula j = i; j != null; j = j.direita) {
				resp += j.getElemento() + " ";
			}
			resp += "\n";
		}
		return resp;
	}
}
