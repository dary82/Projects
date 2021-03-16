class Client:
    def __init__(self,id,name):
        if id<0:
            raise ClientException("Client ID cannot be negative")
        self._id=id
        self.Name=name

    @property
    def Id(self):
        return self._id
    @property
    def Name(self):
        return self._name
    
    @Name.setter
    def Name(self,value):
        if value=="":
            raise ClientException("Client name cannot be empty")
        self._name=value
    
    def __str__(self):
        return str(self.Id)+" "+self.Name

    def __lt__(self, client):
        """
        < operator required for sorting the list
        """
        return self.Id < client.Id

class ClientException(Exception):
    def __init__(self,msg):
        super().__init__(msg)