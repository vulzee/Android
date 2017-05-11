using AndroidProjectApi.Data.Core.Abstract;
using AndroidProjectApi.Data.Repositories.Abstract;
using AndroidProjectApi.Entities.Entities;
using System.Linq;

namespace AndroidProjectApi.Data.Repositories
{
    public class UsersRepository: EntityRepository<User>
    {
        public UsersRepository(IDbFactory dbfactory):base(dbfactory)
        {

        }

        public bool AreUserNotyficationsOn (string externalUserId)
        {
            var user = this.DbContext.Users.FirstOrDefault(x => x.UserExtertnalId == externalUserId);

            return (user == null) ? true : user.ShowNotyfications;
        }

        public void TurnOffNotyfications(string externalUserId)
        {
            var user = this.DbContext.Users.FirstOrDefault(x => x.UserExtertnalId == externalUserId);

            if(user == null)
            {
                user = new User() { UserExtertnalId = externalUserId };
                this.DbContext.Users.Add(user);
            }

            user.ShowNotyfications = false;

            this.DbContext.Commit();
        }

        public void TurnOnNotyfications(string externalUserId)
        {
            var user = this.DbContext.Users.FirstOrDefault(x => x.UserExtertnalId == externalUserId);

            if (user == null)
            {
                user = new User() { UserExtertnalId = externalUserId };
                this.DbContext.Users.Add(user);
            }

            user.ShowNotyfications = true;

            this.DbContext.Commit();
        }
    }
}
