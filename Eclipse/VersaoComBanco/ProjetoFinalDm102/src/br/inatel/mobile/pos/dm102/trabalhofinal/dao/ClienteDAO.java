package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.pmw.tinylog.Logger;

import br.inatel.mobile.pos.dm102.trabalhofinal.model.Cliente;

public class ClienteDAO {

	public static Cliente buscarClientePorId(int cliente_id) {

		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String nome = null;
		String endereco = null;
		String telefone = null;

		try {
			String sql = "SELECT * FROM cliente WHERE id = ?";

			statement = conexao.prepareStatement(sql);
			statement.setInt(1, cliente_id);
			rs = statement.executeQuery();

			while (rs.next()) {
				nome = rs.getString("nome");
				endereco = rs.getString("endereco");
				telefone = rs.getString("telefone");
			}

		} catch (Exception ex) {
			Logger.error(ex, "Não foi possível montar o cliente.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}

		return new Cliente(nome, endereco, telefone);

	}

}
