package sort;

/**
 * 插入排序
 *
 * @author zhangy
 */
public class InsertSort {

    /**
     * 直接插入排序
     *
     * @param arr
     */
    public void insertSort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            int j = i;
            int target = arr[j];
            while (j > 0 && target < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = target;
        }
    }

    public static void main(String[] arg) {
        int[] array = new int[]{2, 3, 4, 5, 6, 7, 1, 8, 9};
        new InsertSort().insertSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}
