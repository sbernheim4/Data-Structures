# this is the Node class used in the LinkedList

class Node:
    nextNode = None
    prevNode = None
    data = -1

    def __init__(self, data):
        self.prevNode = None
        self.data = data
        self.nextNode = None

    def set_data(self, new_data):
        self.data = new_data

    def get_data(self):
        return self.data
