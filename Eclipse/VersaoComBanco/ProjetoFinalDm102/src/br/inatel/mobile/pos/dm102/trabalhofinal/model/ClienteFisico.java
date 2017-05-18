package br.inatel.mobile.pos.dm102.trabalhofinal.model;

public class ClienteFisico extends Cliente {

	private String cpf;
	private String identidade;
	private String tipoDaIdentidade;
	
	public ClienteFisico(String nome, String endereco, String telefone, String cpf, String identidade,
			String tipoDaIdentidade) {
		super(nome, endereco, telefone);
		this.cpf = cpf;
		this.identidade = identidade;
		this.tipoDaIdentidade = tipoDaIdentidade;
	}

	public String getCpf() {
		return cpf;
	}
	
	public String getIdentidade() {
		return identidade;
	}
	
	public String getTipoDaIdentidade() {
		return tipoDaIdentidade;
	}

	@Override
	public String toString() {
		return super.toString() + " -- CPF: " + cpf + " -- Identidade: " + identidade + " -- Tipo da Identidade: " + tipoDaIdentidade;
	}

}
