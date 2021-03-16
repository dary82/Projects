from Book import *
from Client import *
from Rental import *
from BookRepo import *
from ClientRepo import *
from RentalRepo import *
from Service import *
from UndoController import *
import unittest


class TestBook(unittest.TestCase):
    def test_book(self):
        b=Book(1,"Aurora","John")
        self.assertEqual(b.Id,1)
        self.assertEqual(b.Title,"Aurora")
        self.assertEqual("John",b.Author)
    def test_book_again(self):
        with self.assertRaises(BookException):
            Book(-1,"Aurora","John")
        with self.assertRaises(BookException):
            Book(1,"","John")
        with self.assertRaises(BookException):
            Book(1,"Aurora","")


class TestClient(unittest.TestCase):
    def test_client(self):
        c=Client(1,"Bob Albert")
        self.assertEqual(c.Id,1)
        self.assertEqual("Bob Albert",c.Name)

    def test_client_again(self):
        with self.assertRaises(ClientException):
            Client(-1,"Bob Albert")
        with self.assertRaises(ClientException):
            Client(1,"")

class TestBookRepo(unittest.TestCase):
    def test_add(self):
        b1 = Book(0, "Aurora", "John")
        b2 = Book(1, "Night", "Austin")
        b3 = Book(2, "Music", "David")
        uc = UndoController()
        br=BookRepository(uc)
        br.add_book(b1)
        self.assertEqual(1,len(br.Books))
        br.add_book(b2)
        br.add_book(b3)
        self.assertEqual(3, len(br.Books))
        with self.assertRaises(BookException):
            br.add_book(b1)

    def test_update(self):
        b1 = Book(0, "Aurora", "John")
        uc = UndoController()
        br=BookRepository(uc)
        br.add_book(b1)
        self.assertEqual(br.Books[0].Title, "Aurora")
        b2 = Book(0, "File", "Patrick")
        self.assertEqual(br.update_book(b2),"Updated succesfully")
        self.assertEqual(br.Books[0].Title,"File")
        b3 = Book(1, "File", "Patrick")
        self.assertEqual(br.update_book(b3),"Could not find the ID to upgrade")

class TestClientRepo(unittest.TestCase):
    def test_add(self):
        c1 = Client(0, "Bob Albert")
        c2 = Client(1, "John Gold")
        c3 = Client(2, "Andrew Davis")
        uc=UndoController()
        cr=ClientRepository(uc)
        cr.add_client(c1)
        self.assertEqual(1,len(cr.Clients))
        cr.add_client(c2)
        cr.add_client(c3)
        self.assertEqual(3, len(cr.Clients))
        with self.assertRaises(ClientException):
            cr.add_client(c1)

    def test_update(self):
        c1 = Client(0, "Bob Albert")
        uc = UndoController()
        cr=ClientRepository(uc)
        cr.add_client(c1)
        self.assertEqual(cr.Clients[0].Name, "Bob Albert")
        c2 = Client(0, "John Gold")
        self.assertEqual(cr.update_client(c2),"Updated succesfully")
        self.assertEqual(cr.Clients[0].Name,"John Gold")
        c3 = Client(1, "Andrew Davis")
        self.assertEqual(cr.update_client(c3),"Could not find the ID to upgrade")

class TestService(unittest.TestCase):
    def test_remove_book(self):
        uc = UndoController()
        br=BookRepository(uc)
        b1 = Book(0, "Aurora", "John")
        b2 = Book(1, "Night", "Austin")
        b3 = Book(2, "Music", "David")
        br.add_book(b1)
        br.add_book(b2)
        br.add_book(b3)
        cr=ClientRepository(uc)
        rr=RentalRepository(uc)
        s=Service(br.Books,cr.Clients,rr.Rentals,br,cr,rr,uc)
        list=br.Books
        self.assertEqual(len(list), 3)
        with self.assertRaises(ServiceException):
            s.remove_book("list[1].Id")

    def test_remove_client(self):
        uc = UndoController()
        br = BookRepository(uc)
        cr = ClientRepository(uc)
        c1 = Client(0, "Bob Albert")
        c2 = Client(1, "John Gold")
        c3 = Client(2, "Andrew Davis")
        cr.add_client(c1)
        cr.add_client(c2)
        cr.add_client(c3)
        rr = RentalRepository(uc)
        s = Service(br.Books, cr.Clients, rr.Rentals,br,cr,rr,uc)
        list = cr.Clients
        self.assertEqual(len(list), 3)
        with self.assertRaises(ServiceException):
            s.remove_client("list[1].Id")


if __name__ == '__main__':
    unittest.main()
