package br.com.condominiosvirtuais.persistence.impl;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DataSource {
	
	private static Logger logger = Logger.getLogger(C3P0DataSource.class);
	
	 private static C3P0DataSource dataSource;
	 
	 private ComboPooledDataSource comboPooledDataSource;


	 
	 private C3P0DataSource() throws PropertyVetoException {
		 this.comboPooledDataSource = new ComboPooledDataSource();
	     this.comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
	     //this.comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/condvirtuais");	    
	     this.comboPooledDataSource.setJdbcUrl("jdbc:mysql://179.188.20.249:3306/condvirtuais");
	     this.comboPooledDataSource.setUser("aplicacao");
	     this.comboPooledDataSource.setPassword("mkl862");	 
	     this.comboPooledDataSource.setMaxPoolSize(30);
	     this.comboPooledDataSource.setMinPoolSize(10);
	     this.comboPooledDataSource.setMaxIdleTime(60*60*6);
	 }  
	 
	 public static C3P0DataSource getInstance() throws PropertyVetoException {
		
	      if (dataSource == null)
	         dataSource = new C3P0DataSource();
	      return dataSource;
	   }

	   public Connection getConnection() throws SQLException  { 
			return comboPooledDataSource.getConnection();		
	   }
	     
}
