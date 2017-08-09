from Node import Node

# THis is for a doubly linked list
class LinkedList:

    def __init__(self):
        self.head = Node(-1)
        self.tail = self.head
        self.curr = self.head
        self.size = 0

    def add(self, data):
        self.link_after(data)

    def link_after(self, data):
        val_to_add = Node(data)

        if self.head.nextNode is None:
            self.head.nextNode = val_to_add
            val_to_add.prevNode = self.head
            self.tail = self.head.nextNode
        else:
            self.tail.nextNode = val_to_add
            val_to_add.prevNode = self.tail
            self.tail = self.tail.nextNode

        self.size += 1

    def remove(self):
        self.unlink_before()

    def unlink_before(self):
        temp = self.head.get_data()

        self.head = self.head.nextNode
        self.head.prevNode = None
        self.curr = self.head

        self.size -= 1

        return temp

    def get_size(self):
        return self.size

    def head(self):
        return self.head

    def change_head(self, val):
        self.head.set_data(val)

    def tail(self):
        return self.tail

    def to_string(self):
        string = ""
        while self.curr.nextNode is not None:
            string += str(self.curr.nextNode.get_data()) + " --> "
            self.curr = self.curr.nextNode

        self.curr = self.head
        # remove the last 5 characters to remove the last -->
        return string[0:len(string)-5]
