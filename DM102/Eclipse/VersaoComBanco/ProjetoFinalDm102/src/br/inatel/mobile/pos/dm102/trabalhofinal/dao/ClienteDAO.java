package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.pmw.tinylog.Logger;

import br.inatel.mobile.pos.dm102.trabalhofinal.model.Cliente;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteFisico;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteJuridico;

public class ClienteDAO {

	private ClienteDAO() {
	}

	public static void salvar(Cliente clienteFisico) {
		int cliente_id = 0;
		String sql1 = "INSERT INTO cliente(nome, endereco, telefone) VALUES(?, ?, ?)";
		String sql2 = "SELECT MAX(id) id from cliente";
		String sql3 = "INSERT INTO cliente_fisico(cpf, identidade, tipoDaIdentidade, cliente_id) VALUES(?, ?, ?, ?)";
		String sql4 = "INSERT INTO cliente_juridico(cnpj, razaoSocial, inscricaoEstadual, cliente_id) VALUES(?, ?, ?, ?)";

		try (Connection conexao = Conexao.abrirConexao();
				PreparedStatement statement1 = conexao.prepareStatement(sql1);
				PreparedStatement statement2 = conexao.prepareStatement(sql2);
				PreparedStatement statement3 = conexao.prepareStatement(sql3);
				PreparedStatement statement4 = conexao.prepareStatement(sql4)) {

			statement1.setString(1, clienteFisico.getNome());
			statement1.setString(2, clienteFisico.getEndereco());
			statement1.setString(3, clienteFisico.getTelefone());
			statement1.executeUpdate();

			try (ResultSet rs = statement2.executeQuery()) {
				rs.next();
				cliente_id = rs.getInt("id");
			}

			if (clienteFisico instanceof ClienteFisico) {
				statement3.setString(1, ((ClienteFisico) clienteFisico).getCpf());
				statement3.setString(2, ((ClienteFisico) clienteFisico).getIdentidade());
				statement3.setString(3, ((ClienteFisico) clienteFisico).getTipoDaIdentidade());
				statement3.setInt(4, cliente_id);
				statement3.executeUpdate();
			} else {
				statement4.setString(1, ((ClienteJuridico) clienteFisico).getCnpj());
				statement4.setString(2, ((ClienteJuridico) clienteFisico).getRazaoSocial());
				statement4.setString(3, ((ClienteJuridico) clienteFisico).getInscricaoEstadual());
				statement4.setInt(4, cliente_id);
				statement4.executeUpdate();
			}

		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel salvar o cliente fisico.");
		}
	}

	public static List<Cliente> buscarTodosClientesFisicos() {
		ArrayList<Cliente> clientesFisicosCadastrados = new ArrayList<>();
		String sql = "SELECT cliente.id, nome, endereco, telefone, cpf, identidade, tipoDaIdentidade FROM"
				+ " cliente_fisico JOIN cliente ON cliente_fisico.cliente_id = cliente.id";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {

			try (ResultSet rs = statement.executeQuery()) {

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
			}
		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar todos os cliente fisicos no banco.");
		}

		return clientesFisicosCadastrados;
	}

	public static List<Cliente> buscarTodosClientesJuridicos() {
		ArrayList<Cliente> clientesJuridicosCadastrados = new ArrayList<>();
		String sql = "SELECT cliente.id, nome, endereco, telefone, cnpj, razaoSocial, inscricaoEstadual FROM"
				+ " cliente_juridico JOIN cliente ON cliente_juridico.cliente_id = cliente.id";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {

			try (ResultSet rs = statement.executeQuery()) {

				while (rs.next()) {
					String nome = rs.getString("nome");
					String endereco = rs.getString("endereco");
					String telefone = rs.getString("telefone");
					String cnpj = rs.getString("cnpj");
					String razaoSocial = rs.getString("razaoSocial");
					String inscricaoEstadual = rs.getString("inscricaoEstadual");

					ClienteJuridico clienteJuridico = new ClienteJuridico(nome, endereco, telefone, cnpj, razaoSocial,
							inscricaoEstadual);
					clientesJuridicosCadastrados.add(clienteJuridico);
				}
			}
		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar todos os cliente fisicos no banco.");
		}

		return clientesJuridicosCadastrados;
	}

	public static ClienteFisico buscarPorCpf(String cpfParaBusca) {
		ClienteFisico clienteFisico = null;
		String sql = "SELECT cliente.id, nome, endereco, telefone, cpf, identidade, tipoDaIdentidade FROM cliente_fisico JOIN cliente ON cliente_fisico.cliente_id = cliente.id WHERE cpf = ?";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {
			statement.setString(1, cpfParaBusca);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					String nome = rs.getString("nome");
					String endereco = rs.getString("endereco");
					String telefone = rs.getString("telefone");
					String cpf = rs.getString("cpf");
					String identidade = rs.getString("identidade");
					String tipoDaIdentidade = rs.getString("tipoDaIdentidade");
					clienteFisico = new ClienteFisico(nome, endereco, telefone, cpf, identidade, tipoDaIdentidade);
				}
			}
		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar todos os cliente juridicos no banco.");
		}

		return clienteFisico;
	}

	public static ClienteJuridico buscarPorCnpj(String cnpjParaBusca) {
		ClienteJuridico clienteJuridico = null;
		String sql = "SELECT cliente.id, nome, endereco, telefone, cnpj, razaoSocial, inscricaoEstadual FROM "
				+ "cliente_juridico JOIN cliente ON cliente_juridico.cliente_id = cliente.id WHERE cnpj = ?";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {
			statement.setString(1, cnpjParaBusca);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					String nome = rs.getString("nome");
					String endereco = rs.getString("endereco");
					String telefone = rs.getString("telefone");
					String cnpj = rs.getString("cnpj");
					String razaoSocial = rs.getString("razaoSocial");
					String inscricaoEstadual = rs.getString("inscricaoEstadual");
					clienteJuridico = new ClienteJuridico(nome, endereco, telefone, cnpj, razaoSocial,
							inscricaoEstadual);
				}
			}
		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar o cliente juridicos no banco.");
		}

		return clienteJuridico;
	}

	public static int contarNumeroDeAtendimentosPorCpf(String cpfParaBusca) {
		int numeroDeAtendimentos = 0;
		String sql = "SELECT COUNT(*) total_atendimentos FROM atendimento WHERE cliente_id=?";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {
			statement.setInt(1, buscarClienteIdPorCpf(cpfParaBusca));

			try (ResultSet rs = statement.executeQuery()) {
				rs.next();
				numeroDeAtendimentos = rs.getInt("total_atendimentos");
			}
		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel contar o numero de atendimentos.");
		}

		return numeroDeAtendimentos;
	}

	public static int contarNumeroDeAtendimentosPorCnpj(String cnpjParaBusca) {
		int numeroDeAtendimentos = 0;
		String sql = "SELECT COUNT(*) total_atendimentos FROM atendimento WHERE cliente_id=?";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {
			statement.setInt(1, buscarClienteIdPorCnpj(cnpjParaBusca));

			try (ResultSet rs = statement.executeQuery()) {
				rs.next();
				numeroDeAtendimentos = rs.getInt("total_atendimentos");
			}
		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel contar o numero de atendimentos.");
		}

		return numeroDeAtendimentos;
	}

	public static int buscarClienteIdPorCpf(String cpfParaBusca) {
		int cliente_id = -1;
		String sql = "SELECT cliente_id FROM cliente_fisico WHERE cpf = ?";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {
			statement.setString(1, cpfParaBusca);

			try (ResultSet rs = statement.executeQuery()) {
				rs.next();
				cliente_id = rs.getInt("cliente_id");
			}
		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar o cliente_id no banco.");
		}

		return cliente_id;
	}

	public static int buscarClienteIdPorCnpj(String cnpjParaBusca) {
		int cliente_id = -1;
		String sql = "SELECT cliente_id FROM cliente_juridico WHERE cnpj = ?";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {
			statement.setString(1, cnpjParaBusca);

			try (ResultSet rs = statement.executeQuery()) {
				rs.next();
				cliente_id = rs.getInt("cliente_id");
			}
		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar o cliente_id no banco.");
		}

		return cliente_id;
	}
}
