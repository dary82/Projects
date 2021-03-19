import random

class Sentences:
    def __init__(self,fileName):
        self._sentences=[]
        self._filename=fileName
        self._loadFile()

    def _loadFile(self):
        f=open(self._filename,'r')
        lines=f.readlines()
        for l in lines:
            l=l.rstrip('\n')
            self._sentences.append(l)
        f.close()

    def _saveFile(self):
        f=open(self._filename,'w')
        for s in self._sentences:
            f.write(s+'\n')
        f.close()

    def add_sentence(self,sentence):
        '''
        Adds a sentence to the list
        :param sentence - a string
        Raise SentenceException if sentence is a duplicate
        '''
        for s in self._sentences:
            if s==sentence:
                raise SentenceException("Duplicate sentence")
        self._sentences.append(sentence)
        self._saveFile()

    def select_sentence(self):
        i=random.randint(0,len(self._sentences)-1)
        return self._sentences[i]


class SentenceException(Exception):
    pass
