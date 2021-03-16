from Book import *
from Client import *
from Rental import *
from BookRepo import *
from ClientRepo import *
from RentalRepo import *
from Service import *
from UndoController import *
import datetime
class UI:
    def __init__(self,BookRepo,ClientRepo,RentalRepo,Service,UndoController):
        self._bookRepo=BookRepo
        self._clientRepo=ClientRepo
        self._rentalRepo=RentalRepo
        self._service=Service
        self._undoController=UndoController

    def book_ui(self):
        print("1. Add book")
        print("2. Remove book")
        print("3. Update book")
        print("4. List books")
        print("0. Return")
        while True:
            option = input(">>")
            try:
                if option == "1":
                    self.add_book_ui()
                elif option == "2":
                    self.remove_book_ui()
                elif option == "3":
                    self.update_book_ui()
                elif option == "4":
                    self.list_books()
                elif option=="0":
                    self.print_menu()
                    return
                else:
                    print("Bad command")
            except BookException as be:
                print(be)
            except RentalException as re:
                print(re)
            except ServiceException as se:
                print(se)
            except UndoException as ue:
                print(ue)

    def client_ui(self):
        print("1. Add a client")
        print("2. Remove a client")
        print("3. Update a client")
        print("4. List clients")
        print("0. Return")
        while True:
            option = input(">>")
            try:
                if option == "1":
                    self.add_client_ui()
                elif option == "2":
                    self.remove_client_ui()
                elif option == "3":
                    self.update_client_ui()
                elif option == "4":
                    self.list_clients()
                elif option=="0":
                    self.print_menu()
                    return
                else:
                    print("Bad command")
            except ClientException as ce:
                print(ce)
            except RentalException as re:
                print(re)
            except ServiceException as se:
                print(se)
            except UndoException as ue:
                print(ue)
    def rental_ui(self):
        print("1. Rent book")
        print("2. Return book")
        print("3. List rentals")
        print("0. Return")
        while True:
            option = input(">>")
            try:
                if option == "1":
                    self.rent_book_ui()
                elif option == "2":
                    self.return_book_ui()
                elif option == "3":
                    self.list_rentals()
                elif option=="0":
                    self.print_menu()
                    return
                else:
                    print("Bad command")
            except BookException as be:
                print(be)
            except ClientException as ce:
                print(ce)
            except RentalException as re:
                print(re)
            except ServiceException as se:
                print(se)
            except UndoException as ue:
                print(ue)

    def add_book_ui(self):
        '''
        Reads the book parameters(id,title,author)
        '''
        id=input("Book ID= ")
        title=input("title= ")
        author=input("author= ")
        print(self._service.add_book(id,title,author))
    
    def remove_book_ui(self):
        '''
        Reads the book id for the book that will be removed
        Raises ServiceException if the id is not an integer or the id is negative
        '''
        id=input("Book ID= ")
        msg=self._service.remove_book(id)
        print(msg)

    def update_book_ui(self):
        '''
        Reads the book parameters(id,title,author) for updating the existent book of the same id with the new params.
        Raises BookException if id is not an integer
        '''
        id=input("Book ID= ")
        title=input("title= ")
        author=input("author= ")
        msg=self._service.update_book(id,title,author)
        print(msg)

    def list_books(self):
        '''
        Prints the books from the list
        '''
        if len(self._bookRepo.Books)==0:
            print("List is empty")
        for b in self._bookRepo.Books:
            print(b)

    def add_client_ui(self):
        '''
        Reads the client parameters(id,name)
        Raises ClientException in case the id is not an integer
        '''
        id=input("Client ID= ")
        name=input("Name= ")
        print(self._service.add_client(id,name))
    
    def remove_client_ui(self):
        '''
        Reads the client id of the client that will be removed
        Raises ClientException in case the id is not an integer or the id is negative
        '''
        id=input("Client ID= ")
        msg=self._service.remove_client(id)
        print(msg)

    def update_client_ui(self):
        '''
        Reads the client parameters(id,name) for updating the existent client of the same id with the new name
        Raises ClientException in case the id is not an integer
        '''
        id=input("Client ID= ")
        name=input("Name= ")
        msg=self._service.update_client(id,name)
        print(msg)

    def list_clients(self):
        '''
        Prints the clients from the list
        '''
        if len(self._clientRepo.Clients)==0:
            print("List is empty")
        for c in self._clientRepo.Clients:
            print(c)

    def list_rentals(self):
        for r in self._rentalRepo._rentalList:
            print(r)
    def list_rentals_client(self,listC):
        for r in listC:
            print(r)


    def rent_book_ui(self):
        id = input("ID rental: ")
        idc=input("ID Client: ")
        idb=input("ID book: ")
        day=input("Day the rental took place: ")
        month=input("Month the rental took place(number): ")
        year=input("Year the rental took place: ")
        print(self._service.rent_book(id,idb,idc,day,month,year))

    def return_book_ui(self):
        idc=input("ID client: ")
        idc=self._service.validate_return_idc(idc)
        rentals=self._rentalRepo.get_rentals(idc)
        if rentals==[]:
            print("There is no rental to be completed!")
            return
        self.list_rentals_client(rentals)
        id=input("ID rental for return: ")
        returnDay=input("Day the book was returned: ")
        returnMonth=input("Month the book was returned(number): ")
        returnYear=input("Year the book was returned: ")
        print(self._service.return_book(rentals,id,idc,returnDay,returnMonth,returnYear))

    def find_book_ui(self):
        print("1. Find by id")
        print("2. Find by title")
        print("3. Find by author")
        print("0. Return")
        while True:
            option = input(">>")
            try:
                if option == '1':
                    id = input("ID= ")
                    if id.isdigit() == False:
                        raise BookException("ID must be an integer")
                    id = int(id)
                    print(self._bookRepo.find_book_id(id))
                elif option == '2':
                    title = input("Title= ")
                    if title == "":
                        raise BookException("Title cannot be empty")
                    findList = []
                    findList.extend(self._bookRepo.find_book_title(title))
                    for b in findList:
                        print(b)
                elif option == '3':
                    author = input("Author= ")
                    if author == "":
                        raise BookException("Author cannot be empty")
                    findList = []
                    findList.extend(self._bookRepo.find_book_author(author))
                    for b in findList:
                        print(b)
                elif option=='0':
                    self.print_menu()
                    return
                else:
                    print("Bad command")
            except BookException as be:
                print(be)
            except RentalException as re:
                print(re)
            except ServiceException as se:
                print(se)
            except UndoException as ue:
                print(ue)

    def find_client_ui(self):
        print("1. Find by id")
        print("2. Find by name")
        print("0. Return")

        while True:
            option=input(">>")
            try:
                if option=='1':
                    id=input("ID= ")
                    if id.isdigit()==False:
                        raise ClientException("ID must be an integer")
                    id=int(id)
                    print(self._clientRepo.find_client_id(id))
                elif option=='2':
                    name=input("Name= ")
                    if name=="":
                        raise ClientException("Name cannot be empty")
                    findList=[]
                    findList.extend(self._clientRepo.find_client_name(name))
                    for c in findList:
                        print(c)
                elif option=='0':
                    self.print_menu()
                    return
                else:
                    print("Bad command")
            except ClientException as ce:
                print(ce)
            except RentalException as re:
                print(re)
            except ServiceException as se:
                print(se)
            except UndoException as ue:
                print(ue)

    def most_rented_books_ui(self):
        for b in self._service.most_rented_books():
            print(b)

    def most_active_clients_ui(self):
        for c in self._service.most_active_clients():
            print(c)

    def most_rented_authors_ui(self):
        for a in self._service.most_rented_authors():
            print(a)

    def statistics_ui(self):
        print("1. Most rented books")
        print("2. Most active clients")
        print("3. Most rented author")
        print("0. Return")
        while True:
            option=input(">>")
            try:
                if option == '1':
                    self.most_rented_books_ui()
                elif option == '2':
                    self.most_active_clients_ui()
                elif option == '3':
                    self.most_rented_authors_ui()
                elif option=='0':
                    self.print_menu()
                    return
                else:
                    print("Bad command")
            except BookException as be:
                print(be)
            except ClientException as ce:
                print(ce)
            except RentalException as re:
                print(re)
            except ServiceException as se:
                print(se)
            except UndoException as ue:
                print(ue)

    def print_menu(self):
        print("1. Book menu")
        print("2. Client menu")
        print("3. Rental menu")
        print("4. Find a book")
        print("5. Find a client")
        print("6. Statistics")
        print("7. Undo")
        print("8. Redo")
        print("0. Exit")

    def start(self):
        self.print_menu()
        while True:
            choice=input(">>")
            try:
                if choice=="1":
                    self.book_ui()
                elif choice=="2":
                    self.client_ui()
                elif choice=="3":
                    self.rental_ui()
                elif choice=="4":
                    self.find_book_ui()
                elif choice=="5":
                    self.find_client_ui()
                elif choice=="6":
                    self.statistics_ui()
                elif choice=="7":
                    self._undoController.undo()
                elif choice=="8":
                    self._undoController.redo()
                elif choice=="0":
                    return
                else:
                    print("Bad command")
            except BookException as be:
                print(be)
            except ClientException as ce:
                print(ce)
            except RentalException as re:
                print(re)
            except ServiceException as se:
                print(se)
            except UndoException as ue:
                print(ue)