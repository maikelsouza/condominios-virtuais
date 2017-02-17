package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface BeneficiarioDAO {
	
	public abstract void salvar(Beneficiario beneficiario) throws SQLException, BusinessException, Exception;
	
	public abstract void excluir(Beneficiario beneficiario) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizar(Beneficiario beneficiario) throws SQLException, BusinessException, Exception;
	
	public abstract List<Beneficiario> buscarPorIdCondominio(Integer idCondominio) throws SQLException, BusinessException, Exception;
	
	public abstract Beneficiario buscarPorId(Integer idBeneficiario, Connection con) throws SQLException, BusinessException, Exception;

}
