from repoSentence import *


class ServiceSentence:
    def __init__(self,repoSentence):
        self._repoSentence=repoSentence

    def add_sentence(self,sentence):
        '''
        Verifies if a given sentence is valid
        :param sentence - a string
        Raise SentenceException if sentence does not contain one word or the words don't have at least 3 letters
        '''
        sentance1=sentence.split()
        if len(sentance1)<1:
            raise SentenceException("Sentence must contain at least 1 word")
        for s in sentance1:
            if len(s)<3:
                raise SentenceException("Words must have at least 3 letters")
        self._repoSentence.add_sentence(sentence)

    def select_sentence(self):
        return self._repoSentence.select_sentence()