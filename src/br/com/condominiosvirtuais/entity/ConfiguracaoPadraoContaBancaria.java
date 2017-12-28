package br.com.condominiosvirtuais.entity;

public class ConfiguracaoPadraoContaBancaria {
	
	private Integer id;
	
	private Double multa;
	
	private Double jurosAoDia;
	
	private Integer diasSemCobrarJurosAposVencimento;
	
	private Boolean permitirEmitirBoletoSemValor;
	
	private Double desconto;
	
	private Integer diasConcederDescontoAteVencimento;
	
	private String instrucaoPadrao;
	
	private Integer idTipoTitulo;
	
	private Integer idContaBancaria;
	
	private Boolean aceite;

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

	public Integer getIdTipoTitulo() {
		return idTipoTitulo;
	}

	public void setIdTipoTitulo(Integer idTipoTitulo) {
		this.idTipoTitulo = idTipoTitulo;
	}

	public Integer getIdContaBancaria() {
		return idContaBancaria;
	}

	public void setIdContaBancaria(Integer idContaBancaria) {
		this.idContaBancaria = idContaBancaria;
	}	
	

}
