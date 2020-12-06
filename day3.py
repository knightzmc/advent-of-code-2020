map = {}

file = open('day3.txt')

cur_line = 0
for line in file.readlines():
    cur_char = 0
    for char in line:
        map[cur_line, cur_char] = char == '#'
        cur_char += 1
    
    cur_line += 1

def run_slope(right, down):
    start = 0, 0
    trees = 0
    while True:
        if map[start]:
            trees += 1

        start = (start[0] + down), (start[1] + right) % cur_char

        if start[0] >= cur_line:
            break

    return trees

print(run_slope(3, 1))

print(run_slope(1, 1) * run_slope(3, 1) * run_slope(5, 1) * run_slope(7, 1) * run_slope(1, 2))