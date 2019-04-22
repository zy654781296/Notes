package sort;

/**
 * 八大排序
 *
 * @author zhangy
 */
public class Sort {

    /**
     * 冒泡排序
     *
     * @param array 数组
     */
    public static void bubbleSort(int[] array) {

        for (int i = array.length - 1; i > 0; i--) {
            boolean flag = true;
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = false;
                }
            }

            if (flag) {
                break;
            }
        }
    }

    /**
     * 选择排序
     *
     * @param array 数组
     */
    public static void selectSort(int[] array) {

        for (int i = 0; i < array.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[index]) {
                    index = j;
                }
            }

            if (index != i) {
                int temp = array[index];
                array[index] = array[0];
                array[0] = temp;
            }
        }
    }


    /**
     * 快速排序
     *
     * @param array 数组
     */
    public static void quickSort(int[] array) {

    }

    /**
     * 插入排序
     *
     * @param array 数组
     */
    public static void insertSort(int[] array) {

        for (int i = 1; i < array.length; i++) {
            int j = i;
            int target = array[j];
            while (j > 0 && target < array[j - 1]) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = target;
        }
    }

    /**
     * 希尔排序
     *
     * @param array 数组
     * @param step  步长
     */
    public static void shellSort(int[] array, int step) {

        for (int k = 0; k < step; k++) {
            //直接插入排序
            for (int i = k + step; i < array.length; i = i + step) {
                int j = i;
                int target = array[j];
                while (j > step - 1 && target < array[j - step]) {
                    array[j] = array[j - step];
                    j = j - step;
                }
                array[j] = target;
            }
        }
    }

    /**
     * 堆排序
     *
     * @param array
     * @param len
     */
    public static void heapSort(int[] array, int len) {


    }

    /**
     * 大顶堆
     *
     * @param array
     * @param start
     * @param end
     */
    private void maxHeapify(int[] array, int start, int end) {


    }


}
