using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace SIMOV_WS.Models
{
    public class Marcacao
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public string TipoMarcacao { get; set; }
        public System.DateTime Data { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
        public string Local { get; set; }
        public System.DateTime HoraSincronizacao { get; set; }
        public Nullable<int> PacientesID { get; set; }
        public int EstadoMarcacaoId { get; set; }

        public virtual Paciente Pacientes { get; set; }
        public virtual EstadoMarcacao EstadoMarcacao { get; set; }
    }
}
