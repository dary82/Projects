using ConsoleApp4;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.SqlClient;
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
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Security.Cryptography;
using Presentation.Forms;

namespace Presentation
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public string connectionString = System.Configuration.ConfigurationManager.ConnectionStrings["Test"].ConnectionString;

        public MainWindow()
        {
            InitializeComponent();
            UpdateGrid();
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

        public List<User> Users { get; set; }

        private void addUser_Click(object sender, RoutedEventArgs e)
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
                    if (reader[1].ToString() == txUsername.Text || reader[3].ToString() == txEmail.Text || reader[4].ToString() == txPhone.Text)
                    {
                        exists = true;
                        MessageBox.Show("This username, email or phone number has already been used");
                        break;
                    }
                }
                sqlcon.Close();
            }

            if (txUsername.Text != null && txEmail.Text != null && txPassword.Text != null && txPhone.Text != null && txUsername.Text != " " && txEmail.Text != " " && txPassword.Text != " " && txPhone.Text != " ")
            {
                exists = true;
                MessageBox.Show("Please fill the empty boxes");
            }

            if (exists == false)
            {
                User a = new User
                {
                    Userame = txUsername.Text,
                    Password = Encrypt(txPassword.Text),
                    Phone = txPhone.Text,
                    Email = txEmail.Text,

                };


                using (SqlConnection sqlcon = new SqlConnection(connectionString))
                {
                    sqlcon.Open();
                    SqlCommand sqlcmd = new SqlCommand("UserAdd", sqlcon);
                    sqlcmd.CommandType = System.Data.CommandType.StoredProcedure;
                    sqlcmd.Parameters.AddWithValue("@username", a.Userame);
                    sqlcmd.Parameters.AddWithValue("@password", a.Password);
                    sqlcmd.Parameters.AddWithValue("@email", a.Email);
                    sqlcmd.Parameters.AddWithValue("@phone", a.Phone);
                    sqlcmd.Parameters.AddWithValue("@is_admin", isAdmin.IsChecked);

                    sqlcmd.ExecuteNonQuery();
                    sqlcon.Close();
                }
                UpdateGrid();
            }


        }
        void UpdateGrid()
        {
            using (SqlConnection sqlcon = new SqlConnection(connectionString))
            {
                sqlcon.Open();
                SqlCommand sqlcmd = new SqlCommand("UserGet", sqlcon);
                sqlcmd.CommandType = System.Data.CommandType.StoredProcedure;
                DataTable dt = new DataTable();

                dt.Load(sqlcmd.ExecuteReader());
                dgUsers.DataContext = dt.DefaultView;
                sqlcon.Close();
            }
        }

        private void deleteUser_Click(object sender, RoutedEventArgs e)
        {
            DataRowView drv = (DataRowView)dgUsers.SelectedItem;
            String userId = drv["user_id"].ToString();
            string username = drv["username"].ToString();

            if (UserStore.GetUsername() == username)
            {
                MessageBox.Show("Cannot delete logged in user");
                return;
            }


            using (SqlConnection sqlcon = new SqlConnection(connectionString))
            using (SqlCommand cmdDelete = new SqlCommand("dbo.proc_delete", sqlcon))
            {

                cmdDelete.CommandType = CommandType.StoredProcedure;

                // add parameter
                cmdDelete.Parameters.Add("@user_id", SqlDbType.Int);
                cmdDelete.Parameters["@user_id"].Value = int.Parse(userId);
                //((User)dgUsers.CurrentItem).userId;

                // open connection, execute command, close connection
                sqlcon.Open();
                cmdDelete.ExecuteNonQuery();
                sqlcon.Close();
                UpdateGrid();

            }

        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            new MailBox().Show();
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            new ListOfObjects().Show();
            this.Close();
        }
    }
}