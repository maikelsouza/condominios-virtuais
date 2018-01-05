package br.com.condominiosvirtuais.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class CalculoUtil {

	
	public static Double setScala(String valor, Integer escala){
		BigDecimal bigDecimal = new BigDecimal(valor);
		return bigDecimal.setScale(escala, RoundingMode.HALF_UP).doubleValue();	
	}
	
	
}
