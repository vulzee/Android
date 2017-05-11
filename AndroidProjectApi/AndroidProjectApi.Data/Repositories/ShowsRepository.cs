
using AndroidProjectApi.Data.Repositories.Abstract;
using AndroidProjectApi.Entities.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq.Expressions;
using AndroidProjectApi.Data.Core.Abstract;

namespace AndroidProjectApi.Data.Repositories
{
    public class ShowsRepository : EntityRepository<Show>
    {
        public ShowsRepository(IDbFactory dbFactory) : base(dbFactory)
        {
          
        }

        public IEnumerable<Show> GetShowsForUser(string userExternalId)
        {
            return this.DbContext.Shows.Where(x => x.UserExternalId == userExternalId);
        }

        public void AddShowForUser(string userExternalId, string showExternalId)
        {
            this.DbContext.Shows.Add(new Show() { ShowExternalId = showExternalId, UserExternalId = userExternalId });
            this.DbContext.Commit();
        }

        public void DeleteShowForUser(string userExternalId, string showExternalId)
        {
            var show = this.DbContext.Shows.FirstOrDefault(x =>  x.ShowExternalId == showExternalId && x.UserExternalId == userExternalId );

            if (show != null)
            {
                this.DbContext.Shows.Remove(show);
                this.DbContext.Commit();
            }
        }
    }
}
