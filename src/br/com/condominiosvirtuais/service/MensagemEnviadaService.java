package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.MensagemEnviada;
import br.com.condominiosvirtuais.entity.Usuario;

public interface MensagemEnviadaService {
	
	public abstract void excluir(MensagemEnviada mensagemEnviada) throws SQLException, Exception;	
	
	public abstract List<MensagemEnviada> buscarPorUsuarioRemetente(Usuario usuario) throws SQLException, Exception;

}
