package br.inatel.mobile.pos.dm102.trabalhofinal.model;

import br.inatel.mobile.pos.dm102.trabalhofinal.dao.ClienteJuridicoDAO;

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

	public int getNumeroDeAtendimentos() {
		return ClienteJuridicoDAO.contarNumeroDeAtendimentos(this.cnpj);
	}

	@Override
	public String toString() {
		return super.toString() + " -- CNPJ: " + cnpj + " -- Razao Social: " + razaoSocial + " -- Inscricao Estadual: "
				+ inscricaoEstadual + " -- Numero de Atendimentos: " + getNumeroDeAtendimentos();
	}

}
