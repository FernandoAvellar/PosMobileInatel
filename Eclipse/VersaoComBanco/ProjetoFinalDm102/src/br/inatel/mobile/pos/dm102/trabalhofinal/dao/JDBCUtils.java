package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCUtils {

	private JDBCUtils() {
	}

	public static void fecharRecursos(Connection conexao, Statement statement, ResultSet resultado) {
		try {
			conexao.close();
			statement.close();
			resultado.close();
		} catch (Exception ex) {
			System.out.println("Falha ao fechar recursos do banco.");
		}
	}

	public static void fecharRecursos(Connection conexao, Statement statement) {
		try {
			conexao.close();
			statement.close();
		} catch (Exception ex) {
			System.out.println("Falha ao fechar recursos do banco.");
			ex.printStackTrace();
		}
	}

}
