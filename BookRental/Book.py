class Book:
    def __init__(self,id,title,author):
        if id<0:
            raise BookException("Book ID cannot be negative")
        self._id=id
        self.Title=title
        self.Author=author
    
    @property
    def Id(self):
        return self._id
    @property
    def Title(self):
        return self._title
    @property
    def Author(self):
        return self._author

    @Title.setter
    def Title(self,value):
        if value=="":
            raise BookException("Title cannot be empty")
        self._title=value
    @Author.setter
    def Author(self,value):
        if value=="":
            raise BookException("Author name cannot be empty")
        self._author=value

    def __str__(self):
        return str(self.Id)+" "+self.Title+" -- "+self.Author

    def __lt__(self, book):
        """
        < operator required for sorting the list
        """
        return self.Id < book.Id

class BookException(Exception):
    def __init__(self,msg):
        super().__init__(msg)