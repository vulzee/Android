
using AndroidProjectApi.Data.Core.Abstract;
using System.Data.Entity;
using AndroidProjectApi.Entities.Entities;
using System;

namespace AndroidProjectApi.Data.Core
{
    public class AndroidApiContext : DbContext, IContext
    {
        public AndroidApiContext() : base("AndroidApiContext")
        {
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            Database.SetInitializer<AndroidApiContext>(new DbInit());
            base.OnModelCreating(modelBuilder);
        }

        public IDbSet<Show> Shows { get; set; }

        public IDbSet<User> Users { get; set; }

        public virtual void Commit()
        {
            this.SaveChanges();
        }
    }
}
