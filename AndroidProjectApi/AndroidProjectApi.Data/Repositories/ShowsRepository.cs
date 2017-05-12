
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

        public bool AddShowForUser(string userExternalId, string showExternalId)
        {
            var show = this.DbContext.Shows.FirstOrDefault(x => x.ShowExternalId == showExternalId && x.UserExternalId == userExternalId);

            if(show != default(Show))
            {
                return false;
            }

            this.DbContext.Shows.Add(new Show() { ShowExternalId = showExternalId, UserExternalId = userExternalId });
            this.DbContext.Commit();

            return true;
        }

        public bool DeleteShowForUser(string userExternalId, string showExternalId)
        {
            var show = this.DbContext.Shows.FirstOrDefault(x =>  x.ShowExternalId == showExternalId && x.UserExternalId == userExternalId );

            if (show != null)
            {
                this.DbContext.Shows.Remove(show);
                this.DbContext.Commit();

                return true;
            }

            return false;
        }

        public bool IsShowInUsersFavourites(string externalUserId, string externalShowId)
        {
            return this.DbContext.Shows.Any(x => x.ShowExternalId == externalShowId && x.UserExternalId == externalUserId);
        }
    }
}
