from BookRepo import *
from ClientRepo import *
from RentalRepo import *
from Book import *
from Client import *
from Rental import *
import datetime
import pickle

class BookBinaryRepo(BookRepository):
    def __init__(self,uc,fileName):
        BookRepository.__init__(self,uc)
        self._fileName=fileName
        self._loadPickle()

    def store(self,object):
        BookRepository.add_book(self,object)
        self._saveFile()

    def _loadFile(self):
        f=open(self._fileName,'rb')
        return pickle.load(f)

    def _loadPickle(self):
        for obj in self._loadFile():
            self.store(obj)

    def _saveFile(self):
        f=open(self._fileName,'wb')
        pickle.dump(self.Books,f)
        f.close()

    def add_book(self,book):
        msg=BookRepository.add_book(self,book)
        self._saveFile()
        return msg

    def remove_book(self,id):
        msg= BookRepository.remove_book(self,id)
        self._saveFile()
        return msg

    def update_book(self,book):
        msg=BookRepository.update_book(self,book)
        self._saveFile()
        return msg

class ClientBinaryRepo(ClientRepository):
    def __init__(self,uc,fileName):
        ClientRepository.__init__(self,uc)
        self._fileName=fileName
        self._loadPickle()

    def store(self,object):
        ClientRepository.add_client(self,object)
        self._saveFile()

    def _loadFile(self):
        f = open(self._fileName, 'rb')
        return pickle.load(f)

    def _loadPickle(self):
        for obj in self._loadFile():
            self.store(obj)

    def _saveFile(self):
        f = open(self._fileName, 'wb')
        pickle.dump(self.Clients, f)
        f.close()

    def add_client(self,client):
        msg=ClientRepository.add_client(self,client)
        self._saveFile()
        return msg

    def remove_client(self,id):
        msg=ClientRepository.remove_client(self,id)
        self._saveFile()
        return msg

    def update_book(self,client):
        msg=ClientRepository.update_client(self,client)
        self._saveFile()
        return msg


class RentalBinaryRepo(RentalRepository):
    def __init__(self,uc,fileName):
        RentalRepository.__init__(self,uc)
        self._fileName=fileName
        self._loadPickle()

    def store(self,object):
        RentalRepository.add_rental(self,object)
        self._saveFile()

    def _loadFile(self):
        f = open(self._fileName, 'rb')
        return pickle.load(f)

    def _loadPickle(self):
        for obj in self._loadFile():
            self.store(obj)

    def _saveFile(self):
        f = open(self._fileName, 'wb')
        pickle.dump(self.Rentals, f)
        f.close()

    def add_rental(self,rental):
        RentalRepository.add_rental(self,rental)
        self._saveFile()

    def remove_rental(self,id):
        RentalRepository.remove_rental(self,id)
        self._saveFile()

    def rent_book(self,id,bookId,clientId,rentDate):
        msg=RentalRepository.rent_book(self,id,bookId,clientId,rentDate)
        self._saveFile()
        return msg

    def return_book(self,id,returnDate):
        msg=RentalRepository.return_book(self,id,returnDate)
        self._saveFile()
        return msg

    def remove_rentals_book(self,id):
        remove=RentalRepository.remove_rentals_book(self,id)
        self._saveFile()
        return remove

    def remove_rentals_client(self,id):
        remove=RentalRepository.remove_rentals_client(self,id)
        self._saveFile()
        return remove