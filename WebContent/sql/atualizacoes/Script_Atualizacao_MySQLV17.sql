UPDATE MODULO SET NOME = 'financeiro', DESCRICAO = 'financeiroDescricao' WHERE ID = 17;


INSERT INTO TELA VALUES
(DEFAULT,'formCadastroContaBancaria','formCadastroContaBancariaDescricao','formCadastroContaBancaria.xhtml',17),
(DEFAULT,'formListaContaBancaria','formListaContaBancariaDescricao','formListaContaBancaria.xhtml',17),
(DEFAULT,'formEditaContaBancaria','formEditaContaBancariaDescricao','formEditaContaBancaria.xhtml',17),
(DEFAULT,'formVisualizaContaBancaria','formVisualizaContaBancariaDescricao','formVisualizaContaBancaria.xhtml',17),
(DEFAULT,'formCadastroBeneficiario','formCadastroBeneficiarioDescricao','formCadastroBeneficiario.xhtml',17),
(DEFAULT,'formListaBeneficiario','formListaBeneficiarioDescricao','formListaBeneficiario.xhtml',17),
(DEFAULT,'formEditaBeneficiario','formEditaBeneficiarioDescricao','formEditaBeneficiario.xhtml',17),
(DEFAULT,'formVisualizaBeneficiario','formVisualizaDescricao','formVisualizaBeneficiario.xhtml',17),
(DEFAULT,'formCadastroPreCadastroBoleto','formCadastroPreCadastroBoletoDescricao','formCadastroPreCadastroBoleto.xhtml',17),
(DEFAULT,'formListaPreCadastroBoleto','formListaPreCadastroBoletoDescricao','formListaPreCadastroBoleto.xhtml',17),
(DEFAULT,'formEditaPreCadastroBoleto','formEditaPreCadastroBoletoDescricao','formEditaPreCadastroBoleto.xhtml',17),
(DEFAULT,'formVisualizaPreCadastroBoleto','formVisualizaPreCadastroBoletoDescricao','formVisualizaPreCadastroBoleto.xhtml',17),
(DEFAULT,'formGerarBoleto','formGerarBoletoDescricao','formGerarBoleto.xhtml',17),
(DEFAULT,'formListaBoleto','formListaBoletoDescricao','formListaBoleto.xhtml',17),
(DEFAULT,'formMeusBoletos','formMeusBoletosDescricao','formMeusBoletos.xhtml',17);


          
INSERT INTO GRUPO_USUARIO_TELA VALUES 
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroContaBancaria.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaContaBancaria.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaContaBancaria.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaContaBancaria.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroContaBancaria.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaContaBancaria.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaContaBancaria.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaContaBancaria.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroContaBancaria.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaContaBancaria.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaContaBancaria.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaContaBancaria.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroContaBancaria.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaContaBancaria.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaContaBancaria.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaContaBancaria.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formMeusBoletos.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroBeneficiario.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaBeneficiario.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaBeneficiario.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaBeneficiario.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroPreCadastroBoleto.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaPreCadastroBoleto.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaPreCadastroBoleto.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaPreCadastroBoleto.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formGerarBoleto.xhtml')),
(DEFAULT,4,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaBoleto.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroContaBancaria.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaContaBancaria.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaContaBancaria.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaContaBancaria.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroBeneficiario.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaBeneficiario.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaBeneficiario.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaBeneficiario.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroPreCadastroBoleto.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaPreCadastroBoleto.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaPreCadastroBoleto.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaPreCadastroBoleto.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formGerarBoleto.xhtml')),
(DEFAULT,1,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaBoleto.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroBeneficiario.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaBeneficiario.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaBeneficiario.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaBeneficiario.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroPreCadastroBoleto.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaPreCadastroBoleto.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaPreCadastroBoleto.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaPreCadastroBoleto.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formGerarBoleto.xhtml')),
(DEFAULT,2,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaBoleto.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroBeneficiario.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaBeneficiario.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaBeneficiario.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaBeneficiario.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroPreCadastroBoleto.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaPreCadastroBoleto.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaPreCadastroBoleto.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaPreCadastroBoleto.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formGerarBoleto.xhtml')),
(DEFAULT,3,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaBoleto.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroPreCadastroBoleto.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaPreCadastroBoleto.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaPreCadastroBoleto.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaPreCadastroBoleto.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formGerarBoleto.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaBoleto.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formCadastroBeneficiario.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formListaBeneficiario.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formEditaBeneficiario.xhtml')),
(DEFAULT,7,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formVisualizaBeneficiario.xhtml')),
(DEFAULT,5,(SELECT ID FROM TELA WHERE NOME_ARQUIVO = 'formMeusBoletos.xhtml'));


ALTER TABLE BENEFICIARIO ADD COLUMN RAZAO_SOCIAL VARCHAR(150) NOT NULL AFTER NOME;

ALTER TABLE BENEFICIARIO ADD COLUMN NOME_FANTASIA VARCHAR(150) AFTER RAZAO_SOCIAL;

ALTER TABLE BENEFICIARIO ADD COLUMN TIPO_DOCUMENTO INTEGER NOT NULL AFTER CPRF;

ALTER TABLE CONTA_BANCARIA MODIFY TOKEN VARCHAR(150) NULL DEFAULT NULL; 


CREATE TABLE IF NOT EXISTS TIPO_TITULO(
	ID integer NOT NULL AUTO_INCREMENT,
	SIGLA VARCHAR(10) NOT NULL,
	NOME VARCHAR(150) NOT NULL,
	SITUACAO BOOLEAN NOT NULL,
	LOCALE VARCHAR(5) NOT NULL,
	PRIMARY KEY(ID)
);


CREATE TABLE IF NOT EXISTS CONFIGURACAO_PADRAO_CONTA_BANCARIA(
	ID integer NOT NULL AUTO_INCREMENT,
	MULTA DOUBLE,
	JUROS_AO_DIA DOUBLE,
	DIAS_SEM_COBRAR_JUROS_APOS_VENCIMENTO integer,
	PERMITIR_EMITIR_BOLETO_SEM_VALOR BOOLEAN,	
	DESCONTO DOUBLE,
	DIAS_CONCEDER_DESCONTO_ATE_VENCIMENTO integer,
	INSTRUCAO_PADRAO VARCHAR(150),
	ACEITE BOOLEAN,
	ID_CONTA_BANCARIA integer NOT NULL,
	ID_TIPO_TITULO integer,
	CONSTRAINT FK_CONFIG_PADRAO_CONTA_BANC_ID_CONTA_BANC_CONTA_BANC_ID FOREIGN KEY (ID_CONTA_BANCARIA) REFERENCES CONTA_BANCARIA(ID),
	CONSTRAINT FK_CONFIG_PADRAO_CONTA_BANC_ID_TIPO_TITULO_TIPO_TITULO_ID FOREIGN KEY (ID_TIPO_TITULO) REFERENCES TIPO_TITULO(ID),
	PRIMARY KEY(ID)
);

INSERT INTO TIPO_TITULO VALUES
(DEFAULT,'AP','Apólice de Seguro',1),
(DEFAULT,'CC','Cartão de Crédito',1),
(DEFAULT,'CH','Cheque',1),
(DEFAULT,'CPR','Cédula de Produto RuraL',1),
(DEFAULT,'DAE','Dívida Ativa de Estado',1),
(DEFAULT,'DAM','Dívida Ativa de Município',1),
(DEFAULT,'DAU','Dívida Ativa da União',1),
(DEFAULT,'DD','Documento de Dívida',1),
(DEFAULT,'DM','Duplicata Mercantil',1),
(DEFAULT,'DMI','Duplicata Mercantil para Indicação',1),
(DEFAULT,'DR','Duplicata Rural',1),
(DEFAULT,'DS','Duplicata de Serviço',1),
(DEFAULT,'DSI','Duplicata de Serviço para Indicação',1),
(DEFAULT,'EC','Encargos Condominiais',1),
(DEFAULT,'FAT','Fatura',1),
(DEFAULT,'LC','Letra de Câmbio',1),
(DEFAULT,'ME','Mensalidade Escolar',1),
(DEFAULT,'NCC','Nota de Crédito Comercial',1),
(DEFAULT,'NCE','Nota de Crédito à Exportação',1),
(DEFAULT,'NCI','Nota de Crédito Industrial',1),
(DEFAULT,'NCR','Nota de Crédito Rural',1),
(DEFAULT,'ND','Nota de Débito',1),
(DEFAULT,'NF','Nota Fiscal',1),
(DEFAULT,'NP','Nota Promissória',1),
(DEFAULT,'NPR','Nota Promissória Rural',1),
(DEFAULT,'NS','Nota de Seguro',1),
(DEFAULT,'O','Outros',1),
(DEFAULT,'PC','Parcela de Consórcio',1),
(DEFAULT,'RC','Recibo',1),
(DEFAULT,'TM','Triplicata Mercantil',1),
(DEFAULT,'TS','Triplicata de Serviço',1),
(DEFAULT,'W','Warrant',1);

UPDATE condominio SET SITUACAO = 0 WHERE ID IN (8,19);

ALTER TABLE PRE_CADASTRO_BOLETO ADD COLUMN NOME VARCHAR(100) NOT NULL AFTER ID;

ALTER TABLE PRE_CADASTRO_BOLETO DROP COLUMN TITULO;

ALTER TABLE PRE_CADASTRO_BOLETO ADD UNIQUE UQ_PRE_CADASTRO_BOLETO_NOME_ID_CONDOMINIO (NOME,ID_CONDOMINIO);

ALTER TABLE CONTA_BANCARIA ADD COLUMN DESCRICAO VARCHAR(100) NOT NULL AFTER TOKEN;



