import re

format_regex = re.compile("(\\d+-\\d+) ([a-z]): (.+)")
file = open('day2.txt')
infos = [re.split(format_regex, line) for line in file.readlines()]


def p1():
    total = 0
    for info in infos:

        password = info[3]
        char = info[2]
        count = password.count(char)

        min, max = [int(n) for n in info[1].split('-')]

        if count >= min and count <= max:
            total += 1
    print(total)


def p2():
    total = 0
    for info in infos:

        password = info[3]
        char = info[2]

        i1, i2 = [int(n) for n in info[1].split('-')]

        if (password[i1 - 1] == char) ^ (password[i2 - 1] == char):
            total += 1

    print(total)
    return


p1()
p2()
