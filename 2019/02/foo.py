import sys
import itertools

ADD, MULTIPLY = [1, 2]


class TerminateException(Exception):
    pass


def in_chunks(iterable, n, fillvalue=None):
    """
    Borrowed this from another personal project.

    """
    args = [iter(iterable)] * n
    for c in itertools.zip_longest(*args, fillvalue=fillvalue):
        yield filter(None, c)


def do_add(state, a, b, output):
    state[state[output]] = state[state[a]] + state[state[b]]
    return state


def do_multiply(state, a, b, output):
    state[state[output]] = state[state[a]] * state[state[b]]
    return state


class Computer(object):
    def __init__(self, boot):
        self.state = [int(i) for i in boot.strip().split(',')]
        self.current_position = 0

    def get(self, idx):
        return self.state[idx]

    @property
    def current(self):
        return self.get(self.current_position)

    def goto(self, new_position):
        self.current_position = new_position

    def read(self):
        value = self.current

        if value == ADD:
            # read ahead 2 positions for args
            self.state = do_add(
                state=self.state.copy(),
                a=self.current_position + 1,
                b=self.current_position + 2,
                output=self.current_position + 3
            )
            return self.goto(self.current_position + 4)

        if value == MULTIPLY:
            self.state = do_multiply(
                state=self.state.copy(),
                a=self.current_position + 1,
                b=self.current_position + 2,
                output=self.current_position + 3
            )
            return self.goto(self.current_position + 4)

        if value == 99:
            raise TerminateException()
            return

    def run(self):
        try:
            while True:
                self.read()

        except TerminateException:
            pass

        return self.state

    def opcodes(self):
        for opcode in in_chunks(self.state.copy(), 4):
            yield list(opcode)


def main(data):
    c = Computer(data)
    c.run()


if __name__ == '__main__':
    mine = '1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,6,19,1,5,19,23,1,23,6,27,1,5,27,31,1,31,6,35,1,9,35,39,2,10,39,43,1,43,6,47,2,6,47,51,1,5,51,55,1,55,13,59,1,59,10,63,2,10,63,67,1,9,67,71,2,6,71,75,1,5,75,79,2,79,13,83,1,83,5,87,1,87,9,91,1,5,91,95,1,5,95,99,1,99,13,103,1,10,103,107,1,107,9,111,1,6,111,115,2,115,13,119,1,10,119,123,2,123,6,127,1,5,127,131,1,5,131,135,1,135,6,139,2,139,10,143,2,143,9,147,1,147,6,151,1,151,13,155,2,155,9,159,1,6,159,163,1,5,163,167,1,5,167,171,1,10,171,175,1,13,175,179,1,179,2,183,1,9,183,0,99,2,14,0,0'

    for x in range(0, 100):
        for y in range(0, 100):
            c = Computer(mine)

            c.state[1] = x
            c.state[2] = y

            c.run()

            if c.state[0] == 19690720:
                print(f'FOUND!!!!! x:{x}, y:{y}')
                sys.exit()
