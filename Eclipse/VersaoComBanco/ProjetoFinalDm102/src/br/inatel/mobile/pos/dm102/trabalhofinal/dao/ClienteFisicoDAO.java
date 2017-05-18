package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.pmw.tinylog.Logger;

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
			Logger.error(ex, "Nao foi possivel salvar o cliente fisico.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}
	}

	public static ArrayList<Cliente> buscarTodosClientesFisicos() {
		ArrayList<Cliente> clientesFisicosCadastrados = new ArrayList<>();
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			String sql1 = "SELECT * FROM cliente_fisico";

			statement = conexao.prepareStatement(sql1);
			rs = statement.executeQuery();

			while (rs.next()) {

				String cpf = rs.getString("cpf");
				String identidade = rs.getString("identidade");
				String tipoDaIdentidade = rs.getString("tipoDaIdentidade");
				int cliente_id = rs.getInt("cliente_id");

				Cliente cliente = ClienteDAO.buscarClientePorId(cliente_id);
				String nome = cliente.getNome();
				String endereco = cliente.getEndereco();
				String telefone = cliente.getTelefone();

				ClienteFisico clienteFisico = new ClienteFisico(nome, endereco, telefone, cpf, identidade,
						tipoDaIdentidade);
				clientesFisicosCadastrados.add(clienteFisico);
			}

			statement.close();

		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar todos os cliente fisicos no banco.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}

		return clientesFisicosCadastrados;

	}

	public static ClienteFisico buscarPorCpf(String cpfParaBusca) {
		ClienteFisico clienteFisico = null;
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM cliente_fisico where cpf = ?";

			statement = conexao.prepareStatement(sql);
			statement.setString(1, cpfParaBusca);
			rs = statement.executeQuery();

			while (rs.next()) {

				String cpf = rs.getString("cpf");
				String identidade = rs.getString("identidade");
				String tipoDaIdentidade = rs.getString("tipoDaIdentidade");
				int cliente_id = rs.getInt("cliente_id");

				Cliente cliente = ClienteDAO.buscarClientePorId(cliente_id);
				String nome = cliente.getNome();
				String endereco = cliente.getEndereco();
				String telefone = cliente.getTelefone();

				clienteFisico = new ClienteFisico(nome, endereco, telefone, cpf, identidade, tipoDaIdentidade);
			}

			statement.close();

		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar todos os cliente juridicos no banco.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}

		return clienteFisico;

	}

}
