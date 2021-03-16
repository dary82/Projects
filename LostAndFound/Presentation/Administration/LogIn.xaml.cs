using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using System.Data.SqlClient;
using System.Data;
using System.Security.Cryptography;
using ConsoleApp4;
using Presentation.Forms;

namespace Presentation
{
    
    public partial class LogIn : Window
    {
        public LogIn()
        {
            InitializeComponent();
        }

        static string Encrypt(string value)
        {
            using (MD5CryptoServiceProvider md5 = new MD5CryptoServiceProvider())
            {
                UTF8Encoding utf8 = new UTF8Encoding();
                byte[] data = md5.ComputeHash(utf8.GetBytes(value));
                return Convert.ToBase64String(data);
            }
        }

        private void logInBt_Click(object sender, RoutedEventArgs e)
        {
            SqlConnection sqlcon = new SqlConnection(System.Configuration.ConfigurationManager.ConnectionStrings["Test"].ConnectionString);
            string querry = "Select * from users Where username = '" + userTb.Text.Trim() + "' and password = '" + Encrypt(passwordTb.Password) + "'";
            SqlDataAdapter dataAdapter = new SqlDataAdapter(querry, sqlcon);
            DataTable dtbl = new DataTable();
            dataAdapter.Fill(dtbl);

            if (dtbl.Rows.Count==1)
            {
                UserStore.SetUsername(userTb.Text.Trim());
                bool isAdmin = (bool) dtbl.Rows[0].ItemArray[5];
                UserStore.SetUserid((int)dtbl.Rows[0].ItemArray[0]);
                if(isAdmin==true)
                {
                    new MainWindow().Show();
                }
                else
                {
                    new ListOfObjectsUser().Show();
                }
                this.Close();
            }
            else
            {
                MessageBox.Show("Username or password are incorrect");
            }
            

        }

        private void signUpBt_Click(object sender, RoutedEventArgs e)
        {
            new signUp().Show();
            this.Close();
        }
    }
}
