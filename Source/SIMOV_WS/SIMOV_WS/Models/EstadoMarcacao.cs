using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace SIMOV_WS.Models
{

    public partial class EstadoMarcacao
    {
        public EstadoMarcacao()
        {
            this.Marcacao = new HashSet<Marcacao>();
        }

        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public string Designacao { get; set; }
        public DateTime HoraSincronizacao { get; set; }

        public virtual ICollection<Marcacao> Marcacao { get; set; }
    }
}
