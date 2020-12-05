file = open('day1.txt')

lines = set([int(line) for line in file.readlines()])


def p1():
    for l1 in lines:
        value = 2020 - l1
        if value in lines:
            print(value * l1)
            return


# Part 2

def p2():
    for l1 in lines:
        for l2 in lines:
            value = 2020 - l1 - l2

            if value in lines:
                print(value * l1 * l2)
                return

p1()
p2()