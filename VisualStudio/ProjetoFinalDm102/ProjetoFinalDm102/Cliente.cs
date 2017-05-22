using System.Collections.Generic;

namespace ProjetoFinalDm102
{
    class Cliente
    {
        private string nome;
        private string endereco;
        private string telefone;

        private List<Atendimento> atendimentos = new List<Atendimento>();

        public Cliente(string nome, string endereco, string telefone)
        {
            this.nome = nome;
            this.endereco = endereco;
            this.telefone = telefone;
        }

        public string GetNome()
        {
            return nome;
        }

        public string GetEndereco()
        {
            return endereco;
        }

        public string GetTelefone()
        {
            return telefone;
        }

        public List<Atendimento> GetAtendimentos()
        {
            return atendimentos;
        }

        public void SalvarAtendimento(string descricao)
        {
            Atendimento atendimento = new Atendimento(descricao);
            atendimentos.Add(atendimento);
        }

        public override string ToString()
        {
            return "Nome: " + nome + " - Endereco: " + endereco + " - Telefone: " + telefone + " - Atendimentos: " + atendimentos.Count; 
        }

    }
}
