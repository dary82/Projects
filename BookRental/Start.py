from ui import *
from BookRepo import *
from ClientRepo import *
from RentalRepo import *
from Service import *
from UndoController import *
from FileTextRepository import *
from FileBinaryRepository import *


class AppStart:
    def __init__(self,fileName):
        self._fileName=fileName
    def start(self):
        f=open(self._fileName,'r')
        lines = f.readlines()
        types=[]
        files=[]
        for l in lines:
            l = l.split('=')
            l[1]=l[1].strip('\n')
            l[1]=l[1].lstrip('"')
            l[1]=l[1].rstrip('"')
            types.append(l[0])
            files.append(l[1])
        br=""
        cr=""
        rr=""
        uc=UndoController()

        if files[0]=='inmemory':
            br = BookRepository(uc)
            cr = ClientRepository(uc)
            rr = RentalRepository(uc)
            br.init_books()
            cr.init_clients()
            rr.init_rentals()
        elif files[0]=='iterable':
            br = BookRepository(uc)
            cr = ClientRepository(uc)
            rr = RentalRepository(uc)
            br.init_books()
            cr.init_clients()
            rr.init_rentals()
        elif files[0]=='text-file':
            br=BookTextRepo(uc,files[1])
            cr=ClientTextRepo(uc,files[2])
            rr=RentalTextRepo(uc,files[3])
        elif files[0] == 'binary-file':
            br = BookBinaryRepo(uc, files[1])
            cr = ClientBinaryRepo(uc, files[2])
            rr = RentalBinaryRepo(uc, files[3])
        s = Service(br, cr, rr, uc)
        ui = UI(br, cr, rr, s, uc)
        ui.start()


ap=AppStart("settings.properties")
ap.start()