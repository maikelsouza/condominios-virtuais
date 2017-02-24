CREATE TABLE IF NOT EXISTS BANCO(
	ID integer NOT NULL AUTO_INCREMENT,
	NOME VARCHAR(50) NOT NULL,
	CODIGO VARCHAR(10) NOT NULL,
	PRIMARY KEY(ID)
);	

INSERT INTO BANCO VALUES(DEFAULT,'Banco do Brasil','001'),
(DEFAULT,'Sicoob','756'),
(DEFAULT,'Bradesco','237'),
(DEFAULT,'Caixa','104'),
(DEFAULT,'Ita�','341'),
(DEFAULT,'Safra','422'),
(DEFAULT,'Santander','033'),
(DEFAULT,'Sicredi','748'),
(DEFAULT,'Banrisul','041'),
(DEFAULT,'Unicredi','136');
(DEFAULT,'HBSBC','399');


DELETE FROM MEIO_PAGAMENTO WHERE ID IN (3,4,5,6,7,8,9,10,11,12);
UPDATE MEIO_PAGAMENTO SET NOME = 'receitaDespesa.meioPagamento.0' WHERE ID = 1;
UPDATE MEIO_PAGAMENTO SET NOME = 'receitaDespesa.meioPagamento.1' WHERE ID = 2;

CREATE TABLE IF NOT EXISTS CONTA_BANCARIA(
	ID integer NOT NULL AUTO_INCREMENT,
	ID_BANCO integer NOT NULL, 
	NUMERO VARCHAR(50) NOT NULL,
	AGENCIA VARCHAR(50) NOT NULL,
	CARTEIRA VARCHAR(10) NOT NULL,
	CONSTRAINT FK_CONTA_BANCARIA_ID_BANCO_BANCO_ID FOREIGN KEY (ID_BANCO) REFERENCES BANCO(ID),	
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS CONTA_BANCARIA_CONDOMINIO(
	ID integer NOT NULL AUTO_INCREMENT,
	ID_CONTA_BANCARIA integer NOT NULL, 
	ID_CONDOMINIO integer NOT NULL,	
	CONSTRAINT FK_CONTA_BANCA_CONDOMINIO_ID_CONTA_BANCA_ID_CONTA_BANCA_ID FOREIGN KEY (ID_CONTA_BANCARIA) REFERENCES CONTA_BANCARIA(ID),
	CONSTRAINT FK_CONTA_BANCARIA_CONDOMINIO_ID_CONDOMINIO_CONDOMINIO_ID FOREIGN KEY (ID_CONDOMINIO) REFERENCES CONDOMINIO(ID),
	PRIMARY KEY(ID)
);

ALTER TABLE USUARIO ADD COLUMN CPF BIGINT DEFAULT 0 AFTER SITUACAO;

ALTER TABLE ENDERECO CHANGE ID_CONDOMINIO ID_CONDOMINIO integer NULL;

ALTER TABLE ENDERECO ADD COLUMN ID_BENEFICIARIO INTEGER  AFTER ID_CONDOMINIO;

CREATE TABLE IF NOT EXISTS BENEFICIARIO(
	ID integer NOT NULL AUTO_INCREMENT,
	NOME VARCHAR(100) NOT NULL,
	CPRF bigint NOT NULL,
	ID_CONDOMINIO integer NOT NULL,
	CONSTRAINT FK_BENEFICIARIO_ID_CONDOMINIO_CONDOMINIO_ID FOREIGN KEY (ID_CONDOMINIO) REFERENCES CONDOMINIO(ID),
	PRIMARY KEY(ID)
);

ALTER TABLE CONDOMINO ADD COLUMN PAGADOR INTEGER NOT NULL DEFAULT 0 AFTER TELEFONE_COMERCIAL;

CREATE TABLE IF NOT EXISTS BOLETO(
	ID integer NOT NULL AUTO_INCREMENT,
	EMISSAO date NOT NULL,
	VENCIMENTO date NOT NULL,
	DOCUMENTO VARCHAR(10) NOT NULL,
	NUMERO VARCHAR(30) NOT NULL,
	TITULO VARCHAR(4) NOT NULL,
	VALOR DOUBLE NOT NULL,
	PAGO BOOLEAN NOT NULL,	
	INSTRUCAO1 VARCHAR(150),
	INSTRUCAO2 VARCHAR(150),
	INSTRUCAO3 VARCHAR(150),
	ID_CONDOMINIO integer NOT NULL,
	ID_PAGADOR  integer NOT NULL,
	ID_BENEFICIARIO integer NOT NULL,
	ID_CONTA_BANCARIA integer NOT NULL,
	CONSTRAINT FK_BOLETO_ID_CONDOMINIO_CONDOMINIO_ID FOREIGN KEY (ID_CONDOMINIO) REFERENCES CONDOMINIO(ID),
	CONSTRAINT FK_BOLETO_ID_PAGADOR_CONDOMINO_ID FOREIGN KEY (ID_PAGADOR) REFERENCES CONDOMINO(ID),
	CONSTRAINT FK_BOLETO_ID_BENEFICIARIO_BENEFICIARIO_ID FOREIGN KEY (ID_BENEFICIARIO) REFERENCES BENEFICIARIO(ID),
	CONSTRAINT FK_BOLETO_ID_CONTA_BANCARIA_CONTA_BANCARIA_ID FOREIGN KEY (ID_CONTA_BANCARIA) REFERENCES CONTA_BANCARIA(ID),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS PRE_CADASTRO_BOLETO(
	ID integer NOT NULL AUTO_INCREMENT,	
	DIA_MES_VENCIMENTO integer NOT NULL,	
	TITULO VARCHAR(4) NOT NULL,
	INSTRUCAO1 VARCHAR(150),
	INSTRUCAO2 VARCHAR(150),
	INSTRUCAO3 VARCHAR(150),
	PRINCIPAL DOUBLE NOT NULL,
	ID_CONDOMINIO integer NOT NULL,	
	ID_BENEFICIARIO integer NOT NULL,
	ID_CONTA_BANCARIA integer NOT NULL,	
	CONSTRAINT FK_PRE_CADASTRO_BOLETO_ID_CONDOMINIO_CONDOMINIO_ID FOREIGN KEY (ID_CONDOMINIO) REFERENCES CONDOMINIO(ID),	
	CONSTRAINT FK_PRE_CADASTRO_BOLETO_ID_BENEFICIARIO_BENEFICIARIO_ID FOREIGN KEY (ID_BENEFICIARIO) REFERENCES BENEFICIARIO(ID),
	CONSTRAINT FK_PRE_CADASTRO_BOLETO_ID_CONTA_BANCARIA_CONTA_BANCARIA_ID FOREIGN KEY (ID_CONTA_BANCARIA) REFERENCES CONTA_BANCARIA(ID),
	PRIMARY KEY(ID)
);

ALTER TABLE RECEITA ADD COLUMN ID_BANCO INTEGER AFTER ID_MEIO_PAGAMENTO;

ALTER TABLE RECEITA ADD CONSTRAINT FK_RECEITA_ID_BANCO_BANCO_ID FOREIGN KEY (ID_BANCO) REFERENCES BANCO(ID);

ALTER TABLE DESPESA ADD COLUMN ID_BANCO INTEGER AFTER ID_MEIO_PAGAMENTO;

ALTER TABLE DESPESA ADD CONSTRAINT FK_DESPESA_ID_BANCO_BANCO_ID FOREIGN KEY (ID_BANCO) REFERENCES BANCO(ID);

UPDATE RECEITA SET ID_BANCO = 1 WHERE ID_MEIO_PAGAMENTO = 3;
UPDATE RECEITA SET ID_BANCO = 3 WHERE ID_MEIO_PAGAMENTO = 4;
UPDATE RECEITA SET ID_BANCO = 5 WHERE ID_MEIO_PAGAMENTO = 6;
UPDATE RECEITA SET ID_BANCO = 6 WHERE ID_MEIO_PAGAMENTO = 7;
UPDATE RECEITA SET ID_BANCO = 7 WHERE ID_MEIO_PAGAMENTO = 8;
UPDATE RECEITA SET ID_BANCO = 8 WHERE ID_MEIO_PAGAMENTO = 9;
UPDATE RECEITA SET ID_BANCO = 9 WHERE ID_MEIO_PAGAMENTO = 10;
UPDATE RECEITA SET ID_BANCO = 10 WHERE ID_MEIO_PAGAMENTO = 11;
UPDATE RECEITA SET ID_BANCO = 11 WHERE ID_MEIO_PAGAMENTO = 12;

UPDATE DESPESA SET ID_BANCO = 1 WHERE ID_MEIO_PAGAMENTO = 3;
UPDATE DESPESA SET ID_BANCO = 3 WHERE ID_MEIO_PAGAMENTO = 4;
UPDATE DESPESA SET ID_BANCO = 5 WHERE ID_MEIO_PAGAMENTO = 6;
UPDATE DESPESA SET ID_BANCO = 6 WHERE ID_MEIO_PAGAMENTO = 7;
UPDATE DESPESA SET ID_BANCO = 7 WHERE ID_MEIO_PAGAMENTO = 8;
UPDATE DESPESA SET ID_BANCO = 8 WHERE ID_MEIO_PAGAMENTO = 9;
UPDATE DESPESA SET ID_BANCO = 9 WHERE ID_MEIO_PAGAMENTO = 10;
UPDATE DESPESA SET ID_BANCO = 10 WHERE ID_MEIO_PAGAMENTO = 11;
UPDATE DESPESA SET ID_BANCO = 11 WHERE ID_MEIO_PAGAMENTO = 12;


UPDATE RECEITA SET ID_MEIO_PAGAMENTO = 2 WHERE ID_MEIO_PAGAMENTO <> 1
UPDATE DESPESA SET ID_MEIO_PAGAMENTO = 2 WHERE ID_MEIO_PAGAMENTO <> 1









