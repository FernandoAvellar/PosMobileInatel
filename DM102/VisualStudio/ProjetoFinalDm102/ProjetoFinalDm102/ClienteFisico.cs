using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjetoFinalDm102
{
    class ClienteFisico : Cliente
    {
        private string cpf;
        private string identidade;
        private string tipoDeIdentidade;

        public ClienteFisico(string nome, string endereco, string telefone, string cpf, string identidade, string tipoDeIdentidade) : base(nome, endereco, telefone)
        {
            this.cpf = cpf;
            this.identidade = identidade;
            this.tipoDeIdentidade = tipoDeIdentidade;
        }

        public string GetCpf()
        {
            return cpf;
        }

        public string GetIdentidade()
        {
            return identidade;
        }

        public string GetTipoDeIdentidade()
        {
            return tipoDeIdentidade;
        }

        public override string ToString()
        {
            return base.ToString() + " - CPF: "+ cpf + " - Identidade: " + identidade + " - Tipo de Identidade: " + tipoDeIdentidade;
        }

    }
}
