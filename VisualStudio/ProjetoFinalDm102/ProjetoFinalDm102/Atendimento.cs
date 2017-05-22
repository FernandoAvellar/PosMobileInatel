using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProjetoFinalDm102
{
    class Atendimento
    {
        private string descricao;
        private DateTime data;

        public Atendimento(string descricao)
        {
            this.descricao = descricao;
            this.data = DateTime.Now;
        }

        public override string ToString()
        {
            CultureInfo culture = new CultureInfo("pt-br");
            return "Atendimento realizado em: " + data.ToString("f", culture) + " -- Descricao: " + descricao;
        }
    }
}