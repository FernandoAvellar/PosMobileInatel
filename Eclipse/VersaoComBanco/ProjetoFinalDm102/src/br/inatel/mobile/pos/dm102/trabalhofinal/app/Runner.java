package br.inatel.mobile.pos.dm102.trabalhofinal.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.pmw.tinylog.Logger;

import br.inatel.mobile.pos.dm102.trabalhofinal.dao.AtendimentoDAO;
import br.inatel.mobile.pos.dm102.trabalhofinal.dao.ClienteDAO;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.Atendimento;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.Cliente;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteFisico;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteJuridico;

public class Runner {

	private Runner() {
	}

	public static void main(String[] args) {
		exibeMenuInicial();
		String entrada = processaEntrada();

		while (!"sair".equals(entrada)) {
			switch (entrada) {
			case "cadastrarNovoClienteFisico":
				cadastrarNovoClienteFisico();
				exibeMenuInicial();
				entrada = processaEntrada();
				break;

			case "cadastrarNovoClienteJuridico":
				cadastrarNovoClienteJuridico();
				exibeMenuInicial();
				entrada = processaEntrada();
				break;

			case "exibirTodosClientes":
				exibirTodosClientes();
				exibeMenuInicial();
				entrada = processaEntrada();
				break;

			case "cadastrarAtendimento":
				menuCadastrarAtendimento();
				exibeMenuInicial();
				entrada = processaEntrada();
				break;

			case "listarAtendimentos":
				menuListarAtendimentos();
				exibeMenuInicial();
				entrada = processaEntrada();
				break;

			case "opcaoInvalida":
				pulaLinha();
				System.err.println("Opcao digitada invalida!");
				exibeMenuInicial();
				entrada = processaEntrada();
				break;
			}
		}

		System.exit(0);
	}

	private static void cadastrarNovoClienteFisico() {

		String nome = requisicaoDeEntradaDeDados("nome");
		String endereco = requisicaoDeEntradaDeDados("endereco");
		String telefone = requisicaoDeEntradaDeDados("telefone");
		String cpf = requisicaoDeEntradaDeDados("cpf");
		String identidade = requisicaoDeEntradaDeDados("identidade");
		String tipoDaIdentidade = requisicaoDeEntradaDeDados("tipo da identidade");
		Cliente clienteFisico = new ClienteFisico(nome, endereco, telefone, cpf, identidade, tipoDaIdentidade);
		ClienteDAO.salvar(clienteFisico);
	}

	private static void cadastrarNovoClienteJuridico() {
		String nome = requisicaoDeEntradaDeDados("nome");
		String endereco = requisicaoDeEntradaDeDados("endereco");
		String telefone = requisicaoDeEntradaDeDados("telefone");
		String cnpj = requisicaoDeEntradaDeDados("cnpj");
		String razaoSocial = requisicaoDeEntradaDeDados("razão social");
		String inscricaoEstadual = requisicaoDeEntradaDeDados("inscricao estadual");
		Cliente clienteJuridico = new ClienteJuridico(nome, endereco, telefone, cnpj, razaoSocial, inscricaoEstadual);
		ClienteDAO.salvar(clienteJuridico);
	}

	private static void exibirTodosClientes() {
		boolean foiExibido = false;

		List<Cliente> clientesFisicos = ClienteDAO.buscarTodosClientesFisicos();

		if (!clientesFisicos.isEmpty()) {
			pulaLinha();
			System.out.println("Clientes Fisicos cadastrados no sistema: ");
			for (Cliente cliente : clientesFisicos) {
				System.out.println(cliente);
			}
			pulaLinha();
			foiExibido = true;
		}

		List<Cliente> clientesJuridicos = ClienteDAO.buscarTodosClientesJuridicos();

		if (!clientesJuridicos.isEmpty()) {
			pulaLinha();
			System.out.println("Clientes Juridicos cadastrados no sistema: ");
			for (Cliente cliente : clientesJuridicos) {
				System.out.println(cliente);
			}
			pulaLinha();
			foiExibido = true;
		}

		if (!foiExibido) {
			pulaLinha();
			System.err.println("Nao existe cliente cadastrado no sistema!");
			pulaLinha();
		}
	}

	private static void menuCadastrarAtendimento() {
		exibeMenuDeCadastroDeAtendimento();
		String opcaoDeEntrada = leituraDoTeclado();

		while (!"x".equals(opcaoDeEntrada)) {
			switch (opcaoDeEntrada) {
			case "f":
				cadastrarAtendimentoClienteFisico();
				exibeMenuDeCadastroDeAtendimento();
				opcaoDeEntrada = leituraDoTeclado();
				break;
			case "j":
				cadastrarAtendimentoClienteJuridico();
				exibeMenuDeCadastroDeAtendimento();
				opcaoDeEntrada = leituraDoTeclado();
				break;
			case "x":
				break;
			default:
				pulaLinha();
				System.err.println("Opcao invalida, digite novamente!");
				exibeMenuDeCadastroDeAtendimento();
				opcaoDeEntrada = leituraDoTeclado();
				break;
			}
		}
	}

	private static void cadastrarAtendimentoClienteFisico() {
		pulaLinha();
		System.out.println("Digite o cpf da pessoa que sera atendida:");
		String cpf = leituraDoTeclado();
		ClienteFisico pessoaASerAtendida = ClienteDAO.buscarPorCpf(cpf);

		if (pessoaASerAtendida != null) {
			gerarESalvarAtendimentoClienteFisico(pessoaASerAtendida);
		} else {
			pulaLinha();
			System.err.println("CPF nao encontrado!");
		}
	}

	private static void cadastrarAtendimentoClienteJuridico() {
		pulaLinha();
		System.out.println("Digite o cnpj da empresa que sera atendida:");
		String cnpj = leituraDoTeclado();
		ClienteJuridico empresaASerAtendida = ClienteDAO.buscarPorCnpj(cnpj);

		if (empresaASerAtendida != null) {
			gerarESalvarAtendimentoClienteJuridico(empresaASerAtendida);
		} else {
			pulaLinha();
			System.err.println("CNPJ nao encontrado!");
		}
	}

	private static void gerarESalvarAtendimentoClienteFisico(ClienteFisico pessoaASerAtendida) {
		pulaLinha();
		System.out.println("Digite a descricao do atendimento para " + pessoaASerAtendida.getNome() + " :");
		String descricao = leituraDoTeclado();
		Atendimento atendimento = new Atendimento(descricao);
		AtendimentoDAO.salvarAtendimentoClienteFisico(pessoaASerAtendida, atendimento);
	}

	private static void gerarESalvarAtendimentoClienteJuridico(ClienteJuridico empresaASerAtendida) {
		pulaLinha();
		System.out.println(
				"Digite a descricao do atendimento para a empresa " + empresaASerAtendida.getRazaoSocial() + " :");
		String descricao = leituraDoTeclado();
		Atendimento atendimento = new Atendimento(descricao);
		AtendimentoDAO.salvarAtendimentoClienteJuridico(empresaASerAtendida, atendimento);
	}

	private static void menuListarAtendimentos() {
		exibeMenuDeListagemDeAtendimento();
		String opcaoDeEntrada = leituraDoTeclado();

		while (!"x".equals(opcaoDeEntrada)) {
			switch (opcaoDeEntrada) {
			case "f":
				listarAtendimentosParaPessoaFisica();
				exibeMenuDeListagemDeAtendimento();
				opcaoDeEntrada = leituraDoTeclado();
				break;
			case "j":
				listarAtendimentosParaPessoaJuridica();
				exibeMenuDeListagemDeAtendimento();
				opcaoDeEntrada = leituraDoTeclado();
				break;
			case "x":
				break;
			default:
				pulaLinha();
				System.err.println("Opcao Invalida, digite novamente!");
				exibeMenuDeListagemDeAtendimento();
				opcaoDeEntrada = leituraDoTeclado();
				break;
			}
		}
	}

	private static void listarAtendimentosParaPessoaFisica() {
		pulaLinha();
		System.out.println("Digite o cpf da pessoa que deseja listar os atendimentos:");
		String cpf = leituraDoTeclado();
		ClienteFisico pessoaASerAtendida = ClienteDAO.buscarPorCpf(cpf);

		if (pessoaASerAtendida != null) {
			imprimeTodosAtendimentosDeUmClienteFisico(pessoaASerAtendida);
		} else {
			pulaLinha();
			System.err.println("CPF nao encontrado!");
		}
	}

	private static void listarAtendimentosParaPessoaJuridica() {
		pulaLinha();
		System.out.println("Digite o cnpj da empresa que deseja listar os atendimentos:");
		String cnpj = leituraDoTeclado();
		ClienteJuridico empresaASerAtendida = ClienteDAO.buscarPorCnpj(cnpj);

		if (empresaASerAtendida != null) {
			imprimeTodosAtendimentosDeUmClienteJuridico(empresaASerAtendida);
		} else {
			pulaLinha();
			System.err.println("CNPJ nao encontrado!");
		}
	}

	private static void imprimeTodosAtendimentosDeUmClienteFisico(ClienteFisico clienteASerAtendido) {
		pulaLinha();
		System.out.println("Lista dos atendimentos para " + clienteASerAtendido.getNome() + " :");

		List<Atendimento> atendimentos = AtendimentoDAO.buscarTodosAtendimentosClienteFisico(clienteASerAtendido);

		if (atendimentos.isEmpty()) {
			System.out.println("Esse cliente ainda nao teve nenhum atendimento realizado!");
		} else {
			for (Atendimento atendimento : atendimentos) {
				System.out.println(atendimento);
			}
		}
	}

	private static void imprimeTodosAtendimentosDeUmClienteJuridico(ClienteJuridico empresaASerAtendida) {
		pulaLinha();
		System.out.println("Lista dos atendimentos para a empresa " + empresaASerAtendida.getRazaoSocial() + " :");

		List<Atendimento> atendimentos = AtendimentoDAO.buscarTodosAtendimentosClienteJuridico(empresaASerAtendida);

		if (atendimentos.isEmpty()) {
			System.out.println("Essa empresa ainda nao teve nenhum atendimento realizado!");
		} else {
			for (Atendimento atendimento : atendimentos) {
				System.out.println(atendimento);
			}
		}
	}

	private static String processaEntrada() {
		String entrada = leituraDoTeclado();

		if (entrada != null) {

			switch (entrada) {
			case "1":
				return "cadastrarNovoClienteFisico";
			case "2":
				return "cadastrarNovoClienteJuridico";
			case "3":
				return "exibirTodosClientes";
			case "4":
				return "cadastrarAtendimento";
			case "5":
				return "listarAtendimentos";
			case "6":
				return "sair";
			default:
				return "opcaoInvalida";
			}
		}

		return "opcaoInvalida";
	}

	private static void exibeMenuInicial() {
		pulaLinha();
		System.out.println("************************************************************");
		System.out.println("***** APP para Cadastro de Relacionamento com Clientes *****");
		System.out.println("***** Escolha abaixo a opcao desejada e tecle <Enter>  *****");
		System.out.println("************************************************************");
		System.out.println("1 - Cadastrar novo cliente fisico");
		System.out.println("2 - Cadastrar novo cliente juridico");
		System.out.println("3 - Exibir os clientes cadastrados");
		System.out.println("4 - Cadastrar um atendimento");
		System.out.println("5 - Listar todos atendimentos");
		System.out.println("6 - Sair do sistema");
	}

	private static void exibeMenuDeCadastroDeAtendimento() {
		pulaLinha();
		System.out.println("******************************************************************");
		System.out.println("***  Cadastro de atendimento para pessoas fisicas ou juridicas ***");
		System.out.println("***   Tecle (f) para pessoa fisica, (j) para pessoa juridica   ***");
		System.out.println("***           ou (x) para voltar ao menu inicial.              ***");
		System.out.println("******************************************************************");
		pulaLinha();
	}

	private static void exibeMenuDeListagemDeAtendimento() {
		pulaLinha();
		System.out.println("******************************************************************");
		System.out.println("***   Listar atendimento para pessoas fisicas ou juridicas     ***");
		System.out.println("***  Tecle (f) para pessoa fisica, (j) para pessoa juridica    ***");
		System.out.println("***        ou (x) para voltar ao menu inicial.                 ***");
		System.out.println("******************************************************************");
		pulaLinha();
	}

	private static String requisicaoDeEntradaDeDados(String nomeDoDadoASolicitar) {
		String entrada = null;

		while (entrada == null || "".equals(entrada)) {
			System.out.print("Digite o " + nomeDoDadoASolicitar + ":");
			entrada = leituraDoTeclado();
		}
		return entrada;
	}

	private static String leituraDoTeclado() {
		BufferedReader entradaLida = null;
		String resultadoDaLeitura = "falhaLeitura";

		try {
			entradaLida = new BufferedReader(new InputStreamReader(System.in));
			resultadoDaLeitura = entradaLida.readLine();
		} catch (IOException exception) {
			Logger.error(exception, "Falha na leitura do teclado.");
		}
		return resultadoDaLeitura;
	}

	private static void pulaLinha() {
		System.out.println();
	}

}
