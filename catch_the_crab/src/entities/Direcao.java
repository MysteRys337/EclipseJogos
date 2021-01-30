package entities;

public enum Direcao {

	DIREITA((byte)0),
	ESQUERDA((byte)1),
	CIMA((byte)2),
	BAIXO((byte)3);
	
	private byte direcao;
	
	Direcao(byte direcao) {
		this.direcao = direcao;
	}
	
	public byte getDirecao() {
		return this.direcao;
	}
}
