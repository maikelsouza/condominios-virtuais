package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.MensagemEnviada;
import br.com.condominiosvirtuais.entity.Usuario;

public interface MensagemEnviadaDAO {
	
	public abstract void salvar(MensagemEnviada mensagemEnviada, Connection con) throws SQLException, Exception;
	
	public abstract void excluir(MensagemEnviada mensagemEnviada) throws SQLException, Exception;
	
	public abstract List<MensagemEnviada> buscarPorUsuarioRemetente(Usuario usuario) throws SQLException, Exception;

}
