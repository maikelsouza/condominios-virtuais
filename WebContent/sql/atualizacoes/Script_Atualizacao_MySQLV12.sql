CREATE TABLE IF NOT EXISTS MEIO_PAGAMENTO(
	ID integer NOT NULL AUTO_INCREMENT,	
	NOME varchar(100) NOT NULL,
	CONSTRAINT UQ_MEIO_PAGAMENTO_NOME UNIQUE (NOME),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS DESPESA(
	ID integer NOT NULL AUTO_INCREMENT,	
	DESCRICAO varchar(150) NOT NULL,
	OBSERVACAO varchar(200),
	VALOR double precision NOT NULL,
	DATA date NOT NULL,
	NUMERO_DOCUMENTO varchar(70),
	ID_MEIO_PAGAMENTO integer NOT NULL,
	ID_CONDOMINIO integer NOT NULL,
	CONSTRAINT FK_DESPESA_ID_MEIO_PAGAMENTO_MEIO_PAGAMENTO_ID FOREIGN KEY (ID_MEIO_PAGAMENTO) REFERENCES MEIO_PAGAMENTO(ID),
	CONSTRAINT FK_DESPESA_ID_CONDOMINIO_CONDOMINIO_ID FOREIGN KEY (ID_CONDOMINIO) REFERENCES CONDOMINIO(ID),
	PRIMARY KEY(ID)
);


CREATE TABLE IF NOT EXISTS RECEITA(
	ID integer NOT NULL AUTO_INCREMENT,	
	DESCRICAO varchar(150) NOT NULL,
	OBSERVACAO varchar(200),
	VALOR double precision NOT NULL,
	DATA date NOT NULL,	
	NUMERO_DOCUMENTO varchar(70),
	ID_CONDOMINIO integer NOT NULL,
	ID_MEIO_PAGAMENTO integer NOT NULL,
	CONSTRAINT FK_RECEITA_ID_MEIO_PAGAMENTO_MEIO_PAGAMENTO_ID FOREIGN KEY (ID_MEIO_PAGAMENTO) REFERENCES MEIO_PAGAMENTO(ID),
	CONSTRAINT FK_RECEITA_ID_CONDOMINIO_CONDOMINIO_ID FOREIGN KEY (ID_CONDOMINIO) REFERENCES CONDOMINIO(ID),
	PRIMARY KEY(ID)
);


INSERT INTO MEIO_PAGAMENTO (ID, NOME)  VALUES (DEFAULT, 'Dinheiro'), (DEFAULT, 'Sicoob'), (DEFAULT, 'Banco do Brasil'),
(DEFAULT, 'Bradesco'),(DEFAULT, 'Caixa'),(DEFAULT, 'Ita�'),(DEFAULT, 'Safra'),(DEFAULT, 'Santander'),(DEFAULT, 'Sicredi'),
(DEFAULT, 'Banrisul'),(DEFAULT, 'Unicredi'),(DEFAULT, 'HSBC');

