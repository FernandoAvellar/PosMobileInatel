using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjetoFinalDm102
{
    class ClienteJuridico : Cliente
    {
        private string cnpj;
        private string razaoSocial;
        private string inscricaoEstadual;

        public ClienteJuridico(string nome, string endereco, string telefone, string cnpj, string razaoSocial, string inscricaoEstadual) : base(nome, endereco, telefone)
        {
            this.cnpj = cnpj;
            this.razaoSocial = razaoSocial;
            this.inscricaoEstadual = inscricaoEstadual;
        }

        public string GetCnpj()
        {
            return cnpj;
        }

        public string GetRazaoSocial()
        {
            return razaoSocial;
        }

        public string GetInscricaoEstadual()
        {
            return inscricaoEstadual;
        }

        public override string ToString()
        {
            return base.ToString() + " - Cnpj: " + cnpj + " - Razao Social: " + razaoSocial + " - Inscricao Estadual: " + inscricaoEstadual;
        }
    }
}
