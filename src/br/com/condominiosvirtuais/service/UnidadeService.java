package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.vo.UnidadeVO;

public interface UnidadeService {
	
	public abstract List<Unidade> buscarListaUnidadesPorBloco(Bloco bloco) throws SQLException, Exception;
	
	public abstract void excluirUnidade(Unidade unidade) throws SQLException, BusinessException, Exception;
	
	public abstract void salvarUnidade(Unidade unidade) throws SQLException, BusinessException, Exception;
	
	public abstract void salvarUnidadeEmLote(List<Unidade> listaUnidades) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizarUnidade(Unidade unidade) throws SQLException, BusinessException, Exception;
	
	public abstract List<UnidadeVO> buscarListaUnidadesPorCondominioTipoEBloco(Condominio condominio, Integer tipo, Bloco bloco) throws SQLException, Exception;
	
	public abstract Unidade buscarPorId(Integer idUnidade) throws SQLException, Exception;
	
}
