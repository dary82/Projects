from Domain import *
import random
import copy

class Game:
    def __init__(self,board,algorithm):
        self._board=board
        self._algorithm=algorithm

    def playerMove(self,y):
        self._board.move(y,'X')
    def computerMove(self):
        column=self._algorithm.nextMove(self._board)
        self._board.move(column,'O')
    def getBoard(self):
        return self._board


class AlgorithmLvl1:
    '''
    Level one of the algorithm
    Random moves
    '''
    def nextMove(self,board):
        while True:
            i = random.randint(0, 6)
            if board.get_freeSpace(i) > 0:
                return i

class AlgorithmLvl2:
    '''
    Level two of the algorithm
    Random moves and thakes win if possible
    '''
    def nextMove(self,board):
        for i in range(7):
            board2=copy.deepcopy(board)
            if board2.get_freeSpace(i)>0:
                try:
                    board2.move(i,'O')
                except GameWonException:
                    return i
        while True:
            i=random.randint(0,6)
            if board.get_freeSpace(i) > 0:
                return i

class AlgorithmLvl3:
    '''
    Level three of the algorithm
    Random moves, takes win if possible and blocks player win moves
    '''
    def nextMove(self,board):
        for i in range(7):
            board2=copy.deepcopy(board)
            if board2.get_freeSpace(i)>0:
                try:
                    board2.move(i,'O')
                except GameWonException:
                    return i
        for i in range(7):
            board2=copy.deepcopy(board)
            if board2.get_freeSpace(i)>0:
                try:
                    board2.move(i,'X')
                except GameWonException:
                    return i
        while True:
            i=random.randint(0,6)
            if board.get_freeSpace(i) > 0:
                return i