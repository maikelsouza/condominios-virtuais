ALTER TABLE GRUPO_USUARIO ADD COLUMN DESCRICAO VARCHAR(150) NULL DEFAULT '';

ALTER TABLE GRUPO_USUARIO ADD COLUMN SITUACAO BOOLEAN NOT NULL DEFAULT TRUE;

ALTER TABLE GRUPO_USUARIO ADD COLUMN PADRAO BOOLEAN NOT NULL DEFAULT TRUE;

ALTER TABLE GRUPO_USUARIO ADD COLUMN TIPO_USUARIO INTEGER NULL;

ALTER TABLE GRUPO_USUARIO ADD COLUMN ID_CONDOMINIO INTEGER NULL;

ALTER TABLE GRUPO_USUARIO ADD COLUMN ID_SINDICO_PROFISSIONAL INTEGER NULL;

ALTER TABLE GRUPO_USUARIO ADD COLUMN ID_ESCRITORIO_CONTABILIDADE INTEGER NULL;

ALTER TABLE GRUPO_USUARIO ADD CONSTRAINT FK_GRUPO_USUARIO_ID_CONDOMINIO_CONDOMINIO_ID FOREIGN KEY (ID_CONDOMINIO) REFERENCES CONDOMINIO(ID);

ALTER TABLE GRUPO_USUARIO ADD CONSTRAINT FK_GRUPO_USUARIO_ID_SINDICO_PROFISSIONAL_SINDICO_PROFISSIONAL_ID FOREIGN KEY (ID_SINDICO_PROFISSIONAL) REFERENCES SINDICO_PROFISSIONAL(ID);

ALTER TABLE GRUPO_USUARIO ADD CONSTRAINT FK_GRUPO_USUARIO_ID_ESCRITORIO_CONTABILI_ESCRITORIO_CONTABILI_ID FOREIGN KEY (ID_ESCRITORIO_CONTABILIDADE) REFERENCES ESCRITORIO_CONTABILIDADE(ID);

UPDATE GRUPO_USUARIO SET TIPO_USUARIO = 6 WHERE ID = 2;

UPDATE GRUPO_USUARIO SET TIPO_USUARIO = 4 WHERE ID = 3;

UPDATE GRUPO_USUARIO SET TIPO_USUARIO = 3 WHERE ID = 4;

UPDATE GRUPO_USUARIO SET TIPO_USUARIO = 1 WHERE ID = 5;

UPDATE GRUPO_USUARIO SET TIPO_USUARIO = 2 WHERE ID = 6;

UPDATE GRUPO_USUARIO SET TIPO_USUARIO = 5 WHERE ID = 7;


UPDATE GRUPO_USUARIO SET NOME = 'grupoUsuario.nome.0' WHERE ID = 1;

UPDATE GRUPO_USUARIO SET NOME = 'grupoUsuario.nome.1' WHERE ID = 5;

UPDATE GRUPO_USUARIO SET NOME = 'grupoUsuario.nome.2' WHERE ID = 6;

UPDATE GRUPO_USUARIO SET NOME = 'grupoUsuario.nome.3' WHERE ID = 4;

UPDATE GRUPO_USUARIO SET NOME = 'grupoUsuario.nome.4' WHERE ID = 3;

UPDATE GRUPO_USUARIO SET NOME = 'grupoUsuario.nome.5' WHERE ID = 7;

UPDATE GRUPO_USUARIO SET NOME = 'grupoUsuario.nome.6' WHERE ID = 2;


UPDATE GRUPO_USUARIO SET DESCRICAO = 'grupoUsuario.descricao.0' WHERE ID = 1;

UPDATE GRUPO_USUARIO SET DESCRICAO = 'grupoUsuario.descricao.1' WHERE ID = 5;

UPDATE GRUPO_USUARIO SET DESCRICAO = 'grupoUsuario.descricao.2' WHERE ID = 6;

UPDATE GRUPO_USUARIO SET DESCRICAO = 'grupoUsuario.descricao.3' WHERE ID = 4;

UPDATE GRUPO_USUARIO SET DESCRICAO = 'grupoUsuario.descricao.4' WHERE ID = 3;

UPDATE GRUPO_USUARIO SET DESCRICAO = 'grupoUsuario.descricao.5' WHERE ID = 7;

UPDATE GRUPO_USUARIO SET DESCRICAO = 'grupoUsuario.descricao.6' WHERE ID = 2;



CREATE TABLE IF NOT EXISTS MODULO(
	ID integer NOT NULL AUTO_INCREMENT,
	NOME VARCHAR(150) NOT NULL,
	DESCRICAO VARCHAR(150) NOT NULL,
	CONSTRAINT UQ_MODULO_NOME UNIQUE (NOME),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS TELA(
	ID integer NOT NULL AUTO_INCREMENT,	 
	NOME VARCHAR(150) NOT NULL,
	DESCRICAO VARCHAR(150) NOT NULL,
	NOME_ARQUIVO VARCHAR(150) NOT NULL,
	ID_MODULO integer NOT NULL,
	CONSTRAINT UQ_TELA_NOME UNIQUE (NOME),
	CONSTRAINT FK_TELA_ID_MODULO_MODULO_ID FOREIGN KEY (ID_MODULO) REFERENCES MODULO(ID),	
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS ABA(
	ID integer NOT NULL AUTO_INCREMENT,
	NOME VARCHAR(150) NOT NULL,
	DESCRICAO VARCHAR(150) NOT NULL,
	ID_ABA VARCHAR(150) NOT NULL,
	ID_TELA integer NOT NULL,
	CONSTRAINT FK_ABA_ID_TELA_TELA_ID FOREIGN KEY (ID_TELA) REFERENCES TELA(ID),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS COMPONENTE(
	ID integer NOT NULL AUTO_INCREMENT,
	NOME VARCHAR(200) NOT NULL,
	DESCRICAO VARCHAR(150) NOT NULL,
	ID_COMPONENTE VARCHAR(150) NOT NULL,
	ID_TELA integer,
	ID_ABA integer,
	TIPO VARCHAR(150) NOT NULL,
	CONSTRAINT UQ_COMPONENTE_NOME UNIQUE (NOME),
	CONSTRAINT FK_COMPONENTE_ID_TELA_TELA_ID FOREIGN KEY (ID_TELA) REFERENCES TELA(ID),
	CONSTRAINT FK_COMPONENTE_ID_ABA_ABA_ID FOREIGN KEY (ID_ABA) REFERENCES TELA(ID),
	PRIMARY KEY(ID)
);


CREATE TABLE IF NOT EXISTS GRUPO_USUARIO_TELA(
	ID integer NOT NULL AUTO_INCREMENT,
	ID_GRUPO_USUARIO integer NOT NULL, 
	ID_TELA integer NOT NULL,	
	CONSTRAINT UQ_GRUPO_USUARIO_TELA_ID_GRUPO_USUARIO_ID_TELA UNIQUE (ID_GRUPO_USUARIO,ID_TELA),
	CONSTRAINT FK_GRUPO_USUARIO_TELA_ID_GRUPO_USUARIO_GRUPO_USUARIO_ID FOREIGN KEY (ID_GRUPO_USUARIO) REFERENCES GRUPO_USUARIO(ID),
	CONSTRAINT FK_GRUPO_USUARIO_TELA_ID_TELA_TELA_ID FOREIGN KEY (ID_TELA) REFERENCES TELA(ID),	           
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS GRUPO_USUARIO_TELA_ABA(
	ID integer NOT NULL AUTO_INCREMENT,
	ID_TELA integer NOT NULL,
	ID_ABA integer NOT NULL,
	ID_GRUPO_USUARIO integer NOT NULL,	
	CONSTRAINT FK_GRUPO_USUARIO_TELA_ABA_ID_TELA_TELA_ID FOREIGN KEY (ID_TELA) REFERENCES TELA(ID),
	CONSTRAINT FK_GRUPO_USUARIO_TELA_ABA_ID_ABA_ABA_ID FOREIGN KEY (ID_ABA) REFERENCES ABA(ID),	           
	CONSTRAINT FK_GRUPO_USUARIO_TELA_ABA_ID_GRUPO_USUARIO_GRUPO_USUARIO_ID FOREIGN KEY (ID_GRUPO_USUARIO) REFERENCES GRUPO_USUARIO(ID),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS GRUPO_USUARIO_TELA_COMPONENTE(
	ID integer NOT NULL AUTO_INCREMENT,
	ID_TELA integer NOT NULL,
	ID_COMPONENTE integer NOT NULL,	
	ID_GRUPO_USUARIO integer NOT NULL,
	CONSTRAINT FK_GRUPO_USUARIO_TELA_COMPONENTE_ID_TELA_TELA_ID FOREIGN KEY (ID_TELA) REFERENCES TELA(ID),
	CONSTRAINT FK_GRUPO_USUARIO_TELA_COMPONENTE_ID_COMPONENTE_ID_COMPONENTE FOREIGN KEY (ID_COMPONENTE) REFERENCES COMPONENTE(ID),
	CONSTRAINT FK_GRUPO_USUARIO_TELA_COMPONENTE_ID_GRUPO_USUARIO_ID_GRUPO_USUARIO FOREIGN KEY (ID_GRUPO_USUARIO) REFERENCES GRUPO_USUARIO(ID),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS USUARIO_GRUPO_USUARIO(
	ID integer NOT NULL AUTO_INCREMENT,
	ID_USUARIO integer NOT NULL,
	ID_GRUPO_USUARIO integer NOT NULL,
	CONSTRAINT FK_USUARIO_GRUPO_USUARIO_ID_USUARIO_USUARIO_ID FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID),
	CONSTRAINT FK_USUARIO_GRUPO_USUARIO_ID_GRUPO_USUARIO_ID_GRUPO_USUARIO FOREIGN KEY (ID_GRUPO_USUARIO) REFERENCES GRUPO_USUARIO(ID),
	PRIMARY KEY(ID)
);


INSERT INTO MODULO VALUES
(DEFAULT,'documentos','documentosDescricao'),
(DEFAULT,'imagens','imagensDescricao'),
(DEFAULT,'condominio','condominioDescricao'),
(DEFAULT,'bloco','blocoDescricao'),
(DEFAULT,'unidade','unidadeDescricao'),
(DEFAULT,'condomino','condominoDescricao'),
(DEFAULT,'ambiente','ambienteDescricao'),
(DEFAULT,'funcionario','funcionarioDescricao'),
(DEFAULT,'obra','obraDescricao'),
(DEFAULT,'sindicoProfissional','sindicoProfissionalDescricao'),
(DEFAULT,'escritorioContabilidade','escritorioContabilidadeDescricao'),
(DEFAULT,'veiculo','veiculoDescricao'),
(DEFAULT,'mensagem','mensagemDescricao'),
(DEFAULT,'enquete','enqueteDescricao'),
(DEFAULT,'classificados','classificadosDescricao'),
(DEFAULT,'telefonesUteis','telefonesUteisDescricao'),
(DEFAULT,'receitaDespesa','receitaDespesaDescricao'),
(DEFAULT,'chamado','chamadoDescricao'),
(DEFAULT,'visitante','visitanteDescricao'),
(DEFAULT,'reserva','reservaDescricao'),
(DEFAULT,'agendamento','agendamentoDescricao'),
(DEFAULT,'garagem','garagemDescricao'),
(DEFAULT,'grupoUsuario','grupoUsuarioDescricao'),
(DEFAULT,'meuPainel','meuPainelDescricao');



INSERT INTO TELA VALUES
(DEFAULT,'formAnexaDocumento','formAnexoDocumentoDescricao','formAnexaDocumento.xhtml',1),
(DEFAULT,'formListaDocumento','formListaDocumentoDescricao','formListaDocumento.xhtml',1),
(DEFAULT,'formAnexaImagem','formAnexaImagemDescricao','formAnexaImagem.xhtml',2),
(DEFAULT,'formListaImagem','formListaImagemDescricao','formListaImagem.xhtml',2),
(DEFAULT,'formCadastroCondominio','formCadastroCondominioDescricao','formCadastroCondominio.xhtml',3),
(DEFAULT,'formListaCondominio','formListaCondominioDescricao','formListaCondominio.xhtml',3),
(DEFAULT,'formEditaCondominio','formEditaCondominioDescricao','formEditaCondominio.xhtml',3),
(DEFAULT,'formVisualizaCondominio','formVisualizaCondominioDescricao','formVisualizaCondominio.xhtml',3), 
(DEFAULT,'formCadastroBloco','formCadastroBlocoDescricao','formCadastroBloco.xhtml',4),
(DEFAULT,'formListaBloco','formListaBlocoDescricao','formListaBloco.xhtml',4),
(DEFAULT,'formEditaBloco','formEditaBlocoDescricao','formEditaBloco.xhtml',4),
(DEFAULT,'formVisualizaBloco','formVisualizaBlocoDescricao','formVisualizaBloco.xhtml',4),
(DEFAULT,'formCadastroUnidade','formCadastroUnidadeDescricao','formCadastroUnidade.xhtml',5),
(DEFAULT,'formEditaUnidade','formEditaUnidadeDescricao','formEditaUnidade.xhtml',5),
(DEFAULT,'formVisualizaUnidade','formVisualizaUnidadeDescricao','formVisualizaUnidade.xhtml',5),
(DEFAULT,'formListaUnidade','formListaUnidadeDescricao','formListaUnidade.xhtml',5),
(DEFAULT,'formCadastroCondomino','formCadastroCondominoDescricao','formCadastroCondomino.xhtml',6),
(DEFAULT,'formVisualizaCondomino','formVisualizaCondominoDescricao','formVisualizaCondomino.xhtml',6),
(DEFAULT,'formEditaCondomino','formEditaCondominoDescricao','formEditaCondomino.xhtml',6),
(DEFAULT,'formListaCondomino','formListaCondominoDescricao','formListaCondomino.xhtml',6),
(DEFAULT,'formCadastroAmbiente','formCadastroAmbienteDescricao','formCadastroAmbienteCondominio.xhtml',7),
(DEFAULT,'formListaAmbiente','formListaAmbienteDescricao','formListaAmbienteCondominio.xhtml',7),
(DEFAULT,'formEditaAmbiente','formEditaAmbienteDescricao','formEditaAmbienteCondominio.xhtml',7),
(DEFAULT,'formVisualizaAmbiente','formVisualizaAmbienteDescricao','formVisualizaAmbienteCondominio.xhtml',7),
(DEFAULT,'formCadastroItemAmbiente','formCadastroItemAmbienteDescricao','formCadastroItemAmbiente.xhtml',7),
(DEFAULT,'formEditaItemAmbiente','formEditaItemAmbienteDescricao','formEditaItemAmbiente.xhtml',7),
(DEFAULT,'formCadastroFuncionario','formCadastroFuncionarioDescricao','formCadastroFuncionarioCondominio.xhtml',8),
(DEFAULT,'formListaFuncionario','formListaFuncionarioDescricao','formListaFuncionarioCondominio.xhtml',8),
(DEFAULT,'formEditaFuncionario','formEditaFuncionarioDescricao','formEditaFuncionarioCondominio.xhtml',8),
(DEFAULT,'formVisualizaFuncionario','formVisualizaFuncionarioDescricao','formVisualizaFuncionarioCondominio.xhtml',8),
(DEFAULT,'formCadastroObra','formCadastroObraDescricao','formCadastroObra.xhtml',9),
(DEFAULT,'formVisualizaObra','formVisualizaObraDescricao','formVisualizaObra.xhtml',9),
(DEFAULT,'formEditaObra','formEditaObraDescricao','formEditaObra.xhtml',9),
(DEFAULT,'formVisualizaObra','formVisualizaObraDescricao','formVisualizaObra.xhtml',9),
(DEFAULT,'formListaObra','formListaObraDescricao','formListaObra.xhtml',9),
(DEFAULT,'formCadastroSindicoProfissional','formCadastroSindicoProfissionalDescricao','formCadastroSindicoProfissional.xhtml',10),
(DEFAULT,'formListaSindicoProfissional','formListaSindicoProfissionalDescricao','formListaSindicoProfissional.xhtml',10),
(DEFAULT,'formEditaSindicoProfissional','formEditaSindicoProfissionalDescricao','formEditaSindicoProfissional.xhtml',10),
(DEFAULT,'formCadastroContador','formCadastroContadorDescricao','formCadastroContador.xhtml',11),
(DEFAULT,'formListaContador','formListaContadorDescricao','formListaContador.xhtml',11),
(DEFAULT,'formEditaContador','formEditaContadorDescricao','formEditaContador.xhtml',11),
(DEFAULT,'formListaVeiculo','formListaVeiculoDescricao','formListaVeiculo.xhtml',12),
(DEFAULT,'formMensagensRecebidas','formMensagensRecebidasDescricao','formMensagensRecebidas.xhtml',13),
(DEFAULT,'formMensagensEnviadas','formMensagensEnviadasDescricao','formMensagensEnviadas.xhtml',13),
(DEFAULT,'formEscreverMensagem','formEscreverMensagemDescricao','formEscreverMensagem.xhtml',13),
(DEFAULT,'formFaleComSindico','formFaleComSindicoDescricao','formFaleComSindico.xhtml',13),
(DEFAULT,'formVerMensagemEnviada','formVerMensagemEnviadaDescricao','formVerMensagemEnviada.xhtml',13),
(DEFAULT,'formVerMensagemRecebida','formVerMensagemRecebidaDescricao','formVerMensagemRecebida.xhtml',13),
(DEFAULT,'formListaEnquete','formListaEnqueteDescricao','formListaEnquete.xhtml',14),
(DEFAULT,'formVisualizaEnquete','formVisualizaEnqueteDescricao','formVisualizaEnquete.xhtml',14),
(DEFAULT,'formCadastroEnquete','formCadastroEnqueteDescricao','formCadastroEnquete.xhtml',14),
(DEFAULT,'formVotoEnquete','formVotoEnqueteDescricao','formVotoEnquete.xhtml',14),
(DEFAULT,'formListaClassificados','formListaClassificadosDescricao','formListaClassificados.xhtml',15),
(DEFAULT,'formCadastroClassificados','formCadastroClassificadosDescricao','formCadastroClassificados.xhtml',15),
(DEFAULT,'formEditaClassificados','formEditaClassificadosDescricao','formEditaClassificados.xhtml',15),
(DEFAULT,'formListaTelefonesUteis','formListaTelefonesUteisDescricao','formListaTelefonesUteis.xhtml',16),
(DEFAULT,'formCadastroTelefonesUteis','formCadastroTelefonesUteisDescricao','formCadastroTelefonesUteis.xhtml',16),
(DEFAULT,'formEditaTelefonesUteis','formEditaTelefonesUteisDescricao','formEditaTelefonesUteis.xhtml',16),
(DEFAULT,'formListaReceitaDespesa','formListaReceitaDespesaDescricao','formListaReceitaDespesa.xhtml',17),
(DEFAULT,'formEditaReceita','formEditaReceitaDescricao','formEditaReceita.xhtml',17),
(DEFAULT,'formEditaDespesa','formEditaDespesaDescricao','formEditaDespesa.xhtml',17),
(DEFAULT,'formCadastroReceitaDespesa','formCadastroReceitaDespesaDescricao','formCadastroReceitaDespesa.xhtml',17),
(DEFAULT,'formCadastroChamado','formCadastroChamadoDescricao','formCadastroChamado.xhtml',18),
(DEFAULT,'formListaChamado','formListaChamadoDescricao','formListaChamado.xhtml',18),
(DEFAULT,'formListaVisitante','formListaVisitanteDescricao','formListaVisitante.xhtml',19),
(DEFAULT,'formListaVisita','formListaVisitaDescricao','formListaVisita.xhtml',19),
(DEFAULT,'formCadastroVisitante','formCadastroVisitanteDescricao','formCadastroVisitante.xhtml',19),
(DEFAULT,'formVisualizaPrestadorServico','formVisualizaPrestadorServicoDescricao','formVisualizaPrestadorServico.xhtml',19),
(DEFAULT,'formCadastroReserva','formCadastroReservaDescricao','formCadastroReserva.xhtml',20),
(DEFAULT,'formDarAndamentoChamado','formDarAndamentoChamadoDescricao','formDarAndamentoChamado.xhtml',20),
(DEFAULT,'formEditaReserva','formEditaReservaDescricao','formEditaReserva.xhtml',20),
(DEFAULT,'formListaReserva','formListaReservaDescricao','formListaReserva.xhtml',20),
(DEFAULT,'formAprovaReserva','formAprovaReservaDescricao','formAprovaReserva.xhtml',20),
(DEFAULT,'formCadastroListaReserva','formCadastroListaReservaDescricao','formCadastroListaReserva.xhtml',20),
(DEFAULT,'formAprovaAgendamento','formAprovaAgendamentoDescricao','formAprovaAgendamento.xhtml',21),
(DEFAULT,'formListaAgendamento','formListaAgendamentoDescricao','formListaAgendamento.xhtml',21),
(DEFAULT,'formCadastroAgendamento','formCadastroAgendamentoDescricao','formCadastroAgendamento.xhtml',21),
(DEFAULT,'formCadastroGaragem','formCadastroGaragemDescricao','formCadastroGaragem.xhtml',22),
(DEFAULT,'formListaGaragem','formListaGaragemDescricao','formListaGaragem.xhtml',22),
(DEFAULT,'formEditaGaragem','formEditaGaragemDescricao','formEditaGaragem.xhtml',22),
(DEFAULT,'formCadastroGrupoUsuario','formCadastroGrupoUsuarioDescricao','formCadastroGrupoUsuario.xhtml',23),
(DEFAULT,'formListaGrupoUsuario','formListaGrupoUsuarioDescricao','formListaGrupoUsuario.xhtml',23),
(DEFAULT,'formAssociarGrupoUsuario','formAssociarGrupoUsuarioDescricao','formAssociarGrupoUsuario.xhtml',23),
(DEFAULT,'formAssociarGrupoUsuarioCondominio','formAssociarGrupoUsuarioCondominioDescricao','formAssociarGrupoUsuarioCondominio.xhtml',23),
(DEFAULT,'formListaTelaAba','formListaTelaAbaDescricao','formListaTelaAba.xhtml',23),
(DEFAULT,'formListaTelaComponente','formListaTelaComponenteDescricao','formListaTelaComponente.xhtml',23),
(DEFAULT,'formVisualizaTelaAba','formVisualizaTelaAbaDescricao','formVisualizaTelaAba.xhtml',23),
(DEFAULT,'formVisualizaTelaComponente','formVisualizaTelaComponenteDescricao','formVisualizaTelaComponente.xhtml',23),
(DEFAULT,'formVisualizaGrupoUsuarioTela','formVisualizaGrupoUsuarioDescricao','formVisualizaGrupoUsuarioTela.xhtml',23),
(DEFAULT,'formEditaGrupoUsuario','formEditaGrupoUsuarioDescricao','formEditaGrupoUsuario.xhtml',23),
(DEFAULT,'formEditaTelaAba','formEditaTelaAbaDescricao','formEditaTelaAba.xhtml',23),
(DEFAULT,'formEditaTelaComponente','formEditaTelaComponenteDescricao','formEditaTelaComponente.xhtml',23),
(DEFAULT,'formMeuPainel','formMeuPainelDescricao','formMeuPainel.xhtml',24);


INSERT INTO ABA VALUES
(DEFAULT,'formMeuPainel.idTabPanelDadosPessoaisCondomino','formMeuPainel.idTabPanelDadosPessoaisCondomino.descricao','idTabPanelDadosPessoaisCondomino',87),
(DEFAULT,'formMeuPainel.idTabPanelDadosPessoaisFuncionario','formMeuPainel.idTabPanelDadosPessoaisFuncionario.descricao','idTabPanelDadosPessoaisFuncionario',87),
(DEFAULT,'formMeuPainel.idTabPanelDadosPessoaisSindicoProfissional','formMeuPainel.idTabPanelDadosPessoaisSindicoProfissional.descricao','idTabPanelDadosPessoaisSindicoProfissional',87),
(DEFAULT,'formMeuPainel.idTabPanelDadosPessoaisContador','formMeuPainel.idTabPanelDadosPessoaisContador.descricao','idTabPanelDadosPessoaisContador',87),
(DEFAULT,'formMeuPainel.idTabAlterarImagemCondomino','formMeuPainel.idTabAlterarImagemCondomino.descricao','idTabAlterarImagemCondomino',87),
(DEFAULT,'formEscreverMensagem.idTabMensagemFuncionarios','formEscreverMensagem.idTabMensagemFuncionarios.descricao','idTabMensagemFuncionarios',45),
(DEFAULT,'formEscreverMensagem.idTabMensagemCondominios','formEscreverMensagem.idTabMensagemCondominios.descricao','idTabMensagemCondominios',45),
(DEFAULT,'formCadastroClassificados.idTabDadosAnuncio','formCadastroClassificados.idTabDadosAnuncio.descricao','idTabDadosAnuncio',54),
(DEFAULT,'formCadastroClassificados.idTabImagemAnuncio','formCadastroClassificados.idTabImagemAnuncio.descricao','idTabImagemAnuncio',54);
(DEFAULT,'formAssociarGrupoUsuario.idTabGrupoUsuarioCondominios','formAssociarGrupoUsuario.idTabGrupoUsuarioCondominios.descricao','idTabGrupoUsuarioCondominios',82);


INSERT INTO COMPONENTE VALUES
(DEFAULT,'formCadastroListaReserva.idLinkExcluirReserva','formCadastroListaReserva.idLinkExcluirReservaDescricao','idLinkExcluirReserva',74,null,'componente.tipo.1'),
(DEFAULT,'formEditaAmbienteCondominio.idBotaoExcluirCondominio','formEditaAmbienteCondominio.idBotaoExcluirCondominioDescricao','idBotaoExcluirCondominio',23,null,'componente.tipo.0'),
(DEFAULT,'formEditaBloco.idBotaoExcluirBloco','formEditaBloco.idBotaoExcluirBlocoDescricao','idBotaoExcluirBloco',11,null,'componente.tipo.0'),
(DEFAULT,'formEditaClassificados.idBotaoExcluirClassificados','formEditaClassificados.idBotaoExcluirClassificadosDescricao','idBotaoExcluirClassificados',55,null,'componente.tipo.0'),
(DEFAULT,'formEditaCondomino.idBotaoExcluirCondomino','formEditaCondomino.idBotaoExcluirCondominoDescricao','idBotaoExcluirCondomino',19,null,'componente.tipo.0'),
(DEFAULT,'formEditaDespesa.idBotaoExcluirDespesa','formEditaDespesa.idBotaoExcluirDespesaDescricao','idBotaoExcluirDespesa',61,null,'componente.tipo.0'),
(DEFAULT,'formEditaFuncionarioCondominio.idBotaoExcluirFuncionarioCondominio','formEditaFuncionarioCondominio.idBotaoExcluirFuncionarioCondominioDescricao','idBotaoExcluirFuncionarioCondominio',29,null,'componente.tipo.0'),
(DEFAULT,'formEditaGaragem.idBotaoExcluirGaragem','formEditaGaragem.idBotaoExcluirGaragemDescricao','idBotaoExcluirGaragem',80,null,'componente.tipo.0'),
(DEFAULT,'formEditaItemAmbiente.idBotaoExcluirItemAmbiente','formEditaItemAmbiente.idBotaoExcluirItemAmbienteDescricao','idBotaoExcluirItemAmbiente',26,null,'componente.tipo.0'),
(DEFAULT,'formEditaObra.idBotaoExcluirObra','formEditaObra.idBotaoExcluirObraDescricao','idBotaoExcluirObra',33,null,'componente.tipo.0'),
(DEFAULT,'formEditaReceita.idBotaoExcluirReceita','formEditaReceita.idBotaoExcluirReceitaDescricao','idBotaoExcluirReceita',60,null,'componente.tipo.0'),
(DEFAULT,'formEditaTelefonesUteis.idExcluirTelefonesUteis','formEditaTelefonesUteis.idExcluirTelefonesUteisDescricao','idExcluirTelefonesUteis',58,null,'componente.tipo.0'),
(DEFAULT,'formEditaUnidade.idExcluirUnidade','formEditaUnidade.idExcluirUnidadeDescricao','idExcluirUnidade',14,null,'componente.tipo.0'),
(DEFAULT,'formListaDocumento.idLinkExcluirDocumento','formListaDocumento.idLinkExcluirDocumentoDescricao','idLinkExcluirDocumento',2,null,'componente.tipo.1'),
(DEFAULT,'formListaEnquete.idLinkExcluirEnquete','formListaEnquete.idLinkExcluirEnqueteDescricao','idLinkExcluirEnquete',49,null,'componente.tipo.1'),
(DEFAULT,'formListaImagem.idLinkExcluirImagem','formListaImagem.idLinkExcluirImagemDescricao','idLinkExcluirImagem',4,null,'componente.tipo.1'),
(DEFAULT,'formListaReceitaDespesa.idBotaoGerarExcel','formListaReceitaDespesa.idBotaoGerarExcelDescricao','idBotaoGerarExcel',59,null,'componente.tipo.0');

          

