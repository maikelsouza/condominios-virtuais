ALTER TABLE GRUPO_USUARIO ADD COLUMN DESCRICAO VARCHAR(150) NULL DEFAULT '';

ALTER TABLE GRUPO_USUARIO ADD COLUMN SITUACAO BOOLEAN NOT NULL DEFAULT TRUE;

ALTER TABLE GRUPO_USUARIO ADD COLUMN ID_CONDOMINIO INTEGER NULL;

ALTER TABLE GRUPO_USUARIO ADD COLUMN ID_SINDICO_PROFISSIONAL INTEGER NULL;

ALTER TABLE GRUPO_USUARIO ADD COLUMN ID_ESCRITORIO_CONTABILIDADE INTEGER NULL;

ALTER TABLE GRUPO_USUARIO ADD CONSTRAINT FK_GRUPO_USUARIO_ID_CONDOMINIO_CONDOMINIO_ID FOREIGN KEY (ID_CONDOMINIO) REFERENCES CONDOMINIO(ID);

ALTER TABLE GRUPO_USUARIO ADD CONSTRAINT FK_GRUPO_USUARIO_ID_SINDICO_PROFISSIONAL_SINDICO_PROFISSIONAL_ID FOREIGN KEY (ID_SINDICO_PROFISSIONAL) REFERENCES SINDICO_PROFISSIONAL(ID);

ALTER TABLE GRUPO_USUARIO ADD CONSTRAINT FK_GRUPO_USUARIO_ID_ESCRITORIO_CONTABILI_ESCRITORIO_CONTABILI_ID FOREIGN KEY (ID_ESCRITORIO_CONTABILIDADE) REFERENCES ESCRITORIO_CONTABILIDADE(ID);


CREATE TABLE IF NOT EXISTS MODULO(
	ID integer NOT NULL AUTO_INCREMENT,
	NOME VARCHAR(150) NOT NULL,
	DESCRICAO VARCHAR(150) NOT NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS TELA(
	ID integer NOT NULL AUTO_INCREMENT,	 
	NOME VARCHAR(150) NOT NULL,
	DESCRICAO VARCHAR(150) NOT NULL,
	NOME_ARQUIVO VARCHAR(150) NOT NULL,
	ID_MODULO integer NOT NULL,
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
	NOME VARCHAR(150) NOT NULL,
	DESCRICAO VARCHAR(150) NOT NULL,
	ID_COMPONENTE VARCHAR(150) NOT NULL,
	ID_TELA integer,
	ID_ABA integer,
	TIPO integer NOT NULL,
	CONSTRAINT FK_COMPONENTE_ID_TELA_TELA_ID FOREIGN KEY (ID_TELA) REFERENCES TELA(ID),
	CONSTRAINT FK_COMPONENTE_ID_ABA_ABA_ID FOREIGN KEY (ID_ABA) REFERENCES TELA(ID),
	PRIMARY KEY(ID)
);


CREATE TABLE IF NOT EXISTS GRUPO_USUARIO_TELA(
	ID integer NOT NULL AUTO_INCREMENT,
	ID_GRUPO_USUARIO integer NOT NULL, 
	ID_TELA integer NOT NULL,
	ACAO VARCHAR(100) NOT NULL,
	CONSTRAINT FK_GRUPO_USUARIO_TELA_ID_GRUPO_USUARIO_GRUPO_USUARIO_ID FOREIGN KEY (ID_GRUPO_USUARIO) REFERENCES GRUPO_USUARIO(ID),
	CONSTRAINT FK_GRUPO_USUARIO_TELA_ID_TELA_TELA_ID FOREIGN KEY (ID_TELA) REFERENCES TELA(ID),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS GRUPO_USUARIO_TELA_ABA(
	ID integer NOT NULL AUTO_INCREMENT,
	ID_TELA integer NOT NULL,
	ID_ABA integer NOT NULL,
	ID_GRUPO_USUARIO integer NOT NULL,
	ACAO VARCHAR(100) NOT NULL,
	CONSTRAINT FK_GRUPO_USUARIO_TELA_ABA_ID_TELA_TELA_ID FOREIGN KEY (ID_TELA) REFERENCES TELA(ID),
	CONSTRAINT FK_GRUPO_USUARIO_TELA_ABA_ID_ABA_ABA_ID FOREIGN KEY (ID_ABA) REFERENCES ABA(ID),
	CONSTRAINT FK_GRUPO_USUARIO_TELA_ABA_ID_GRUPO_USUARIO_GRUPO_USUARIO_ID FOREIGN KEY (ID_GRUPO_USUARIO) REFERENCES GRUPO_USUARIO(ID),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS TELA_COMPONENTE(
	ID integer NOT NULL AUTO_INCREMENT,
	ID_TELA integer NOT NULL,
	ID_COMPONENTE integer NOT NULL,
	ACAO VARCHAR(100) NOT NULL,
	CONSTRAINT FK_TELA_COMPONENTE_ID_TELA_TELA_ID FOREIGN KEY (ID_TELA) REFERENCES TELA(ID),
	CONSTRAINT FK_TELA_COMPONENTE_ID_COMPONENTE_ID_COMPONENTE FOREIGN KEY (ID_COMPONENTE) REFERENCES COMPONENTE(ID),
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
(DEFAULT,'arquivoDocumentos','arquivoDocumentosDescricao'),
(DEFAULT,'arquivoImagens','arquivoImagensDescricao'),
(DEFAULT,'cadastroCondominio','cadastroCondominioDescricao'),
(DEFAULT,'cadastroBloco','cadastroBlocoDescricao'),
(DEFAULT,'cadastroUnidade','cadastroUnidadeDescricao'),
(DEFAULT,'cadastroCondomino','cadastroCondominoDescricao'),
(DEFAULT,'cadastroAmbiente','cadastroAmbienteDescricao'),
(DEFAULT,'cadastroFuncionario','cadastroFuncionarioDescricao'),
(DEFAULT,'cadastroObra','cadastroObraDescricao'),
(DEFAULT,'cadastroSindicoProfissional','cadastroSindicoProfissionalDescricao'),
(DEFAULT,'cadastroEscritorioContabilidade','cadastroEscritorioContabilidadeDescricao'),
(DEFAULT,'veiculo','veiculoDescricao'),
(DEFAULT,'mensagem','mensagemDescricao'),
(DEFAULT,'enquete','enqueteDescricao'),
(DEFAULT,'classificados','classificadosDescricao'),
(DEFAULT,'telefonesUteis','telefonesUteisDescricao'),
(DEFAULT,'receitaDespesa','cadastroReceitaDespesaDescricao'),
(DEFAULT,'chamado','chamadoDescricao'),
(DEFAULT,'visitante','visitanteDescricao'),




INSERT INTO TELA VALUES
(DEFAULT,'formAnexaDocumento','formAnexoDocumentoDescricao','formAnexaDocumento.xhtml',1),
(DEFAULT,'formListaDocumento','formListaDocumentoDescricao','formListaDocumento.xhtml',1),
(DEFAULT,'formAnexaImagem','formAnexaImagemDescricao','formAnexaImagem.xhtml',2),
(DEFAULT,'formListaImagem','formListaAnexaImagemDescricao','formListaImagem.xhtml',2),
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
(DEFAULT,'formCadastroFuncionario','formCadastroFuncionarioDescricao','formCadastroFuncionarioCondominio.xhtml',8),
(DEFAULT,'formListaFuncionario','formListaFuncionarioDescricao','formListaFuncionarioCondominio.xhtml',8),
(DEFAULT,'formEditaFuncionario','formEditaFuncionarioDescricao','formEditaFuncionarioCondominio.xhtml',8),
(DEFAULT,'formVisualizaFuncionario','formVisualizaFuncionarioDescricao','formVisualizaFuncionarioCondominio.xhtml',8),
(DEFAULT,'formCadastroObra','formCadastroObraDescricao','formCadastroObra.xhtml',9),
(DEFAULT,'formVisualizaObra','formVisualizaObraDescricao','formVisualizaObra.xhtml',9),
(DEFAULT,'formEditaObra','formEditaObraDescricao','formEditaObra.xhtml',9),
(DEFAULT,'formVisualizaObra','formVisualizaObraDescricao','formVisualizaObra.xhtml',9),
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
(DEFAULT,'formListaEnquete','formListaEnqueteDescricao','formListaEnquete.xhtml',14),
(DEFAULT,'formVisualizaEnquete','formVisualizaEnqueteDescricao','formVisualizaEnquete.xhtml',14),
(DEFAULT,'formCadastroEnquete','formCadastroEnqueteDescricao','formCadastroEnquete.xhtml',14),
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

http://app.condominiosvirtuais.com.br/CondominiosVirtuais/pages/formCadastroVisitante.xhtml
http://app.condominiosvirtuais.com.br/CondominiosVirtuais/pages/formListaVisita.xhtml






INSERT INTO ABA VALUES(DEFAULT,'formMeuPainel.idTabDadosPessoais','formMeuPainel.idTabDadosPessoais.descricao','idTabDadosPessoais',11),
(DEFAULT,'formMeuPainel.idTabAlterarImagemCondomino','formMeuPainel.idTabAlterarImagemCondomino.descricao','idTabAlterarImagemCondomino',11);

INSERT INTO COMPONENTE VALUES
(DEFAULT,'formListaDocumento.idBotaoAnexaoDocumento','formListaDocumento.idBotaoAnexaoDocumentoDescricao','idBotaoAnexaoDocumento',2,null,0),
(DEFAULT,'formListaCondominio.idBotaoCadastroCondominio','formListaCondominio.idBotaoCadastroCondominioDescricao','idBotaoCadastroCondominio',5,null,0);






