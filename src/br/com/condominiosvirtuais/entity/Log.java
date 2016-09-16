package br.com.condominiosvirtuais.entity;

import java.util.Date;

import br.com.condominiosvirtuais.enumeration.LogAcaoEnum;

/**
 * Entidade que representa o log de uma determinada ação feita pelo usuário. Exemplo de ação: EXCLUIR_RESERVA, veja o Enum {@link LogAcaoEnum} <\br>
 * Para cada funcionalidade que necessita de um log, será necessári criar uma entidade e uma tabela que irá armazenar o registro. Exemplo {@link LogReserva} 
 * @author Maikel
 * @since 16/09/2016
 */
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
