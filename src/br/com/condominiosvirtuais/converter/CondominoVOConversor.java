package br.com.condominiosvirtuais.converter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.condominiosvirtuais.vo.CondominoVO;

public class CondominoVOConversor implements Converter {
	
	private CondominoVO condominoVO = null;
	
	private Field atritutos[] = null;
		

	public CondominoVOConversor() {
		this.condominoVO = new CondominoVO();
		this.atritutos = this.condominoVO.getClass().getDeclaredFields();
		
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String conteudoAtributosVO) {
		try{
			this.condominoVO = new CondominoVO();
			BeanConversor beanConversor = null;
			List<BeanConversor> listaBeanConversor = new ArrayList<BeanConversor>();
			String valores[] = new String[this.atritutos.length];
			for (int i = 0; i < valores.length; i++) {
				if(conteudoAtributosVO.contains(this.atritutos[i].getName()+"=")){
					beanConversor = new BeanConversor();
					beanConversor.setPosicaoInicial(conteudoAtributosVO.indexOf(this.atritutos[i].getName()+"="));
					beanConversor.setPosicaoFinal(beanConversor.getPosicaoInicial()+this.atritutos[i].getName().length()+1);
					beanConversor.setAtributo(conteudoAtributosVO.substring(beanConversor.getPosicaoInicial(),beanConversor.getPosicaoFinal()-1));
					listaBeanConversor.add(beanConversor);
				}			
					
			}
			// Necessário ordenar, para facilitar na recuperação do conteúdo dos atributos.
			Collections.sort(listaBeanConversor);			
			for (int j = 0; j < listaBeanConversor.size(); j++) {
				beanConversor = listaBeanConversor.get(j);
				if(j+1 < listaBeanConversor.size()){
					beanConversor.setConteudo((conteudoAtributosVO.substring(beanConversor.getPosicaoFinal(),listaBeanConversor.get(j+1).getPosicaoInicial())));					
				}else{
					beanConversor.setConteudo((conteudoAtributosVO.substring(beanConversor.getPosicaoFinal(),conteudoAtributosVO.length())));	
				}
			}			

			for (int i = 0; i < this.atritutos.length; i++) {
				String nomeAtributo = this.atritutos[i].getName();
				for (int j = 0; j < listaBeanConversor.size(); j++) {
					beanConversor = listaBeanConversor.get(j);
					if(nomeAtributo.equalsIgnoreCase(beanConversor.getAtributo())){
						String stringMetodo = "set"+ nomeAtributo.substring(0,1).toUpperCase() + nomeAtributo.substring(1);
						Method metodo = this.condominoVO.getClass().getMethod(stringMetodo,  this.atritutos[i].getType());						
						if(this.atritutos[i].getType().getName().equals("java.lang.Integer")){
							metodo.invoke(this.condominoVO, Integer.parseInt((String) beanConversor.getConteudo()));	
						}else if(this.atritutos[i].getType().getName().equals("java.lang.Long")){
							metodo.invoke(this.condominoVO, Long.parseLong((String) beanConversor.getConteudo()));
						}else if(this.atritutos[i].getType().getName().equals("java.lang.Boolean")){
							metodo.invoke(this.condominoVO, Boolean.parseBoolean((String) beanConversor.getConteudo()));
						}else if(this.atritutos[i].getType().getName().equals("java.lang.String")){
							metodo.invoke(this.condominoVO,  beanConversor.getConteudo());
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		return this.condominoVO;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object condominoVOObject) {		
		String conteudoAtributosVO = null;
		Field atritutos[] = null;
		String nomeAtributo = null;
		Object conteudoMetodo = null;		
		try{
			CondominoVO condominoVO = (CondominoVO) condominoVOObject;
			atritutos = condominoVO.getClass().getDeclaredFields();
			conteudoAtributosVO = "";
			for (int i = 0; i < atritutos.length; i++) {
				nomeAtributo = atritutos[i].getName();				
				String stringMetodo = "get"+ nomeAtributo.substring(0,1).toUpperCase() + nomeAtributo.substring(1);
				Method metodo = condominoVO.getClass().getMethod(stringMetodo);
				conteudoMetodo = metodo.invoke(condominoVO);
				if(conteudoMetodo != null){
					conteudoAtributosVO+=nomeAtributo+"="+conteudoMetodo;					
				}
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
		return conteudoAtributosVO;
	}
}
