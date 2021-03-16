class Rental:
    def __init__(self,id,bid,cid,rentDate):
        if id <0:
            raise RentalException("ID must be positve")
        self._id=id
        self._bookId=bid
        self._clientId=cid
        self._rentDate=rentDate
        self._returnDate=None

    @property
    def Id(self):
        return self._id
    @property
    def BookId(self):
        return self._bookId
    @property
    def ClientId(self):
        return self._clientId
    @property
    def RentDate(self):
        return self._rentDate
    @property
    def ReturnDate(self):
        return self._returnDate

    @ReturnDate.setter
    def ReturnDate(self,value):
        self._returnDate=value

    def __str__(self):
        if self.ReturnDate is None:
            return str(self.Id)+" BookID: "+str(self.BookId)+", Client renting it: "+str(self.ClientId)+", Rented at: "+str(self.RentDate)+", is yet to be returned"
        return str(self.Id)+" BookID: "+str(self.BookId)+", Client renting it: "+str(self.ClientId)+", Rented at: "+str(self.RentDate)+", Returned at: "+str(self.ReturnDate)

    def __lt__(self, rental):
        """
        < operator required for sorting the list
        """
        return self.Id < rental.Id
        
        
class RentalException(Exception):
    def __init__(self,msg):
        super().__init__(msg)