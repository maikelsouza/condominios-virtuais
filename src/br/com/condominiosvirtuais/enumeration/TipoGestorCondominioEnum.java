package br.com.condominiosvirtuais.enumeration;

public enum TipoGestorCondominioEnum {
	
	SINDICO_GERAL(1),
	
	SUBSINDICO_GERAL(2),
	
	CONSELHEIRO_CONDOMINIO(3),
	
	SINDICO(4),
	
	SUBSINDICO(5),
	
	CONSELHEIRO_BLOCO(6);

	
	private Integer gestorCondominio = null;
	
	TipoGestorCondominioEnum(Integer gestorCondominio){
		this.gestorCondominio = gestorCondominio;
	}

	public Integer getGestorCondominio() {
		return gestorCondominio;
	}

	
}
