package br.inatel.mobile.pos.dm102.trabalhofinal.model;

public class ClienteJuridico extends Cliente {

	private String cnpj;
	private String razaoSocial;
	private String inscricaoEstadual;

	public ClienteJuridico(String nome, String endereco, String telefone, String cnpj, String razaoSocial,
			String inscricaoEstadual) {
		super(nome, endereco, telefone);
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	@Override
	public String toString() {
		return super.toString() + " -- CNPJ: " + cnpj + " -- Razão Social: " + razaoSocial + " -- Inscrição Estadual: "
				+ inscricaoEstadual;
	}

}
