package br.inatel.mobile.pos.dm102.trabalhofinal.model;

import java.util.ArrayList;

public abstract class Cliente {

	private String nome;
	private ArrayList<Atendimento> atendimentos = new ArrayList<>();

	public Cliente(String nome) {
		this.nome = nome;
	}
	
	public void cadastrarAtendimento(String descricao) {
		Atendimento atendimento = new Atendimento(descricao);
		atendimentos.add(atendimento);
	}
	
	public String getNome() {
		return nome;
	}
	
	public ArrayList<Atendimento> getAtendimentos() {
		return atendimentos;
	}
	
	@Override
	public String toString() {
		return "Nome: "+ nome + ", Atendimentos: " + atendimentos.size();
	}

}
