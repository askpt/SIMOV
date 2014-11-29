using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace SIMOV_WS.Models
{
    public class Paciente
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ID { get; set; }
        public string Nome { get; set; }
        public string Apelido { get; set; }
        [EmailAddress]
        public string Email { get; set; }
        public string ContactoTlf { get; set; }
        public float Longitude { get; set; }
        public float Latitude { get; set; }
        public string NomeLocal { get; set; }
        public DateTime DataLocal { get; set; }
        public bool Ativo { get; set; }
        public DateTime HoraSincronizacao { get; set; }
    }
}