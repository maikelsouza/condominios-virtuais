package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.Endereco;

public interface EnderecoDAO {
	
	public abstract void salvarEndereco(Endereco endereco, Connection con) throws SQLException, Exception;
	
	public abstract Endereco buscarEnderecoPorIdBeneficiario(Integer idBeneficiario, Connection con) throws SQLException, Exception;
	
	public abstract void atualizarEndereco(Endereco endereco,  Connection con) throws SQLException, Exception;
	
	public abstract void atualizarEnderecoPorId(Endereco endereco,  Connection con) throws SQLException, Exception;
	
	public abstract void excluirEndereco(Endereco endereco,  Connection con) throws SQLException, Exception;

}
