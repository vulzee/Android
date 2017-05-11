using AndroidProjectApi.Entities.Entities;
using System;
using System.Data.Entity;

namespace AndroidProjectApi.Data.Core.Abstract
{
    public interface IContext : IDisposable
    {
        int SaveChanges();

        IDbSet<Show> Shows { get; set; }

        IDbSet<User> Users { get; set; }
    }
}
