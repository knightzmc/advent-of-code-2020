#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

//Shamelessly stolen from stackoverflow
int fsize(FILE *fp)
{
    int prev = ftell(fp);
    fseek(fp, 0L, SEEK_END);
    int sz = ftell(fp);
    fseek(fp, prev, SEEK_SET); //go back to where we were
    return sz;
}

int *load_numbers()
{
    FILE *fp;
    fp = fopen("data.txt", "r");
    if (fp == NULL)
    {
        perror("Could not read file");
        return NULL;
    }

    long size = fsize(fp);
    char *fcontent = malloc(size);
    fread(fcontent, 1, size, fp);
    fclose(fp);

    int *numbers = malloc(1000 * sizeof(int));
    if (!numbers)
    {
        return NULL;
    }

    int numindex = 0;
    int index = 0;

    int value = 0;
    while (index < size)
    {
        char next = fcontent[index++];
        if (next == '\n')
        {
            numbers[numindex++] = value;
            value = 0;
            continue;
        }
        if (next == '\0')
        {
            break;
        }

        value *= 10;
        value += (next - '0');
    }

    return numbers;
}

bool is_valid(int comp, int *preamble)
{
    if (comp < 2)
    {
        return false;
    }

    for (int i = 0; i < 25; i++)
    {
        for (int j = 0; j < 25; j++)
        {
            if (preamble[i] + preamble[j] == comp)
            {
                return true;
            }
        }
    }
    return false;
}

int main()
{
    int *numbers = load_numbers();

    int preamble[25];

    for (int i = 0; i < 1000 - 25; i++)
    {
        for (int j = 0; j < 25; j++)
        {
            preamble[j] = numbers[j + i];
        }
        int num = numbers[i + 25];
        if (!is_valid(num, preamble))
        {
            printf("%d\n", num);
            break;
        }
    }

    free(numbers);
}