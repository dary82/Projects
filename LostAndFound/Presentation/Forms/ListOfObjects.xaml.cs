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
using ConsoleApp4;
using ConsoleApp4.Repositories;
using Forms;
using System.Data.SqlClient;
using System.Data;
using Presentation.Model;
using Presentation;

namespace Presentation.Forms
{
    /// <summary>
    /// Interaction logic for ListOfObjects.xaml
    /// </summary>
    public partial class ListOfObjects : Window
    {
        public string connectionString = System.Configuration.ConfigurationManager.ConnectionStrings["Test"].ConnectionString;

        public List<LostObject> LostObjects { get; set; }
        public List<FoundObjectDto> FoundObjects { get; set; }
        FoundObjectRepository foundObjectsRepo;
        LostObjectRepository lostObjectsRepo;
        UserRepository userRepository;
        public ListOfObjects()
        {
            InitializeComponent();
            foundObjectsRepo = new FoundObjectRepository();
            lostObjectsRepo = new LostObjectRepository();
            userRepository = new UserRepository();
            LoadData();
        }

        private void LoadData()
        {
            var lostObjects = lostObjectsRepo.GetAll();
            var foundObjects = foundObjectsRepo.GetAll();
            dgListaLost.ItemsSource = MapLostObjects(lostObjects);
            dgListaFound.ItemsSource = MapFoundObjects(foundObjects);
        }

        private List<LostObject> MapLostObjects(List<LostObjectDto> allObjects)
        {
            List<LostObject> result = new List<LostObject>();
            foreach(var obj in allObjects)
            {
                var user = userRepository.GetById(obj.UserId.Value);
                result.Add(new LostObject { UserId = user.Id, Name = user.Name, Id = obj.Id, Description = obj.Description, Location = obj.Location, DateLost = obj.LostDate, Resolved = obj.Resolved });
            }
            return result;
        }

        private List<FoundObject> MapFoundObjects(List<FoundObjectDto> allObjects)
        {
            List<FoundObject> result = new List<FoundObject>();
            
            foreach (var obj in allObjects)
            {
                var user = userRepository.GetById(obj.UserId.Value);
                result.Add(new FoundObject { UserId = user.Id, Name = user.Name, Id = obj.Id, Description = obj.Description, Location = obj.Location, DateFound = obj.FoundDate, Resolved = obj.Resolved });
            }
            return result;
        }

        private void bMatch_Click(object sender, RoutedEventArgs e)
        {
            if (((FoundObject)dgListaFound.SelectedItem).Resolved == false && ((LostObject)dgListaLost.SelectedItem).Resolved == false)
            {
                MessageBox.Show("There is a match between " +
                ((FoundObject)dgListaFound.SelectedItem).Id + " " +
                ((LostObject)dgListaLost.SelectedItem).Id);

                lostObjectsRepo.SetFoundObject(((LostObject)dgListaLost.SelectedItem).Id, ((FoundObject)dgListaFound.SelectedItem).Id);
                lostObjectsRepo.SetLostResolved(((LostObject)dgListaLost.SelectedItem).Id);
                foundObjectsRepo.SetFoundResolved(((FoundObject)dgListaFound.SelectedItem).Id);

                new MailBox().Show();
            }
            else
                MessageBox.Show("One or both items are already resolved");



        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            new MainWindow().Show();
            this.Close();
        }
    }
}
