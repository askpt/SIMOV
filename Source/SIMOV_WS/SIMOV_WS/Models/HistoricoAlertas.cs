﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace SIMOV_WS.Models
{
    public class HistoricoAlertas
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public System.DateTime Data { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
        public string Local { get; set; }
        public System.DateTime HoraSincronizacao { get; set; }
        public Nullable<int> AlertaId { get; set; }
        public Nullable<int> PacientesID { get; set; }

        public virtual Alerta Alerta { get; set; }
        public virtual Paciente Pacientes { get; set; }
    }
}