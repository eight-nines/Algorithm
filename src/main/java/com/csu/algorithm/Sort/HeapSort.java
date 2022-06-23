
import java.util.Arrays;

public class HeapSort {

	public static void heapSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		// 转化为大根堆
		for (int i = 0; i < arr.length; i++) {
			heapInsert(arr, i);
		}
		// 大根堆初始的数据长度
		int size = arr.length;
		// 交换堆尾和堆根
		// 堆根一定是最大的元素，所以每次找到数组最大值
		// 但由于根节点发生变化，需要重新堆化
		swap(arr, 0, --size);
		// 迭代堆，直到堆中只有一个节点（在循环中被消耗）
		while (size > 0) {
			// 堆化
			heapify(arr, 0, size);
			// 交换堆头堆尾，并把堆size-1
			swap(arr, 0, --size);
		}
	}

	// 插入节点
	public static void heapInsert(int[] arr, int index) {
		// 如果自己的值大于父节点值，则交换
		while (arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			// 向上迭代，每次只需要处理自己所在的一整条到达根节点的分支
			index = (index - 1) / 2;
		}
	}

	// 调整堆，堆化
	// size 为当前堆数据量的长度，index 需要调整的位置
	public static void heapify(int[] arr, int index, int size) {
		// 左孩子
		int left = index * 2 + 1;
		// 左孩子不越界（如果越界则自己为叶节点，不用动）
		while (left < size) {
			// 比较左右孩子的大小，并赋值给largest
			int largest = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
			// 比较自己和孩子的大小，赋值给largest
			largest = arr[largest] > arr[index] ? largest : index;
			// 自己最大，不用动，跳出
			if (largest == index) {
				break;
			}
			// 否则，交换自己和最大的子节点
			swap(arr, largest, index);
			index = largest;
			// 对left 重新赋值
			left = index * 2 + 1;
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			heapSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		heapSort(arr);
		printArray(arr);
	}

}
