from Book import *
from BookRepo import *
from Client import *
from ClientRepo import *
from Rental import *
from RentalRepo import *
import datetime

class Service:
    def __init__(self,bookrepo,clientrepo,rentalrepo,undocontroller):
        self._bookList = bookrepo
        self._clientList = clientrepo
        self._rentalList = rentalrepo
        self._undoController = undocontroller

    def add_book(self,id,title,author):
        if not id.isdigit():
            raise BookException("ID is a positive integer")
        id=int(id)
        book = Book(id, title, author)
        msg=self._bookList.add_book(book)
        return msg

    def update_book(self,id,title,author):
        if id.isdigit() == False:
            raise BookException("ID is a positive integer")
        id = int(id)
        book = Book(id, title, author)
        return self._bookList.update_book(book)

    def add_client(self,id,name):
        if id.isdigit()==False:
            raise ClientException("ID is a positive integer")
        id=int(id)
        client = Client(id, name)
        return self._clientList.add_client(client)

    def update_client(self,id,name):
        if id.isdigit() == False:
            raise ClientException("ID is a positive integer")
        id = int(id)
        client = Client(id, name)
        return self._clientList.update_client(client)

    def remove_book(self,id):
        '''
        Removes a book from the book list by ID and the rentals of the deleted book
        :param:
            -id - id of the book that will be removed
        '''
        if id.isdigit()==False:
            raise ServiceException("ID is a positive integer")
        id=int(id)
        if id<0:
            raise ServiceException("ID cannot be negative")
        msg=self._bookList.remove_book(id)
        if msg=="Removed successfully!":
            op = self._undoController.pop()
            ops = self._rentalList.remove_rentals_book(id)
            for o in ops:
                op.add(o)
            self._undoController.recordOperation(op)
        return msg

    def remove_client(self,id):
        '''
        Removes a client from the list by ID and the rentals of the deleted client
        :param:
            -id - id of the client that will be removed
        '''
        if id.isdigit()==False:
            raise ServiceException("ID is a positive integer")
        id=int(id)
        if id<0:
            raise ServiceException("ID cannot be negative")
        msg=self._clientList.remove_client(id)
        if msg=="Removed successfully!":
            op = self._undoController.pop()
            ops = self._rentalList.remove_rentals_client(id)
            for o in ops:
                op.add(o)
            self._undoController.recordOperation(op)
        return msg

    def rent_book(self,id,idb,idc,day,month,year):
        if idc.isdigit()==False or idb.isdigit()==False or id.isdigit()==False or day.isdigit()==False or month.isdigit()==False or year.isdigit()==False:
            raise RentalException("The values must be numbers")
        idc=int(idc)
        idb=int(idb)
        id=int(id)
        if idc<0 or idb<0 or id<0:
            raise RentalException("ID must be a positive integer")
        day=int(day)
        month=int(month)
        year=int(year)
        if month<1 or month>12:
            raise RentalException("Month number between 1-12")
        if month in [1, 3, 5, 7, 8, 10, 12]:
            if day < 1 or day > 31:
                raise RentalException("Day number between 1-31 for the given month")
        elif month in [4, 6, 9, 11]:
            if day < 1 or day > 30:
                raise RentalException("Day number between 1-30 for the given month")
        else:
            if day < 1 or day > 28:
                raise RentalException("Day number between 1-28 for the given month")
        date = datetime.date(year, month, day)
        existsB=False
        for b in self._bookList:
            if b.Id==idb:
                existsB=True
        existsC = False
        for c in self._clientList:
            if c.Id == idc:
                existsC = True
        if existsB==True and existsC==True:
            return self._rentalList.rent_book(id, idb, idc, date)
        if existsB==False:
            raise RentalException("Book ID could not be found!")
        if existsC==False:
            raise RentalException("Client ID could not be found!")

    def validate_return_idc(self,idc):
        if idc.isdigit()==False:
            raise RentalException("ID must be a number")
        idc=int(idc)
        return idc

    def return_book(self,rentals,id,idc,returnDay,returnMonth,returnYear):
        if id.isdigit()==False:
            raise RentalException("ID must be a number")
        id=int(id)
        ex = False
        for r in rentals:
            if r.Id == id:
                ex = True
                rentDate = r.RentDate
        if ex == False:
            raise RentalException("ID not in the list")
        if returnDay.isdigit()==False or returnMonth.isdigit()==False or returnYear.isdigit()==False:
            raise RentalException("The values must be numbers")
        returnDay=int(returnDay)
        returnMonth=int(returnMonth)
        returnYear=int(returnYear)
        day=rentDate.day
        month=rentDate.month
        year=rentDate.year

        if returnMonth<1 or returnMonth>12:
            raise RentalException("Month number between 1-12")
        if returnMonth in [1, 3, 5, 7, 8, 10, 12]:
            if returnDay < 1 or returnDay > 31:
                raise RentalException("Day number between 1-31 for the given month")
        elif returnMonth in [4, 6, 9, 11]:
            if returnDay < 1 or returnDay > 30:
                raise RentalException("Day number between 1-30 for the given month")
        else:
            if returnDay < 1 or returnDay > 28:
                raise RentalException("Day number between 1-28 for the given month")
        if returnYear<year:
            raise RentalException("The returning date is before the rental date")
        elif returnYear==year:
            if returnMonth<month:
                raise RentalException("The returning date is before the rental date")
            elif returnMonth==month:
                if returnDay<day:
                    raise RentalException("The returning date is before the rental date")
        returnDate=datetime.date(returnYear,returnMonth,returnDay)
        return self._rentalList.return_book(id, returnDate)

    def most_rented_books(self):
        bookList=self._bookList.Books
        rentalList=self._rentalList.Rentals
        result=[]
        for i in range(len(bookList)):
            appearances=0
            for r in rentalList:
                if bookList[i].Id == r.BookId:
                    appearances += 1
            result.append(RentedBooksCount(appearances, bookList[i]))
        result.sort(reverse=True)
        return result


    def most_active_clients(self):
        clientList = self._clientList.Clients
        rentalList = self._rentalList.Rentals
        result=[]
        for i in range(len(clientList)):
            days=0
            for r in rentalList:
                if clientList[i].Id==r.ClientId:
                    if r.ReturnDate==r.RentDate:
                        days+=1
                    else:
                        days+=(r.ReturnDate-r.RentDate).days
            result.append(ClientDays(days,clientList[i]))
        result.sort(reverse=True)
        return result

    def most_rented_authors(self):
        listBookCount=self.most_rented_books()
        authors=[]
        for b in listBookCount:
            authors.append(b.book.Author)
        return authors

class RentedBooksCount:
    def __init__(self, count, book):
        self._count=count
        self._book = book

    @property
    def book(self):
        return self._book
    @property
    def getCount(self):
        return self._count

    def __lt__(self, rentedBook):
        """
        < operator required for sorting the list
        """
        return self.getCount < rentedBook.getCount

    def __str__(self):
        return str(self._count)+ " for book: "+str(self._book)

class ClientDays:
    def __init__(self, days, client):
        self._days=days
        self._client = client

    @property
    def client(self):
        return self._client
    @property
    def getDays(self):
        return self._days

    def __lt__(self, clientDay):
        """
        < operator required for sorting the list
        """
        return self.getDays < clientDay.getDays

    def __str__(self):
        return str(self._days)+ " for client: "+str(self._client)

class AuthorCount:
    def __init__(self, count, author):
        self._count=count
        self._author = author

    @property
    def author(self):
        return self._author
    @property
    def getCount(self):
        return self._count

    def __lt__(self, authorCount):
        """
        < operator required for sorting the list
        """
        return self.getCount < authorCount.getCount

    def __str__(self):
        return str(self._count)+ " for client: "+str(self._author)


class ServiceException(Exception):
    def __init__(self,msg):
        super().__init__(msg)