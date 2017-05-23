using AndroidProjectApi.Api.Models;
using AndroidProjectApi.Data.Core.Infrastructure;
using AndroidProjectApi.Data.Repositories;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
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

        private SettingsRepository settingsRepository;

        public AndroidProjectController()
        {
            this.showsRepository = new ShowsRepository(new DbFactory());
            this.usersRepository = new UsersRepository(new DbFactory());
            this.settingsRepository = new SettingsRepository(new DbFactory());
        }

        [HttpGet]
        [Route("GetUserShows/{externalUserId}")]
        [ResponseType(typeof(int[]))]
        public IHttpActionResult GetUserShowsIds(string externalUserId)
        {
            return Ok(this.showsRepository.GetShowsForUser(externalUserId).Select(x => x.ShowExternalId).ToArray());
        }

        [HttpPost]
        [Route("AddShowForUser")]
        public IHttpActionResult AddShowForUser(ExternalForm form)
        {
            var added = this.showsRepository.AddShowForUser(form.ExternalUserId, form.ExternalShowId);
            if(added)
                return Ok();

            return Conflict();
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
        [Route("DeleteShowForUser")]
        public IHttpActionResult DeleteShowForUser(ExternalForm form)
        {
            var deleted = this.showsRepository.DeleteShowForUser(form.ExternalUserId, form.ExternalShowId);
            if (deleted)
                return Ok();

            return Conflict();
        }

        [HttpPost]
        [Route("IsShowInUsersFavourites")]
        public IHttpActionResult IsShowInUsersFavourites(ExternalForm form)
        {
            var isFavourite = this.showsRepository.IsShowInUsersFavourites(form.ExternalUserId, form.ExternalShowId);

            if(isFavourite)
                return Ok();

            return Conflict();
        }

        [HttpGet]
        [Route("GetUserNotificationTime/{externalUserId}")]
        [ResponseType(typeof(int))]
        public IHttpActionResult GetUserNotificationTime(string externalUserId)
        {
            return Ok(this.settingsRepository.GetNotificationTime(externalUserId));
        }

        [HttpPost]
        [Route("SaveUserNotificationTime")]
        public IHttpActionResult SaveUserNotificationTime(ExternalSettingsForm form)
        {
            this.settingsRepository.SaveNotificationTime(form.ExternalUserId, form.Time);

            return Ok();
        }
    }
}