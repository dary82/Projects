using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Presentation.Model
{
    class UserLostObject
    {
        public string Description { get; set; }
        public string Location { get; set; }
        public DateTime? DateLost { get; set; }
        public bool Resolved { get; set; }
    }
}
