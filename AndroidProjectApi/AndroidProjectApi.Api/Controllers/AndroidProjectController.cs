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

        public AndroidProjectController()
        {
            this.showsRepository = new ShowsRepository(new DbFactory());
            this.usersRepository = new UsersRepository(new DbFactory());
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

        [HttpPost]
        [Route("SaveSettings")]
        public IHttpActionResult SaveSettings(SettingsForm form)
        {
            this.usersRepository.SaveUserSettings(form.ExternalUserId, form.AreNotificationsOn, form.Time);

            return Ok();
        }

        [HttpGet]
        [Route("GetSettings/{externalUserId}")]
        [ResponseType(typeof(SettingsResponse))]
        public IHttpActionResult GetSettings(string externalUserId)
        {
            var settings = this.usersRepository.GetUserSettings(externalUserId);
            var result = new SettingsResponse();

            if (settings == null)
            {
                result.AreNotificationsOn = true;
                result.Time = 60;
            }
            else
            {
                result.AreNotificationsOn = settings.ShowNotyfications;
                result.Time = settings.TimeBeforeNotification;
            }

            return Ok(result);
        }
    }
}