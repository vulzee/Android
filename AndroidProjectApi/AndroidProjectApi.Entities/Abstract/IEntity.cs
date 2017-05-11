using System.ComponentModel.DataAnnotations;

namespace AndroidProjectApi.Entities.Abstract
{
    public interface IEntity
    {
        [Key]
        int Id { get; set; }
        [Timestamp]
        int Version { get; set; }
    }
}
