package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.UsuariosRecebidosMensagem;

public interface UsuariosRecebidosMensagemDAO {
	
	public abstract void salvar(UsuariosRecebidosMensagem usuariosRecebidosMensagem, Connection con) throws SQLException, Exception;
	
	public abstract void excluir(UsuariosRecebidosMensagem usuariosRecebidosMensagem, Connection con) throws SQLException, Exception;
	
	public abstract List<UsuariosRecebidosMensagem> buscar(Integer idMensagemEnviada, Connection con) throws SQLException, Exception;

}
