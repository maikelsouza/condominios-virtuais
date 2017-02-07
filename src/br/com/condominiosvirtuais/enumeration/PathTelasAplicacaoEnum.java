package br.com.condominiosvirtuais.enumeration;


public enum PathTelasAplicacaoEnum {
	
	PREFIXO("/pages/"),
	
	FORM_LOGIN("/security/formLogin.xhtml"),
	FORM_PRINCIPAL(PREFIXO.getPathTelas()+"formPrincipal.xhtml"),	                                              
	FORM_LISTA_CONDOMINIO(PREFIXO.getPathTelas()+"formListaCondominio.xhtml"),	
	FORM_EDITA_CONDOMINIO(PREFIXO.getPathTelas()+"formEditaCondominio.xhtml"),
	FORM_CADASTRO_CONDOMINIO(PREFIXO.getPathTelas()+"formCadastroCondominio.xhtml"),
	FORM_VISUALIZA_CONDOMINIO(PREFIXO.getPathTelas()+"formVisualizaCondominio.xhtml"),
	FORM_PESQUISA_CONDOMINIO(PREFIXO.getPathTelas()+"formPesquisaCondominio.xhtml"),
	FORM_CADASTRO_CONDOMINIO_PRIMEIRA_VEZ(PREFIXO.getPathTelas()+"formCadastroCondominoPrimeiraVez.xhtml"),
	FORM_LISTA_BLOCO(PREFIXO.getPathTelas()+"formListaBloco.xhtml"),
	FORM_EDITA_BLOCO(PREFIXO.getPathTelas()+"formEditaBloco.xhtml"),
	FORM_CADASTRO_BLOCO(PREFIXO.getPathTelas()+"formCadastroBloco.xhtml"),
	FORM_VISUALIZA_BLOCO(PREFIXO.getPathTelas()+"formVisualizaBloco.xhtml"),
	FORM_LISTA_UNIDADE(PREFIXO.getPathTelas()+"formListaUnidade.xhtml"),
	FORM_EDITA_UNIDADE(PREFIXO.getPathTelas()+"formEditaUnidade.xhtml"),
	FORM_CADASTRO_UNIDADE(PREFIXO.getPathTelas()+"formCadastroUnidade.xhtml"),
	FORM_VISUALIZA_UNIDADE(PREFIXO.getPathTelas()+"formVisualizaUnidade.xhtml"),
	FORM_LISTA_CONDOMINO(PREFIXO.getPathTelas()+"formListaCondomino.xhtml"),
	FORM_EDITAR_CONDOMINO(PREFIXO.getPathTelas()+"formEditaCondomino.xhtml"),
	FORM_CADASTRO_CONDOMINO(PREFIXO.getPathTelas()+"formCadastroCondomino.xhtml"),
	FORM_VISUALIZA_CONDOMINO(PREFIXO.getPathTelas()+"formVisualizaCondomino.xhtml"),
	FORM_LISTA_AMBIENTE_CONDOMINIO(PREFIXO.getPathTelas()+"formListaAmbienteCondominio.xhtml"),
	FORM_EDITA_AMBIENTE_CONDOMINIO(PREFIXO.getPathTelas()+"formEditaAmbienteCondominio.xhtml"),
	FORM_CADASTRO_AMBIENTE_CONDOMINIO(PREFIXO.getPathTelas()+"formCadastroAmbienteCondominio.xhtml"),
	FORM_VISUALIZA_AMBIENTE_CONDOMINIO(PREFIXO.getPathTelas()+"formVisualizaAmbienteCondominio.xhtml"),
	FORM_LISTA_AMBIENTE_BLOCO(PREFIXO.getPathTelas()+"formListaAmbienteBloco.xhtml"),
	FORM_EDITA_AMBIENTE_BLOCO(PREFIXO.getPathTelas()+"formEditaAmbienteBloco.xhtml"),
	FORM_CADASTRO_AMBIENTE_BLOCO(PREFIXO.getPathTelas()+"formCadastroAmbienteBloco.xhtml"),
	FORM_VISUALIZA_AMBIENTE_BLOCO(PREFIXO.getPathTelas()+"formVisualizaAmbienteBloco.xhtml"),
	FORM_CADASTRO_ITEM_AMBIENTE(PREFIXO.getPathTelas()+"formCadastroItemAmbiente.xhtml"),
	FORM_EDITA_ITEM_AMBIENTE(PREFIXO.getPathTelas()+"formEditaItemAmbiente.xhtml"),	
	FORM_LISTA_FUNCIONARIO_CONDOMINIO(PREFIXO.getPathTelas()+"formListaFuncionarioCondominio.xhtml"),
	FORM_CADASTRO_FUNCIONARIO_CONDOMINIO(PREFIXO.getPathTelas()+"formCadastroFuncionarioCondominio.xhtml"),
	FORM_VISUALIZA_FUNCIONARIO_CONDOMINIO(PREFIXO.getPathTelas()+"formVisualizaFuncionarioCondominio.xhtml"),
	FORM_EDITA_FUNCIONARIO_CONDOMINIO(PREFIXO.getPathTelas()+"formEditaFuncionarioCondominio.xhtml"),
	FORM_LISTA_FUNCIONARIO_BLOCO(PREFIXO.getPathTelas()+"formListaFuncionarioBloco.xhtml"),
	FORM_CADASTRO_FUNCIONARIO_BLOCO(PREFIXO.getPathTelas()+"formCadastroFuncionarioBloco.xhtml"),
	FORM_VISUALIZA_FUNCIONARIO_BLOCO(PREFIXO.getPathTelas()+"formVisualizaFuncionarioBloco.xhtml"),
	FORM_EDITA_FUNCIONARIO_BLOCO(PREFIXO.getPathTelas()+"formEditaFuncionarioBloco.xhtml"),
	FORM_CADASTRO_LISTA_RESERVA(PREFIXO.getPathTelas()+"formCadastroListaReserva.xhtml"),
	FORM_CADASTRO_RESERVA(PREFIXO.getPathTelas()+"formCadastroReserva.xhtml"),
	FORM_EDITA_RESERVA(PREFIXO.getPathTelas()+"formEditaReserva.xhtml"),
	FORM_APROVA_RESERVA(PREFIXO.getPathTelas()+"formAprovaReserva.xhtml"),
	FORM_LISTA_RESERVA(PREFIXO.getPathTelas()+"formListaReserva.xhtml"),
	FORM_ESQUECI_MINHA_SENHA(PREFIXO.getPathTelas()+"formEsqueciMinhaSenha.xhtml"),
	FORM_FALE_COM_SINDICO(PREFIXO.getPathTelas()+"formFaleComSindico.xhtml"),
	FORM_ESCREVER_MENSAGEM(PREFIXO.getPathTelas()+"formEscreverMensagem.xhtml"),
	FORM_MENSAGENS_RECEBIDAS(PREFIXO.getPathTelas()+"formMensagensRecebidas.xhtml"),
	FORM_MENSAGENS_ENVIADAS(PREFIXO.getPathTelas()+"formMensagensEnviadas.xhtml"),
	FORM_VER_MENSAGEM_RECEBIDA(PREFIXO.getPathTelas()+"formVerMensagemRecebida.xhtml"),
	FORM_VER_MENSAGEM_ENVIADA(PREFIXO.getPathTelas()+"formVerMensagemEnviada.xhtml"),
	FORM_MEU_PAINEL(PREFIXO.getPathTelas()+"formMeuPainel.xhtml"),
	FORM_LISTA_DOCUMENTO(PREFIXO.getPathTelas()+"formListaDocumento.xhtml"),
	FORM_ANEXA_DOCUMENTO(PREFIXO.getPathTelas()+"formAnexaDocumento.xhtml"),
	FORM_LISTA_IMAGEM(PREFIXO.getPathTelas()+"formListaImagem.xhtml"),
	FORM_ANEXA_IMAGEM(PREFIXO.getPathTelas()+"formAnexaImagem.xhtml"),
	FORM_LISTA_TELEFONES_UTEIS(PREFIXO.getPathTelas()+"formListaTelefonesUteis.xhtml"),
	FORM_CADASTRO_TELEFONES_UTEIS(PREFIXO.getPathTelas()+"formCadastroTelefonesUteis.xhtml"),
	FORM_EDITA_TELEFONES_UTEIS(PREFIXO.getPathTelas()+"formEditaTelefonesUteis.xhtml"),
	FORM_LISTA_CLASSIFICADOS(PREFIXO.getPathTelas()+"formListaClassificados.xhtml"),
	FORM_CADASTRO_CLASSIFICADOS(PREFIXO.getPathTelas()+"formCadastroClassificados.xhtml"),
	FORM_EDITA_CLASSIFICADOS(PREFIXO.getPathTelas()+"formEditaClassificados.xhtml"),
	FORM_CADASTRO_ENQUETE(PREFIXO.getPathTelas()+"formCadastroEnquete.xhtml"),
	FORM_LISTA_ENQUETE(PREFIXO.getPathTelas()+"formListaEnquete.xhtml"),
	FORM_VISUALIZA_ENQUETE(PREFIXO.getPathTelas()+"formVisualizaEnquete.xhtml"),
	FORM_VOTO_ENQUETE(PREFIXO.getPathTelas()+"formVotoEnquete.xhtml"),
	FORM_CADASTRO_GARAGEM(PREFIXO.getPathTelas()+"formCadastroGaragem.xhtml"),
	FORM_EDITA_GARAGEM(PREFIXO.getPathTelas()+"formEditaGaragem.xhtml"),
	FORM_LISTA_VEICULO(PREFIXO.getPathTelas()+"formListaVeiculo.xhtml"),
	FORM_CADASTRO_AGENDAMENTO(PREFIXO.getPathTelas()+"formCadastroAgendamento.xhtml"),
	FORM_APROVA_AGENDAMENTO(PREFIXO.getPathTelas()+"formAprovaAgendamento.xhtml"),
	FORM_LISTA_AGENDAMENTO(PREFIXO.getPathTelas()+"formListaAgendamento.xhtml"),
	FORM_CADASTRO_DESPESAS_TODOS_CONDOMINIOS(PREFIXO.getPathTelas()+"formCadastroDespesasTodosCondominos.xhtml"),
	FORM_CADASTRO_DESPESAS_CONDOMINIO(PREFIXO.getPathTelas()+"formCadastroDespesasCondominio.xhtml"),
	FORM_CADASTRO_DESPESAS_CONDOMINO(PREFIXO.getPathTelas()+"formCadastroDespesasCondomino.xhtml"),
	FORM_LISTA_DESPESAS_CONDOMINIO(PREFIXO.getPathTelas()+"formListaDespesasCondominio.xhtml"),
	FORM_EDITA_DESPESAS_CONDOMINIO(PREFIXO.getPathTelas()+"formEditaDespesasCondominio.xhtml"),
	FORM_EDITA_DESPESAS_CONDOMINO(PREFIXO.getPathTelas()+"formEditaDespesasCondomino.xhtml"),
	FORM_CADASTRO_CHAMADO(PREFIXO.getPathTelas()+"formCadastroChamado.xhtml"),
	FORM_LISTA_CHAMADO(PREFIXO.getPathTelas()+"formListaChamado.xhtml"),
	FORM_DAR_ANDAMENTO_CHAMADO(PREFIXO.getPathTelas()+"formDarAndamentoChamado.xhtml"),
	FORM_CADASTRO_VISITANTE(PREFIXO.getPathTelas()+"formCadastroVisitante.xhtml"),
	FORM_LISTA_VISITANTE(PREFIXO.getPathTelas()+"formListaVisitante.xhtml"),
	FORM_LISTA_VISITA(PREFIXO.getPathTelas()+"formListaVisita.xhtml"),
	FORM_VISUALIZAR_PRESTADOR_SERVICO(PREFIXO.getPathTelas()+"formVisualizaPrestadorServico.xhtml"),
	FORM_CADASTRO_OBRA(PREFIXO.getPathTelas()+"formCadastroObra.xhtml"),
	FORM_LISTA_OBRA(PREFIXO.getPathTelas()+"formListaObra.xhtml"),
	FORM_EDITA_OBRA(PREFIXO.getPathTelas()+"formEditaObra.xhtml"),
	FORM_VISUALIZAR_OBRA(PREFIXO.getPathTelas()+"formVisualizaObra.xhtml"),
	FORM_CADASTRO_SINDICO_PROFISSIONAL(PREFIXO.getPathTelas()+"formCadastroSindicoProfissional.xhtml"),
	FORM_LISTA_SINDICO_PROFISSIONAL(PREFIXO.getPathTelas()+"formListaSindicoProfissional.xhtml"),
	FORM_EDITA_SINDICO_PROFISSIONAL(PREFIXO.getPathTelas()+"formEditaSindicoProfissional.xhtml"),
	FORM_CADASTRO_RECEITA_DESPESA(PREFIXO.getPathTelas()+"formCadastroReceitaDespesa.xhtml"),
	FORM_LISTA_RECEITA_DESPESA(PREFIXO.getPathTelas()+"formListaReceitaDespesa.xhtml"),
	FORM_EDITA_DESPESA(PREFIXO.getPathTelas()+"formEditaDespesa.xhtml"),
	FORM_EDITA_RECEITA(PREFIXO.getPathTelas()+"formEditaReceita.xhtml"),
	FORM_CADASTRO_CONTADOR(PREFIXO.getPathTelas()+"formCadastroContador.xhtml"),
	FORM_LISTA_CONTADOR(PREFIXO.getPathTelas()+"formListaContador.xhtml"),	
	FORM_EDITA_CONTADOR(PREFIXO.getPathTelas()+"formEditaContador.xhtml"),
	FORM_CADASTRO_CONTA_BANCARIA(PREFIXO.getPathTelas()+"formCadastroContaBancaria.xhtml"),
	FORM_VISUALIZAR_CONTA_BANCARIA(PREFIXO.getPathTelas()+"formVisualizaContaBancaria.xhtml"),
	FORM_LISTA_CONTA_BANCARIA(PREFIXO.getPathTelas()+"formListaContaBancaria.xhtml"),
	FORM_EDITA_CONTA_BANCARIA(PREFIXO.getPathTelas()+"formEditaContaBancaria.xhtml");
	
	
	
	
	PathTelasAplicacaoEnum(String pathTelas){
		this.pathTelas = pathTelas;		
	}
	
	private String pathTelas = null;

	public String getPathTelas() {
		return pathTelas;
	}

	public void setPathTelas(String pathTelas) {
		this.pathTelas = pathTelas;
	}
	

}
