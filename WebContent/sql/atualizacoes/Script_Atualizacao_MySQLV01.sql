ALTER TABLE CONDOMINIO ADD COLUMN CODIGO int(11) NULL after TELEFONE_FIXO;
UPDATE CONDOMINIO SET CODIGO = 351 WHERE ID = 1;
UPDATE CONDOMINIO SET CODIGO = 450 WHERE ID = 2;
ALTER TABLE CONDOMINIO CHANGE CODIGO CODIGO int(11) NOT NULL;
ALTER TABLE CONDOMINIO ADD UNIQUE UQ_CODIGO (CODIGO);
