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
using System.Net;
using System.Net.Mail;
using System.ComponentModel;
namespace Presentation
{ 
    public partial class MailBox : Window
    {

        NetworkCredential login;
        SmtpClient client;
        MailMessage msg;

        public MailBox()
        {
            InitializeComponent();
        }

        private void btnSend_Click(object sender, RoutedEventArgs e)
        {
            login = new NetworkCredential(textUser_name.Text, textPassword.Password);
            client = new SmtpClient(txtSmtp.Text);
            client.Port =Convert.ToInt32(txtPort.Text);
            client.EnableSsl = chkSSL.IsChecked ?? false;
            client.Credentials = login;
            msg = new MailMessage { From = new MailAddress(textUser_name.Text + txtSmtp.Text.Replace("smtp.","@"),"Lost&Found Admin",Encoding.UTF8) };
            msg.To.Add(new MailAddress(txtTo.Text));
            if (!string.IsNullOrEmpty(txtCC.Text))
                msg.To.Add(new MailAddress(txtCC.Text));
            msg.Subject = txtSubject.Text;
            msg.Body = txtMessage.Text;
            msg.BodyEncoding = Encoding.UTF8;
            msg.IsBodyHtml = true;
            msg.Priority = MailPriority.Normal;
            msg.DeliveryNotificationOptions = DeliveryNotificationOptions.OnFailure;
            client.SendCompleted += new SendCompletedEventHandler(SendCompletedCallback);
            string userstate = "Sending...";
            client.SendAsync(msg, userstate);
        }

        private static void SendCompletedCallback(object sender, AsyncCompletedEventArgs e)
        {
            if (e.Cancelled)
                MessageBox.Show(string.Format("{0} send cancelled.", e.UserState), "Message", MessageBoxButton.OK, MessageBoxImage.Information);
            if (e.Error != null)
                MessageBox.Show(string.Format("{0}", e.UserState), "Message", MessageBoxButton.OK, MessageBoxImage.Information);
            else
                MessageBox.Show("Message sent successfully","Message",MessageBoxButton.OK, MessageBoxImage.Information);
        }
        
    }
}
