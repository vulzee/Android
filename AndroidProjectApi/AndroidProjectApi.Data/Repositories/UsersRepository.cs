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

        public void SaveUserSettings(string externalUserId, bool areNotificationsOn, int time)
        {
            var user = this.DbContext.Users.FirstOrDefault(x => x.UserExtertnalId == externalUserId);

            if(user == null)
            {
                user = new User() { UserExtertnalId = externalUserId };
                this.DbContext.Users.Add(user);
            }

            user.ShowNotyfications = areNotificationsOn;
            user.TimeBeforeNotification = time;

            this.DbContext.Commit();
        }

        public User GetUserSettings(string externalUserId)
        {
            var user = this.DbContext.Users.FirstOrDefault(x => x.UserExtertnalId == externalUserId);

            return user;
        }
    }
}
