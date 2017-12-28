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

ALTER TABLE CONTA_BANCARIA MODIFY TOKEN VARCHAR(150) NULL DEFAULT NULL; 


CREATE TABLE IF NOT EXISTS TIPO_TITULO(
	ID integer NOT NULL AUTO_INCREMENT,
	SIGLA VARCHAR(10) NOT NULL,
	NOME VARCHAR(150) NOT NULL,
	SITUACAO BOOLEAN NOT NULL,
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
(DEFAULT,'AP','AP�LICE DE SEGURO',1),
(DEFAULT,'CC','CART�O DE CR�DITO',1),
(DEFAULT,'CH','CHEQUE',1),
(DEFAULT,'CPR','C�DULA DE PRODUTO RURAL',1),
(DEFAULT,'DAE','D�VIDA ATIVA DE ESTADO',1),
(DEFAULT,'DAM','D�VIDA ATIVA DE MUNIC�PIO',1),
(DEFAULT,'DAU','D�VIDA ATIVA DA UNI�O',1),
(DEFAULT,'DD','DOCUMENTO DE D�VIDA',1),
(DEFAULT,'DM','DUPLICATA MERCANTIL',1),
(DEFAULT,'DMI',' DUPLICATA MERCANTIL PARA INDICA��O',1),
(DEFAULT,'DR','DUPLICATA RURAL',1),
(DEFAULT,'DS','DUPLICATA DE SERVI�O',1),
(DEFAULT,'DSI','DUPLICATA DE SERVI�O PARA INDICA��O',1),
(DEFAULT,'EC','ENCARGOS CONDOMINIAIS',1),
(DEFAULT,'FAT','FATURA',1),
(DEFAULT,'LC','LETRA DE C�MBIO',1),
(DEFAULT,'ME','MENSALIDADE ESCOLAR',1),
(DEFAULT,'NCC','NOTA DE CR�DITO COMERCIAL',1),
(DEFAULT,'NCE','NOTA DE CR�DITO � EXPORTA��O',1),
(DEFAULT,'NCI','NOTA DE CR�DITO INDUSTRIAL',1),
(DEFAULT,'NCR','NOTA DE CR�DITO RURAL',1),
(DEFAULT,'ND','NOTA DE D�BITO',1),
(DEFAULT,'NF','NOTA FISCAL',1),
(DEFAULT,'NP','NOTA PROMISS�RIA',1),
(DEFAULT,'NPR','NOTA PROMISS�RIA RURAL',1),
(DEFAULT,'NS','NOTA DE SEGURO',1),
(DEFAULT,'O','OUTROS',1),
(DEFAULT,'PC','PARCELA DE CONS�RCIO',1),
(DEFAULT,'RC','RECIBO',1),
(DEFAULT,'TM','TRIPLICATA MERCANTIL',1),
(DEFAULT,'TS','TRIPLICATA DE SERVI�O',1),
(DEFAULT,'W','WARRANT',1);





