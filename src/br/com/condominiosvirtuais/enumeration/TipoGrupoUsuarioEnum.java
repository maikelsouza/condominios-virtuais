package br.com.condominiosvirtuais.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.condominiosvirtuais.util.AplicacaoUtil;


public enum TipoGrupoUsuarioEnum {
	
	ADMINISTRADOR(1),
	
	ADMINISTRADORA(2),
	
	SINDICO_PROFISSIONAL(3),
	
	SINDICO(4),
	
	CONDOMINO(5),
	
	FUNCIONARIO(6),
	
	ESCRITORIO_CONTABILIDADE(7);	
	
	private Integer grupoUsuario = null;
	
	private Map<Integer, List<String>> grupoUsuariosTelasAcesso = new HashMap<Integer, List<String>>();
	
	private List<String> telaAcesso = null;
	
	
	TipoGrupoUsuarioEnum(Integer grupoUsuario){
		this.grupoUsuario = grupoUsuario;
		this.popularTelasAcessoUsuarios();
	}

	public Integer getGrupoUsuario() {
		return grupoUsuario;
	}	
	
	public Map<Integer, List<String>> getGrupoUsuariosTelasAcesso() {
		return grupoUsuariosTelasAcesso;
	}

	public void setGrupoUsuariosTelasAcesso(
			Map<Integer, List<String>> grupoUsuariosTelasAcesso) {
		this.grupoUsuariosTelasAcesso = grupoUsuariosTelasAcesso;
	}
	
	/**
	 * FIXME: Método temporário. Criado para garantir que os if's no caso do funcionário seja sempre executado
	 */
	public void inicializar(){
		this.popularTelasAcessoUsuarios();
	}

	private void popularTelasAcessoUsuarios(){
		this.telaAcesso = new ArrayList<String>();
		this.grupoUsuariosTelasAcesso = new HashMap<Integer, List<String>>();				
		if(this.getGrupoUsuario() == 1){ // Administrador
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PRINCIPAL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PESQUISA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINIO_PRIMEIRA_VEZ.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_UNIDADE.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITAR_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_FUNCIONARIO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_ITEM_AMBIENTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_ITEM_AMBIENTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_FUNCIONARIO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_FUNCIONARIO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_FUNCIONARIO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_FUNCIONARIO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_FUNCIONARIO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_FUNCIONARIO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_FUNCIONARIO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESQUECI_MINHA_SENHA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_RECEBIDAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_ENVIADAS.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_RECEBIDA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_ENVIADA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MEU_PAINEL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DOCUMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ANEXA_DOCUMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_IMAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ANEXA_IMAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AGENDAMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_APROVA_AGENDAMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AGENDAMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_SINDICO_PROFISSIONAL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_SINDICO_PROFISSIONAL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_SINDICO_PROFISSIONAL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_RECEITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_RECEITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_RECEITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_RECEITA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONTADOR.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONTADOR.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CONTADOR.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_GERAR_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MEUS_BOLETOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_PRE_CADASTRO_BOLETO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_PRE_CADASTRO_BOLETO.getPathTelas());
			
			
			
// TODO: Retirar permissão após testes.			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_FALE_COM_SINDICO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESCREVER_MENSAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_GARAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_GARAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VEICULO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_DESPESAS_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_DESPESAS_TODOS_CONDOMINIOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_DESPESAS_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DESPESAS_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_DESPESAS_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_DESPESAS_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_DAR_ANDAMENTO_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_VISITANTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VISITANTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VISITA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_PRESTADOR_SERVICO.getPathTelas());
			
			
			
			
			
		}else if(this.getGrupoUsuario() == 2){ // Administradora
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PRINCIPAL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINIO.getPathTelas());						
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINIO.getPathTelas());	
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PESQUISA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINIO_PRIMEIRA_VEZ.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_UNIDADE.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITAR_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_ITEM_AMBIENTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_ITEM_AMBIENTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_FUNCIONARIO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_FUNCIONARIO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_FUNCIONARIO_CONDOMINIO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_FUNCIONARIO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_FUNCIONARIO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_FUNCIONARIO_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_APROVA_RESERVA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESQUECI_MINHA_SENHA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_RECEBIDAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_ENVIADAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_ENVIADA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_RECEBIDA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESCREVER_MENSAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MEU_PAINEL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DOCUMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ANEXA_DOCUMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BENEFICIARIO.getPathTelas());
			
			
		}else if(this.getGrupoUsuario() == 3){ // Síndico Profissional
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PRINCIPAL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CONDOMINIO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINIO.getPathTelas());	
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PESQUISA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINIO_PRIMEIRA_VEZ.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_UNIDADE.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITAR_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_ITEM_AMBIENTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_ITEM_AMBIENTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_FUNCIONARIO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_FUNCIONARIO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_FUNCIONARIO_CONDOMINIO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_FUNCIONARIO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_FUNCIONARIO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_FUNCIONARIO_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_APROVA_RESERVA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_RESERVA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESQUECI_MINHA_SENHA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_RECEBIDAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_ENVIADAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESCREVER_MENSAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_RECEBIDA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_ENVIADA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MEU_PAINEL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_GARAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_GARAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VEICULO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DOCUMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ANEXA_DOCUMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_IMAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ANEXA_IMAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AGENDAMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_APROVA_AGENDAMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AGENDAMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_DESPESAS_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_DESPESAS_TODOS_CONDOMINIOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_DESPESAS_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DESPESAS_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_DESPESAS_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_DAR_ANDAMENTO_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_VISITANTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VISITANTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VISITA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_PRESTADOR_SERVICO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_RECEITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_RECEITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_RECEITA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_GERAR_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_PRE_CADASTRO_BOLETO.getPathTelas());
			
		}else if(this.getGrupoUsuario() == 4){ // Síndico
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PRINCIPAL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CONDOMINIO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINIO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PESQUISA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINIO_PRIMEIRA_VEZ.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_UNIDADE.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_ITEM_AMBIENTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_ITEM_AMBIENTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_FUNCIONARIO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_FUNCIONARIO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_FUNCIONARIO_CONDOMINIO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_FUNCIONARIO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_FUNCIONARIO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_FUNCIONARIO_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_LISTA_RESERVA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_RESERVA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_APROVA_RESERVA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_RESERVA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESQUECI_MINHA_SENHA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_RECEBIDAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_ENVIADAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_RECEBIDA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_ENVIADA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESCREVER_MENSAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MEU_PAINEL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DOCUMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ANEXA_DOCUMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_IMAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ANEXA_IMAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VOTO_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_GARAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_GARAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VEICULO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AGENDAMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_APROVA_AGENDAMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AGENDAMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_DESPESAS_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_DESPESAS_TODOS_CONDOMINIOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_DESPESAS_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DESPESAS_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_DESPESAS_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_DESPESAS_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_DAR_ANDAMENTO_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_VISITANTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VISITANTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VISITA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_PRESTADOR_SERVICO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_RECEITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_RECEITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_RECEITA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_GERAR_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MEUS_BOLETOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_PRE_CADASTRO_BOLETO.getPathTelas());
			
		}else if (this.getGrupoUsuario() == 5){ // Condômino
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PRINCIPAL.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINIO.getPathTelas());	
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PESQUISA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINIO_PRIMEIRA_VEZ.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_UNIDADE.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITAR_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_AMBIENTE_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_AMBIENTE_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_LISTA_RESERVA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_RESERVA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESQUECI_MINHA_SENHA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_FALE_COM_SINDICO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESCREVER_MENSAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_RECEBIDAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_ENVIADAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_RECEBIDA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_ENVIADA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MEU_PAINEL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DOCUMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_IMAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VOTO_ENQUETE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_GARAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VEICULO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_AGENDAMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DESPESAS_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_OBRA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_RECEITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MEUS_BOLETOS.getPathTelas());
			
			
		}else if (this.getGrupoUsuario() == 6){ // Funcionario
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PRINCIPAL.getPathTelas());	
			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_RESERVA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESQUECI_MINHA_SENHA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PESQUISA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINIO_PRIMEIRA_VEZ.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_FALE_COM_SINDICO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESCREVER_MENSAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_RECEBIDAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_ENVIADAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_RECEBIDA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_ENVIADA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MEU_PAINEL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_TELEFONES_UTEIS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CLASSIFICADOS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VEICULO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CHAMADO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_VISITANTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VISITANTE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VISITA.getPathTelas());
<<<<<<< HEAD
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_PRESTADOR_SERVICO.getPathTelas());
			
			if(AplicacaoUtil.getUsuarioAutenticado().getId() == 367 || AplicacaoUtil.getUsuarioAutenticado().getId() == 365){
				this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DOCUMENTO.getPathTelas());				
			}
			if(AplicacaoUtil.getUsuarioAutenticado().getId() == 365){
				this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ANEXA_DOCUMENTO.getPathTelas());
			}
			
=======
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_PRESTADOR_SERVICO.getPathTelas());	
>>>>>>> refs/heads/CriacaoBoleto
			
			
		} else if (this.getGrupoUsuario() == 7){ // Escritório Contabilidade
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PRINCIPAL.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINIO.getPathTelas());	
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_PESQUISA_CONDOMINIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINIO_PRIMEIRA_VEZ.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BLOCO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_BLOCO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_UNIDADE.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_UNIDADE.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITAR_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZA_CONDOMINO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESQUECI_MINHA_SENHA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_FALE_COM_SINDICO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ESCREVER_MENSAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_RECEBIDAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MENSAGENS_ENVIADAS.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_RECEBIDA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VER_MENSAGEM_ENVIADA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_MEU_PAINEL.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_DOCUMENTO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_IMAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ANEXA_DOCUMENTO.getPathTelas());			
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_ANEXA_IMAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_RECEITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_RECEITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_GARAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_GARAGEM.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_VEICULO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_DESPESA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_RECEITA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_CONTA_BANCARIA.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_BENEFICIARIO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_GERAR_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_CADASTRO_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_LISTA_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_EDITA_PRE_CADASTRO_BOLETO.getPathTelas());
			this.telaAcesso.add(PathTelasAplicacaoEnum.FORM_VISUALIZAR_PRE_CADASTRO_BOLETO.getPathTelas());
			
	}
		this.grupoUsuariosTelasAcesso.put(this.getGrupoUsuario(), this.telaAcesso);		
	}

}
