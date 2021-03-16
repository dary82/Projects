using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp4
{
    public class UserDto
    {
        public int Id;
        public string Name;
        public string Password;
        public string Email;
        public double Rating = 10;
        public string Phone;

        public UserDto()
        {

        }

        public override string ToString()
        {
            return Name ;
        }

        public UserDto(string nameDefault)
        {
            Name = nameDefault;
        }
    }
}
