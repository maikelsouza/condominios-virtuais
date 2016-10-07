ALTER TABLE RESERVA ADD COLUMN MOTIVO_SUSPENSAO VARCHAR(150) DEFAULT '' AFTER MOTIVO_REPROVACAO;


CREATE TABLE IF NOT EXISTS LOG(
	ID integer NOT NULL AUTO_INCREMENT,	
	ACAO varchar(150) NOT NULL,
	DATA DATETIME NOT NULL,
	ID_USUARIO integer NOT NULL,
	CONSTRAINT FK_LOG_ID_USUARIO_USUARIO_ID FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS LOG_RESERVA(
	ID integer NOT NULL AUTO_INCREMENT,
	ID_RESERVA integer NOT NULL,
	ID_CONDOMINO integer NOT NULL,           
	ID_AMBIENTE integer NOT NULL,         
    SITUACAO VARCHAR(30) NOT NULL,         
    MOTIVO_REPROVACAO varchar(150),         
    MOTIVO_SUSPENSAO  varchar(150),         
    DATA DATE NOT NULL,
    ID_LOG integer NOT NULL,
    CONSTRAINT FK_LOG_RESERVA_ID_LOG_LOG_ID FOREIGN KEY (ID_LOG) REFERENCES LOG(ID),
	PRIMARY KEY(ID)
);