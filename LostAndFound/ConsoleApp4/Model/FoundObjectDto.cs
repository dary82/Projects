using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp4
{
    public class FoundObjectDto
    {
        public int? UserId { get; set; }
        public int Id { get; set; }
        public string Description { get; set; }
        public string Location { get; set; }
        public DateTime? FoundDate { get; set; }
        public bool Resolved { get; set; }
    }
}
