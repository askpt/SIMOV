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
            return db.Marcacoes.ToList();
        }

        // GET: api/Marcacoes/5
        [ResponseType(typeof(Marcacao))]
        public async Task<IHttpActionResult> GetMarcacao(int id)
        {
            Marcacao marcacao = await db.Marcacoes.FindAsync(id);
            if (marcacao == null)
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

            return CreatedAtRoute("DefaultApi", new { id = marcacao.Id }, marcacao);
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
        public IQueryable<Marcacao> ObterMarcacoesPacientes(int id)
        {
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