using AndroidProjectApi.Api.Models;
using AndroidProjectApi.Data.Core.Infrastructure;
using AndroidProjectApi.Data.Repositories;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Http.Description;

namespace AndroidProjectApi.Api.Controllers
{
    [RoutePrefix("api")]
    public class AndroidProjectController : ApiController
    {
        private UsersRepository usersRepository;

        private ShowsRepository showsRepository;

        public AndroidProjectController()
        {
            this.showsRepository = new ShowsRepository(new DbFactory());
            this.usersRepository = new UsersRepository(new DbFactory());
        }

        [HttpGet]
        [Route("GetUserShows/{externalUserId}")]
        [ResponseType(typeof(ShowModel))]
        public IHttpActionResult GetUserShows(string externalUserId)
        {
            return Ok(Mapper.Map<IEnumerable<ShowModel>>(this.showsRepository.GetShowsForUser(externalUserId)));
        }

        [HttpPost]
        [Route("AddShowForUser/{externalUserId}")]
        public IHttpActionResult AddShowForUser(string externalUserId, [FromBody]string externalShowId)
        {
            this.showsRepository.AddShowForUser(externalUserId, externalShowId);

            return Ok();
        }

        [HttpPost]
        [Route("TurnOffNotyfications/{externalUserId}")]
        public IHttpActionResult TurnOffNotyfications(string externalUserId)
        {
            this.usersRepository.TurnOffNotyfications(externalUserId);

            return Ok();
        }

        [HttpPost]
        [Route("TurnOnNotyfications/{externalUserId}")]
        public IHttpActionResult TurnOnNotyfications(string externalUserId)
        {
            this.usersRepository.TurnOnNotyfications(externalUserId);

            return Ok();
        }

        [HttpGet]
        [Route("AreNotyficationsOn/{externalUserId}")]
        [ResponseType(typeof(bool))]
        public IHttpActionResult AreNotyficationsOn(string externalUserId)
        {
            return Ok(this.usersRepository.AreUserNotyficationsOn(externalUserId));
        }

        [HttpPost]
        [Route("DeleteShowForUser/{externalUserId}")]
        public IHttpActionResult DeleteShowForUser(string externalUserId, [FromBody]string externalShowId)
        {
            this.showsRepository.DeleteShowForUser(externalUserId, externalShowId);

            return Ok();
        }
    }
}