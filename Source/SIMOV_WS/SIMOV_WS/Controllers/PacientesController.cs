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
    [RoutePrefix("api/Pacientes")]
    public class PacientesController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Pacientes
        public List<Paciente> GetPacientes()
        {
            return db.Pacientes.Where(p => p.Ativo).ToList();
        }

        // GET: api/Pacientes/5
        [ResponseType(typeof(Paciente))]
        public async Task<IHttpActionResult> GetPaciente(int id)
        {
            Paciente paciente = await db.Pacientes.FindAsync(id);
            if (paciente == null || !paciente.Ativo)
            {
                return NotFound();
            }

            return Ok(paciente);
        }

        // PUT: api/Pacientes/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutPaciente(int id, Paciente paciente)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != paciente.ID)
            {
                return BadRequest();
            }

            if (!PacienteExists(id))
            {
                return NotFound();
            }

            db.Entry(paciente).State = EntityState.Modified;

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

        // POST: api/Pacientes
        [ResponseType(typeof(Paciente))]
        public async Task<IHttpActionResult> PostPaciente(Paciente paciente)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            if (paciente.Data == default(DateTime))
            {
                paciente.Data = null;
            }

            db.Pacientes.Add(paciente);
            await db.SaveChangesAsync();

            return Ok(paciente.ID);
        }

        // DELETE: api/Pacientes/5
        [ResponseType(typeof(Paciente))]
        public async Task<IHttpActionResult> DeletePaciente(int id)
        {
            Paciente paciente = await db.Pacientes.FindAsync(id);
            if (paciente == null)
            {
                return NotFound();
            }

            db.Pacientes.Remove(paciente);
            await db.SaveChangesAsync();

            return Ok(paciente);
        }

        [HttpGet]
        [Route("GetHoraSincronizacao/{id}")]
        [ResponseType(typeof(DateTime))]
        public async Task<IHttpActionResult> GetHoraSincronizacao(int id)
        {
            Paciente paciente = await db.Pacientes.FindAsync(id);
            if (paciente == null || !paciente.Ativo)
            {
                return NotFound();
            }

            return Ok(paciente.HoraSincronizacao);
        }

        [HttpGet]
        [Route("GetPacientes/{id}")]
        [ResponseType(typeof(ICollection<Paciente>))]
        public async Task<IHttpActionResult> GetPacientes(int id)
        {
            var pacientes = await db.Pacientes.Where(p => p.Responsavel_ID == id && p.Ativo).ToArrayAsync();

            return Ok(pacientes);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool PacienteExists(int id)
        {
            return db.Pacientes.Count(e => e.ID == id) > 0;
        }
    }
}