using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AndroidProjectApi.Data.Core
{
    public class AndroidApiInit : DropCreateDatabaseIfModelChanges<AndroidApiContext>
    {
    }
}
