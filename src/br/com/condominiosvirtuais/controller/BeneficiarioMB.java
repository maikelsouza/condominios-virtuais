package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.service.BeneficiarioService;

@Named @SessionScoped
public class BeneficiarioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BeneficiarioService beneficiarioService;
	
	private Beneficiario beneficiario;
	
	private List<SelectItem> listaSICondominios;
	
	public BeneficiarioMB(){
		this.beneficiario = new Beneficiario();
	}
	
	
	

	public List<SelectItem> getListaSICondominios() {
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}
	
	
	
	

}
