package br.inatel.mobile.pos.dm102.trabalhofinal.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.pmw.tinylog.Logger;

import br.inatel.mobile.pos.dm102.trabalhofinal.dao.AtendimentoDAO;
import br.inatel.mobile.pos.dm102.trabalhofinal.dao.ClienteFisicoDAO;
import br.inatel.mobile.pos.dm102.trabalhofinal.dao.ClienteJuridicoDAO;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.Atendimento;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.Cliente;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteFisico;
import br.inatel.mobile.pos.dm102.trabalhofinal.model.ClienteJuridico;

public class Runner
{

    private Runner()
    {
    }

    public static void main(String[] args)
    {

        exibeMenuInicial();
        String entrada = processaEntrada();

        while (!"sair".equals(entrada))
        {

            switch (entrada)
            {
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
                    System.out.println("Opcao digitada invalida!");
                    exibeMenuInicial();
                    entrada = processaEntrada();
                    break;
            }
        }

        System.exit(0);
    }

    private static void menuListarAtendimentos()
    {
        exibeMenuDeAtendimento();
        String opcaoDeEntrada = leituraDoTeclado();

        switch (opcaoDeEntrada)
        {
            case "f":
                listarAtendimentosParaPessoaFisica();
                break;
            case "j":
                listarAtendimentosParaPessoaJuridica();
                break;

            default:
                System.out.println("Opcao Invalida!");
                break;
        }

    }

    private static void listarAtendimentosParaPessoaFisica()
    {
        pulaLinha();
        System.out.println("Digite o cpf da pessoa que deseja listar os atendimentos:");
        String cpf = leituraDoTeclado();
        ClienteFisico pessoaASerAtendida = ClienteFisicoDAO.buscarPorCpf(cpf);

        if (pessoaASerAtendida != null)
        {
            listarAtendimentosClienteFisico(pessoaASerAtendida);
        }
        else
        {
            pulaLinha();
            System.out.println("CPF nao encontrado!");
        }
    }

    private static void listarAtendimentosClienteFisico(ClienteFisico clienteASerAtendido)
    {
        pulaLinha();
        System.out.println("Lista dos atendimentos para " + clienteASerAtendido.getNome() + " :");

        List<Atendimento> atendimentos = AtendimentoDAO.buscarTodosAtendimentosClienteFisico(clienteASerAtendido);

        if (atendimentos.isEmpty())
        {
            System.out.println("Esse cliente ainda nao teve nenhum atendimento realizado!");
        }
        else
        {
            for (Atendimento atendimento : atendimentos)
            {
                System.out.println(atendimento);
            }
        }
    }

    private static void listarAtendimentosParaPessoaJuridica()
    {
        pulaLinha();
        System.out.println("Digite o cnpj da empresa que deseja listar os atendimentos:");
        String cnpj = leituraDoTeclado();
        ClienteJuridico empresaASerAtendida = ClienteJuridicoDAO.buscarPorCnpj(cnpj);

        if (empresaASerAtendida != null)
        {
            listarAtendimentosClienteJuridico(empresaASerAtendida);
        }
        else
        {
            pulaLinha();
            System.out.println("CNPJ nao encontrado!");
        }
    }

    private static void listarAtendimentosClienteJuridico(ClienteJuridico empresaASerAtendida)
    {
        pulaLinha();
        System.out.println("Lista dos atendimentos para a empresa " + empresaASerAtendida.getRazaoSocial() + " :");

        List<Atendimento> atendimentos = AtendimentoDAO
                .buscarTodosAtendimentosClienteJuridico(empresaASerAtendida);

        if (atendimentos.isEmpty())
        {
            System.out.println("Essa empresa ainda nao teve nenhum atendimento realizado!");
        }
        else
        {
            for (Atendimento atendimento : atendimentos)
            {
                System.out.println(atendimento);
            }
        }
    }

    private static void menuCadastrarAtendimento()
    {
        exibeMenuDeAtendimento();
        String opcaoDeEntrada = leituraDoTeclado();

        switch (opcaoDeEntrada)
        {
            case "f":
                cadastrarAtendimentoPessoaFisica();
                break;
            case "j":
                cadastrarAtendimentoPessoaJuridica();
                break;

            default:
                System.out.println("Opcao invalida!");
                break;
        }
    }

    private static void cadastrarAtendimentoPessoaFisica()
    {
        pulaLinha();
        System.out.println("Digite o cpf da pessoa que sera atendida:");
        String cpf = leituraDoTeclado();
        ClienteFisico pessoaASerAtendida = ClienteFisicoDAO.buscarPorCpf(cpf);

        if (pessoaASerAtendida != null)
        {
            gerarESalvarAtendimentoClienteFisico(pessoaASerAtendida);
        }
        else
        {
            pulaLinha();
            System.out.println("CPF nao encontrado!");
        }

    }

    private static void cadastrarAtendimentoPessoaJuridica()
    {
        pulaLinha();
        System.out.println("Digite o cnpj da empresa que sera atendida:");
        String cnpj = leituraDoTeclado();
        ClienteJuridico empresaASerAtendida = ClienteJuridicoDAO.buscarPorCnpj(cnpj);

        if (empresaASerAtendida != null)
        {
            gerarESalvarAtendimentoPessoaJuridica(empresaASerAtendida);
        }
        else
        {
            pulaLinha();
            System.out.println("CNPJ nao encontrado!");
        }

    }

    private static void gerarESalvarAtendimentoClienteFisico(ClienteFisico pessoaASerAtendida)
    {
        pulaLinha();
        System.out.println("Digite a descricao do atendimento para " + pessoaASerAtendida.getNome() + " :");
        String descricao = leituraDoTeclado();
        Atendimento atendimento = new Atendimento(descricao);
        AtendimentoDAO.salvarAtendimentoClienteFisico(pessoaASerAtendida, atendimento);
    }

    private static void gerarESalvarAtendimentoPessoaJuridica(ClienteJuridico empresaASerAtendida)
    {
        pulaLinha();
        System.out.println("Digite a descricao do atendimento para a empresa " + empresaASerAtendida.getRazaoSocial() + " :");
        String descricao = leituraDoTeclado();
        Atendimento atendimento = new Atendimento(descricao);
        AtendimentoDAO.salvarAtendimentoClienteJuridico(empresaASerAtendida, atendimento);
    }

    private static void pulaLinha()
    {
        System.out.println();
    }

    private static void exibeMenuDeAtendimento()
    {
        pulaLinha();
        System.out.println("******************************************************************");
        System.out.println("***       Atendimento para pessoas fisicas ou juridicas        ***");
        System.out.println("***  Tecle (f) para pessoa fisica ou (j) para pessoa juridica  ***");
        System.out.println("******************************************************************");
        pulaLinha();
    }

    private static void exibirTodosClientes()
    {

        boolean foiExibido = false;

        List<Cliente> clientesFisicos = ClienteFisicoDAO.buscarTodosClientesFisicos();

        if (!clientesFisicos.isEmpty())
        {
            pulaLinha();
            System.out.println("Clientes Fisicos cadastrados no sistema: ");
            for (Cliente cliente : clientesFisicos)
            {
                System.out.println(cliente);
            }
            pulaLinha();
            foiExibido = true;
        }

        List<Cliente> clientesJuridicos = ClienteJuridicoDAO.buscarTodosClientesJuridicos();

        if (!clientesJuridicos.isEmpty())
        {
            pulaLinha();
            System.out.println("Clientes Juridicos cadastrados no sistema: ");
            for (Cliente cliente : clientesJuridicos)
            {
                System.out.println(cliente);
            }
            pulaLinha();
            foiExibido = true;
        }

        if (!foiExibido)
        {
            pulaLinha();
            System.out.println("Nao existe cliente cadastrado no sistema!");
            pulaLinha();
        }

    }

    private static void cadastrarNovoClienteJuridico()
    {

        System.out.println("Digite o nome: ");
        String nome = leituraDoTeclado();

        System.out.println("Digite o endereco: ");
        String endereco = leituraDoTeclado();

        System.out.println("Digite o telefone: ");
        String telefone = leituraDoTeclado();

        System.out.println("Digite o cnpj: ");
        String cnpj = leituraDoTeclado();

        System.out.println("Digite a razao social: ");
        String razaoSocial = leituraDoTeclado();

        System.out.println("Digite a inscricao estadual: ");
        String inscricaoEstadual = leituraDoTeclado();

        Cliente clienteJuridico = new ClienteJuridico(nome, endereco, telefone, cnpj, razaoSocial, inscricaoEstadual);

        // clientesJuridicos.add(clienteJuridico);
        ClienteJuridicoDAO.salvar(clienteJuridico);
    }

    private static void cadastrarNovoClienteFisico()
    {

        System.out.println("Digite o nome: ");
        String nome = leituraDoTeclado();

        System.out.println("Digite o endereco: ");
        String endereco = leituraDoTeclado();

        System.out.println("Digite o telefone: ");
        String telefone = leituraDoTeclado();

        System.out.println("Digite o cpf: ");
        String cpf = leituraDoTeclado();

        System.out.println("Digite o identidade: ");
        String identidade = leituraDoTeclado();

        System.out.println("Digite o tipo da identidade: ");
        String tipoDaIdentidade = leituraDoTeclado();

        Cliente clienteFisico = new ClienteFisico(nome, endereco, telefone, cpf, identidade, tipoDaIdentidade);

        // clientesFisicos.add(clienteFisico);
        ClienteFisicoDAO.salvar(clienteFisico);
    }

    private static String leituraDoTeclado()
    {

        BufferedReader entradaLida;
        String resultadoDaLeitura = "falhaLeitura";

        try
        {
            entradaLida = new BufferedReader(new InputStreamReader(System.in));
            resultadoDaLeitura = entradaLida.readLine();

        }
        catch (IOException exception)
        {
            Logger.error(exception, "Falha na leitura do teclado.");
        }

        return resultadoDaLeitura;
    }

    private static String processaEntrada()
    {

        String entrada = leituraDoTeclado();

        if (entrada != null)
        {

            switch (entrada)
            {
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

    private static void exibeMenuInicial()
    {
        pulaLinha();
        System.out.println("************************************************************");
        System.out.println("***** APP para Cadastro de Relacionamento com Clientes *****");
        System.out.println("***** Escolha abaixo a opcao desejada e tecle <Enter>  *****");
        System.out.println("************************************************************");
        System.out.println("1 - Cadastrar novo cliente fisico");
        System.out.println("2 - Cadastrar novo cliente juridico");
        System.out.println("3 - Exibir todos os clientes cadastrados");
        System.out.println("4 - Cadastrar um atendimento");
        System.out.println("5 - Listar todos atendimentos");
        System.out.println("6 - Sair do sistema");
    }

}
