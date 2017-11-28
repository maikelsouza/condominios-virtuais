package br.com.condominiosvirtuais.persistence.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class Conexao {
	
	private static Logger logger = Logger.getLogger(Conexao.class);

	private static Connection conexao = null;

	private Conexao() {}
	
// TODO: Criar lógica para erro ao não conseguir conexão	
	public static Connection getConexao(){
		DataSource ds = null;
		InitialContext cxt = null;
		
		DriverManager dm = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			cxt = new InitialContext();
			//ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres")
			//ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/mysql");
			//conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/condvirtuais", "root", "AQKpza87141");
			//conexao = DriverManager.getConnection("jdbc:mysql://179.188.20.249:3306/condvirtuais", "root", "AQKpza87141");
			conexao = DriverManager.getConnection("jdbc:mysql://179.188.20.249:3306/condvirtuais", "aplicacao", "mkl862");
				//conexao = ds.getConnection();					
		
		}catch (Exception e){
			logger.error("", e);
		}
		return conexao;
	}

}
