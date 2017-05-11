using AndroidProjectApi.Data.Core.Abstract;

namespace AndroidProjectApi.Data.Core.Infrastructure
{
    public class DbFactory : Disposable, IDbFactory
    {
        private AndroidApiContext _dbContext;

        public AndroidApiContext Init()
        {
            return _dbContext ?? (_dbContext = new AndroidApiContext());
        }

        protected override void DisposeCore()
        {
            if (_dbContext != null)
                _dbContext.Dispose();
        }
    }
}
