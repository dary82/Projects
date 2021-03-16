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
using System.Security.Cryptography;
using Presentation.Forms;

namespace Presentation
{
    /// <summary>
    /// Interaction logic for signUp.xaml
    /// </summary>
    public partial class signUp : Window
    {
        string connectionString = System.Configuration.ConfigurationManager.ConnectionStrings["Test"].ConnectionString;
        public signUp()
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

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            bool exists = false;
            using (SqlConnection sqlcon = new SqlConnection(connectionString))
            {
                sqlcon.Open();
                SqlCommand sqlcmd = new SqlCommand();
                sqlcmd.CommandText = "select * from users";
                sqlcmd.Connection = sqlcon;

                SqlDataReader reader = sqlcmd.ExecuteReader();
                while (reader.Read())
                {
                    if (reader[1].ToString() == txtUsername.Text || reader[3].ToString() == txtEmail.Text || reader[4].ToString() == txtPhone.Text)
                    {
                        exists = true;
                        MessageBox.Show("This username, email or phone number has already been used");
                        break;
                    }
                }
                sqlcon.Close();
            }

            if (txtUsername.Text != null && txtEmail.Text != null && txtPassword.Text != null && txtPhone.Text != null && txtUsername.Text != " " && txtEmail.Text != " " && txtPassword.Text != " " && txtPhone.Text != " ")
            {
                exists = true;
                MessageBox.Show("Please fill the empty boxes");
            }

            if (exists == false)
            {
                using (SqlConnection sqlcon = new SqlConnection(connectionString))
                {
                    sqlcon.Open();
                    SqlCommand sqlcmd = new SqlCommand("UserAdd", sqlcon);
                    sqlcmd.CommandType = System.Data.CommandType.StoredProcedure;
                    sqlcmd.Parameters.AddWithValue("@Username", txtUsername.Text.Trim());
                    sqlcmd.Parameters.AddWithValue("@Password", Encrypt(txtPassword.Text));
                    sqlcmd.Parameters.AddWithValue("@Email", txtEmail.Text.Trim());
                    sqlcmd.Parameters.AddWithValue("@Phone", txtPhone.Text.Trim());
                    sqlcmd.Parameters.AddWithValue("@is_admin", false);
                    sqlcmd.ExecuteNonQuery();
                    sqlcon.Close();
                }

                new LogIn().Show();
                this.Close();
            }

        }
        
    }
}
