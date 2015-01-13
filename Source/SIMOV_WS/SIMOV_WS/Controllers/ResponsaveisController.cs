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
using System.Net.Mail;

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

            return Ok(responsavel.ID);
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
        [Route("ResetPassword")]
        [ResponseType(typeof(bool))]
        public async Task<IHttpActionResult> ResetPassword(string email)
        {
            var responsavel = await db.Responsaveis.FirstOrDefaultAsync(p => p.Email == email);

            if (responsavel == null)
            {
                return NotFound();
            }

            MailMessage mail = new MailMessage("lifechecker@mail.com", responsavel.Email);
            SmtpClient client = new SmtpClient();
            client.DeliveryMethod = SmtpDeliveryMethod.Network;
            client.EnableSsl = true;
            client.Port = 587;
            client.Host = "smtp.mail.com";
            client.Credentials = new NetworkCredential("lifechecker@mail.com", "1234.abcd");
            mail.Subject = "Password Reset";
            mail.Body = string.Format("Your password is {0}", responsavel.Password);
            try
            {
                await client.SendMailAsync(mail);
            }
            catch (Exception)
            {
                return StatusCode(HttpStatusCode.InternalServerError);
            }

            return Ok(true);
        }

        [HttpPost]
        [Route("EnviaEmail")]
        [ResponseType(typeof(bool))]
        public async Task<IHttpActionResult> EnviaEmail(dynamic data)
        {
            var x = int.Parse(data.id.ToString());
            var text = data.text.ToString();

            var responsavel = await db.Responsaveis.FindAsync(x);

            if (responsavel == null)
            {
                return NotFound();
            }

            MailMessage mail = new MailMessage("lifechecker@sapo.pt", responsavel.Email);
            SmtpClient client = new SmtpClient();
            client.DeliveryMethod = SmtpDeliveryMethod.Network;
            client.EnableSsl = true;
            client.Port = 587;
            client.Host = "smtp.sapo.pt";
            client.Credentials = new NetworkCredential("lifechecker@sapo.pt", "1234.abcd");
            mail.Subject = "Alerta";
            mail.Body = text;
            try
            {
                await client.SendMailAsync(mail);
            }
            catch (Exception)
            {
                return StatusCode(HttpStatusCode.InternalServerError);
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