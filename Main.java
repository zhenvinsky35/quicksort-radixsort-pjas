import java.util.ArrayList;

public class Main {

    public static void main(String[] args)
	{
		int size = (int)(Math.pow(10, 8));
		int min = (int)Math.pow(2, 3);
		int maxExclusive = (int)Math.pow(2, 4);
		int[] a = randomIntArrayGenerator(size, min, maxExclusive);
		long start = System.nanoTime();
		quicksort(a);
		long end = System.nanoTime();
		System.out.println(isArraySorted(a));
		System.out.println(end-start + " ns");
    }


	static void printArray(int[] array)
	{
		for (int i : array)
		{
			System.out.println(i);
		}
	}

	static void printArrayTuples(int[][] array)
	{
		for (int[] tuple : array)
		{
			String v0 = Integer.toBinaryString(tuple[0]);
			String v1 = Integer.toBinaryString(tuple[1]);
			System.out.println(v0+", "+v1);
		}
	}

	static boolean isArraySorted(int[] array)
	{
		for (int i = 0; i < array.length-1; i++)
		{
			if (array[i] > array[i+1])
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Generates a random array of integers.
	 * @param size Size of the array
	 * @param min Minimum value of integers
	 * @param max Maximum value of integers, exclusive
	 * @return
	 */
    static int[] randomIntArrayGenerator(int size, int min, int max)
	{
		int[] r = new int[size];
		for (int i = 0; i < r.length; i++)
		{
			r[i] = (int)((Math.random() * (max-min)) + min);
		}

		return r;
	}

	static void radixsort(int[] arrayOriginal)
	{
		int lengthOfLongestInt = (int)(Math.floor(Math.log(getLargestElement(arrayOriginal)) / Math.log(2))); //binary length of the longest int

		//the 0th element stores the number that will be changed during the sort
		//the 1st element stores the original number
		int[][] array = new int[arrayOriginal.length][2];
		for (int i = 0; i < arrayOriginal.length; i++)
		{
			array[i][0] = arrayOriginal[i];
			array[i][1] = arrayOriginal[i];
		}

		ArrayList<int[]> bucket0;
		ArrayList<int[]> bucket1;

		for (int h = 0; h <= lengthOfLongestInt; h++) //iterate through the digits
		{
			bucket0 = new ArrayList<>(array.length); //clear the bins
			bucket1 = new ArrayList<>(array.length);

			for (int i = 0; i < array.length; i++) //iterate through the array
			{
				int lastDigit = array[i][0] % 2; //place into the right bucket
				if (lastDigit == 0)
				{
					bucket0.add(array[i]);
				}
				else
				{
					bucket1.add(array[i]);
				}

				array[i][0] = array[i][0] / 2; //take off the last binary digit
			}

			int iArray = 0;
			for (int j0 = 0; j0 < bucket0.size(); j0++) //overwrite the array with the contents of the buckets
			{
				array[iArray] = bucket0.get(j0);
				iArray++;
			}

			for (int j1 = 0; j1 < bucket1.size(); j1++)
			{
				array[iArray] = bucket1.get(j1);
				iArray++;
			}

			//printArrayTuples(array);
		}

		for (int i = 0; i < array.length; i++) //overwrite the original array with the sorted array.
		{
			arrayOriginal[i] = array[i][1];
		}
	}

	/**
	 * Verified to work
	 * @param array An array of ints
	 * @return The largest element of the array.
	 */
	static int getLargestElement(int[] array)
	{
		if (array.length == 0)
		{
			assert false;
		}
		if (array.length == 1)
		{
			return array[0];
		}
		//else
		int largestInt = array[0];
		for (int i = 1; i < array.length; i++)
		{
			if (array[i] > largestInt)
			{
				largestInt = array[i];
			}
		}

		return largestInt;
	}

	static void quicksort(int[] array)
	{
		quicksort(array, 0, array.length-1);
	}

	static void quicksort(int[] array, int start, int end)
	{
		if (start >= end)
		{
			return;
		}

		int pivotIndex = pivotIndexChooser(array, start, end);
		int pivotValue = array[pivotIndex];

		ArrayList<Integer> lessThanPivot = new ArrayList<>(end-start);
		ArrayList<Integer> equalToPivot = new ArrayList<>(end-start + 1);
		ArrayList<Integer> greaterThanPivot = new ArrayList<>(end-start);

		for (int i = start; i <= end; i++) //iterate through the elements
		{
			int n = array[i];
			if (n < pivotValue) //place elements into 3 lists: a less than pivot list, an equal to pivot list, and a greater than pivot list
			{
				lessThanPivot.add(n);
				continue;
			}
			//else
			if (n == pivotValue)
			{
				equalToPivot.add(n);
				continue;
			}
			//else
			greaterThanPivot.add(n);
		}

		int indexOfLastElementLessThanPivot;
		int indexOfFirstElementGreaterThanPivot;

		int iArray = start;
		for (int j0 = 0; j0 < lessThanPivot.size(); j0++) //overwrite the section to sort with the 3 lists in order
		{
			array[iArray] = lessThanPivot.get(j0);
			iArray++;
		}

		indexOfLastElementLessThanPivot = iArray - 1;

		for (int j1 = 0; j1 < equalToPivot.size(); j1++)
		{
			array[iArray] = equalToPivot.get(j1);
			iArray++;
		}

		indexOfFirstElementGreaterThanPivot = iArray;

		for (int j2 = 0; j2 < greaterThanPivot.size(); j2++)
		{
			array[iArray] = greaterThanPivot.get(j2);
			iArray++;
		}

		quicksort(array, start, indexOfLastElementLessThanPivot); //recurse
		quicksort(array, indexOfFirstElementGreaterThanPivot, end);
	}

	/**
	 * @return index of the pivot
	 */
	static int pivotIndexChooser(int[] array, int start, int end)
	{

		int index1 = (int)(Math.random() * (end-start+1) + start);
		int index2 = (int)(Math.random() * (end-start+1) + start);
		int index3 = (int)(Math.random() * (end-start+1) + start);

		if ((array[index1] <= array[index2] && array[index1] >= array[index3]) || (array[index1] >= array[index2] && array[index1] <= array[index3])) //if [index1] is middle, [index2] is biggest/smallest, and [index3] is biggest/smallest
		{
			return index1;
		}
		//else
		if ((array[index2] <= array[index1] && array[index2] >= array[index3]) || (array[index2] >= array[index1] && array[index2] <= array[index3]))
		{
			return index2;
		}
		//else
		return index3;
	}

	static void swapElements(int[] array, int pos1, int pos2)
	{
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}
}
