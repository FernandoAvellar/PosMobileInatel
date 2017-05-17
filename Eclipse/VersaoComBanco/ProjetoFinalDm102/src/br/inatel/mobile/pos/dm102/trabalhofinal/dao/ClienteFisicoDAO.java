package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.inatel.mobile.pos.dm102.trabalhofinal.model.Cliente;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteFisico;

public class ClienteFisicoDAO {

	private ClienteFisicoDAO() {
	}

	public static void salvar(Cliente clienteFisico) {

		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		int cliente_id = 0;

		try {
			String sql1 = "INSERT INTO cliente(nome, endereco, telefone) VALUES(?, ?, ?)";
			String sql2 = "SELECT MAX(id) id from cliente";
			String sql3 = "INSERT INTO cliente_fisico(cpf, identidade, tipoDaIdentidade, cliente_id) VALUES(?, ?, ?, ?)";

			statement = conexao.prepareStatement(sql1);

			statement.setString(1, clienteFisico.getNome());
			statement.setString(2, clienteFisico.getEndereco());
			statement.setString(3, clienteFisico.getTelefone());
			statement.executeUpdate();
			statement.close();

			statement = conexao.prepareStatement(sql2);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				 cliente_id = rs.getInt("id");
			}

			statement.close();

			statement = conexao.prepareStatement(sql3);

			statement.setString(1, ((ClienteFisico) clienteFisico).getCpf());
			statement.setString(2, ((ClienteFisico) clienteFisico).getIdentidade());
			statement.setString(3, ((ClienteFisico) clienteFisico).getTipoDaIdentidade());
			statement.setInt(4, cliente_id);
			statement.executeUpdate();

		} catch (Exception ex) {
			System.out.println("Não foi possível salvar o cliente fisico.");
			ex.printStackTrace();

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}
	}

}
