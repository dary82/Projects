class Game:
    def __init__(self,sentence):
        self._sentence=sentence
        self._found=[0]*len(self._sentence)
        self._letters = []
        self._hangman = 0
        self._initial_state()

    def _initial_state(self):
        words=self._sentence.split()
        for i in range(len(words)):
            self._letters+=[words[i][0]]
            self._letters+=[words[i][len(words[i])-1]]

        for i in range(0,len(self._sentence)):
            if self._sentence[i] in self._letters:
                self._found[i]=1
            if self._sentence[i] in self._letters:
                self._found[i]=1
            if self._sentence[i]==" ":
                self._found[i]=1

    def _wonGame(self):
        for i in range(len(self._found)):
            if self._found[i]==0:
                return
        raise GameWonException

    def _lostGame(self):
        if self._hangman==7:
            raise GameLostException

    def guess(self,letter):
        if letter in self._letters:
            self._hangman+=1
        elif letter in self._sentence:
            self._letters+=[letter]
            for i in range(0, len(self._sentence)):
                if self._sentence[i]==letter:
                    self._found[i] = 1
        else:
            self._hangman+=1
            self._letters+=[letter]
        self._lostGame()
        self._wonGame()


    def __str__(self):
        hangman = ""
        if self._hangman!=0:
            hangmanLetter = ['h', 'a', 'n', 'g', 'm', 'a', 'n']
            for i in range(self._hangman):
                hangman=hangman+hangmanLetter[i]

        printed=''
        for i in range(len(self._sentence)):
            if self._sentence[i]==" ":
                printed=printed+" "
            elif self._found[i]==0:
                printed=printed+" _ "
            elif self._found[i]==1:
                printed=printed+self._sentence[i]+""
        return printed + " - " + hangman



class GameWonException(Exception):
    pass

class GameLostException(Exception):
    pass