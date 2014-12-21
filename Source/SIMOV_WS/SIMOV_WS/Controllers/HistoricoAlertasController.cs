using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using SIMOV_WS.Models;

namespace SIMOV_WS.Controllers
{
    [RoutePrefix("api/HistoricoAlertas")]
    public class HistoricoAlertasController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/HistoricoAlertas
        public List<HistoricoAlertas> GetHistoricoAlertas()
        {
            return db.HistoricoAlertas.Where(h => db.Pacientes.Count(p => (h.PacienteID == p.ID && p.Ativo)) > 0).ToList();
        }

        // GET: api/HistoricoAlertas/5
        [ResponseType(typeof(HistoricoAlertas))]
        public async Task<IHttpActionResult> GetHistoricoAlertas(int id)
        {
            HistoricoAlertas historicoAlertas = await db.HistoricoAlertas.FindAsync(id);
            if (historicoAlertas == null && db.Pacientes.Count(p => p.ID == historicoAlertas.PacienteID && p.Ativo) > 0)
            {
                return NotFound();
            }

            return Ok(historicoAlertas);
        }

        // PUT: api/HistoricoAlertas/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutHistoricoAlertas(int id, HistoricoAlertas historicoAlertas)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != historicoAlertas.Id)
            {
                return BadRequest();
            }

            var hist = await db.HistoricoAlertas.FindAsync(id);
            if (db.Pacientes.Count(p => p.ID == hist.PacienteID && p.Ativo) <= 0)
            {
                return NotFound();
            }

            db.Entry(historicoAlertas).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!HistoricoAlertasExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/HistoricoAlertas
        [ResponseType(typeof(HistoricoAlertas))]
        public async Task<IHttpActionResult> PostHistoricoAlertas(HistoricoAlertas historicoAlertas)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.HistoricoAlertas.Add(historicoAlertas);
            await db.SaveChangesAsync();

            return Ok(historicoAlertas.Id);
        }

        // DELETE: api/HistoricoAlertas/5
        [ResponseType(typeof(HistoricoAlertas))]
        public async Task<IHttpActionResult> DeleteHistoricoAlertas(int id)
        {
            HistoricoAlertas historicoAlertas = await db.HistoricoAlertas.FindAsync(id);
            if (historicoAlertas == null)
            {
                return NotFound();
            }

            db.HistoricoAlertas.Remove(historicoAlertas);
            await db.SaveChangesAsync();

            return Ok(historicoAlertas);
        }

        [HttpGet]
        [Route("ObterHistoricoAlertaPacientes/{id}")]
        public async Task<IQueryable<HistoricoAlertas>> ObterHistoricoAlertaPacientes(int id)
        {
            var pac = await db.Pacientes.FindAsync(id);
            if (pac == null || pac.Ativo)
            {
                return null;
            }

            return db.HistoricoAlertas.Where(h => h.PacienteID == id);
        }

        [HttpGet]
        [Route("GetHoraSincronizacao/{id}")]
        [ResponseType(typeof(DateTime))]
        public async Task<IHttpActionResult> GetHoraSincronizacao(int id)
        {
            HistoricoAlertas historico = await db.HistoricoAlertas.FindAsync(id);
            if (historico == null)
            {
                return NotFound();
            }

            return Ok(historico.HoraSincronizacao);
        }

        [HttpGet]
        [Route("GetAlertas/{responsavel}")]
        [ResponseType(typeof(List<HistoricoAlertas>))]
        public async Task<IHttpActionResult> GetAlertas(int responsavel)
        {
            var alertas = db.HistoricoAlertas.Where(a => db.Pacientes.FirstOrDefault(p => a.PacienteID == p.ID && p.Responsavel_ID == responsavel) != null);
            if (alertas == null)
            {
                return NotFound();
            }

            return Ok(alertas.ToList());
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool HistoricoAlertasExists(int id)
        {
            return db.HistoricoAlertas.Count(e => e.Id == id) > 0;
        }
    }
}