package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.pmw.tinylog.Logger;

import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteJuridico;

public class ClienteJuridicoDAO {

	private ClienteJuridicoDAO() {
	}

	public static ClienteJuridico buscarPorCnpj(String cnpjParaBusca) {
		ClienteJuridico clienteJuridico = null;
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT cliente.id, nome, endereco, telefone, cnpj, razaoSocial, inscricaoEstadual FROM "
					+ "cliente_juridico JOIN cliente ON cliente_juridico.cliente_id = cliente.id WHERE cnpj = ?";

			statement = conexao.prepareStatement(sql);
			statement.setString(1, cnpjParaBusca);
			rs = statement.executeQuery();

			while (rs.next()) {
				String nome = rs.getString("nome");
				String endereco = rs.getString("endereco");
				String telefone = rs.getString("telefone");
				String cnpj = rs.getString("cnpj");
				String razaoSocial = rs.getString("razaoSocial");
				String inscricaoEstadual = rs.getString("inscricaoEstadual");

				clienteJuridico = new ClienteJuridico(nome, endereco, telefone, cnpj, razaoSocial, inscricaoEstadual);
			}

		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar todos os cliente juridicos no banco.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement, rs);
		}

		return clienteJuridico;
	}

	public static int contarNumeroDeAtendimentos(String cnpjParaBusca) {
		int numeroDeAtendimentos = 0;
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT COUNT(*) total_atendimentos FROM atendimento WHERE cliente_id=?";

			statement = conexao.prepareStatement(sql);
			statement.setInt(1, buscarClienteIdPorCnpj(cnpjParaBusca));
			rs = statement.executeQuery();

			rs.next();
			numeroDeAtendimentos = rs.getInt("total_atendimentos");

		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel contar o numero de atendimentos.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement, rs);
		}

		return numeroDeAtendimentos;
	}

	public static int buscarClienteIdPorCnpj(String cnpjParaBusca) {
		int cliente_id = -1;
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT cliente_id FROM cliente_juridico WHERE cnpj = ?";

			statement = conexao.prepareStatement(sql);
			statement.setString(1, cnpjParaBusca);
			rs = statement.executeQuery();

			rs.next();
			cliente_id = rs.getInt("cliente_id");

		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel buscar o cliente_id no banco.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement, rs);
		}

		return cliente_id;
	}

}
