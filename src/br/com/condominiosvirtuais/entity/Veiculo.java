package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa um veículo. Exemplo: Sandero   
 * @author Maikel Joel de Souza
 * @since 16/01/2014
 */
public class Veiculo implements Comparable<Veiculo> {
	
	private Integer id;
	
	private String nome;
	
	private String placa;
	
	private Integer tamanho;
	
	private Integer tipo;
	
    private Bloco bloco;
    
    private Unidade unidade;
	
	private Condomino condomino;
	
	private Garagem garagem;
	

	public Veiculo(){
		this.garagem = new Garagem();
		this.bloco = new Bloco();
		this.unidade = new Unidade();
		this.condomino = new Condomino();
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

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Condomino getCondomino() {	
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}

	public Garagem getGaragem() {
		return garagem;
	}

	public void setGaragem(Garagem garagem) {
		this.garagem = garagem;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	@Override
	public int compareTo(Veiculo outroVeiculo) {
		Integer retorno = null;
		 if(this.getBloco().compareTo(outroVeiculo.getBloco())  <= -1){			 
			 retorno = -1; 
         } else if(this.getBloco().compareTo(outroVeiculo.getBloco())  >= 1){
        	 retorno =  1; 
         }
		 retorno = this.getUnidade().compareTo(outroVeiculo.getUnidade());
         return retorno;
	}	
	
}