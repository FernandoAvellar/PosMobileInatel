package br.inatel.mobile.pos.dm102.trabalhofinal.model;

public class PessoaFisica extends Cliente {

	private String cpf;

	public PessoaFisica(String nome, String cpf) {
		super(nome);
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	@Override
	public String toString() {
		 return super.toString() + ", CPF: " + cpf;
	}

}
