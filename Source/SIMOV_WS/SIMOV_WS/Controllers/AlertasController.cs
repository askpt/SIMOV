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
    [RoutePrefix("api/Alertas")]
    public class AlertasController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Alertas
        public List<Alerta> GetAlertas()
        {
            return db.Alertas.ToList();
        }

        // GET: api/Alertas/5
        [ResponseType(typeof(Alerta))]
        public async Task<IHttpActionResult> GetAlerta(int id)
        {
            Alerta alerta = await db.Alertas.FindAsync(id);
            if (alerta == null)
            {
                return NotFound();
            }

            return Ok(alerta);
        }

        // PUT: api/Alertas/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutAlerta(int id, Alerta alerta)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != alerta.Id)
            {
                return BadRequest();
            }

            db.Entry(alerta).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!AlertaExists(id))
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

        // POST: api/Alertas
        [ResponseType(typeof(Alerta))]
        public async Task<IHttpActionResult> PostAlerta(Alerta alerta)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Alertas.Add(alerta);
            await db.SaveChangesAsync();

            return Ok(alerta.Id);
        }

        // DELETE: api/Alertas/5
        [ResponseType(typeof(Alerta))]
        public async Task<IHttpActionResult> DeleteAlerta(int id)
        {
            Alerta alerta = await db.Alertas.FindAsync(id);
            if (alerta == null)
            {
                return NotFound();
            }

            db.Alertas.Remove(alerta);
            await db.SaveChangesAsync();

            return Ok(alerta);
        }

        [HttpGet]
        [Route("GetHoraSincronizacao/{id}")]
        [ResponseType(typeof(DateTime))]
        public async Task<IHttpActionResult> GetHoraSincronizacao(int id)
        {
            Alerta alerta = await db.Alertas.FindAsync(id);
            if (alerta == null)
            {
                return NotFound();
            }

            return Ok(alerta.HoraSincronizacao);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool AlertaExists(int id)
        {
            return db.Alertas.Count(e => e.Id == id) > 0;
        }
    }
}