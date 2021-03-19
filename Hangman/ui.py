from service import *
from repoSentence import *
from game import *


class UI:
    def __init__(self,serviceSentance):
        self._service=serviceSentance

    def printMenu(self):
        print('1. Add a sentence')
        print('2. Play game')
        print('0. Exit')

    def add_sentence_ui(self):
        sentence=input('Sentence: ')
        self._service.add_sentence(sentence)

    def start(self):
        self.printMenu()
        while True:
            try:
                cmd=input(">>")
                if cmd=='1':
                    self.add_sentence_ui()
                elif cmd=='2':
                    sentence=self._service.select_sentence()
                    g=Game(sentence)
                    print(g)
                    while True:
                        cmd=input("User guess: ")
                        alphabet="abcdefghijklmnopqrstuvwxyz"
                        if cmd not in alphabet:
                            print("Bad input for guess")
                        else:
                            g.guess(cmd)
                            print(g)
                elif cmd=='0':
                    return
                else:
                    print('Bad command')
            except SentenceException as se:
                print(se)
            except GameWonException:
                print("Congrats! You Win!")
            except GameLostException:
                print("YOU LOSE!")