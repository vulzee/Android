using AndroidProjectApi.Api.Mappings;
using Microsoft.Practices.Unity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;

namespace AndroidProjectApi.Api
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            AutoMapperConfiguration.Configure();

            config.MapHttpAttributeRoutes();

            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );
        }
    }
}
