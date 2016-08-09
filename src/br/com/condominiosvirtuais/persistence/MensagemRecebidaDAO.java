package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.MensagemRecebida;
import br.com.condominiosvirtuais.entity.Usuario;

public interface MensagemRecebidaDAO {
	
	public abstract void salvar(MensagemRecebida mensagemRecebida) throws SQLException, Exception;
	
	public abstract void salvarListaMensagemRecebida(List<MensagemRecebida> listaMensagemRecebida) throws SQLException, Exception;
	
	public abstract void excluir(MensagemRecebida mensagemRecebida) throws SQLException, Exception;
	
	public abstract List<MensagemRecebida> buscarPorUsuarioDestinatario(Usuario usuarioDestinatario) throws SQLException, Exception;
	
	public abstract void atualizar(MensagemRecebida mensagemRecebida) throws SQLException, Exception;
	
	public abstract void visualizar(MensagemRecebida mensagemRecebida) throws SQLException, Exception;

}
