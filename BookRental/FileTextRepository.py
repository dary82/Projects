from BookRepo import *
from ClientRepo import *
from RentalRepo import *
from Book import *
from Client import *
from Rental import *
import datetime


class BookTextRepo(BookRepository):
    def __init__(self, uc, fileName):
        BookRepository.__init__(self, uc)
        self._fileName = fileName
        self._loadFile()

    def store(self, object):
        BookRepository.add_book(self, object)
        self._saveFile()

    def _loadFile(self):
        f = open(self._fileName, 'r')
        lines = f.readlines()
        for l in lines:
            l = l.rstrip('\n')
            l = l.split(';')
            book = Book(int(l[0]), l[1], l[2])
            self.store(book)

    def _saveFile(self):
        f = open(self._fileName, 'w')
        for i in self.Books:
            f.write(str(i.Id) + ";" + i.Title + ";" + i.Author + '\n')

    def add_book(self, book):
        msg = BookRepository.add_book(self, book)
        self._saveFile()
        return msg

    def remove_book(self, id):
        msg = BookRepository.remove_book(self, id)
        self._saveFile()
        return msg

    def update_book(self, book):
        msg = BookRepository.update_book(self, book)
        self._saveFile()
        return msg


class ClientTextRepo(ClientRepository):
    def __init__(self, uc, fileName):
        ClientRepository.__init__(self, uc)
        self._fileName = fileName
        self._loadFile()

    def store(self, object):
        ClientRepository.add_client(self, object)
        self._saveFile()

    def _loadFile(self):
        f = open(self._fileName, 'r')
        lines = f.readlines()
        for l in lines:
            l = l.rstrip('\n')
            l = l.split(';')
            client = Client(int(l[0]), l[1])
            self.store(client)

    def _saveFile(self):
        f = open(self._fileName, 'w')
        for i in self.Clients:
            f.write(str(i.Id) + ";" + i.Name + '\n')

    def add_client(self, client):
        msg = ClientRepository.add_client(self, client)
        self._saveFile()
        return msg

    def remove_client(self, id):
        msg = ClientRepository.remove_client(self, id)
        self._saveFile()
        return msg

    def update_book(self, client):
        msg = ClientRepository.update_client(self, client)
        self._saveFile()
        return msg


class RentalTextRepo(RentalRepository):
    def __init__(self, uc, fileName):
        RentalRepository.__init__(self, uc)
        self._fileName = fileName
        self._loadFile()

    def store(self, object):
        RentalRepository.add_rental(self, object)
        self._saveFile()

    def _loadFile(self):
        f = open(self._fileName, 'r')
        lines = f.readlines()
        for l in lines:
            l = l.rstrip('\n')
            l = l.split(';')
            date = datetime.datetime.strptime(l[3], '%Y-%M-%d').date()
            rental = Rental(int(l[0]), int(l[1]), int(l[2]), date)
            if l[4] is not None:
                date = datetime.datetime.strptime(l[4], '%Y-%M-%d').date()
                rental.ReturnDate = date
            self.store(rental)

    def _saveFile(self):
        f = open(self._fileName, 'w')
        for i in self.Rentals:
            f.write(str(i.Id) + ";" + str(i.BookId) + ";" + str(i.ClientId) + ";" + str(i.RentDate) + ";" + str(
                i.ReturnDate) + '\n')

    def add_rental(self, rental):
        RentalRepository.add_rental(self, rental)
        self._saveFile()

    def remove_rental(self, id):
        RentalRepository.remove_rental(self, id)
        self._saveFile()

    def rent_book(self, id, bookId, clientId, rentDate):
        msg = RentalRepository.rent_book(self, id, bookId, clientId, rentDate)
        self._saveFile()
        return msg

    def return_book(self, id, returnDate):
        msg = RentalRepository.return_book(self, id, returnDate)
        self._saveFile()
        return msg

    def remove_rentals_book(self, id):
        remove = RentalRepository.remove_rentals_book(self, id)
        self._saveFile()
        return remove

    def remove_rentals_client(self, id):
        remove = RentalRepository.remove_rentals_client(self, id)
        self._saveFile()
        return remove
