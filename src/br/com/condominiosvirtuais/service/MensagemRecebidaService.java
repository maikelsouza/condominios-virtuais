package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.MensagemRecebida;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.vo.MensagemRecebidaVO;

public interface MensagemRecebidaService {
	
	public abstract void salvar(MensagemRecebida mensagemRecebida) throws SQLException, Exception;
	
	public abstract void salvarListaMensagemRecebida(List<MensagemRecebida> listaMensagemRecebida) throws SQLException, Exception;
	
	public abstract void enviarListaMensagemRecebida(List<MensagemRecebida> listaMensagemRecebida) throws SQLException, Exception;
	
	public abstract void enviar(MensagemRecebida mensagemRecebida) throws SQLException, Exception;
	
	public abstract void excluir(MensagemRecebida mensagemRecebida) throws SQLException, Exception;
	
	public abstract List<MensagemRecebidaVO> buscarPorUsuarioDestinatario(Usuario usuarioDestinatario) throws SQLException, Exception;
	
	public abstract void atualizar(MensagemRecebida mensagemRecebida) throws SQLException, Exception;
	
	public abstract void visualizar(MensagemRecebida mensagemRecebida) throws SQLException, Exception;
	
}