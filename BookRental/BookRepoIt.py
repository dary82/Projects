from Book import *
from UndoController import *
from Module import *
import random
class BookRepository:
    def __init__(self,undoController):
        d = IterableStorage(data)
        self._bookList = iter(d)
        self._undoController = undoController

    @property
    def Books(self):
        return self._bookList

    def add_book(self,book):
        '''
        Adds a book to the list
        :param:
            -book - A book that contains an Id, title and author
        '''
        for b in self._bookList:
            if book.Id==b.Id:
                raise BookException("Duplicate ID")
        undo = FunctionCall(self.remove_book, book.Id)
        redo = FunctionCall(self.add_book, book)
        op = CascadedOperation(Operation(undo, redo))
        self._undoController.recordOperation(op)
        self._bookList.append(book)
        self._bookList.sort()
        return "Book added successfully!"
    def remove_book(self,id):
        ok = False
        for b in self._bookList:
            if b.Id == id:
                ok = True
                undo = FunctionCall(self.add_book, b)
                redo = FunctionCall(self.remove_book, id)
                op = CascadedOperation(Operation(undo, redo))
                self._undoController.recordOperation(op)
                self._bookList.remove(b)

        if ok == False:
            return "Could not find ID to remove"
        else:
            return "Removed successfully!"

    def update_book(self,book):
        '''
        Updates a book from the list
        :param:
            -book - the object of type Book, that contains the new parameters
        '''
        for b in self._bookList:
            if b.Id==book.Id:
                undo=FunctionCall(self.update_book,Book(b.Id,b.Title,b.Author))
                redo=FunctionCall(self.update_book,book)
                op=CascadedOperation(Operation(undo,redo))
                self._undoController.recordOperation(op)
                b.Title=book.Title
                b.Author=book.Author
                return("Updated succesfully")
        return("Could not find the ID to upgrade")

    def find_book_id(self,id):
        for b in self._bookList:
            if b.Id==id:
                return b
        return "There was no book with that ID"

    def find_book_title(self,title):
        findList=[]
        title=title.lower()
        for b in self._bookList:
            if b.Title.lower().find(title)!=-1:
                findList.append(b)
        if len(findList)==0:
            raise BookException("Title was not found")
        return findList

    def find_book_author(self,author):
        findList=[]
        author=author.lower()
        for b in self._bookList:
            if b.Author.lower().find(author)!=-1:
                findList.append(b)
        if len(findList)==0:
            raise BookException("Author was not found")
        return findList

    def init_books(self):
        self.__books=["In Search of Lost Time by Marcel Proust","Ulysses by James Joyce","Don Quixote by Miguel de Cervantes","The Great Gatsby by F. Scott Fitzgerald","Moby Dick by Herman Melville","One Hundred Years of Solitude by Gabriel Garcia Marquez","War and Peace by Leo Tolstoy","Hamlet by William Shakespeare","Lolita by Vladimir Nabokov","The Odyssey by Homer","The Brothers Karamazov by Fyodor Dostoyevsky","The Adventures of Huckleberry Finn by Mark Twain","Madame Bovary by Gustave Flaubert","The Catcher in the Rye by J. D. Salinger","The Divine Comedy by Dante Alighieri","Hotul de umbre by Marc Levy","Alice's Adventures in Wonderland by Lewis Carroll","Wuthering Heights by Emily Brontë","To the Lighthouse by Virginia Woolf","Pride and Prejudice by Jane Austen"]
        self.__numbers=[]
        i=0
        while len(self._bookList)!=10:
            nr=random.randint(0,19)
            if nr not in self.__numbers:
                self.__numbers.append(nr)
                book=self.__books[nr]
                idx=book.find(" by ")
                title=book[:idx]
                author=book[idx+4:]
                self._bookList.append(Book(i,title,author))
                i+=1