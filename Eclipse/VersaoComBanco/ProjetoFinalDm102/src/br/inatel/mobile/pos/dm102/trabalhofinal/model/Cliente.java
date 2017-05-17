package br.inatel.mobile.pos.dm102.trabalhofinal.model;

import java.util.ArrayList;

public abstract class Cliente {

	private String nome;
	private String endereco;
	private String telefone;
	private ArrayList<Atendimento> atendimentos = new ArrayList<>();

	public Cliente(String nome, String endereco, String telefone) {
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
	}


	public void cadastrarAtendimento(String descricao) {
		Atendimento atendimento = new Atendimento(descricao);
		atendimentos.add(atendimento);
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
	
	public ArrayList<Atendimento> getAtendimentos() {
		return atendimentos;
	}
	
	@Override
	public String toString() {
		return "Nome: "+ nome + " -- Endereço: "+ endereco + " -- Telefone: "+ telefone + " -- Atendimentos: " + atendimentos.size();
	}

}
