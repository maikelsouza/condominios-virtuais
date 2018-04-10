package br.com.condominiosvirtuais.entity;

public class ConfiguracaoPadraoContaBancaria {
	
	private Integer id;
	
	private Double multa = 0.000;
	
	private Double jurosAoDia = 0.000;
	
	private Integer diasSemCobrarJurosAposVencimento = 0;
	
	private Boolean permitirEmitirBoletoSemValor;
	
	private Double desconto = 0.000;
	
	private Integer diasConcederDescontoAteVencimento = 0;
	
	private String instrucaoPadrao;	
	
	private TipoTitulo tipoTitulo;
	
	private Integer idContaBancaria;
	
	private Boolean aceite;
	
	private String siglaMaisNomeTipoTitulo;
	
	public ConfiguracaoPadraoContaBancaria(){
		this.tipoTitulo = new TipoTitulo();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getMulta() {
		return multa;
	}

	public void setMulta(Double multa) {
		this.multa = multa;
	}

	public Double getJurosAoDia() {
		return jurosAoDia;
	}

	public void setJurosAoDia(Double jurosAoDia) {
		this.jurosAoDia = jurosAoDia;
	}

	public Integer getDiasSemCobrarJurosAposVencimento() {
		return diasSemCobrarJurosAposVencimento;
	}

	public void setDiasSemCobrarJurosAposVencimento(Integer diasSemCobrarJurosAposVencimento) {
		this.diasSemCobrarJurosAposVencimento = diasSemCobrarJurosAposVencimento;
	}

	public Boolean getPermitirEmitirBoletoSemValor() {
		return permitirEmitirBoletoSemValor;
	}

	public void setPermitirEmitirBoletoSemValor(Boolean permitirEmitirBoletoSemValor) {
		this.permitirEmitirBoletoSemValor = permitirEmitirBoletoSemValor;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getDiasConcederDescontoAteVencimento() {
		return diasConcederDescontoAteVencimento;
	}

	public void setDiasConcederDescontoAteVencimento(Integer diasConcederDescontoAteVencimento) {
		this.diasConcederDescontoAteVencimento = diasConcederDescontoAteVencimento;
	}

	public String getInstrucaoPadrao() {
		return instrucaoPadrao;
	}

	public void setInstrucaoPadrao(String instrucaoPadrao) {
		this.instrucaoPadrao = instrucaoPadrao;
	}	

	public Boolean getAceite() {
		return aceite;
	}

	public void setAceite(Boolean aceite) {
		this.aceite = aceite;
	}

	public Integer getIdContaBancaria() {
		return idContaBancaria;
	}

	public void setIdContaBancaria(Integer idContaBancaria) {
		this.idContaBancaria = idContaBancaria;
	}

	public String getSiglaMaisNomeTipoTitulo() {
		return siglaMaisNomeTipoTitulo;
	}

	public void setSiglaMaisNomeTipoTitulo(String siglaMaisNomeTipoTitulo) {
		this.siglaMaisNomeTipoTitulo = siglaMaisNomeTipoTitulo;
	}

	public TipoTitulo getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(TipoTitulo tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}	

}
