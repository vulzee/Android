using AndroidProjectApi.Api.Models;
using AndroidProjectApi.Entities.Entities;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace AndroidProjectApi.Api.Mappings
{
    public class DomainToViewModelMappingProfile : Profile
    {
        protected override void Configure()
        {
            Mapper.CreateMap<User, UserModel>();
            Mapper.CreateMap<Show, ShowModel>();
        }
    }
}