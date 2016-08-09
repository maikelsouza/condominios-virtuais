package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Chamado;
import br.com.condominiosvirtuais.entity.Condominio;

public interface ChamadoDAO {
	
	public abstract void salvar(Chamado chamado) throws SQLException, Exception;
	
	public abstract void excluir(Integer idChamado) throws SQLException, Exception;
	
	public abstract List<Chamado> buscarPorCondominioEStatus(Condominio condominio, Integer status) throws SQLException, Exception;
	
	public abstract List<Chamado> buscarPorCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract List<Chamado> buscarPorUsuario(Integer idChamado) throws SQLException, Exception;
	
	public abstract void atualizarStatus(Integer idChamado, Integer status) throws SQLException, Exception;
	
	public abstract void fecharChamado(Integer idChamado, Date dataFechamento, String comentario) throws SQLException, Exception;

}
