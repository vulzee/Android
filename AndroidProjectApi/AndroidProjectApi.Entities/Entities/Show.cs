using AndroidProjectApi.Entities.Abstract;
using System;


namespace AndroidProjectApi.Entities.Entities
{
    public class Show : IEntity
    {
        public int Id { get; set; }

        public int Version { get; set; }

        public string ShowExternalId { get; set; }

        public string UserExternalId { get; set; }
    }
}
