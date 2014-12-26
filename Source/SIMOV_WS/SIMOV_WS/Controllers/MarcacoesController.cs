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
    [RoutePrefix("api/Marcacoes")]
    public class MarcacoesController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Marcacoes
        public List<Marcacao> GetMarcacoes()
        {
            return db.Marcacoes.Where(m => db.Pacientes.Count(p => (m.PacienteID == p.ID && p.Ativo)) > 0).ToList();
        }

        // GET: api/Marcacoes/5
        [ResponseType(typeof(Marcacao))]
        public async Task<IHttpActionResult> GetMarcacao(int id)
        {
            Marcacao marcacao = await db.Marcacoes.FindAsync(id);
            if (marcacao == null && db.Pacientes.Count(p => p.ID == marcacao.PacienteID && p.Ativo) > 0)
            {
                return NotFound();
            }

            return Ok(marcacao);
        }

        // PUT: api/Marcacoes/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutMarcacao(int id, Marcacao marcacao)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != marcacao.Id)
            {
                return BadRequest();
            }

            db.Entry(marcacao).State = EntityState.Modified;

            var marc = await db.Marcacoes.FindAsync(id);
            if (db.Pacientes.Count(p => p.ID == marc.PacienteID && p.Ativo) <= 0)
            {
                return NotFound();
            }

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!MarcacaoExists(id))
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

        // POST: api/Marcacoes
        [ResponseType(typeof(Marcacao))]
        public async Task<IHttpActionResult> PostMarcacao(Marcacao marcacao)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Marcacoes.Add(marcacao);
            await db.SaveChangesAsync();

            return Ok(marcacao.Id);
        }

        // DELETE: api/Marcacoes/5
        [ResponseType(typeof(Marcacao))]
        public async Task<IHttpActionResult> DeleteMarcacao(int id)
        {
            Marcacao marcacao = await db.Marcacoes.FindAsync(id);
            if (marcacao == null)
            {
                return NotFound();
            }

            db.Marcacoes.Remove(marcacao);
            await db.SaveChangesAsync();

            return Ok(marcacao);
        }

        [HttpGet]
        [Route("ObterMarcacoesPacientes/{id}")]
        public async Task<IQueryable<Marcacao>> ObterMarcacoesPacientes(int id)
        {
            var pac = await db.Pacientes.FindAsync(id);
            if (pac == null || pac.Ativo)
            {
                return null;
            }

            return db.Marcacoes.Where(h => h.PacienteID == id);
        }

        [HttpGet]
        [Route("GetHoraSincronizacao/{id}")]
        [ResponseType(typeof(DateTime))]
        public async Task<IHttpActionResult> GetHoraSincronizacao(int id)
        {
            Marcacao marcacao = await db.Marcacoes.FindAsync(id);
            if (marcacao == null)
            {
                return NotFound();
            }

            return Ok(marcacao.HoraSincronizacao);
        }

        [HttpGet]
        [Route("GetMarcacaoPacientes/{responsavel}/{estado}")]
        [ResponseType(typeof(List<Marcacao>))]
        public IHttpActionResult GetMarcacaoPacientes(int responsavel, int estado)
        {
            var marcacoes = db.Marcacoes.Where(m => m.EstadoMarcacaoId == estado && db.Pacientes.FirstOrDefault(p => p.ID == m.PacienteID && p.Ativo && p.Responsavel_ID == responsavel) != null);

            if (marcacoes == null)
            {
                return NotFound();
            }

            return Ok(marcacoes.ToList());
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool MarcacaoExists(int id)
        {
            return db.Marcacoes.Count(e => e.Id == id) > 0;
        }
    }
}