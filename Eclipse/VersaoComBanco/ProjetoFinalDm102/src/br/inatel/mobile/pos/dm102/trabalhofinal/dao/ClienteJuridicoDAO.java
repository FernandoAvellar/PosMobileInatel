package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.pmw.tinylog.Logger;

import br.inatel.mobile.pos.dm102.trabalhofinal.model.Cliente;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteJuridico;

public class ClienteJuridicoDAO {

	private ClienteJuridicoDAO() {
	}

	public static void salvar(Cliente clienteJuridico) {

		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		int cliente_id = 0;

		try {
			String sql1 = "INSERT INTO cliente(nome, endereco, telefone) VALUES(?, ?, ?)";
			String sql2 = "SELECT MAX(id) id from cliente";
			String sql3 = "INSERT INTO cliente_juridico(cnpj, razaoSocial, inscricaoEstadual, cliente_id) VALUES(?, ?, ?, ?)";

			statement = conexao.prepareStatement(sql1);

			statement.setString(1, clienteJuridico.getNome());
			statement.setString(2, clienteJuridico.getEndereco());
			statement.setString(3, clienteJuridico.getTelefone());
			statement.executeUpdate();
			statement.close();

			statement = conexao.prepareStatement(sql2);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				cliente_id = rs.getInt("id");
			}

			statement.close();

			statement = conexao.prepareStatement(sql3);

			statement.setString(1, ((ClienteJuridico) clienteJuridico).getCnpj());
			statement.setString(2, ((ClienteJuridico) clienteJuridico).getRazaoSocial());
			statement.setString(3, ((ClienteJuridico) clienteJuridico).getInscricaoEstadual());
			statement.setInt(4, cliente_id);
			statement.executeUpdate();

		} catch (Exception ex) {
			Logger.error(ex, "Não foi possível salvar o cliente juridico.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}
	}

}
