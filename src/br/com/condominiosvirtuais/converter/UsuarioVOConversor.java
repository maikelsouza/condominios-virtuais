package br.com.condominiosvirtuais.converter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


import br.com.condominiosvirtuais.vo.UsuarioVO;

public class UsuarioVOConversor implements Converter{
	
	private UsuarioVO usuarioVO = null;
	
	private Field atritutos[] = null;
		

	public UsuarioVOConversor() {
		this.usuarioVO = new UsuarioVO();
		this.atritutos = this.usuarioVO.getClass().getDeclaredFields();
		
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String conteudoAtributosUsuarioVO) {
		try{
			this.usuarioVO = new UsuarioVO();
			BeanConversor beanConversor = null;
			List<BeanConversor> listaBeanConversor = new ArrayList<BeanConversor>();
			String valores[] = new String[this.atritutos.length];
			for (int i = 0; i < valores.length; i++) {
				if(conteudoAtributosUsuarioVO.contains(this.atritutos[i].getName()+"=")){
					beanConversor = new BeanConversor();
					beanConversor.setPosicaoInicial(conteudoAtributosUsuarioVO.indexOf(this.atritutos[i].getName()+"="));
					beanConversor.setPosicaoFinal(beanConversor.getPosicaoInicial()+this.atritutos[i].getName().length()+1);
					beanConversor.setAtributo(conteudoAtributosUsuarioVO.substring(beanConversor.getPosicaoInicial(),beanConversor.getPosicaoFinal()-1));
					listaBeanConversor.add(beanConversor);
				}			
					
			}
			
			// Necessário ordenar, para facilitar na recuperação do conteúdo dos atributos.
			Collections.sort(listaBeanConversor);			
			for (int j = 0; j < listaBeanConversor.size(); j++) {
				beanConversor = listaBeanConversor.get(j);
				if(j+1 < listaBeanConversor.size()){
					beanConversor.setConteudo((conteudoAtributosUsuarioVO.substring(beanConversor.getPosicaoFinal(),listaBeanConversor.get(j+1).getPosicaoInicial())));					
				}else{
					beanConversor.setConteudo((conteudoAtributosUsuarioVO.substring(beanConversor.getPosicaoFinal(),conteudoAtributosUsuarioVO.length())));	
				}
			}
			

			for (int i = 0; i < this.atritutos.length; i++) {
				String nomeAtributo = this.atritutos[i].getName();
				for (int j = 0; j < listaBeanConversor.size(); j++) {
					beanConversor = listaBeanConversor.get(j);
					if(nomeAtributo.equalsIgnoreCase(beanConversor.getAtributo())){
						String stringMetodo = "set"+ nomeAtributo.substring(0,1).toUpperCase() + nomeAtributo.substring(1);
						Method metodo = this.usuarioVO.getClass().getMethod(stringMetodo,  this.atritutos[i].getType());		
						if(this.atritutos[i].getType().getName().equals("java.lang.Integer")){
							metodo.invoke(this.usuarioVO,   Integer.parseInt((String) beanConversor.getConteudo()));							
						}else if (this.atritutos[i].getType().getName().equals("java.lang.String")){							
							metodo.invoke(this.usuarioVO,  beanConversor.getConteudo());
						}else if (this.atritutos[i].getType().getName().equals("java.lang.Long")){							
							metodo.invoke(this.usuarioVO, Long.parseLong((String) beanConversor.getConteudo()));
						}
						
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return usuarioVO;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object usuarioVOObject) {		
		String conteudoAtributosUsuarioVO = null;
		Field atritutos[] = null;
		String nomeAtributo = null;
		Object conteudoMetodo = null;
		
		try{
			UsuarioVO usuarioVO = (UsuarioVO) usuarioVOObject;
			atritutos = usuarioVO.getClass().getDeclaredFields();
			conteudoAtributosUsuarioVO = "";
			for (int i = 0; i < atritutos.length; i++) {
				nomeAtributo = atritutos[i].getName();				
				String stringMetodo = "get"+ nomeAtributo.substring(0,1).toUpperCase() + nomeAtributo.substring(1);
				Method metodo = usuarioVO.getClass().getMethod(stringMetodo);
				conteudoMetodo = metodo.invoke(usuarioVO);
				if(conteudoMetodo != null){
					conteudoAtributosUsuarioVO+=nomeAtributo+"="+conteudoMetodo;					
				}
			}
		}catch(Exception e){
			e.printStackTrace();	
		}		
		return conteudoAtributosUsuarioVO;
	}
}
