using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace SIMOV_WS.Models
{
    public class Paciente
    {
        public Paciente()
        {
            this.HistoricoAlertas = new HashSet<HistoricoAlertas>();
            this.Marcacoes = new HashSet<Marcacao>();
        }

        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ID { get; set; }
        public string Nome { get; set; }
        public string Apelido { get; set; }
        public string Email { get; set; }
        public string ContactoTlf { get; set; }
        public float Longitude { get; set; }
        public float Latitude { get; set; }
        public string NomeLocal { get; set; }
        public DateTime? Data { get; set; }
        public bool Ativo { get; set; }
        public DateTime HoraSincronizacao { get; set; }
        public int Responsavel_ID { get; set; }

        public virtual ICollection<HistoricoAlertas> HistoricoAlertas { get; set; }
        public virtual ICollection<Marcacao> Marcacoes { get; set; }
    }
}