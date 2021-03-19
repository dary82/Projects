import unittest
from service import *

class test_sentence_service(unittest.TestCase):
    def test_add_sentances_less_words(self):
        s = Sentences("sentences.txt")
        service = ServiceSentence(s)
        with self.assertRaises(SentenceException):
            service.add_sentence('')

    def test_add_sentances_less_letters(self):
        s=Sentences("sentences.txt")
        service = ServiceSentence(s)
        with self.assertRaises(SentenceException):
            service.add_sentence('anna has a bird')

class test_sentence_repo(unittest.TestCase):
    def test_add_sentances_duplicate(self):
        s = Sentences("sentences.txt")
        with self.assertRaises(SentenceException):
            s.add_sentence('anna has apples')

if __name__ == '__main__':
    unittest.main()
