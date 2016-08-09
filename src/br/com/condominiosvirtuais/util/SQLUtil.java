package br.com.condominiosvirtuais.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Classe utilitária criada para centralizar os métodos que são uteis para a camada de persitência. * 
 * @author Maikel Joel de Souza 
 */
public abstract class SQLUtil {
	
		
	/**
	 * Método que seta no {@link PreparedStatement} o valor que será utilizado em um CRUD, considerado dados null.
	 * 
	 * @param preparedStatement - Onde será armazenados os dados do CRUD
	 * @param indice - Posição do simbolo de interrogação na query
	 * @param valor - Valor que será usado no CRUD
	 * @param tipoDado - Tipo do dado para inserção. Ex.: java.sql.Types.INTEGER, java.sql.Types.VARCHAR e etc.
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public static void setValorPpreparedStatement(PreparedStatement preparedStatement, Integer indice, Object valor, Integer TipoDado ) throws NumberFormatException, SQLException {
		if (valor == null) {
			preparedStatement.setNull(indice, TipoDado);			
		}else{
			switch(TipoDado){
				case java.sql.Types.INTEGER :
					preparedStatement.setInt(indice, ((Integer) valor).intValue());
					break;
				case java.sql.Types.VARCHAR :
					preparedStatement.setString(indice, valor.toString());
					break;
				case java.sql.Types.DATE :
					preparedStatement.setDate(indice, new java.sql.Date(((Date) valor).getTime()));
					break;
				case java.sql.Types.TIMESTAMP :					
					preparedStatement.setTimestamp(indice, new java.sql.Timestamp(((Date) valor).getTime()));
					break;	
				case java.sql.Types.BOOLEAN :
					preparedStatement.setBoolean(indice, (Boolean) valor);
					break;
				case java.sql.Types.BINARY:
					preparedStatement.setBytes(indice, (byte[]) valor);
					break;
				case java.sql.Types.BIGINT:
					preparedStatement.setLong(indice,(Long) valor);	
					break;
				case java.sql.Types.DOUBLE:
					preparedStatement.setDouble(indice,(Double) valor);	
	
			}			
		}
	 }
	
	/**
	 * Método que retorna a valor do {@link ResultSet}, considerando um valor null.
	 * 
	 * @param resultSet - Onde será recuperado os dados
	 * @param nomeCampo - Nome da coluna da tabela
	 * @param TipoDado - Tipo de dado para recuperação. Ex.: java.sql.Types.INTEGER, java.sql.Types.VARCHAR e etc.
	 * @return Valor dessa coluna
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public static Object getValorResultSet(ResultSet resultSet, String nomeCampo, Integer TipoDado ) throws NumberFormatException, SQLException {
		Object retornoValor = null;
		switch(TipoDado){
			case java.sql.Types.INTEGER : 
				retornoValor = resultSet.getInt(nomeCampo);
				break;
			case java.sql.Types.VARCHAR :	
				retornoValor = resultSet.getString(nomeCampo);
				break;
			case java.sql.Types.DATE :
				retornoValor = resultSet.getDate(nomeCampo);
				break;
			case java.sql.Types.TIMESTAMP :
				retornoValor = resultSet.getTimestamp(nomeCampo);			
				break;	
			case java.sql.Types.BOOLEAN :
				retornoValor = resultSet.getBoolean(nomeCampo);
				break;
			case java.sql.Types.BINARY :
				retornoValor = resultSet.getBytes(nomeCampo);
				break;
			case java.sql.Types.BIGINT :
				retornoValor = resultSet.getLong(nomeCampo);
				break;
			case java.sql.Types.DOUBLE:
				retornoValor = resultSet.getDouble(nomeCampo);	
		}
		// Caso o conteúdo do campo seja null, então retorna null e não vazio ou zero.
		if (resultSet.wasNull()){
			retornoValor = null;
		}			
		return retornoValor;
	 }
	
	/**
	 * Método que fecha um statement, caso esse seja diferente de null
	 *  
	 * @param statement - Statement recebido.
	 * @throws SQLException
	 */
	public static void closeStatement(Statement statement) throws SQLException{
		if(statement != null){
			statement.close();
		}
	}
	
	/**
	 * Método que fecha uma conexão, caso essa seja diferente de null
	 * @param connection - Conexão recebida.
	 * @throws SQLException 
	 */
	public static void closeConnection(Connection connection) throws SQLException{
		if(connection != null){
			connection.close();
		}
	}
	
	/**
	 * Método que popula a quantidade de pontos de interrogação na sintaxe sql. 
	 * @param numeroInterrogacoes - Quantidade de ponto de interrogação.
	 * @return sintaxe sql. Exemplo: "?,?,?"
	 */
	public static String popularInterrocacoes(Integer numeroInterrogacoes){
		String interrogacoes = "";		
		for (int i = 1 ; i < numeroInterrogacoes; i++) {
			interrogacoes+= "?,";
		}
		interrogacoes+= "?";
		return interrogacoes;
	}
}
