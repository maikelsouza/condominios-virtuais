package br.com.condominiosvirtuais.entity;

import java.sql.Time;
import java.util.Date;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

public class Agendamento {
	
	private Integer id;
	
	private Date data;
	
	private Time hora;
	
	private String horaInicial;	               
	
	private String horaFinal;
	
	private String observacao;
	
	private String situacao;
	
	private String motivoReprovacao;
	
	private Bloco bloco;
	
	private Unidade unidade;
	
	private Condomino condomino;
	
	public Agendamento(){
		this.motivoReprovacao = ""; 
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}	

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getMotivoReprovacao() {
		return motivoReprovacao;
	}

	public void setMotivoReprovacao(String motivoReprovacao) {
		this.motivoReprovacao = motivoReprovacao;
	}

	public String getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}	
	
	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}
	

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}
	

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	/* Existe três possibilidade de situação: Pedente, Aprovada ou Reprovada.
	 * Para que seja possíve exibir essas três possibilidades em um único campo e em momentos diferentes, foi
	 * gravado a string no banco. 
	 */
	public String getSituacaoI18n() {
		return AplicacaoUtil.i18n(this.situacao);
	}

	
	
	
}
