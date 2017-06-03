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

	private static final String MENSAGEM_DE_FALHA_AO_TENTAR_SALVAR_UM_ATENDIMENTO = "Nao foi possivel salvar o atendimento.";

	private AtendimentoDAO() {
	}

	public static void salvarAtendimentoClienteFisico(ClienteFisico clienteAtendido, Atendimento atendimento) {
		String sql = "INSERT INTO atendimento(descricao, data, cliente_id) VALUES(?, ?, ?)";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) 
		{
			statement.setString(1, atendimento.getDescricao());
			statement.setObject(2, Timestamp.valueOf(atendimento.getData()));
			statement.setInt(3, buscarIdDoClienteFisicoNaTabelaCliente(clienteAtendido));
			statement.executeUpdate();
		} catch (Exception ex) {
			Logger.error(ex, MENSAGEM_DE_FALHA_AO_TENTAR_SALVAR_UM_ATENDIMENTO);
		}
	}

	public static int buscarIdDoClienteFisicoNaTabelaCliente(ClienteFisico clienteFisico) {
		int cliente_id = -1;
		String sql = "SELECT cliente_id FROM cliente_fisico WHERE cpf = ?";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql))
		{
			statement.setString(1, clienteFisico.getCpf());
			
			try (ResultSet rs = statement.executeQuery()) {
				rs.next();
				cliente_id = rs.getInt("cliente_id");
			}
		} catch (Exception ex) {
			Logger.error(ex, MENSAGEM_DE_FALHA_AO_TENTAR_SALVAR_UM_ATENDIMENTO);
		} 

		return cliente_id;
	}

	public static void salvarAtendimentoClienteJuridico(ClienteJuridico clienteAtendido, Atendimento atendimento) {
		String sql = "INSERT INTO atendimento(descricao, data, cliente_id) VALUES(?, ?, ?)";
		
		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql))
		{
			statement.setString(1, atendimento.getDescricao());
			statement.setObject(2, Timestamp.valueOf(atendimento.getData()));
			statement.setInt(3, buscarIdDoClienteJuridicoNaTabelaCliente(clienteAtendido));
			statement.executeUpdate();
		} catch (Exception ex) {
			Logger.error(ex, MENSAGEM_DE_FALHA_AO_TENTAR_SALVAR_UM_ATENDIMENTO);
		} 
	}

	public static int buscarIdDoClienteJuridicoNaTabelaCliente(ClienteJuridico clienteJuridico) {
		int cliente_id = -1;
		String sql = "SELECT cliente_id FROM cliente_juridico WHERE cnpj = ?";

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql))
		{
			statement.setString(1, clienteJuridico.getCnpj());
			
			try (ResultSet rs = statement.executeQuery()) {
				rs.next();
				cliente_id = rs.getInt("cliente_id");
			}
		} catch (Exception ex) {
			Logger.error(ex, MENSAGEM_DE_FALHA_AO_TENTAR_SALVAR_UM_ATENDIMENTO);
		} 

		return cliente_id;
	}

	public static List<Atendimento> buscarTodosAtendimentosClienteFisico(ClienteFisico clienteFisico) {
		ArrayList<Atendimento> atendimentos = new ArrayList<>();
		String sql = "SELECT * FROM atendimento WHERE cliente_id = ?";
		LocalDateTime data;
		String descricao;

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql))
		{
			statement.setInt(1, buscarIdDoClienteFisicoNaTabelaCliente(clienteFisico));
			
			try (ResultSet rs = statement.executeQuery())
			{
				while (rs.next()) {
					descricao = rs.getString("descricao");
					data = ((Timestamp) rs.getObject("data")).toLocalDateTime();
					Atendimento atendimento = new Atendimento(descricao, data);
					atendimentos.add(atendimento);
				}
			}

		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel montar a lista de atendimentos do cliente fisico.");
		} 

		return atendimentos;
	}

	public static List<Atendimento> buscarTodosAtendimentosClienteJuridico(ClienteJuridico clienteJuridico) {
		ArrayList<Atendimento> atendimentos = new ArrayList<>();
		String sql = "SELECT * FROM atendimento WHERE cliente_id = ?";
		LocalDateTime data;
		String descricao;

		try (Connection conexao = Conexao.abrirConexao(); PreparedStatement statement = conexao.prepareStatement(sql))
		{
			statement.setInt(1, buscarIdDoClienteJuridicoNaTabelaCliente(clienteJuridico));
			
			try (ResultSet rs = statement.executeQuery())
			{
				while (rs.next()) {
					descricao = rs.getString("descricao");
					data = ((Timestamp) rs.getObject("data")).toLocalDateTime();
					Atendimento atendimento = new Atendimento(descricao, data);
					atendimentos.add(atendimento);
				}
			}
		} catch (Exception ex) {
			Logger.error(ex, "Nao foi possivel montar a lista de atendimentos do cliente juridico.");
		} 

		return atendimentos;
	}
}
