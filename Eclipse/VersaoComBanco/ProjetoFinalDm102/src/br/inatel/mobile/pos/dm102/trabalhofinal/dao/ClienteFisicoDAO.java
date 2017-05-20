package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

	public static List<Cliente> buscarTodosClientesFisicos() {
		ArrayList<Cliente> clientesFisicosCadastrados = new ArrayList<>();
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT cliente.id, nome, endereco, telefone, cpf, identidade, tipoDaIdentidade FROM"
					+ " cliente_fisico JOIN cliente ON cliente_fisico.cliente_id = cliente.id";

			statement = conexao.prepareStatement(sql);
			rs = statement.executeQuery();

			while (rs.next()) {
				String nome = rs.getString("nome");
				String endereco = rs.getString("endereco");
				String telefone = rs.getString("telefone");
				String cpf = rs.getString("cpf");
				String identidade = rs.getString("identidade");
				String tipoDaIdentidade = rs.getString("tipoDaIdentidade");

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
			String sql = "SELECT cliente.id, nome, endereco, telefone, cpf, identidade, tipoDaIdentidade FROM"
					+ " cliente_fisico JOIN cliente ON cliente_fisico.cliente_id = cliente.id WHERE cpf = ?";

			statement = conexao.prepareStatement(sql);
			statement.setString(1, cpfParaBusca);
			rs = statement.executeQuery();

			while (rs.next()) {
				String nome = rs.getString("nome");
				String endereco = rs.getString("endereco");
				String telefone = rs.getString("telefone");
				String cpf = rs.getString("cpf");
				String identidade = rs.getString("identidade");
				String tipoDaIdentidade = rs.getString("tipoDaIdentidade");

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

	public static int contarNumeroDeAtendimentos(String cpfParaBusca) {
		int numeroDeAtendimentos = 0;
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT COUNT(*) total_atendimentos FROM atendimento WHERE cliente_id=?";

			statement = conexao.prepareStatement(sql);
			statement.setInt(1, buscarClienteIdPorCPF(cpfParaBusca));
			rs = statement.executeQuery();

			rs.next();
			numeroDeAtendimentos = rs.getInt("total_atendimentos");

			statement.close();

		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel contar o numero de atendimentos.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}

		return numeroDeAtendimentos;
	}

	public static int buscarClienteIdPorCPF(String cpfParaBusca) {
		int cliente_id = -1;
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT cliente_id FROM cliente_fisico WHERE cpf = ?";

			statement = conexao.prepareStatement(sql);
			statement.setString(1, cpfParaBusca);
			rs = statement.executeQuery();

			rs.next();
			cliente_id = rs.getInt("cliente_id");

			statement.close();
			
		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar o cliente_id no banco.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}

		return cliente_id;
	}

}
