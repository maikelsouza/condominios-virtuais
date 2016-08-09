package br.com.condominiosvirtuais.enumeration;

public enum EnderecoImagemEnum {
	
	URL_FOTO_NAO_DISPONIVEL ("/imagens/fotoNaoDisponivel.jpg");
	
	private String enderecoImagem = null;	
	
	EnderecoImagemEnum(String imagem){
		this.enderecoImagem = imagem;
	}

	public String getEnderecoImagem() {
		return enderecoImagem;
	}	


}
