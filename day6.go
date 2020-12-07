package main

import (
	"bufio"
	"io/ioutil"
	"regexp"
	"strings"
)

func main() {
	file, err := ioutil.ReadFile("day6.txt")
	if err != nil {
		panic(err)
	}
	re, err := regexp.Compile("\n\n")

	data := string(file)

	lines := re.Split(data, -1)

	anyoneAnswered := 0
	for _, line := range lines {
		anyoneAnswered += getQuestionsAnyoneAnswered(line)
	}
	println(anyoneAnswered)

	everyoneAnswered := 0
	for _, line := range lines {
		everyoneAnswered += getQuestionsEveryoneAnswered(line)
	}
	println(everyoneAnswered)
}

func getLines(text string) []string {
	scanner := bufio.NewScanner(strings.NewReader(text))
	lines := make([]string, 1)
	for scanner.Scan() {
		line := scanner.Text()
		lines = append(lines, line)
	}
	return lines
}
func getQuestionsAnyoneAnswered(group string) int {
	questions := make(map[rune]int)

	for _, line := range getLines(group) {
		for _, char := range line {
			_, success := questions[char]
			if success {
				continue
			}
			questions[char] = 1
		}
	}
	total := 0

	for range questions {
		total++
	}

	return total
}

func getQuestionsEveryoneAnswered(group string) int {
	questions := make(map[rune]int)

	peopleCount := 0

	for _, line := range getLines(group) {
		peopleCount++
		for _, char := range line {
			questions[char]++
		}
	}

	total := 0

	for char := range questions {
		if questions[char] == peopleCount {
			total++
		}
	}

	return total
}
