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
        public DateTime Data { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
        public string Local { get; set; }
        public DateTime HoraSincronizacao { get; set; }
        public Nullable<int> PacienteID { get; set; }
        public int EstadoMarcacaoId { get; set; }
    }
}
