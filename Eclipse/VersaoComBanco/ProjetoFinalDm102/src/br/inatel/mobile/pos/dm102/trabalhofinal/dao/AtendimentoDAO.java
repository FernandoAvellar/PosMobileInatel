package br.inatel.mobile.pos.dm102.trabalhofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.pmw.tinylog.Logger;

import br.inatel.mobile.pos.dm102.trabalhofinal.model.Atendimento;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteFisico;

public class AtendimentoDAO {

	private AtendimentoDAO() {
	}

	public static void salvarAtendimentoPessoaFisica(ClienteFisico clienteAtendido, Atendimento atendimento) {

		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;

		try {
			String sql1 = "INSERT INTO atendimento(descricao, data, cliente_id) VALUES(?, ?, ?)";

			statement = conexao.prepareStatement(sql1);

			statement.setString(1, atendimento.getDescricao());
			statement.setTimestamp(2, Timestamp.from(atendimento.getData().toInstant(null)));
			statement.setInt(3, buscarIdDoClienteFisicoNoBanco(clienteAtendido));
			statement.executeUpdate();
			statement.close();

		} catch (Exception ex) {
			Logger.error(ex, "N�o foi poss�vel salvar o atendimento.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}
	}

	public static int buscarIdDoClienteFisicoNoBanco(ClienteFisico clienteFisico) {
		Connection conexao = Conexao.abrirConexao();
		PreparedStatement statement = null;
		ResultSet rs = null;
		int cliente_id = 0;

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
			Logger.error(ex, "N�o foi poss�vel salvar o atendimento.");

		} finally {
			JDBCUtils.fecharRecursos(conexao, statement);
		}

		return cliente_id;
	}

}
