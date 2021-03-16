from Rental import *
from UndoController import *
from Module import *
import random
import datetime


class RentalRepository:
    def __init__(self, undoController):
        d = IterableStorage(data)
        self._rentalList = iter(d)
        self._undoController = undoController

    @property
    def Rentals(self):
        return self._rentalList

    def add_rental(self, rental):
        for i in self._rentalList:
            if i.Id == rental.Id:
                raise RentalException("Rental ID already exists!")
        undo = FunctionCall(self.remove_rental, id)
        redo = FunctionCall(self.add_rental, rental)
        op = CascadedOperation(Operation(undo, redo))
        self._undoController.recordOperation(op)
        self._rentalList.append(rental)
        self._rentalList.sort()

    def remove_rental(self, id):
        ok = False
        for r in self._rentalList:
            if r.Id == id:
                ok = True
                undo = FunctionCall(self.add_rental, r)
                redo = FunctionCall(self.remove_rental, id)
                op = CascadedOperation(Operation(undo, redo))
                self._undoController.recordOperation(op)
                self._rentalList.remove(r)

        if ok == False:
            raise RentalException("ID does not exist!")

    def remove_rentals_book(self, id):
        removed = []
        i = 0
        while i < len(self._rentalList):
            if self._rentalList[i].BookId == id:
                undo = FunctionCall(self.add_rental, self._rentalList[i])
                redo = FunctionCall(self.remove_rental, self._rentalList[i].Id)
                op = CascadedOperation(Operation(undo, redo))
                removed.append(op)
                self._rentalList.remove(self._rentalList[i])
            else:
                i += 1
        return removed

    def remove_rentals_client(self, id):
        removed = []
        i = 0
        while i < len(self._rentalList):
            if self._rentalList[i].ClientId == id:
                undo = FunctionCall(self.add_rental, self._rentalList[i])
                redo = FunctionCall(self.remove_rental, self._rentalList[i].Id)
                op = CascadedOperation(Operation(undo, redo))
                removed.append(op)
                self._rentalList.remove(self._rentalList[i])
            else:
                i += 1
        return removed

    def rent_book(self, id, bookId, clientId, rentDate):
        for r in self._rentalList:
            if r.Id == id:
                raise RentalException("Duplicate ID")
        for r in self._rentalList:
            if r.BookId == bookId and r.ReturnDate == None:
                raise RentalException("Book already rented")
        rental = Rental(id, bookId, clientId, rentDate)
        undo = FunctionCall(self.remove_rental, id)
        redo = FunctionCall(self.rent_book, id, bookId, clientId, rentDate)
        op = CascadedOperation(Operation(undo, redo))
        self._undoController.recordOperation(op)
        self._rentalList.append(rental)
        return "Rental added successfully!"

    def return_book(self, id, returnDate):
        for r in self._rentalList:
            if r.Id == id:
                undo = FunctionCall(self.return_book, id, None)
                redo = FunctionCall(self.return_book, id, returnDate)
                op = CascadedOperation(Operation(undo, redo))
                self._undoController.recordOperation(op)
                r.ReturnDate = returnDate
                return ("Returned book!")

    def get_rentals(self, clientID):
        rentals = []
        for r in self._rentalList:
            if r.ClientId == clientID and r.ReturnDate == None:
                rentals.append(r)
        return rentals

    def init_rentals(self):
        _books = []
        _clients = []
        i = 0
        while len(self._rentalList) != 10:
            book = random.randint(0, 9)
            client = random.randint(0, 9)
            renty = 2019
            rentm = random.randint(1, 12)
            if rentm in [1, 3, 5, 7, 8, 10, 12]:
                rentd = random.randint(1, 31)
            elif rentm in [4, 6, 9, 11]:
                rentd = random.randint(1, 30)
            else:
                rentd = random.randint(1, 28)
            returny = 2019
            returnm = random.randint(rentm, 12)
            if returnm == rentm:
                if returnm in [1, 3, 5, 7, 8, 10, 12]:
                    returnd = random.randint(rentd, 31)
                elif returnm in [4, 6, 9, 11]:
                    returnd = random.randint(rentd, 30)
                else:
                    returnd = random.randint(rentd, 28)
            else:
                if returnm in [1, 3, 5, 7, 8, 10, 12]:
                    returnd = random.randint(1, 31)
                elif returnm in [4, 6, 9, 11]:
                    returnd = random.randint(1, 30)
                else:
                    returnd = random.randint(1, 28)
            rentdate = datetime.date(renty, rentm, rentd)
            returndate = datetime.date(returny, returnm, returnd)
            exists = False
            for r in self._rentalList:
                if r.BookId == book and r.ReturnDate == None:
                    exists = True
            if exists == False:
                rental = Rental(i, book, client, rentdate)
                rental.ReturnDate = returndate
                self._rentalList.append(rental)
                i += 1