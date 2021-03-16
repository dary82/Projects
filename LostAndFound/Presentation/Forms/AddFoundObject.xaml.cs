using ConsoleApp4;
using ConsoleApp4.Repositories;
using Microsoft.Win32;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Forms
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class AddFoundObject : Window
    {
        public AddFoundObject()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            if (txDescription.Text != "" && txLocation.Text != "" && dtData.SelectedDate.Value.ToString() != "")
            {
                FoundObjectDto Obj = new FoundObjectDto();
                Obj.Description = txDescription.Text;
                Obj.Location = txLocation.Text;
                Obj.FoundDate = dtData.SelectedDate.Value;
                Obj.UserId = UserStore.GetUserid();
                Obj.Resolved = false;
                FoundObjectRepository repo = new FoundObjectRepository();
                repo.Create(Obj);
                this.Close();
            }
            else
                MessageBox.Show("Incomplete fields!");
            
        }
        

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            OpenFileDialog op = new OpenFileDialog();
            op.Title = "Select a picture";
            op.Filter = "All supported graphics|*.jpg;*.jpeg;*.png|" +
              "JPEG (*.jpg;*.jpeg)|*.jpg;*.jpeg|" +
              "Portable Network Graphic (*.png)|*.png";
            if (op.ShowDialog() == true)
            {
                imgPhoto.Source = new BitmapImage(new Uri(op.FileName));
            }
        }
    }
}
