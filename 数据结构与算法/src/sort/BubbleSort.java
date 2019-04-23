package sort;

/**
 * 冒泡排序
 *
 * @author zhangy
 */
public class BubbleSort<E extends Comparable<E>> {

    // 冒泡排序对于n<=5的时候，速度是最快的


    /**
     * 蛮力法 冒泡排序
     *
     * @param data
     */
    public void bubbleSort(int[] data) {

        for (int i = data.length - 1; i > 0; i--) {
            boolean flag = true;
            for (int j = 0; j < i; j++) {
                if (data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    flag = false;
                }
            }

            if (flag) {
                break;
            }
        }

    }

    /**
     * 蛮力法 冒泡排序
     *
     * @param data
     */
    public void bubbleSort(E[] data) {

        for (int i = data.length - 1; i > 0; i--) {
            boolean flag = true;
            for (int j = 0; j < i; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    E temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    public static void main(String[] arg) {

        int[] arr = {3, 1, 5, 8, 2, 9, 4, 6, 7};
        BubbleSort<Integer> bubbleSort = new BubbleSort<>();
        bubbleSort.bubbleSort(arr);
        for (int i : arr) {
            System.out.print(i+" ");
        }
    }
}
