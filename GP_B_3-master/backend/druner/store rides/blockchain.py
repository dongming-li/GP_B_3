import datetime
import hashlib
import json
import sys

class Block(object):
    """docstring for Block"""
    def __init__(self, last_hash, data, index, difficulty, time=datetime.datetime.now(), nonce_=None):
        self.data = data
        self.index = index
        self.date_time = time
        self.difficulty = difficulty

        

        if nonce_ == None:
            self.nonce = 0
            my_hash = hashlib.sha256(self.data + str(last_hash) + str(self.index) + str(self.date_time) + str(self.nonce)).hexdigest()
            while(str(my_hash)[:self.difficulty] != "0"*self.difficulty):
                self.nonce += 1
                my_hash = hashlib.sha256(self.data + str(last_hash) + str(self.index) + str(self.date_time) + str(self.nonce)).hexdigest()
        else:
            self.nonce = nonce_

        # self.data = self.xor(data, my_hash)

    def xor(self, a, b):
        result = "".join(chr( ord(a[i]) ^ ord(b[i%(len(b))]) ) for i in range(len(a)))
        return result

    def is_valid(self, last_hash):
        my_hash = hashlib.sha256(self.data + str(last_hash) + str(self.index) + str(self.date_time) + str(self.nonce)).hexdigest()
        return my_hash[:self.difficulty] == "0"*self.difficulty

    def get_hash(self, last_hash):
        return hashlib.sha256(self.get_data(last_hash) + str(last_hash) + str(self.index) + str(self.date_time) + str(self.nonce)).hexdigest()

    def get_data(self, last_hash):
        if(not self.is_valid(last_hash)):
            return "INVALID LAST HASH"
        return str(self.data)


class BlockChain(object):
    """docstring for BlockChain"""
    def __init__(self, difficulty=4, origin_message="", json_data=""):

        self.chain = []

        if json_data != "":
            json_loaded_data = json.loads(json_data)
            self.difficulty = json_loaded_data["difficulty"]

            last_hash = None
            for block_data in json_loaded_data["data"]:
                b = Block(last_hash, block_data["data"], block_data["index"], self.difficulty, block_data["date_time"], block_data["nonce"])
                last_hash = block_data["hash"]
                self.chain += [b]
        else:
            self.difficulty = difficulty
            self.chain += [Block(None, origin_message, 0, self.difficulty)]
        

    def add_block(self, data):
        last_hash = None
        for i in range(len(self.chain)):
            last_hash = self.chain[i].get_hash(last_hash)
        self.chain += [Block(last_hash, data, len(self.chain), self.difficulty)]


    def __repr__(self):
        total = "Block: 0 " + self.chain[0].get_data(None) + "\n"
        last_hash = None
        for i in range(1,len(self.chain)):
            last_hash = self.chain[i-1].get_hash(last_hash)
            total += "Block: " + str(i) + " " + self.chain[i].get_data(last_hash) + "\n"
        return total

    def __str__(self):
        all_blocks = [{"data": self.chain[0].get_data(None), "index": 0, "date_time": str(self.chain[0].date_time), "nonce": self.chain[0].nonce, "hash": self.chain[0].get_hash(None)}]
        last_hash = None
        for i in range(1,len(self.chain)):
            last_hash = self.chain[i-1].get_hash(last_hash)
            all_blocks += [{"data": self.chain[i].get_data(last_hash), "index": i, "date_time": str(self.chain[i].date_time), "nonce": self.chain[i].nonce, "hash": self.chain[i].get_hash(last_hash)}]
        return json.dumps({"difficulty": self.difficulty, "data": all_blocks})


# EXAMPLE USE

# my_chain = BlockChain(4, "origin message here")
# my_chain.add_block("hello world!")
# my_chain.add_block("com s 309")
# my_chain.add_block("Tesla is cool")
# my_chain.add_block("some message here")
# my_chain.add_block("12345")
# print repr(my_chain)
# print "============="
# print str(my_chain)

# new_chain = BlockChain(json_data=str(my_chain))
# print repr(new_chain)

# print "trying to change block 2... muah ha ha...\n"
# ########## now try to change some data
# last_hash = None
# for i in range(2):
#     last_hash = my_chain.chain[i].get_hash(last_hash)
# my_chain.chain[2].data = "evil message... muah ha ha... "

# print repr(my_chain)


####################################
#          actual use              #
####################################



# get the file name
file_name = sys.argv[1]
f = open(file_name)
data = "".join([line for line in f])
f.close()
chain = BlockChain(json_data=str(data))

for option_num in range(len(sys.argv[1:])+1):
    option = sys.argv[option_num]
    if option == "-p":
        print "printing"
        print repr(chain)
    if option == "-a":
        print "adding"
        new_data = sys.argv[option_num+1]
        chain.add_block(new_data)
    if option == "-d":
        print "dumping"
        f = open(file_name, "w")
        f.write(str(chain))
        f.close()
    if option == "-r":
        print "printing data"
        print str(chain)















