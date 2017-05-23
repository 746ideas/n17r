/*
 * lesson30.c
 *
 *  Created on: 27 нояб. 2015 г.
 *      Author: Мадияр
 */

#include <stdio.h>
#include <stdlib.h>

#define SIZE 8
// #define SIZE 12

#define USE_NAMED_STUDENT
// #define USE_YEAR_MAJOR_STUDENT

#define USE_INSERTION_SORT
// #define USE_SELECTION_SORT
// #define USE_BUBBLE_SORT
// #define USE_QUICKSORT

/*
 * Select one student record type below,
 * depending on the above define command
 */

#ifdef USE_NAMED_STUDENT// Named student type
typedef struct {
	int id;
	char name[12];
} student;

#elif defined(USE_YEAR_MAJOR_STUDENT)// Year-major student type
typedef struct {
	int id;
	int year; // 1, 2, 3, or 4
	char major[5];// e.g. "robt", "phys", "math"
}student;
#endif

/*
 * Select one sort function implementation below,
 * depending on the above define command
 */

#ifdef USE_INSERTION_SORT// Selection sort
void sort(student *x[]) {

	int i;
	for (i = 0; i < SIZE - 1; i++) {
		int j, mindex = i;
		for (j = i + 1; j < SIZE; j++) {
			if (x[j]->id < x[mindex]->id) {
				mindex = j;
			}
		}

		student *temp = x[i];
		x[i] = x[mindex];
		x[mindex] = temp; // Put your selection sort code here
	}
}

#elif USE_SELECTION_SORT// Insertion sort
void sort(student *x[]) {

	int i;
	for (i = 0; i < SIZE; i++) {
		student *toInsert = x[i];
		int j = 0;
		for (j = i; j >= 0; j--) {
			if (j == 0 || x[j - 1]->id >= toInsert->id) {

				x[j] = toInsert;
				break;

			} else {
				x[j] = x[j - 1];
			}
		}
	} // Put your insertion sort code here
}

#elif USE_BUBBLE_SORT// Bubble sort
void sort(student *x[]) {

	int i, j;

	for (i = 0; i < SIZE - 1; i++) {

		for (j = 0; j < SIZE - 1 - i; j++) {

			if (x[j]->id > x[j + 1]->id) {

				student *temp = x[j + 1];
				x[j + 1] = x[j];
				x[j] = temp;
			}
		}

	} // Put your bubble sort code here
}

#elif USE_QUICKSORT// Quick sort
void sort(student *x[]) {

	quicksort(student, 0, SIZE - 1);
}

int partition(student *x[], int first, int last) {

	int pivot = first;

	int up = first;
	int down = last;
	while (up < down) {

		while (x[up]->id >= x[pivot]->id && up < last) {
			up++;
		}
		while (x[down]->id < x[pivot]->id) {
			down--;
		}
		if (up < down) {
			student *temp = x[up];
			x[up] = x[down];
			x[down] = temp;
		}
	}

	student *temp2 = x[pivot];
	x[pivot] = x[down];
	x[down] = temp2;

	return down; // Put your quicksort partition code here
}

void quicksort(student *x[], int first, int last) {

	if (first < last) {
		int pivIndex = partition(x, first, last);
		quicksort(x, first, pivIndex-1);
		quicksort(x, pivIndex+1, last);
	}
}
#endif

/*
 * Select one getRecords implementation below,
 * depending on the student record type
 */

#ifdef USE_YEAR_MAJOR_STUDENT// Use with named student type
void getRecords(student *x[]) {

	// Opening the input file
	FILE *file = fopen("records2.txt", "r");
	if (file == NULL) {
		printf("Cannot find file.");
		exit(1);
	}
	printf("File opened successfully.\n");

	// Reading the records into the array from the file
	int i;
	for (i = 0; i < SIZE; i++) {
		x[i] = malloc(sizeof(student));
		fscanf(file, "%i %i %s \n", &x[i]->id, &x[i]->year, x[i]->name);
	};
	fclose(file);
}

#elif defined(USE_NAMED_STUDENT)// Use with year-major student type
void getRecords(student *x[]) {

	FILE *file = fopen("records.txt", "r");
	if (file == NULL) {
		printf("Cannot find file.");
		exit(1);
	}
	printf("File opened successfully.\n");

	// Reading the records into the array from the file
	int i;
	for (i = 0; i < SIZE; i++) {
		x[i] = malloc(sizeof(student));
		fscanf(file, "%i %s \n", &x[i]->id, x[i]->name);
	};
	fclose(file); // Copy, paste, and modify the getRecords code
	// from above to work with year-major student
	// records
}
#endif

/*
 * Select one printArray implementation below,
 * depending on the student record type
 */

#ifdef USE_NAMED_STUDENT// Use with named student type
void printArray(student *x[]) {

	int i;
	for (i = 0; i < SIZE; i++) {
		printf("%i %s\n", x[i]->id, x[i]->name);
	}
	printf("\n");	// Put your old printArray code here
}

#elif defined(USE_YEAR_MAJOR_STUDENT) // Use with year-major student type
void printArray(student *x[]) {

	int i;
	for (i = 0; i < SIZE; i++) {
		printf("%i %i %s\n", x[i]->id, x[i]->year, x[i]->name);
	}
	printf("\n");	// Copy, paste, and modify your old printArray
	// code here for year-major student records
}
#endif
// This should work for both student types
void freeRecords(student *x[]) {

	int i;
	for (i = 0; i < SIZE; i++) {
		free(x[i]);
	}
}

int main() {

	student *roster[SIZE];

	printf("Testing whatever sorting algorithm was selected \n");

	// Load in the values from the file
	getRecords(roster);

	// Printing out the roster, in the initial order
	printArray(roster);

	// Sorting the array (hopefully)
	sort(roster);

	// Printing out the roster, in sorted order (hopefully)
	printArray(roster);

	// Free up the items in the array
	freeRecords(roster);

	return 0;
}
