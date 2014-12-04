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
    [RoutePrefix("api/EstadoMarcacoes")]
    public class EstadoMarcacoesController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/EstadoMarcacoes
        public List<EstadoMarcacao> GetEstadosMarcacao()
        {
            return db.EstadosMarcacao.ToList();
        }

        // GET: api/EstadoMarcacoes/5
        [ResponseType(typeof(EstadoMarcacao))]
        public async Task<IHttpActionResult> GetEstadoMarcacao(int id)
        {
            EstadoMarcacao estadoMarcacao = await db.EstadosMarcacao.FindAsync(id);
            if (estadoMarcacao == null)
            {
                return NotFound();
            }

            return Ok(estadoMarcacao);
        }

        // PUT: api/EstadoMarcacoes/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutEstadoMarcacao(int id, EstadoMarcacao estadoMarcacao)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != estadoMarcacao.Id)
            {
                return BadRequest();
            }

            db.Entry(estadoMarcacao).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EstadoMarcacaoExists(id))
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

        // POST: api/EstadoMarcacoes
        [ResponseType(typeof(EstadoMarcacao))]
        public async Task<IHttpActionResult> PostEstadoMarcacao(EstadoMarcacao estadoMarcacao)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.EstadosMarcacao.Add(estadoMarcacao);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = estadoMarcacao.Id }, estadoMarcacao);
        }

        // DELETE: api/EstadoMarcacoes/5
        [ResponseType(typeof(EstadoMarcacao))]
        public async Task<IHttpActionResult> DeleteEstadoMarcacao(int id)
        {
            EstadoMarcacao estadoMarcacao = await db.EstadosMarcacao.FindAsync(id);
            if (estadoMarcacao == null)
            {
                return NotFound();
            }

            db.EstadosMarcacao.Remove(estadoMarcacao);
            await db.SaveChangesAsync();

            return Ok(estadoMarcacao);
        }

        [HttpGet]
        [Route("GetHoraSincronizacao/{id}")]
        [ResponseType(typeof(DateTime))]
        public async Task<IHttpActionResult> GetHoraSincronizacao(int id)
        {
            EstadoMarcacao estado = await db.EstadosMarcacao.FindAsync(id);
            if (estado == null)
            {
                return NotFound();
            }

            return Ok(estado.HoraSincronizacao);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool EstadoMarcacaoExists(int id)
        {
            return db.EstadosMarcacao.Count(e => e.Id == id) > 0;
        }
    }
}