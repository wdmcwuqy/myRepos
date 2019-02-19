package com.duanjiao.dpsTest.sort;

import java.util.ArrayList;

public class SortTest {
	
	/**
     * 冒泡排序
     * @param data    要排序的数组
     * @param reverse 从大到小(false)还是从小到大(ture)
     */
    public static void sort(int[] data, boolean reverse) {
        if (data.length == 1) {
            return;
        }
        for (int i = 0; i < data.length - 1; i++) {
            int tmp = 0;
            for (int j = 0; j < data.length - i - 1; j++) {
                if (reverse) {  //从小到大(ture)
                    if (data[j] >= data[j+1]) {
                        tmp = data[j];
                        data[j] = data[j +1 ];
                        data[j+1] = tmp;
                    }
                } else {    //从大到小(false)
                    if (data[j] <= data[j+1]) {
                        tmp = data[j+1];
                        data[j+1] = data[j];
                        data[j] = tmp;
                    }
                }
            }
        }
    }
    
    /**
     * 堆排序
     * @param data    要排序的数组
     * @param reverse 从大到小(false)还是从小到大(ture)
     */
    public static void sort1(int[] data, boolean reverse) {
        if (data.length == 1) {
            return;
        }
        for (int i = 0; i < data.length; i++) {
            //建堆
            buildHeap(data, 0, data.length -1 - i, reverse);
            int tmp = data[0];
            data[0] = data[data.length - 1 - i];
            data[data.length - 1 - i] = tmp;
        }
    }

    /**
     * 将指定开始和结束段的数据建堆
     * @param data
     * @param beginIndex
     * @param endIndex
     * @param reverse
     */
    public static void buildHeap(int[] data, int beginIndex, int endIndex, boolean reverse) {
        if (beginIndex >= endIndex) {
            return;
        }
        for (int i = (endIndex + beginIndex - 1) / 2; i >= beginIndex; i--) {
            int cur = i;
            if (reverse) {   //大顶堆,用来从小到大排序
                //发生交换之后需要检查孙子节点,重孙子节点...
                while (2 * cur + 1 <= endIndex) {
                    int biggerChildIndex = 2 * cur + 1;
                    if (biggerChildIndex + 1 <= endIndex) {
                        if (data[biggerChildIndex] < data[biggerChildIndex + 1]) {
                            biggerChildIndex = biggerChildIndex + 1;
                        }
                    }
                    //找到最大子节点,如果比当前节点大,就交换
                    if (data[i] < data[biggerChildIndex]) {
                        int tmp = data[i];
                        data[i] = data[biggerChildIndex];
                        data[biggerChildIndex] = tmp;
                        //准备检查孙子节点
                        cur = biggerChildIndex;
                    } else {
                        break;
                    }
                }
            } else {    //小顶堆,用来从大到小排序
                //发生交换之后需要检查孙子节点,重孙子节点...
                while (2 * cur + 1 <= endIndex) {
                    int samllerChildIndex = 2 * i + 1;
                    if (samllerChildIndex + 1 <= endIndex) {
                        if (data[samllerChildIndex] > data[samllerChildIndex + 1]) {
                            samllerChildIndex = samllerChildIndex + 1;
                        }
                    }
                    //找到最小子节点,如果比当前节点小,就交换
                    if (data[i] > data[samllerChildIndex]) {
                        int tmp = data[i];
                        data[i] = data[samllerChildIndex];
                        data[samllerChildIndex] = tmp;
                        cur = samllerChildIndex;
                    } else {
                        break;
                    }
                }
            }
        }
    }
    
    /**
     *  插入排序
     * @param data  要排序的数组
     * @param reverse 从大到小(false)还是从小到大(ture)
     */
    public static void sort2(int[] data, boolean reverse) {
        if (data.length == 1) {
            return;
        }
        int tmp = 0;
        for (int i = 1; i < data.length; i++) {
            tmp = data[i];
            int n = i - 1;
            for (; n >= 0; n--) {
                if (reverse) {   //从小到大排序
                    if (data[n] >= tmp) {
                        data[n + 1] = data[n];  //将大于当前值的数后移一个位置
                    } else {
                        break;
                    }
                } else {    //从大到小排序
                    if (data[n] <= tmp) {
                        data[n + 1] = data[n];  //将小于当前值的数后移一个位置
                    } else {
                        break;
                    }
                }
            }
            data[n+1] = tmp;
        }
    }
    
    /**
     * 归并排序
     * @param data    要排序的数组
     * @param reverse 从大到小(false)还是从小到大(ture)
     */
    public static void sort(int[] data, int left, int right, boolean reverse) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        sort(data, left, mid, reverse);
        sort(data, mid + 1, right, reverse);
        merge(data, left, mid, right, reverse);
    }

    /**
     * 合并已排序好的两段
     * @param data
     * @param left
     * @param mid
     * @param right
     * @param reverse
     */
    public static void merge(int[] data, int left, int mid, int right, boolean reverse) {
        int[] tmp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int n = 0;
        while (i <= mid && j <= right) {
            if (reverse) {  //从小到大
                if (data[i] <= data[j]) {
                    tmp[n++] = data[i++];
                } else {
                    tmp[n++] = data[j++];
                }
            } else {    //从大到小
                if (data[i] <= data[j]) {
                    tmp[n++] = data[j++];
                } else {
                    tmp[n++] = data[i++];
                }
            }
        }
        while (i <= mid) {
            tmp[n++] = data[i++];
        }
        while (j <= right) {
            tmp[n++] = data[j++];
        }
        for (int k = 0; k < tmp.length; k++) {
            data[left + k] = tmp[k];
        }
    }
    
    /**
     * 快速排序
     * @param data
     * @param left
     * @param right
     * @param reverse 从大到小(false)还是从小到大(ture)
     */
    public static void sort1(int[] data, int left, int right, boolean reverse) {
        if (left >= right) {
            return;
        }
        int index = getIndex(data, left, right, reverse);
        sort(data, left, index - 1, reverse);
        sort(data, index + 1, right, reverse);
    }

    /**
     * 将待排序片段调整顺序,获得"中间数据"的正确索引
     * @param data
     * @param left
     * @param right
     * @param reverse 从大到小(false)还是从小到大(ture)
     * @return
     */
    public static int getIndex(int[] data, int left, int right, boolean reverse) {
        int cur = data[left];
        int i = left;
        int j = right;
        while (i < j) {
            if (reverse) {  //从小到大
                while (data[j] > cur && i < j) {
                    --j;
                }
                data[i] = data[j];
                while (data[i] <= cur && i < j) {
                    ++i;
                }
                data[j]=data[i];
            } else {    //从大到小
                while (data[j] < cur && i < j) {
                    --j;
                }
                data[i]=data[j];
                while (data[i] >= cur && i < j) {
                    ++i;
                }
                data[j]=data[i];
            }
        }
        data[i] = cur;
        return i;
    }
    
    /**
     * 基数排序
     * @param data    要排序的数组
     * @param reverse 从大到小(false)还是从小到大(ture)
     */
    public static void sort3(int[] data, boolean reverse) {
        if (data.length == 1) {
            return;
        }
        int max = 0;
        for (int i = 0; i < data.length; i++) { //找出最大的数据
            if (max < data[i]) {
                max = data[i];
            }
        }
        System.out.println("the max number is :" + max);
        int radix = 1;
        ArrayList<ArrayList<Integer>> numbers = new ArrayList<ArrayList<Integer>>(10);
        for (int i = 0; i < 10; i++) {
            numbers.add(i, new ArrayList<Integer>());
        }
        while (max > radix) {
            for (int i = 0; i < data.length; i++) {
                int index = (data[i] / radix) % 10;
                ArrayList<Integer> list = numbers.get(index);
                list.add(data[i]);
                numbers.set(index, list);
            }
            resetOrder(data, numbers, reverse);
            radix = radix * 10;
        }
    }

    /**
     * 重新调整数组顺序
     * @param data
     * @param numbers
     * @param reverse 从大到小(false)还是从小到大(ture)
     */
    public static void resetOrder(int[] data, ArrayList<ArrayList<Integer>> numbers, boolean reverse) {
        int n = 0;
        if (reverse) {
            for (int i = 0; i < numbers.size(); i++) {
                ArrayList<Integer> list = numbers.get(i);
                while(list.size()>0){
                    data[n++] = list.get(0);
                    list.remove(0);
                }
            }
        } else {
            for (int i = numbers.size() - 1; i >= 0; i--) {
                ArrayList<Integer> list = numbers.get(i);
                while(list.size()>0){
                    data[n++] = list.get(0);
                    list.remove(0);
                }
            }
        }
    }
    
    /**
     * 选择排序
     * @param data  要排序的数组
     * @param reverse 从大到小(false)还是从小到大(ture)
     */
    public static void sort4(int[] data, boolean reverse) {
        if (data.length == 1) {
            return;
        }
        for(int i=0;i<data.length-1;i++){
            int tmp=data[i];    //要初始化
            int index = i;      //要初始化
            for(int j=i;j<data.length;j++){
                if(reverse) {   //从小到大(ture)
                    if (tmp>=data[j]){
                        tmp = data[j];  //最小值
                        index = j;
                    }
                }else {
                    if (tmp<=data[j]){
                        tmp = data[j];  //最大值
                        index = j;
                    }
                }
            }
            data[index] = data[i];
            data[i] = tmp;
        }
    }
    
    /**
     * 希尔排序
     * @param data    要排序的数组
     * @param reverse 从大到小(false)还是从小到大(ture)
     */
    public static void sort5(int[] data, boolean reverse) {
        if (data.length == 1) {
            return;
        }
        for (int d = data.length / 2; d >= 1; d = d / 2) {  //组大小
            for (int k = 0; k < d; k++) {   //多少组
                for (int n = d + k; n < data.length; n = n + d) {   //同一组
                    int cur = n;
                    while (cur - d >= 0) {  //插入排序
                        int tmp = 0;
                        if (reverse) {  //小到大(ture)
                            if (data[cur] <= data[cur - d]) {
                                tmp = data[cur];
                                data[cur] = data[cur - d];
                                data[cur - d] = tmp;
                            }
                        } else {         //从大到小(false)
                            if (data[cur] >= data[cur - d]) {
                                tmp = data[cur];
                                data[cur] = data[cur - d];
                                data[cur - d] = tmp;
                            }
                        }
                        cur = cur - d;
                    }
                }
            }
        }

    }
}
