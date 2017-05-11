using AndroidProjectApi.Entities.Abstract;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;

namespace AndroidProjectApi.Data.Repositories.Abstract
{
    public interface IEntityRepository<T> where T : class, IEntity, new()
    {
        void Add(T entity);
        void Update(T entity);
        void Delete(T entity);
        void Delete(Expression<Func<T, bool>> where);
        T GetById(int id);
        T Get(Expression<Func<T, bool>> where);
        ICollection<T> GetAll();
        ICollection<T> GetMany(Expression<Func<T, bool>> where);

        void Commit();
    }
}
