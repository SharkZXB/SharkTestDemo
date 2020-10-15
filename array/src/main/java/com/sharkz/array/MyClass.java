package com.sharkz.array;

import java.util.Arrays;

/**
 * 测试 数据
 */
public class MyClass {

    public static void main(String[] args) {

        int[] arr1 = {22, 3, 12, 45, 367, 85, 234, 1, 76, 34, 78, 83, 13};
        // 冒泡排序之前
        System.out.println("原数组：" + Arrays.toString(arr1));
        bubbleSort1(arr1);
        // 冒泡排序之后
        System.out.println("排序后：" + Arrays.toString(arr1));

        System.out.println("==================================================");

        int[] arr2 = {11, 3, 29, 49, 30, 7, 50, 63, 46, 31, 22};
        System.out.println("原数组：" + Arrays.toString(arr2));
        bubbleSort2(arr2);
        System.out.println("排序后：" + Arrays.toString(arr2));


        System.out.println("==================================================");

        int[] arr3 = {11, 3, 29, 49, 30, 7, 50, 63, 46, 11, 1};
        System.out.println("原数组：" + Arrays.toString(arr3));
        bubbleSort3(arr3);
        System.out.println("排序后：" + Arrays.toString(arr3));
    }


    /**
     * 冒泡排序---基础版
     *
     * @param arr
     * @return
     */
    public static int[] bubbleSort1(int[] arr) {
        // 外层大循环 最后一位不参与循环
        for (int i = 0; i < arr.length - 1; i++) {
            // 内层一一对比最后一位不参与循环
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * 冒泡排序---进阶版1
     *
     * @param arr
     * @return
     */
    public static int[] bubbleSort2(int[] arr) {
        int i = arr.length - 1;//初始时,最后位置保持不变　　
        while (i > 0) {
            int flag = 0;//每趟开始时,无记录交换
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = j;//记录交换的位置
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
            i = flag; //为下一趟排序作准备
        }
        return arr;
    }

    /**
     * 冒泡排序---进阶版2
     *
     * @param arr
     * @return
     */
    public static int[] bubbleSort3(int[] arr) {
        int low = 0;
        int high = arr.length - 1;//设置变量的初始值
        while (low < high) {
            int f1 = 0, f2 = 0;//每趟开始时,无记录交换
            for (int i = low; i < high; ++i) {         //正向冒泡,找到最大者
                if (arr[i] > arr[i + 1]) {
                    int tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    f1 = i;
                }
            }
            high = f1;// 记录上次位置
            for (int j = high; j > low; --j) {          //反向冒泡,找到最小者
                if (arr[j] < arr[j - 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = tmp;
                    f2 = j;
                }
            }
            low = f2; //修改low值
        }
        return arr;
    }

}