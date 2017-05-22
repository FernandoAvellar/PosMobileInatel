using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjetoFinalDm102
{
    class Program
    {   
        //Variaveis para definir o tamanho da tela Console
        const int WIDTH = 165;
        const int HEIGHT = 40;

        private static List<ClienteFisico> clientesFisicos = new List<ClienteFisico>();
        private static List<ClienteJuridico> clientesJuridicos = new List<ClienteJuridico>();

        static void Main(string[] args) {

            ConsoleSetup();
            ExibeMenuInicial();
            string entrada = ProcessaEntrada();

            while (!"sair".Equals(entrada))
            {
                switch (entrada)
                {
                    case "cadastrarNovoClienteFisico":
                        CadastrarNovoClienteFisico();
                        ExibeMenuInicial();
                        entrada = ProcessaEntrada();
                        break;

                    case "cadastrarNovoClienteJuridico":
                        CadastrarNovoClienteJuridico();
                        ExibeMenuInicial();
                        entrada = ProcessaEntrada();
                        break;

                    case "exibirTodosClientes":
                        ExibirTodosClientes();
                        ExibeMenuInicial();
                        entrada = ProcessaEntrada();
                        break;

                    case "cadastrarAtendimento":
                        MenuCadastrarAtendimento();
                        ExibeMenuInicial();
                        entrada = ProcessaEntrada();
                        break;

                    case "listarAtendimentos":
                        MenuListarAtendimentos();
                        ExibeMenuInicial();
                        entrada = ProcessaEntrada();
                        break;

                    case "opcaoInvalida":
                        Console.WriteLine("Opcao digitada invalida, tecle <Enter> para tentar novamente!");
                        Console.ReadLine();
                        ExibeMenuInicial();
                        entrada = ProcessaEntrada();
                        break;

                     default:
                        Console.WriteLine("Opcao digitada invalida, tecle <Enter> para tentar novamente!");
                        Console.ReadLine();
                        ExibeMenuInicial();
                        entrada = ProcessaEntrada();
                        break;
                }
            }

            Environment.Exit(0);
        }

        private static void CadastrarNovoClienteFisico()
        {

            string nome = RequisicaoDeEntradaDeDados("nome");
            string endereco = RequisicaoDeEntradaDeDados("endereco");
            string telefone = RequisicaoDeEntradaDeDados("telefone");
            string cpf = RequisicaoDeEntradaDeDados("cpf");
            string identidade = RequisicaoDeEntradaDeDados("identidade");
            string tipoDaIdentidade = RequisicaoDeEntradaDeDados("tipo da identidade");
            ClienteFisico clienteFisico = new ClienteFisico(nome, endereco, telefone, cpf, identidade, tipoDaIdentidade);
            clientesFisicos.Add(clienteFisico);
        }

        private static void CadastrarNovoClienteJuridico()
        {
            string nome = RequisicaoDeEntradaDeDados("nome");
            string endereco = RequisicaoDeEntradaDeDados("endereco");
            string telefone = RequisicaoDeEntradaDeDados("telefone");
            string cnpj = RequisicaoDeEntradaDeDados("cnpj");
            string razaoSocial = RequisicaoDeEntradaDeDados("razao social");
            string inscricaoEstadual = RequisicaoDeEntradaDeDados("inscricao estadual");
            ClienteJuridico clienteJuridico = new ClienteJuridico(nome, endereco, telefone, cnpj, razaoSocial, inscricaoEstadual);
            clientesJuridicos.Add(clienteJuridico);
        }

        private static void ExibirTodosClientes()
        {
            bool foiExibido = false;

            ExibeMenuDeExibicaoDeClientesCadastrados();

            if (clientesFisicos.Count() != 0)
            {
                PulaLinha();
                Console.WriteLine("Clientes Fisicos cadastrados no sistema: ");
                foreach(ClienteFisico cliente in clientesFisicos)
                {
                    Console.WriteLine(cliente);
                }
                PulaLinha();
                foiExibido = true;
            }

            if (clientesJuridicos.Count() != 0)
            {
                PulaLinha();
                Console.WriteLine("Clientes Juridicos cadastrados no sistema: ");
                foreach(Cliente cliente in clientesJuridicos)
                {
                    Console.WriteLine(cliente);
                }
                PulaLinha();
                foiExibido = true;
            }

            if (!foiExibido)
            {
                PulaLinha();
                Console.WriteLine("Nao existe cliente cadastrado no sistema!");
            }

            PulaLinha();
            Console.WriteLine("Tecle <Enter> para voltar ao Menu Principal!");
            Console.ReadLine();
        }

        private static void MenuCadastrarAtendimento()
        {
            ExibeMenuDeCadastroDeAtendimento();
            string opcaoDeEntrada = LeituraDoTeclado();

            while (!"x".Equals(opcaoDeEntrada))
            {
                switch (opcaoDeEntrada)
                {
                    case "f":
                        CadastrarAtendimentoClienteFisico();
                        ExibeMenuDeCadastroDeAtendimento();
                        opcaoDeEntrada = LeituraDoTeclado();
                        break;
                    case "j":
                        CadastrarAtendimentoClienteJuridico();
                        ExibeMenuDeCadastroDeAtendimento();
                        opcaoDeEntrada = LeituraDoTeclado();
                        break;
                    case "x":
                        break;
                    default:
                        PulaLinha();
                        Console.WriteLine("Opcao invalida, digite novamente!");
                        ExibeMenuDeCadastroDeAtendimento();
                        opcaoDeEntrada = LeituraDoTeclado();
                        break;
                }
            }
        }

        private static void CadastrarAtendimentoClienteFisico()
        {
            PulaLinha();
            Console.WriteLine("Digite o cpf da pessoa que sera atendida:");
            string cpf = LeituraDoTeclado();
            ClienteFisico pessoaASerAtendida = BuscarClienteFisicoPorCpf(cpf);

            if (pessoaASerAtendida != null)
            {
                GerarESalvarAtendimento(pessoaASerAtendida);
            }
            else
            {
                PulaLinha();
                Console.WriteLine("CPF nao encontrado!");
                Console.WriteLine("Tecle <Enter> para voltar ao Menu!");
                Console.ReadLine();
            }
        }

        private static void CadastrarAtendimentoClienteJuridico()
        {
            PulaLinha();
            Console.WriteLine("Digite o cnpj da empresa que sera atendida:");
            string cnpj = LeituraDoTeclado();
            ClienteJuridico empresaASerAtendida = BuscarClienteJuridicoPorCnpj(cnpj);

            if (empresaASerAtendida != null)
            {
                GerarESalvarAtendimento(empresaASerAtendida);
            }
            else
            {
                PulaLinha();
                Console.WriteLine("CNPJ nao encontrado!");
                Console.WriteLine("Tecle <Enter> para voltar ao Menu!");
                Console.ReadLine();
            }
        }

        private static void GerarESalvarAtendimento(Cliente pessoaASerAtendida)
        {
            PulaLinha();
            if(pessoaASerAtendida is ClienteFisico)
            {
                Console.WriteLine("Digite a descricao do atendimento para " + pessoaASerAtendida.GetNome() + ":");
            }    
            else
            {
                Console.WriteLine("Digite a descricao do atendimento para " + ((ClienteJuridico) pessoaASerAtendida).GetRazaoSocial() + ":");
            }

            string descricao = LeituraDoTeclado();
            pessoaASerAtendida.SalvarAtendimento(descricao);
        }

        private static void MenuListarAtendimentos()
        {
            ExibeMenuDeListagemDeAtendimento();
            String opcaoDeEntrada = LeituraDoTeclado();

            while (!"x".Equals(opcaoDeEntrada))
            {
                switch (opcaoDeEntrada)
                {
                    case "f":
                        ListarAtendimentosParaPessoaFisica();
                        ExibeMenuDeListagemDeAtendimento();
                        opcaoDeEntrada = LeituraDoTeclado();
                        break;
                    case "j":
                        ListarAtendimentosParaPessoaJuridica();
                        ExibeMenuDeListagemDeAtendimento();
                        opcaoDeEntrada = LeituraDoTeclado();
                        break;
                    case "x":
                        break;
                    default:
                        PulaLinha();
                        Console.WriteLine("Opcao Invalida, digite novamente!");
                        Console.ReadLine();
                        ExibeMenuDeListagemDeAtendimento();
                        opcaoDeEntrada = LeituraDoTeclado();
                        break;
                }
            }
        }

        private static void ListarAtendimentosParaPessoaFisica()
        {
            PulaLinha();
            Console.WriteLine("Digite o cpf da pessoa que deseja listar os atendimentos:");
            string cpf = LeituraDoTeclado();
            ClienteFisico pessoaASerAtendida = BuscarClienteFisicoPorCpf(cpf);

            if (pessoaASerAtendida != null)
            {
                ImprimeTodosAtendimentosPara(pessoaASerAtendida);
            }
            else
            {
                PulaLinha();
                Console.WriteLine("CPF nao encontrado!");
                PulaLinha();
                Console.WriteLine("Tecle <Enter> para voltar ao Menu!");
                Console.ReadLine();
            }         
        }

        private static void ListarAtendimentosParaPessoaJuridica()
        {
            PulaLinha();
            Console.WriteLine("Digite o cnpj da empresa que deseja listar os atendimentos:");
            String cnpj = LeituraDoTeclado();
            ClienteJuridico empresaASerAtendida = BuscarClienteJuridicoPorCnpj(cnpj);

            if (empresaASerAtendida != null)
            {
                ImprimeTodosAtendimentosPara(empresaASerAtendida);
            }
            else
            {
                PulaLinha();
                Console.WriteLine("CNPJ nao encontrado!");
                PulaLinha();
                Console.WriteLine("Tecle <Enter> para voltar ao Menu!");
                Console.ReadLine();
            }        
        }

        private static void ImprimeTodosAtendimentosPara(Cliente clienteASerAtendido)
        {
            PulaLinha();
            if (clienteASerAtendido is ClienteFisico)
            {
                Console.WriteLine("Lista dos atendimentos para " + clienteASerAtendido.GetNome() + ":");
            }
            else
            {
                Console.WriteLine("Lista dos atendimentos para " + ((ClienteJuridico) clienteASerAtendido).GetRazaoSocial() + ":");
            }

            List<Atendimento> atendimentos = clienteASerAtendido.GetAtendimentos();

            if (atendimentos.Count() == 0)
            {
                Console.WriteLine("Esse cliente ainda nao teve nenhum atendimento realizado!");
            }
            else
            {
                foreach(Atendimento atendimento in atendimentos)
                {
                    Console.WriteLine(atendimento);
                }
            }

            PulaLinha();
            Console.WriteLine("Tecle <Enter> para voltar ao Menu!");
            Console.ReadLine();
        }

        private static ClienteFisico BuscarClienteFisicoPorCpf(string cpf)
        {
            foreach(ClienteFisico cliente in clientesFisicos)
            {
                if(cliente.GetCpf().Equals(cpf))
                {
                    return cliente;
                }
            }

            return null;
        }

        private static ClienteJuridico BuscarClienteJuridicoPorCnpj(string cnpj)
        {
            foreach (ClienteJuridico cliente in clientesJuridicos)
            {
                if (cliente.GetCnpj().Equals(cnpj))
                {
                    return cliente;
                }
            }

            return null;
        }

        private static string ProcessaEntrada()
        {
            string entrada = LeituraDoTeclado();

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

        private static void ExibeMenuInicial()
        {
            Console.Clear();
            Console.WriteLine("************************************************************");
            Console.WriteLine("***** APP para Cadastro de Relacionamento com Clientes *****");
            Console.WriteLine("***** Escolha abaixo a opcao desejada e tecle <Enter>  *****");
            Console.WriteLine("************************************************************");
            Console.WriteLine("1 - Cadastrar novo cliente fisico");
            Console.WriteLine("2 - Cadastrar novo cliente juridico");
            Console.WriteLine("3 - Exibir os clientes cadastrados");
            Console.WriteLine("4 - Cadastrar um atendimento");
            Console.WriteLine("5 - Listar todos atendimentos");
            Console.WriteLine("6 - Sair do sistema");
        }

        private static void ExibeMenuDeCadastroDeAtendimento()
        {
            Console.Clear();
            Console.WriteLine("******************************************************************");
            Console.WriteLine("***  Cadastro de atendimento para pessoas fisicas ou juridicas ***");
            Console.WriteLine("***   Tecle (f) para pessoa fisica, (j) para pessoa juridica   ***");
            Console.WriteLine("***           ou (x) para voltar ao menu inicial.              ***");
            Console.WriteLine("******************************************************************");
        }

        private static void ExibeMenuDeListagemDeAtendimento()
        {
            Console.Clear();
            Console.WriteLine("******************************************************************");
            Console.WriteLine("***   Listar atendimento para pessoas fisicas ou juridicas     ***");
            Console.WriteLine("***  Tecle (f) para pessoa fisica, (j) para pessoa juridica    ***");
            Console.WriteLine("***        ou (x) para voltar ao menu inicial.                 ***");
            Console.WriteLine("******************************************************************");
        }

        private static void ExibeMenuDeExibicaoDeClientesCadastrados()
        {
            Console.Clear();
            Console.WriteLine("******************************************************************");
            Console.WriteLine("***                                                            ***");
            Console.WriteLine("***           Listagem dos clientes cadastrados                ***");
            Console.WriteLine("***                                                            ***");
            Console.WriteLine("******************************************************************");
        }

        private static string RequisicaoDeEntradaDeDados(string nomeDoDadoASolicitar)
        {
            Console.WriteLine("Digite o " + nomeDoDadoASolicitar + ":");
            return LeituraDoTeclado();
        }

        private static string LeituraDoTeclado()
        {
            string dadoLido = null;

            while (dadoLido == null || "".Equals(dadoLido))
            {
                dadoLido = Console.ReadLine();
                if("".Equals(dadoLido))
                {
                    Console.WriteLine("Entrada vazia, tente novamente!");
                }
            }

            return dadoLido;
        }

        private static void PulaLinha()
        {
            Console.WriteLine();
        }

        private static void ConsoleSetup()
        {
            Console.SetWindowSize(WIDTH, HEIGHT);
        }

    }
}
