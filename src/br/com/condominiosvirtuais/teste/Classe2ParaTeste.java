package br.com.condominiosvirtuais.teste;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static javax.ws.rs.core.MediaType.WILDCARD;
import static javax.ws.rs.core.Response.Status.CREATED;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import br.com.condominiosvirtuais.util.AplicacaoUtil;
import jxl.write.WriteException;

public class Classe2ParaTeste {

	public static void main(String[] args) throws IOException, WriteException {
	
		/*
		 * Dados para criação do boleto
		 */
		
		System.out.println(AplicacaoUtil.getLocale().toString());
		
		Form formData = new Form();
		formData.param("boleto.conta.banco","237");
		formData.param("boleto.conta.agencia","1234-5");
		formData.param("boleto.conta.numero","123456-0");
		formData.param("boleto.conta.carteira","12");
		formData.param("boleto.beneficiario.nome","DevAware Solutions");
		formData.param("boleto.beneficiario.cprf","15.719.277/0001-46");
		formData.param("boleto.beneficiario.endereco.cep","59020-000");
		formData.param("boleto.beneficiario.endereco.uf","RN");
		formData.param("boleto.beneficiario.endereco.localidade","Natal");
		formData.param("boleto.beneficiario.endereco.bairro","Petrópolis");
		formData.param("boleto.beneficiario.endereco.logradouro","Avenida Hermes da Fonseca");
		formData.param("boleto.beneficiario.endereco.numero","384");
		formData.param("boleto.beneficiario.endereco.complemento","Sala 2A, segundo andar");
		formData.param("boleto.emissao","2014-07-11");
		formData.param("boleto.vencimento","2020-05-30");
		formData.param("boleto.documento","EX1");
		formData.param("boleto.numero","12345678901-P");
		formData.param("boleto.titulo","DM");
		formData.param("boleto.valor","1250.43");
		formData.param("boleto.pagador.nome","Alberto Santos Dumont");
		formData.param("boleto.pagador.cprf","111.111.111-11");
		formData.param("boleto.pagador.endereco.cep","36240-000");
		formData.param("boleto.pagador.endereco.uf","MG");
		formData.param("boleto.pagador.endereco.localidade","Santos Dumont");
		formData.param("boleto.pagador.endereco.bairro","Casa Natal");
		formData.param("boleto.pagador.endereco.logradouro","BR-499");
		formData.param("boleto.pagador.endereco.numero","s/n");
		formData.param("boleto.pagador.endereco.complemento","Sítio - Subindo a serra da Mantiqueira");
		formData.param("boleto.instrucao","Atenção! NÃO RECEBER ESTE BOLETO.");
		formData.param("boleto.instrucao","Este é apenas um teste utilizando a API Boleto Cloud");
		formData.param("boleto.instrucao","Mais info em http://www.boletocloud.com/app/dev/api");
//		/*
//		 * Requisição para criação do boleto
//		 */
		Response response = ClientBuilder
				.newClient()
				.target("https://sandbox.boletocloud.com/api/v1")
				.path("/boletos")
				.register(
						//Define o tipo de autenticação HTTP Basic 
						HttpAuthenticationFeature.basic(
								"api-key_vJxqRj_8ZIu7850rCC3R-lDRcedJ40l4KuMlLH-Kjxs=",
								"token"
						)
				)
				.request(WILDCARD)//Aceita qualquer resposta
				.post(Entity.form(formData));
//		/*
//		 * Dados da resposta
//		 */
//		System.out.println("HTTP Status Code: "+response.getStatus());
//		System.out.println("Boleto Cloud Version: "+response.getHeaderString("X-BoletoCloud-Version"));
//		System.out.println("Boleto Cloud Token: "+response.getHeaderString("X-BoletoCloud-Token"));
//		/*
//		 * Identifica se o boleto foi criado ou houve erro
//		 */		
//		if(response.getStatus() == CREATED.getStatusCode()){
//			
//			//Salva o arquivo do diretório corrente.
//			Files.copy(response.readEntity(InputStream.class), Paths.get("arquivo-api-boleto-post-teste.pdf"), REPLACE_EXISTING);
//			
//			//Caso tenha leitor pdf no sistema..
//			//Abrirá o arquivo PDF utilizando o leitor de PDF do sistema operacional
//			//java.awt.Desktop.getDesktop().open(new File("arquivo-api-boleto-post-teste.pdf"));
//			
//		}else{
//			System.err.println("Erro retornado em json: "+response.readEntity(String.class));
//		}
//		//Para saber mais sobre tratamento de erros veja a seção Status & Erros
//	}
	
		Long cep = 342683918l;
	//	System.out.println(AplicacaoUtil.completaZerosAEsquerta(cep.toString(), 11));
	System.out.println(AplicacaoUtil.formatarCpf(cep));
			
		
		
		
	}
}


