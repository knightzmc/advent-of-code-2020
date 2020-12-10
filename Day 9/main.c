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

int *minmaxbetween(int r1, int r2, int *numbers, int len)
{
    int min, max = 0;
    for (int i = r1; i < r2; i++)
    {
        int num = numbers[i];
        if (num < min)
        {
            min = num;
        }
        if (num > max)
        {
            max = num;
        }
    }

    int *arr = malloc(2 * sizeof(int));
    arr[0] = min;
    arr[1] = max;
    return arr;
}

int *findconsecutivesum(int total, int *numbers, int length)
{
    int sum = 0;
    for (int i = 0; i < length; i++)
    {
        if (numbers[i] > total)
        {
            continue;
        }
        for (int j = i; j < length; j++)
        {
            sum += numbers[j];
            if (sum == total)
            {
                int *returned = malloc(2 * sizeof(int));
                returned[0] = i;
                returned[1] = j;
                return returned;
            }
            if (sum > total)
            {
                sum = 0;
                break;
            }
        }
    }
}

int main()
{
    int *numbers = load_numbers();

    int preamble[25];

    int invalid = -1;
    for (int i = 0; i < 1000 - 25; i++)
    {
        for (int j = 0; j < 25; j++)
        {
            preamble[j] = numbers[j + i];
        }
        int num = numbers[i + 25];
        if (!is_valid(num, preamble))
        {
            invalid = num;
            printf("%d\n", num);
            break;
        }
    }

    //part 2

    int *sum = findconsecutivesum(invalid, numbers, 1000);

    int *minmax = minmaxbetween(sum[0], sum[1], numbers, 1000);

    printf("%d\n", minmax[0] + minmax[1]);

    free(numbers);
    free(sum);
    free(minmax);
}