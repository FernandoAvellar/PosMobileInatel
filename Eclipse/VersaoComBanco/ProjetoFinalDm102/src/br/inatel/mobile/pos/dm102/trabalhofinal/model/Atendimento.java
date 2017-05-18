package br.inatel.mobile.pos.dm102.trabalhofinal.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Atendimento {

	private LocalDateTime data;
	private String descricao;

	public Atendimento(String descricao) {
		this.data = LocalDateTime.now();
		this.descricao = descricao;
	}
	
	public Atendimento(String descricao, LocalDateTime data) {
		this.descricao = descricao;
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public LocalDateTime getData() {
		return data;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatador = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
				.withLocale(new Locale("pt", "br"));

		return "Atendimento realizado em: " + data.format(formatador) + ", Descrição: " + descricao;
	}

}
