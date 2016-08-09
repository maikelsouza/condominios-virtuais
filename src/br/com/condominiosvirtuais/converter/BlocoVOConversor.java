package br.com.condominiosvirtuais.converter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.condominiosvirtuais.vo.BlocoVO;

public class BlocoVOConversor implements Converter {
	
	private BlocoVO blocoVO = null;
	
	private Field atritutos[] = null;
		

	public BlocoVOConversor() {
		this.blocoVO = new BlocoVO();
		this.atritutos = this.blocoVO.getClass().getDeclaredFields();
		
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String conteudoAtributosBlocoVO) {
		try{
			this.blocoVO = new BlocoVO();
			BeanConversor beanConversor = null;
			List<BeanConversor> listaBeanConversor = new ArrayList<BeanConversor>();
			String valores[] = new String[this.atritutos.length];
			for (int i = 0; i < valores.length; i++) {
				if(conteudoAtributosBlocoVO.contains(this.atritutos[i].getName()+"=")){
					beanConversor = new BeanConversor();
					beanConversor.setPosicaoInicial(conteudoAtributosBlocoVO.indexOf(this.atritutos[i].getName()+"="));
					beanConversor.setPosicaoFinal(beanConversor.getPosicaoInicial()+this.atritutos[i].getName().length()+1);
					beanConversor.setAtributo(conteudoAtributosBlocoVO.substring(beanConversor.getPosicaoInicial(),beanConversor.getPosicaoFinal()-1));
					listaBeanConversor.add(beanConversor);
				}			
					
			}
			
			// Necessário ordenar, para facilitar na recuperação do conteúdo dos atributos.
			Collections.sort(listaBeanConversor);			
			for (int j = 0; j < listaBeanConversor.size(); j++) {
				beanConversor = listaBeanConversor.get(j);
				if(j+1 < listaBeanConversor.size()){
					beanConversor.setConteudo((conteudoAtributosBlocoVO.substring(beanConversor.getPosicaoFinal(),listaBeanConversor.get(j+1).getPosicaoInicial())));					
				}else{
					beanConversor.setConteudo((conteudoAtributosBlocoVO.substring(beanConversor.getPosicaoFinal(),conteudoAtributosBlocoVO.length())));	
				}
			}
			

			for (int i = 0; i < this.atritutos.length; i++) {
				String nomeAtributo = this.atritutos[i].getName();
				for (int j = 0; j < listaBeanConversor.size(); j++) {
					beanConversor = listaBeanConversor.get(j);
					if(nomeAtributo.equalsIgnoreCase(beanConversor.getAtributo())){
						String stringMetodo = "set"+ nomeAtributo.substring(0,1).toUpperCase() + nomeAtributo.substring(1);
						Method metodo = this.blocoVO.getClass().getMethod(stringMetodo,  this.atritutos[i].getType());		
						if(this.atritutos[i].getType().getName().equals("java.lang.Integer")){
							metodo.invoke(this.blocoVO,   Integer.parseInt((String) beanConversor.getConteudo()));							
						}else if (this.atritutos[i].getType().getName().equals("java.lang.String")){							
							metodo.invoke(this.blocoVO,  beanConversor.getConteudo());
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return blocoVO;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object blocoVOObject) {		
		String conteudoAtributosBloco = null;
		Field atritutos[] = null;
		String nomeAtributo = null;
		Object conteudoMetodo = null;
		
		try{
			BlocoVO blocoVO = (BlocoVO) blocoVOObject;
			atritutos = blocoVO.getClass().getDeclaredFields();
			conteudoAtributosBloco = "";
			for (int i = 0; i < atritutos.length; i++) {
				nomeAtributo = atritutos[i].getName();				
				String stringMetodo = "get"+ nomeAtributo.substring(0,1).toUpperCase() + nomeAtributo.substring(1);
				Method metodo = blocoVO.getClass().getMethod(stringMetodo);
				conteudoMetodo = metodo.invoke(blocoVO);
				if(conteudoMetodo != null){
					conteudoAtributosBloco+=nomeAtributo+"="+conteudoMetodo;					
				}
			}
		}catch(Exception e){
			e.printStackTrace();	
		}		
		return conteudoAtributosBloco;
	}
}
