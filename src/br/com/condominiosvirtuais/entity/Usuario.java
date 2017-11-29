package br.com.condominiosvirtuais.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Entidade que representa qualquer usu�rio da aplica��o. (Cond�minio, funcion�rio e etc)
 * 
 * @author Maikel Joel de Souza
 * @since 20/02/2013
 */
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;
	
	private String senha;
	
	private String confirmarSenha;
	
	private Integer sexo;
	
	private Date dataNascimento;
	
	private Integer dataNascimentoDia = 0; 
	
	// Necess�rio setar o m�s com -1, pois em um caso de erro de vali��o na tela o campo m�s 
	// n�o � reendereizado com 0 que � igual a 1 (situa��o ocorre somente se a data de nascimento n�o tiver sido preenchida). 
	private Integer dataNascimentoMes = - 1;
	
	private Integer dataNascimentoAno = 0;
	
	private Arquivo imagem;
	
	private Date ultimoLogin;
	
	private Date ultimoLogout;
	
	private Long cpf;
 	
// TODO: Rever essa modelagem idGrupoUsuario. Porque foi criado um idGrupoUsuario e um grupoUsuario
// TODO: C�digo comentado em 20/09/2017. Apagar em 180 dias	
//	private Integer idGrupoUsuario;
	
// TODO: Rever essa modelagem listaEmail e email. Considerar tela, e-mails poss�vel e e-mail principal que ser� o login.	
	private List<EmailUsuario> listaEmail;
	
	private EmailUsuario email;
	
	private GrupoUsuario grupoUsuario;
	
	// Lista de condom�nios que o usu�rio tem acesso. Ex.: Funcion�rio que trabalha em mais de um condom�nio
	private List<Condominio> listaCondominio;
	
	private Integer situacao;
	
	private List<GrupoUsuario> listaGrupoUsuario;
	
	public Usuario(){
		this.email = new EmailUsuario();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<EmailUsuario> getListaEmail() {
		return listaEmail;
	}

	public void setListaEmail(List<EmailUsuario> listaEmail) {
		this.listaEmail = listaEmail;
	}
	
	public Integer getSexo() {
		return sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}

	
	public Date getDataNascimento() {	
		/* Essa valida��o (diferente null e zero), se faz necess�ria, pois no caso de uma inser��o/atualiza��o o zero � para garantir a data null
		 e o null � a recupera��o da informa��o do banco. */
		if(this.getDataNascimentoAno() != null && this.getDataNascimentoAno() != 0 
				&& this.getDataNascimentoMes() != null  && 
				this.getDataNascimentoDia() != null && this.getDataNascimentoDia() != 0){
			Calendar calendar = Calendar.getInstance();	
			calendar.set(this.getDataNascimentoAno(), this.getDataNascimentoMes(), this.getDataNascimentoDia(),0,0);
			this.dataNascimento = calendar.getTime();			
		}
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		if (dataNascimento != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataNascimento);			
			this.setDataNascimentoDia(calendar.get(Calendar.DAY_OF_MONTH));
			this.setDataNascimentoMes(calendar.get(Calendar.MONTH));
			this.setDataNascimentoAno(calendar.get(Calendar.YEAR));			
			this.dataNascimento = dataNascimento;			
		}		
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public Integer getDataNascimentoDia() {
		return dataNascimentoDia;
	}

	public void setDataNascimentoDia(Integer dataNascimentoDia) {
		this.dataNascimentoDia = dataNascimentoDia;
	}

	public Integer getDataNascimentoMes() {
		return dataNascimentoMes;
	}

	public void setDataNascimentoMes(Integer dataNascimentoMes) {
		this.dataNascimentoMes = dataNascimentoMes;
	}

	public Integer getDataNascimentoAno() {
		return dataNascimentoAno;
	}

	public void setDataNascimentoAno(Integer dataNascimentoAno) {
		this.dataNascimentoAno = dataNascimentoAno;
	}	

//TODO: C�digo comentado em 20/09/2017. Apagar em 180 dias	
//	public Integer getIdGrupoUsuario() {
//		return idGrupoUsuario;
//	}

//	TODO: C�digo comentado em 20/09/2017. Apagar em 180 dias
//	public void setIdGrupoUsuario(Integer idGrupoUsuario) {
//		this.idGrupoUsuario = idGrupoUsuario;
//	}

	public EmailUsuario getEmail() {
		return email;
	}

	public void setEmail(EmailUsuario email) {
		this.email = email;
	}

	public GrupoUsuario getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

	public List<Condominio> getListaCondominio() {
		return listaCondominio;
	}

	public void setListaCondominio(List<Condominio> listaCondominio) {
		this.listaCondominio = listaCondominio;
	}

	public Arquivo getImagem() {
		return imagem;
	}

	public void setImagem(Arquivo imagem) {
		this.imagem = imagem;
	}	
	
	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public Date getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public Date getUltimoLogout() {
		return ultimoLogout;
	}

	public void setUltimoLogout(Date ultimoLogout) {
		this.ultimoLogout = ultimoLogout;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public List<GrupoUsuario> getListaGrupoUsuario() {
		return listaGrupoUsuario;
	}

	public void setListaGrupoUsuario(List<GrupoUsuario> listaGrupoUsuario) {
		this.listaGrupoUsuario = listaGrupoUsuario;
	}
	
	
	
	

}