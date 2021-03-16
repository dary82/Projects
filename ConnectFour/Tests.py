from Domain import *
from Service import *
import unittest


class TestBoard(unittest.TestCase):
    def testWinner1(self):
        b=Board()
        b.move(0,'X')
        b.move(1, 'X')
        b.move(2, 'X')
        with self.assertRaises(GameWonException):
            b.move(3, 'X')

    def testWinner2(self):
        b=Board()
        b.move(0,'X')
        b.move(0, 'X')
        b.move(0, 'X')
        with self.assertRaises(GameWonException):
            b.move(0, 'X')

    def testNoWinner1(self):
        b=Board()
        b.move(0,'X')
        b.move(1, 'X')
        b.move(2, 'X')
        b.move(3,'O')
        self.assertFalse(b._isWon())

    def testNoWinner2(self):
        b=Board()
        b.move(0,'X')
        b.move(0, 'X')
        b.move(0, 'X')
        b.move(0,'O')
        self.assertFalse(b._isWon())

    def testWrongMove(self):
        b=Board()
        with self.assertRaises(ValueError):
            b.move(7,'X')

    def testWrongSymbol(self):
        b=Board()
        with self.assertRaises(ValueError):
            b.move(3,'Z')


if __name__ == '__main__':
    unittest.main()
