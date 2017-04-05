package br.com.condominiosvirtuais.converter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


import br.com.condominiosvirtuais.entity.MeioPagamento;

public class MeioPagamentoConversor implements Converter {
	
	private MeioPagamento meioPagamento  = null;
	
	private Field atritutos[] = null;
		

	public MeioPagamentoConversor() {
		this.meioPagamento = new MeioPagamento();
		this.atritutos = this.meioPagamento.getClass().getDeclaredFields();
		
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String conteudoAtributosMeioPagamento) {
		try{
			this.meioPagamento = new MeioPagamento();
			BeanConversor beanConversor = null;
			List<BeanConversor> listaBeanConversor = new ArrayList<BeanConversor>();
			String valores[] = new String[this.atritutos.length];
			for (int i = 0; i < valores.length; i++) {
				if(conteudoAtributosMeioPagamento.contains(this.atritutos[i].getName()+"=")){
					beanConversor = new BeanConversor();
					beanConversor.setPosicaoInicial(conteudoAtributosMeioPagamento.indexOf(this.atritutos[i].getName()+"="));
					beanConversor.setPosicaoFinal(beanConversor.getPosicaoInicial()+this.atritutos[i].getName().length()+1);
					beanConversor.setAtributo(conteudoAtributosMeioPagamento.substring(beanConversor.getPosicaoInicial(),beanConversor.getPosicaoFinal()-1));
					listaBeanConversor.add(beanConversor);
				}			
					
			}
			
			// Necessário ordenar, para facilitar na recuperação do conteúdo dos atributos.
			Collections.sort(listaBeanConversor);			
			for (int j = 0; j < listaBeanConversor.size(); j++) {
				beanConversor = listaBeanConversor.get(j);
				if(j+1 < listaBeanConversor.size()){
					beanConversor.setConteudo((conteudoAtributosMeioPagamento.substring(beanConversor.getPosicaoFinal(),listaBeanConversor.get(j+1).getPosicaoInicial())));					
				}else{
					beanConversor.setConteudo((conteudoAtributosMeioPagamento.substring(beanConversor.getPosicaoFinal(),conteudoAtributosMeioPagamento.length())));	
				}
			}
			

			for (int i = 0; i < this.atritutos.length; i++) {
				String nomeAtributo = this.atritutos[i].getName();
				for (int j = 0; j < listaBeanConversor.size(); j++) {
					beanConversor = listaBeanConversor.get(j);
					if(nomeAtributo.equalsIgnoreCase(beanConversor.getAtributo())){
						String stringMetodo = "set"+ nomeAtributo.substring(0,1).toUpperCase() + nomeAtributo.substring(1);
						Method metodo = this.meioPagamento.getClass().getMethod(stringMetodo,  this.atritutos[i].getType());		
						if(this.atritutos[i].getType().getName().equals("java.lang.Integer")){
							metodo.invoke(this.meioPagamento,   Integer.parseInt((String) beanConversor.getConteudo()));							
						}else if (this.atritutos[i].getType().getName().equals("java.lang.String")){							
							metodo.invoke(this.meioPagamento,  beanConversor.getConteudo());
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return meioPagamento;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object condominoVOObject) {		
		String conteudoAtributosMeioPagamento = null;
		Field atritutos[] = null;
		String nomeAtributo = null;
		Object conteudoMetodo = null;
		
		try{
			MeioPagamento meioPagamento = (MeioPagamento) condominoVOObject;
			atritutos = meioPagamento.getClass().getDeclaredFields();
			conteudoAtributosMeioPagamento = "";
			for (int i = 0; i < atritutos.length; i++) {
				nomeAtributo = atritutos[i].getName();				
				String stringMetodo = "get"+ nomeAtributo.substring(0,1).toUpperCase() + nomeAtributo.substring(1);
				Method metodo = meioPagamento.getClass().getMethod(stringMetodo);
				conteudoMetodo = metodo.invoke(meioPagamento);
				if(conteudoMetodo != null){
					conteudoAtributosMeioPagamento+=nomeAtributo+"="+conteudoMetodo;					
				}
			}
		}catch(Exception e){
			e.printStackTrace();	
		}		
		return conteudoAtributosMeioPagamento;
	}
}
