class IterableStorage:
    def __init__(self, data=[]):
        self._data = data

    def __iter__(self):
        self._current_item = 0
        return self

    def __next__(self):
        if self._current_item < len(self._data):
            result = self._data[self._current_item]
            self._current_item += 1
            return result
        else:
            raise StopIteration

    def remove(self, index):
        self._data.remove(self._data[index])

    def append(self, obj):
        self._data.append(obj)

    def __len__(self):
        return len(self._data)

    def __getitem__(self, index):
        return self._data[index]

    def __setitem__(self, index, value):
        self._data[index] = value

    def __delitem__(self, index):
        self._data.remove(self._data[index])

    def sort(self):
        pass