using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AndroidProjectApi.Data.Core.Abstract
{
    public interface IDbFactory : IDisposable
    {
        AndroidApiContext Init();
    }
}
