package br.com.condominiosvirtuais.teste;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.DateFormats;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.WritableFonts;

public class Classe2ParaTeste {

	public static void main(String[] args) throws IOException, WriteException {
		
		
		List<Integer> listaInteger = new ArrayList<>(); 
			listaInteger.add(5);
			listaInteger.add(2);
			listaInteger.add(500);
			listaInteger.add(10);
			listaInteger.add(501);
			
			System.out.println(Collections.max(listaInteger));
			
			
		}
		
		
//		BigDecimal valor = new BigDecimal ("RS 12000000.12");  
//		NumberFormat nf = NumberFormat.getCurrencyInstance();  
//		String formatado = nf.format (valor);
//		System.out.println(formatado);
//
//		String filename = "entrada.xls";
//		 WorkbookSettings ws = new WorkbookSettings();
//		 ws.setLocale(new Locale("pt", "BR"));
//		 WritableWorkbook workbook =  Workbook.createWorkbook(new File(filename), ws);
//		 WritableSheet s = workbook.createSheet("Folha1", 0);
//		 
//		 WritableFont wf = new WritableFont(WritableFont.ARIAL,  12, WritableFont.BOLD);
//		 WritableFont wf2 = new WritableFont(WritableFont.ARIAL,  12, WritableFont.BOLD);
//		 WritableFont wf3 = new WritableFont(WritableFont.ARIAL,  10, WritableFont.BOLD);
//		 WritableCellFormat cf = new WritableCellFormat(wf);
//		 cf.setWrap(true);
//		 
//		 Label l = new Label(0,0,"Receitas e Despesas - Condomínio XYZ - 01/12/2016 Até 21/12/2016",cf);
//		 s.mergeCells(0, 0, 6, 0);
//		 s.addCell(l);
//		 
//		 WritableCellFormat cf1 = new WritableCellFormat(wf2);
//		 Label l2 = new Label(0,1,"Lista De Receitas",cf1);
//		 s.mergeCells(0, 1, 3, 1);
//		 s.addCell(l2);
//		 
//		 WritableCellFormat cf4 = new WritableCellFormat(wf3);
//		 Label l3 = new Label(0,3,"Descrição",cf4);		 
//		 s.addCell(l3);
//		 
//		 WritableCellFormat cf5 = new WritableCellFormat(wf3);
//		 Label l4 = new Label(1,3,"Data",cf5);		 
//		 s.addCell(l4);
//		 
//		 WritableCellFormat cf6 = new WritableCellFormat(wf3);
//		 Label l5 = new Label(2,3,"Valor",cf6);		 
//		 s.addCell(l5);
//		 
//		 WritableCellFormat cf7 = new WritableCellFormat(wf3);
//		 Label l6 = new Label(3,3,"Número Documento",cf7);		 
//		 s.addCell(l6);
//		 
//		 WritableCellFormat cf8 = new WritableCellFormat(wf3);
//		 Label l7 = new Label(4,3,"Meio Pagamento",cf8);		 
//		 s.addCell(l7);
//		 
//		 WritableCellFormat cf9 = new WritableCellFormat(wf3);
//		 Label l8 = new Label(3,3,"Observação",cf9);		 
//		 s.addCell(l8);
//		 
//		 
//		 workbook.write();
//		 workbook.close();
		
	//}
}


