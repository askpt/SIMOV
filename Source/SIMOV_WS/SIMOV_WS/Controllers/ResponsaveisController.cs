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
    [RoutePrefix("api/Responsaveis")]
    public class ResponsaveisController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Responsaveis
        /// <summary>
        /// Obter todos os responsáveis
        /// </summary>
        /// <returns>Listagem de todos os responsáveis</returns>
        [ResponseType(typeof(List<Responsavel>))]
        public List<Responsavel> GetResponsaveis()
        {
            return db.Responsaveis.ToList();
        }

        // GET: api/Responsaveis/5
        [ResponseType(typeof(Responsavel))]
        public async Task<IHttpActionResult> GetResponsavel(int id)
        {
            Responsavel responsavel = await db.Responsaveis.FindAsync(id);
            if (responsavel == null)
            {
                return NotFound();
            }

            return Ok(responsavel);
        }

        // PUT: api/Responsaveis/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutResponsavel(int id, Responsavel responsavel)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != responsavel.ID)
            {
                return BadRequest();
            }

            if (!ResponsavelExists(id))
            { 
                return NotFound();
            }

            var respTemp = await db.Responsaveis.FindAsync(id);
            responsavel.Pacientes = respTemp.Pacientes;
            db.Entry(responsavel).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                throw;
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Responsaveis
        [ResponseType(typeof(Responsavel))]
        public async Task<IHttpActionResult> PostResponsavel(Responsavel responsavel)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Responsaveis.Add(responsavel);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = responsavel.ID }, responsavel);
        }

        // DELETE: api/Responsaveis/5
        [ResponseType(typeof(Responsavel))]
        public async Task<IHttpActionResult> DeleteResponsavel(int id)
        {
            Responsavel responsavel = await db.Responsaveis.FindAsync(id);
            if (responsavel == null)
            {
                return NotFound();
            }

            db.Responsaveis.Remove(responsavel);
            await db.SaveChangesAsync();

            return Ok(responsavel);
        }

        [HttpGet]
        [Route("GetHoraSincronizacao/{id}")]
        [ResponseType(typeof(DateTime))]
        public async Task<IHttpActionResult> GetHoraSincronizacao(int id)
        {
            Responsavel responsavel = await db.Responsaveis.FindAsync(id);
            if (responsavel == null)
            {
                return NotFound();
            }

            return Ok(responsavel.HoraSincronizacao);
        }

        [HttpPost]
        [Route("VerificarSeExisteEmail")]
        [ResponseType(typeof(bool))]
        public async Task<IHttpActionResult> VerificarSeExisteEmail(string email)
        {
            Responsavel responsavel = await db.Responsaveis.FirstOrDefaultAsync(r => r.Email == email);
            Paciente paciente = await db.Pacientes.FirstOrDefaultAsync(p => p.Email == email);

            if (responsavel == null && paciente == null)
            {
                return Ok(false);
            }

            return Ok(true);
        }

        [HttpPost]
        [Route("Login")]
        [ResponseType(typeof(Responsavel))]
        public async Task<IHttpActionResult> Login(string email, string pass)
        {
            Responsavel responsavel = await db.Responsaveis.FirstOrDefaultAsync(r => r.Email == email && r.Password == pass);

            if (responsavel == null)
            {
                return NotFound();
            }

            return Ok(responsavel);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ResponsavelExists(int id)
        {
            return db.Responsaveis.Count(e => e.ID == id) > 0;
        }
    }
}