package br.com.condominiosvirtuais.entity;

import java.util.Date;

public class Log {
	
	private Integer id;
	
	private String acao;
	
	private Date data;
	
	private Integer idUsuario;
	
	private LogReserva logReserva;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public LogReserva getLogReserva() {
		return logReserva;
	}

	public void setLogReserva(LogReserva logReserva) {
		this.logReserva = logReserva;
	}
	

}
