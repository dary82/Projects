using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace ConsoleApp4
{
    public class UserStore
    {
        static string username, email;
        static int userid;

        public static string GetEmail()
        {
            return email;
        }

        public static void SetEmail(string Email)
        {
            email = Email;
        }

        public static int GetUserid()
        {
            return userid;
        }

        public static void SetUserid(int id)
        {
            userid = id;
        }

        public static string GetUsername()
        {
            return username;
        }

        public static void SetUsername(string intrare)
        {
            username = intrare;
        }
    }
}
