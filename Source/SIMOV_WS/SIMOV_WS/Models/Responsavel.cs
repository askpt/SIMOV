﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace SIMOV_WS.Models
{
    public class Responsavel
    {
        public Responsavel()
        {
            this.Pacientes = new HashSet<Paciente>();
        }

        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ID { get; set; }
        public string Nome { get; set; }
        public string Apelido { get; set; }
        public string ContactoTlf { get; set; }
        public bool NotifMail { get; set; }
        public bool NotifSms { get; set; }
        public int PeriodDiurna { get; set; }
        public int PeriodNoturna { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public DateTime HoraSincronizacao { get; set; }

        public virtual ICollection<Paciente> Pacientes { get; set; }
    }
}