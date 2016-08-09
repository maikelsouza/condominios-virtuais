package br.com.condominiosvirtuais.teste;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class ClasseParaTestes {
	
	public static void main(String[] args) throws IOException{
//		FileReader fileReader = new FileReader("C:\\Users\\Maikel\\Desktop\\arquivo.html");
//		BufferedReader reader = new BufferedReader(fileReader);
//		String html = "";
//		String data = null;
//		while((data = reader.readLine()) != null){
//			html+=data;
//		   System.out.println(data);
//		}
//		fileReader.close();
//		reader.close();
//		
//		// String criadas para remover o botao busca rapida
//		String antesDoForm = "";
//		String despoisDoForm= "";
//		String htmlSemBuscaRapida = "";
//		String htmlSemLocaisSolicitacoes = "";
//		String tagsFinalHtml = "";
//		String novoHtml = "";
//		System.out.println("*******************");
//		//novoHtml.equals(arg0)
//		
//		//Codigos que recuperam somente o html sem o bot�o busca rapida
//		antesDoForm = html.substring(0, html.indexOf("<form id=\"quicksearch\""));
//		despoisDoForm = html.substring(html.indexOf("</form>")+7);
//		
//		// Html sem o botao busca rapida
//		htmlSemBuscaRapida = antesDoForm + despoisDoForm;
//		
//		System.out.println(htmlSemBuscaRapida);
//		// Codigo que recupera o html sem os menus locais e solicitacoes 
//		htmlSemLocaisSolicitacoes = html.substring(0, html.indexOf("Dashboards</a>")+14);
//		
//		tagsFinalHtml = "</ul></div></div></div>";
//		novoHtml = htmlSemLocaisSolicitacoes + tagsFinalHtml;
//		
//		
//		System.out.println(html);
//		System.out.println(novoHtml);
		

		Date data = new Date();
		   String dataString = "dd/MM/yyyy";
		   SimpleDateFormat spd = new SimpleDateFormat(dataString);
		   System.out.println("Data de hoje: "+spd.format(data));
    

	}
	
	
	
	
//	public static String getHtml(){
//		String html = "<div id="header">
//	    <div id="todo-msgbox"></div>
//	    <div id="header-top">
//	        <a id="logo" href="/secure/Dashboard.jspa">
//	        </a>
//	            <form id="quicksearch" action="/secure/QuickSearch.jspa" method="post">
//	            <label for="quickSearchInput" title="Encontre Solicitações digitando a chave ou um critério de pesquisa de texto." class="overlabel"><u>B</u>USCA R�PIDA</label>
//	            <input id="quickSearchInput" class="quickSearchInput" type="text" size="25" title="Encontre Solicitações digitando a chave ou um critério de pesquisa de texto." name="searchString" accessKey="b"  valign="absmiddle" />
//				</form>
//	                
//	        <div id="header-details-user" class="aui-dd-parent header-link-container">
//	         <a class="lnk" href="/secure/ViewProfile.jspa">Maikel Souza</a>
//	        <span class="drop-wrap">
//	        <a class="drop aui-dd-link"
//	                        href="#"><span>Access more options</span>
//	        </a>
//	    </span>
//	    <div class="aui-dropdown standard hidden">
//	                                            <ul id="personal" class="  first     ">
//	                                            <li><a  id="view_profile"   class=""  title="Ver e mudar seus detalhes e preferências" href="/secure/ViewProfile.jspa">Perfil</a></li>
//	                                    </ul>
//	                                                <ul id="jira-help" class="    ">
//	                                            <li><a  id="view_help"   class=""  title="Goto the online documentation for JIRA" href="http://docs.atlassian.com/jira/docs-041/Home?clicked=jirahelp">Online Help</a></li>
//	                                            <li><a  id="keyshortscuthelp"   class=""  title="Mais informações sobre os atalhos JIRA" href="/secure/ViewKeyboardShortcuts.jspa">Keyboard Shortcuts</a></li>
//	                                            <li><a  id="user_history"   class=""  title="Mais informações sobre JIRA" href="http://www.atlassian.com/software/jira">Sobre JIRA</a></li>
//	                                    </ul>
//	                                                <ul id="system" class="    last  ">
//	                                            <li><a  id="log_out"   class=""  title="Sair do sistema e cancelar qualquer entrada automática nele." href="/logout?atl_token=97EXGGlZtL">Sair do Sistema</a></li>
//	                                    </ul>
//	                        </div>
//	</div>
//	        
//	    </div>
//	    <div id="header-bottom">
//	                                                                                                                                                                                        <div id="menu">
//	            
//	            
//	<ul id="main-nav" class="menu-bar">
//	                        <li class="aui-dd-parent lazy  selected  ">
//	                    <a class="lnk"
//	                href="/secure/Dashboard.jspa"
//	                 id="home_link" accessKey="p" title="View your current Dashboard" >Dashboards</a>
//
//	                    <span class="drop-wrap">
//	            <a class="drop aui-dd-link"
//	                                    id="home_link_drop"
//	                    rel="home_link"
//	                    accessKey="d"
//	                    title="Access more options (Alt+d)"
//	                                href="#"><span>Access more options (Alt+d)</span></a></span>
//	            <div class="aui-dropdown standard hidden"></div>
//	                </li>
//	                    <li class="aui-dd-parent lazy  ">
//	                    <a class="lnk" href="/browse/CTXSALPB" id="browse_link" accessKey="n" title="Navegar pelo Local recentemente ativo 'CTX � Boa Viagem � SALPB'">Locais</a>
//						<span class="drop-wrap">
//	            <a class="drop aui-dd-link"
//	                                    id="browse_link_drop"
//	                    rel="browse_link"
//	                    accessKey="p"
//	                    title="Access more options (Alt+p)"
//	                                href="#"><span>Access more options (Alt+p)</span></a></span>
//	            <div class="aui-dropdown standard hidden"></div>
//	                </li>
//	                        <li class="aui-dd-parent lazy  ">
//	                    <a class="lnk"
//	                href="/secure/IssueNavigator.jspa"
//	                 id="find_link"                 accessKey="e"                 title="Visualizar o seu filtro atual"                >Solicitações</a>
//
//	                    <span class="drop-wrap">
//	            <a class="drop aui-dd-link"
//	                                    id="find_link_drop"
//	                    rel="find_link"
//	                    accessKey="i"
//	                    title="Access more options (Alt+i)"
//	                                href="#"><span>Access more options (Alt+i)</span></a></span>
//	            <div class="aui-dropdown standard hidden"></div>
//	                </li>
//	    </ul>
//
//	            
//	        </div>
//
//	    </div>
//	</div>"
//
//
// 
//				
//		return html;		
//	}

}
