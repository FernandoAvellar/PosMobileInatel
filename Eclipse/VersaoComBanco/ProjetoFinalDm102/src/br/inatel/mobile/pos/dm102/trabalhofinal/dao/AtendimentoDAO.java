package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.pmw.tinylog.Logger;

import br.inatel.mobile.pos.dm102.trabalhofinal.model.Atendimento;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteFisico;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteJuridico;

public class AtendimentoDAO {

	private static final String MENSAGEM_FALHA_SALVAR_ATENDIMENTO = "Nao foi possivel salvar o atendimento.";

	private AtendimentoDAO() {
	}

	public static void salvarAtendimentoClienteFisico(ClienteFisico clienteAtendido, Atendimento atendimento) {

		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;

		try {
			String sql1 = "INSERT INTO atendimento(descricao, data, cliente_id) VALUES(?, ?, ?)";

			statement = conexao.prepareStatement(sql1);

			statement.setString(1, atendimento.getDescricao());
			statement.setObject(2, Timestamp.valueOf(atendimento.getData()));
			statement.setInt(3, buscarIdDoClienteFisicoNaTabelaCliente(clienteAtendido));
			statement.executeUpdate();
			statement.close();

		} catch (Exception ex) {
			Logger.error(ex, MENSAGEM_FALHA_SALVAR_ATENDIMENTO);

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}
	}

	public static int buscarIdDoClienteFisicoNaTabelaCliente(ClienteFisico clienteFisico) {
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;
		int cliente_id = -1;

		try {

			String sql1 = "SELECT cliente_id FROM cliente_fisico WHERE cpf = ?";

			statement = conexao.prepareStatement(sql1);

			statement.setString(1, clienteFisico.getCpf());
			rs = statement.executeQuery();
			while (rs.next()) {
				cliente_id = rs.getInt("cliente_id");
			}
			statement.close();

		} catch (Exception ex) {
			Logger.error(ex, MENSAGEM_FALHA_SALVAR_ATENDIMENTO);

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}

		return cliente_id;
	}

	public static void salvarAtendimentoClienteJuridico(ClienteJuridico clienteAtendido, Atendimento atendimento) {

		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;

		try {
			String sql1 = "INSERT INTO atendimento(descricao, data, cliente_id) VALUES(?, ?, ?)";

			statement = conexao.prepareStatement(sql1);

			statement.setString(1, atendimento.getDescricao());
			statement.setObject(2, Timestamp.valueOf(atendimento.getData()));
			statement.setInt(3, buscarIdDoClienteJuridicoNaTabelaCliente(clienteAtendido));
			statement.executeUpdate();
			statement.close();

		} catch (Exception ex) {
			Logger.error(ex, MENSAGEM_FALHA_SALVAR_ATENDIMENTO);

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}
	}

	public static int buscarIdDoClienteJuridicoNaTabelaCliente(ClienteJuridico clienteJuridico) {
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;
		int cliente_id = -1;

		try {

			String sql1 = "SELECT cliente_id FROM cliente_juridico WHERE cnpj = ?";

			statement = conexao.prepareStatement(sql1);

			statement.setString(1, clienteJuridico.getCnpj());
			rs = statement.executeQuery();
			while (rs.next()) {
				cliente_id = rs.getInt("cliente_id");
			}
			statement.close();

		} catch (Exception ex) {
			Logger.error(ex, MENSAGEM_FALHA_SALVAR_ATENDIMENTO);

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}

		return cliente_id;
	}

	public static List<Atendimento> buscarTodosAtendimentosClienteFisico(ClienteFisico clienteFisico) {
		ArrayList<Atendimento> atendimentos = new ArrayList<>();
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;
		LocalDateTime data;
		String descricao;

		try {
			String sql = "SELECT * FROM atendimento WHERE cliente_id = ?";

			statement = conexao.prepareStatement(sql);
			statement.setInt(1, buscarIdDoClienteFisicoNaTabelaCliente(clienteFisico));
			rs = statement.executeQuery();

			while (rs.next()) {
				descricao = rs.getString("descricao");
				data =  ((Timestamp) rs.getObject("data")).toLocalDateTime();
				Atendimento atendimento = new Atendimento(descricao, data);
				atendimentos.add(atendimento);
			}

		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel montar a lista de atendimentos do cliente fisico.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}

		return atendimentos;

	}

	public static List<Atendimento> buscarTodosAtendimentosClienteJuridico(ClienteJuridico clienteJuridico) {
		ArrayList<Atendimento> atendimentos = new ArrayList<>();
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;
		LocalDateTime data;
		String descricao;

		try {
			String sql = "SELECT * FROM atendimento WHERE cliente_id = ?";

			statement = conexao.prepareStatement(sql);
			statement.setInt(1, buscarIdDoClienteJuridicoNaTabelaCliente(clienteJuridico));
			rs = statement.executeQuery();

			while (rs.next()) {
				descricao = rs.getString("descricao");
				data =  ((Timestamp) rs.getObject("data")).toLocalDateTime();
				Atendimento atendimento = new Atendimento(descricao, data);
				atendimentos.add(atendimento);
			}

		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel montar a lista de atendimentos do cliente juridico.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}

		return atendimentos;

	}

}
