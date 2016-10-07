package br.com.condominiosvirtuais.teste;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.condominiosvirtuais.entity.GestorCondominio;
import br.com.condominiosvirtuais.entity.SindicoProfissional;

import com.itextpdf.text.pdf.PdfCopyForms;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfSignature;


public class ClasseParaTestes {
	
	public static void main(String[] args) throws IOException, DocumentException{
	
		SindicoProfissional profissional = new SindicoProfissional();
		profissional.setId(new Integer(10));
		GestorCondominio condominio = new GestorCondominio();
		condominio.setIdUsuario(new Integer(10));
		if (profissional.getId().equals(condominio.getIdUsuario())){
			System.out.println("ENTROU");
		}
		
	}

}
