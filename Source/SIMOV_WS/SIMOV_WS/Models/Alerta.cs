using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace SIMOV_WS.Models
{

    public partial class Alerta
    {
        public Alerta()
        {
            this.HistoricoAlertas = new HashSet<HistoricoAlertas>();
        }

        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public string Designacao { get; set; }
        public string HoraSincronizacao { get; set; }

        public virtual ICollection<HistoricoAlertas> HistoricoAlertas { get; set; }
    }
}
