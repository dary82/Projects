using ConsoleApp4;
using ConsoleApp4.Repositories;
using Forms;
using Presentation.Model;
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

namespace Presentation.Forms
{
    /// <summary>
    /// Interaction logic for ListOfObjectsUser.xaml
    /// </summary>
    public partial class ListOfObjectsUser : Window
    {
        public List<FoundObjectDto> FoundObjects { get; set; }
        public List<LostObjectDto> LostObjects { get; set; }
        FoundObjectRepository foundObjectsRepo;
        LostObjectRepository lostObjectsRepo;
        public ListOfObjectsUser()
        {
            InitializeComponent();
            foundObjectsRepo = new FoundObjectRepository();
            lostObjectsRepo = new LostObjectRepository();
            LoadFoundData();
            LoadLostData();
        }

        private void LoadFoundData()
        {
            FoundObjects = foundObjectsRepo.GetAll().Where(x=>x.UserId==UserStore.GetUserid()).ToList();
            dgListaFound.ItemsSource = MapFoundObjects(FoundObjects);
        }

        private void LoadLostData()
        {
            LostObjects = lostObjectsRepo.GetAll().Where(x => x.UserId == UserStore.GetUserid()).ToList();
            dgListaLost.ItemsSource = MapLostObjects(LostObjects);
        }

        private List<UserFoundObject> MapFoundObjects(List<FoundObjectDto> allObjects)
        {
            List<UserFoundObject> result = new List<UserFoundObject>();

            foreach (var obj in allObjects)
            {
                result.Add(new UserFoundObject { Description = obj.Description, Location = obj.Location, DateFound = obj.FoundDate, Resolved = obj.Resolved });
            }
            return result;
        }

        private List<UserLostObject> MapLostObjects(List<LostObjectDto> allObjects)
        {
            List<UserLostObject> result = new List<UserLostObject>();

            foreach (var obj in allObjects)
            {
                result.Add(new UserLostObject { Description = obj.Description, Location = obj.Location, DateLost = obj.LostDate, Resolved = obj.Resolved });
            }
            return result;
        }

        private void bAddFound_Click(object sender, RoutedEventArgs e)
        {
            AddFoundObject add = new AddFoundObject();
            add.ShowDialog();
            LoadFoundData();
        }

        private void bAddLost_Click(object sender, RoutedEventArgs e)
        {
            AddLostObject add = new AddLostObject();
            add.ShowDialog();
            LoadLostData();
        }

        private void bDelete_Click(object sender, RoutedEventArgs e)
        {
            if (dgListaLost.SelectedItem != null)
            {
                object drvL = dgListaLost.SelectedItem;
                int objLId = Convert.ToInt32(((LostObjectDto)drvL).Id);
                lostObjectsRepo.Delete(objLId);
                LoadLostData();
            }
            if (dgListaFound.SelectedItem !=null)
            {
                object drvF = dgListaFound.SelectedItem;
                int objFId = Convert.ToInt32(((FoundObjectDto)drvF).Id);
                foundObjectsRepo.Delete(objFId);
                LoadFoundData();
            }
        }
    }
}
