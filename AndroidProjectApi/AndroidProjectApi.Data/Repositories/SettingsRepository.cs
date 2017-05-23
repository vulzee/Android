using AndroidProjectApi.Data.Core.Abstract;
using AndroidProjectApi.Entities.Entities;
using System;
using System.Linq;

namespace AndroidProjectApi.Data.Repositories
{
    public class SettingsRepository : EntityRepository<Setting>
    {
        public SettingsRepository(IDbFactory dbFactory) : base(dbFactory)
        {
        }

        public int GetNotificationTime(string userExternalId)
        {
            var setting = this.DbContext.Settings.FirstOrDefault(x => x.UserExtertnalId == userExternalId);

            return setting != null ? setting.TimeBeforeNotyfication : -1;
        }

        public void SaveNotificationTime(string userExternalId, int time)
        {
            var setting = this.DbContext.Settings.FirstOrDefault(x => x.UserExtertnalId == userExternalId);

            if(setting == null)
            {
                setting = new Setting() {
                    UserExtertnalId = userExternalId,
                };

                this.DbContext.Settings.Add(setting);
            }

            setting.TimeBeforeNotyfication = time;

            this.DbContext.Commit();
        }
    }
}
