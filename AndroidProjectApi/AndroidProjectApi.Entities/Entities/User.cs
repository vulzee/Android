using AndroidProjectApi.Entities.Abstract;
using System;


namespace AndroidProjectApi.Entities.Entities
{
    public class User : IEntity
    {
        public int Id { get; set; }

        public int Version { get; set; }

        public string UserExtertnalId { get; set; }

        public bool ShowNotyfications { get; set; }
    }
}
