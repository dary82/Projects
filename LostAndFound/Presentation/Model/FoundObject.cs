using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Presentation.Model
{
    class FoundObject
    {
        public int? UserId { get; set; }
        public string Name { get; set; }
        public int Id { get; set; }
        public string Description { get; set; }
        public string Location { get; set; }
        public DateTime? DateFound { get; set; }
        public bool Resolved { get; set; }
    }
}
