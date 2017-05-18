package br.inatel.mobile.pos.dm102.trabalhofinal.model;

public class Cliente {

	private String nome;
	private String endereco;
	private String telefone;

	public Cliente(String nome, String endereco, String telefone) {
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	@Override
	public String toString() {
		return "Nome: " + nome + " -- Endereco: " + endereco + " -- Telefone: " + telefone;
	}

}
