package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.vo.CondominoVO;

public interface CondominoService {
	
	
	public abstract List<Condomino> buscarPorUnidade(Unidade unidade) throws SQLException, Exception;
	
	public abstract void salvar(Condomino condomino) throws  SQLException, Exception;
	
	public abstract void excluir(Condomino condomino) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizar(Condomino condomino) throws SQLException, Exception;
		
	public abstract List<CondominoVO> buscarListaCondominosVOPorNomeCondominoECondominio(String nomeCondomino, Condominio condominio) throws SQLException, Exception;
	
	public abstract List<CondominoVO> buscarListaCondominosVOPorNomeCondominoECondominio(String nomeCondomino, Condominio condominio, Bloco bloco) throws SQLException, Exception;
	
	public abstract List<CondominoVO> buscarListaCondominosVOPorCondominioBlocoEUnidade(Condominio condominio, Bloco bloco, Unidade unidade) throws SQLException, Exception;
	
	public abstract List<Condomino> buscarConselheirosPorCondominio(Condominio condominio) throws SQLException, Exception; 
	
	public abstract Condomino buscarSindicoGeralPorCondominio(Condominio condominio) throws SQLException, Exception;
		
	public abstract Condomino buscarSubSindicoGeralPorCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract Condomino buscarSindicoPorBloco(Bloco bloco) throws SQLException, Exception;
	
	public abstract Condomino buscarSubSindicoPorBloco(Bloco bloco) throws SQLException, Exception;
	
	public abstract List<Condomino> buscarConselheirosPorBloco(Bloco bloco) throws SQLException, Exception;
	
	public abstract Condomino buscarPorId(Integer id) throws SQLException, Exception;	
	
	public abstract List<CondominoVO> buscarListaCondominosVOPorIdCondominioEPagadorSemImagem(Integer idCondominio) throws SQLException, Exception;
	
}