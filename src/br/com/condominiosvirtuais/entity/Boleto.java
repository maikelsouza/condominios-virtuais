package br.com.condominiosvirtuais.entity;

import java.util.Date;

import br.com.condominiosvirtuais.vo.CondominoVO;

public class Boleto {	
	
	private Integer id;	
	
	private Date emissao;
	
	private Date vencimento;
	
	private String documento;
	
	private String numero;
	
	private String titulo;
	
	private Double valor;
	
	private Boolean pago;
	
	private String instrucao1;
	
	private String instrucao2;
	
	private String instrucao3;
	
	private Integer idCondominio;
	
	private ContaBancaria contaBancaria;
	
	private Beneficiario beneficiario;
	
	private CondominoVO condominoVO;
	
	public Boleto(){
		this.contaBancaria = new ContaBancaria();
		this.beneficiario = new Beneficiario();
		this.condominoVO = new CondominoVO();
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}	

	public CondominoVO getCondominoVO() {
		return condominoVO;
	}

	public void setCondominoVO(CondominoVO condominoVO) {
		this.condominoVO = condominoVO;
	}

	public Boolean getPago() {
		return pago;
	}

	public void setPago(Boolean pago) {
		this.pago = pago;
	}

	public String getInstrucao1() {
		return instrucao1;
	}

	public void setInstrucao1(String instrucao1) {
		this.instrucao1 = instrucao1;
	}

	public String getInstrucao2() {
		return instrucao2;
	}

	public void setInstrucao2(String instrucao2) {
		this.instrucao2 = instrucao2;
	}

	public String getInstrucao3() {
		return instrucao3;
	}

	public void setInstrucao3(String instrucao3) {
		this.instrucao3 = instrucao3;
	}

	
	
	
	

}
