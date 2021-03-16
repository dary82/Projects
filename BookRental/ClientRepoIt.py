from Client import *
from UndoController import *
from Module import *
import random
class ClientRepository:
    def __init__(self,undoController):
        d = IterableStorage(data)
        self._clientList = iter(d)
        self._undoController = undoController

    @property
    def Clients(self):
        return self._clientList

    def add_client(self,client):
        '''
        Adds a client to the list
        :param:
            -client - A client that contains an id, name
        '''
        for c in self._clientList:
            if client.Id==c.Id:
                raise ClientException("Duplicate ID")
        undo = FunctionCall(self.remove_client, client.Id)
        redo = FunctionCall(self.add_client, client)
        op = CascadedOperation(Operation(undo, redo))
        self._undoController.recordOperation(op)
        self._clientList.append(client)
        self._clientList.sort()
        return "Client added successfully!"

    def remove_client(self,id):
        ok=False
        for c in self._clientList:
            if c.Id==id:
                ok=True
                undo=FunctionCall(self.add_client,c)
                redo=FunctionCall(self.remove_client,id)
                op=CascadedOperation(Operation(undo,redo))
                self._undoController.recordOperation(op)
                self._clientList.remove(c)

        if ok==False:
            return "Could not find ID to remove"
        else:
            return "Removed successfully!"
    def update_client(self,client):
        '''
        Updates a client from the list
        :param:
            -client - An object that contains the new parameters
        '''
        for c in self._clientList:
            if c.Id==client.Id:
                undo = FunctionCall(self.update_client, Client(c.Id, c.Name))
                redo = FunctionCall(self.update_client, client)
                op = CascadedOperation(Operation(undo, redo))
                self._undoController.recordOperation(op)
                c.Name=client.Name
                return ("Updated succesfully")
        return ("Could not find the ID to upgrade")

    def find_client_id(self,id):
        for c in self._clientList:
            if c.Id==id:
                return c
        return "There was no client with that ID"

    def find_client_name(self,name):
        findList=[]
        name=name.lower()
        for c in self._clientList:
            if c.Name.lower().find(name)!=-1:
                findList.append(c)
        if len(findList)==0:
            raise ClientException("Name was not found")
        return findList

    def init_clients(self):
        self.__names=["Denise Hall","Carroll Bailey","Daryl Flores","Daisy Munoz","Keith Wood","Dominic Greer","Jim Rodriquez","Garry Newton","Natasha Davidson","Dora Cortez","Dominic Peters","Elvira Fernandez","Olive Lynch","Emilio Romero","Elmer Kennedy","Melvin Lambert","Alexander Blair","Sidney Neal","Angela Burns","Deborah Colon"]
        self.__numbers=[]
        i=0
        while len(self._clientList)!=10:
            nr=random.randint(0,19)
            if nr not in self.__numbers:
                self.__numbers.append(nr)
                name=self.__names[nr]
                self._clientList.append(Client(i,name))
                i+=1