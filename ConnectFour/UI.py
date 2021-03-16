from Domain import *
from Service import *

class UI:
    def __init__(self,game):
        self._game=game

    def _readPlayerMove(self):
        cmd=input(">>")
        if cmd not in ['0','1','2','3','4','5','6']:
            raise ValueError("Bad input")
        return int(cmd)

    def start(self):
        b=self._game.getBoard()
        playerMove=True
        while True:
            print(b)
            try:
                if playerMove == True:
                    column = self._readPlayerMove()
                    self._game.playerMove(column)
                else:
                    self._game.computerMove()
                playerMove = not playerMove
            except GameWonException:
                print(b)
                if playerMove == True:
                    print("Congratulations! You win!")
                else:
                    print("You were defeated!")
                return
            except GameDrawException:
                print(b)
                print("Game is a draw!")
                return
            except ValueError as ve:
                print(ve)
                continue

b=Board()
choice=""
while choice not in ["0","1","2","3"]:
    print("Levels of difficulty:")
    print("1 - Very Easy")
    print("2 - Easy")
    print("3 - Intermediate")
    print("0 - Exit")
    choice=input(">>")

if choice=="0":
    exit(0)
if choice=="1":
    ai=AlgorithmLvl1()
elif choice=="2":
    ai=AlgorithmLvl2()
else:
    ai = AlgorithmLvl3()
g=Game(b,ai)
ui=UI(g)
ui.start()