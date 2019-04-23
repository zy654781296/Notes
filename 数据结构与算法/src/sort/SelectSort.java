package sort;

/**
 * 选择排序
 */
public class SelectSort<E extends Comparable<E>> {


    /**
     * 选择排序
     *
     * @param arr
     */
    public void selectSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[index]) {
                    index = j;
                }
            }

            //如果已经是最小的，就不需要交换
            if (index != i) {
                int temp = arr[index];
                arr[index] = arr[i];
                arr[i] = temp;
            }
        }
    }

    /**
     * 选择排序
     *
     * @param arr
     */
    public void selectSort(E[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(arr[index]) > 0) {
                    index = j;
                }
            }

            //如果已经是最小的，就不需要交换
            if (index != i) {
                E temp = arr[index];
                arr[index] = arr[i];
                arr[i] = temp;
            }
        }
    }

    public static void main(String[] arg) {

        int[] arr = {3, 1, 5, 8, 2, 9, 4, 6, 7};
        SelectSort<Integer> selectSort = new SelectSort<>();
        selectSort.selectSort(arr);
        for (int i : arr) {
            System.out.print(i+" ");
        }
    }
}
