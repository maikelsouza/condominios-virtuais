package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) as enquetes. Página: formListaEnquete.xhtml
 * @author Maikel Joel de Souza
 * @since 16/12/2013  
 */
public class OrdenaEnqueteMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder perguntaEnquete = SortOrder.unsorted;
	
	private SortOrder alternativa1Enquete = SortOrder.unsorted;
	
	private SortOrder alternativa2Enquete = SortOrder.unsorted;
	
	private SortOrder alternativa3Enquete = SortOrder.unsorted;
	
	private SortOrder alternativa4Enquete = SortOrder.unsorted;
	
	private SortOrder alternativa5Enquete = SortOrder.unsorted;
	
	private SortOrder dataFimEnquete = SortOrder.unsorted;
	
	private SortOrder dataCriacaoEnquete = SortOrder.unsorted;

	
	
	public void ordenarPorPerguntaEnquete(){		
		this.setAlternativa1Enquete(SortOrder.unsorted);
		this.setAlternativa2Enquete(SortOrder.unsorted);
		this.setAlternativa3Enquete(SortOrder.unsorted);
		this.setAlternativa4Enquete(SortOrder.unsorted);
		this.setAlternativa5Enquete(SortOrder.unsorted);
		this.setDataFimEnquete(SortOrder.unsorted);
		this.setDataCriacaoEnquete(SortOrder.unsorted);
		if (perguntaEnquete.equals(SortOrder.ascending)) {
			 this.setPerguntaEnquete(SortOrder.descending);
		} else {
			 this.setPerguntaEnquete(SortOrder.ascending);
		}
	}
	
	public void ordenarPorAlternativa1Enquete(){
		this.setAlternativa2Enquete(SortOrder.unsorted);
		this.setAlternativa3Enquete(SortOrder.unsorted);
		this.setAlternativa4Enquete(SortOrder.unsorted);
		this.setAlternativa5Enquete(SortOrder.unsorted);
		this.setDataFimEnquete(SortOrder.unsorted);		
		this.setDataCriacaoEnquete(SortOrder.unsorted);
		this.setPerguntaEnquete(SortOrder.unsorted);
		if (alternativa1Enquete.equals(SortOrder.ascending)) {
			 this.setAlternativa1Enquete(SortOrder.descending);
		} else {
			 this.setAlternativa1Enquete(SortOrder.ascending);
		}
	}
	
	public void ordenarPorAlternativa2Enquete(){
		this.setAlternativa1Enquete(SortOrder.unsorted);
		this.setAlternativa3Enquete(SortOrder.unsorted);
		this.setAlternativa4Enquete(SortOrder.unsorted);
		this.setAlternativa5Enquete(SortOrder.unsorted);
		this.setDataFimEnquete(SortOrder.unsorted);		
		this.setDataCriacaoEnquete(SortOrder.unsorted);
		this.setPerguntaEnquete(SortOrder.unsorted);
		if (this.alternativa2Enquete.equals(SortOrder.ascending)) {
			 this.setAlternativa2Enquete(SortOrder.descending);
		} else {
			 this.setAlternativa2Enquete(SortOrder.ascending);
		}
	}
	
	public void ordenarPorAlternativa3Enquete(){		
		this.setAlternativa1Enquete(SortOrder.unsorted);
		this.setAlternativa2Enquete(SortOrder.unsorted);
		this.setAlternativa4Enquete(SortOrder.unsorted);
		this.setAlternativa5Enquete(SortOrder.unsorted);
		this.setDataFimEnquete(SortOrder.unsorted);
		this.setDataCriacaoEnquete(SortOrder.unsorted);
		this.setPerguntaEnquete(SortOrder.unsorted);
		if (this.alternativa3Enquete.equals(SortOrder.ascending)) {
			 this.setAlternativa3Enquete(SortOrder.descending);
		} else {
			 this.setAlternativa3Enquete(SortOrder.ascending);
		}
	}
	
	public void ordenarPorAlternativa4Enquete(){		
		this.setAlternativa1Enquete(SortOrder.unsorted);
		this.setAlternativa2Enquete(SortOrder.unsorted);
		this.setAlternativa3Enquete(SortOrder.unsorted);
		this.setAlternativa5Enquete(SortOrder.unsorted);
		this.setDataFimEnquete(SortOrder.unsorted);
		this.setDataCriacaoEnquete(SortOrder.unsorted);
		this.setPerguntaEnquete(SortOrder.unsorted);
		if (this.alternativa4Enquete.equals(SortOrder.ascending)) {
			 this.setAlternativa4Enquete(SortOrder.descending);
		} else {
			 this.setAlternativa4Enquete(SortOrder.ascending);
		}
	}
	
	public void ordenarPorAlternativa5Enquete(){		
		this.setAlternativa1Enquete(SortOrder.unsorted);
		this.setAlternativa2Enquete(SortOrder.unsorted);
		this.setAlternativa3Enquete(SortOrder.unsorted);
		this.setAlternativa4Enquete(SortOrder.unsorted);
		this.setDataFimEnquete(SortOrder.unsorted);
		this.setDataCriacaoEnquete(SortOrder.unsorted);
		this.setPerguntaEnquete(SortOrder.unsorted);
		if (this.alternativa5Enquete.equals(SortOrder.ascending)) {
			 this.setAlternativa5Enquete(SortOrder.descending);
		} else {
			 this.setAlternativa5Enquete(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDataFimEnquete(){		
		this.setAlternativa1Enquete(SortOrder.unsorted);
		this.setAlternativa2Enquete(SortOrder.unsorted);
		this.setAlternativa3Enquete(SortOrder.unsorted);
		this.setAlternativa4Enquete(SortOrder.unsorted);
		this.setAlternativa5Enquete(SortOrder.unsorted);
		this.setDataCriacaoEnquete(SortOrder.unsorted);
		this.setPerguntaEnquete(SortOrder.unsorted);
		if (this.dataFimEnquete.equals(SortOrder.ascending)) {
			 this.setDataFimEnquete(SortOrder.descending);
		} else {
			 this.setDataFimEnquete(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDataCriacaoEnquete(){		
		this.setAlternativa1Enquete(SortOrder.unsorted);
		this.setAlternativa2Enquete(SortOrder.unsorted);
		this.setAlternativa3Enquete(SortOrder.unsorted);
		this.setAlternativa4Enquete(SortOrder.unsorted);
		this.setAlternativa5Enquete(SortOrder.unsorted);
		this.setDataFimEnquete(SortOrder.unsorted);
		this.setPerguntaEnquete(SortOrder.unsorted);
		if (this.dataCriacaoEnquete.equals(SortOrder.ascending)) {
			 this.setDataCriacaoEnquete(SortOrder.descending);
		} else {
			 this.setDataCriacaoEnquete(SortOrder.ascending);
		}
	}
	
	public void setPerguntaEnquete(SortOrder perguntaEnquete) {
		this.perguntaEnquete = perguntaEnquete;
	}

	public void setAlternativa1Enquete(SortOrder alternativa1Enquete) {
		this.alternativa1Enquete = alternativa1Enquete;
	}

	public void setAlternativa2Enquete(SortOrder alternativa2Enquete) {
		this.alternativa2Enquete = alternativa2Enquete;
	}

	public void setAlternativa3Enquete(SortOrder alternativa3Enquete) {
		this.alternativa3Enquete = alternativa3Enquete;
	}

	public void setAlternativa4Enquete(SortOrder alternativa4Enquete) {
		this.alternativa4Enquete = alternativa4Enquete;
	}

	public void setAlternativa5Enquete(SortOrder alternativa5Enquete) {
		this.alternativa5Enquete = alternativa5Enquete;
	}

	public void setDataFimEnquete(SortOrder dataFimEnquete) {
		this.dataFimEnquete = dataFimEnquete;
	}

	public SortOrder getPerguntaEnquete() {
		return perguntaEnquete;
	}

	public SortOrder getAlternativa1Enquete() {
		return alternativa1Enquete;
	}

	public SortOrder getAlternativa2Enquete() {
		return alternativa2Enquete;
	}

	public SortOrder getAlternativa3Enquete() {
		return alternativa3Enquete;
	}

	public SortOrder getAlternativa4Enquete() {
		return alternativa4Enquete;
	}

	public SortOrder getAlternativa5Enquete() {
		return alternativa5Enquete;
	}

	public SortOrder getDataFimEnquete() {
		return dataFimEnquete;
	}

	public SortOrder getDataCriacaoEnquete() {
		return dataCriacaoEnquete;
	}

	public void setDataCriacaoEnquete(SortOrder dataCriacaoEnquete) {
		this.dataCriacaoEnquete = dataCriacaoEnquete;
	}	
	
}