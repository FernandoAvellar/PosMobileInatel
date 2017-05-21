package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.pmw.tinylog.Logger;

public class Conexao {

	private Conexao() {
	}

	private static final String URL = "jdbc:mysql://localhost/projetofinaldm102?useSSL=false";
	private static final String USUARIO = "root";
	private static final String SENHA = "root";

	public static Connection abrirConexao() {

		try {
			return DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (SQLException ex) {
			Logger.error(ex, "Falha na conexao com o banco de dados.");
		}

		return null;
	}
	
	public static void main(String[] args) {
		Conexao.abrirConexao();
	}
}
