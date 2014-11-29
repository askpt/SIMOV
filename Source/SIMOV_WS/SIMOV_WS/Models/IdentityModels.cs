using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;
using System.Data.Entity;

namespace SIMOV_WS.Models
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext() : base("DefaultConnection")
        {
        }

        public DbSet<Paciente> Pacientes { get; set; }
        public DbSet<Responsavel> Responsaveis { get; set; }
        public DbSet<Alerta> Alertas { get; set; }
        public DbSet<EstadoMarcacao> EstadosMarcacao { get; set; }
        public DbSet<HistoricoAlertas> HistoricoAlertas { get; set; }
        public DbSet<Marcacao> Marcacoes { get; set; }
    }
}