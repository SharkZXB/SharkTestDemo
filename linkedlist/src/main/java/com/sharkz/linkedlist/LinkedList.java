package com.sharkz.linkedlist;

import org.w3c.dom.Node;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/15  14:21
 * 描    述
 * 修订历史：
 * ================================================
 */
public class LinkedList<T> {

    /**
     * 节点 class
     *
     * @param <T>
     */
    private static class Node<T> {

        private T t;
        private Node<T> next;

        private Node(T t) {
            this.t = t;
        }

    }

    /**
     * 头节点，头节点是为了方便在下面的方法中遍历链表用的
     * 初始化的时候默认创建一个空的node对象
     */
    private Node<T> head = new Node<>(null);

    /**
     * 链表的数据量
     */
    private int size;

    public LinkedList() {

    }


    /**
     * 增
     *
     * @param t
     */
    public void add(T t) {
        Node<T> temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Node<>(t);
        size++;
    }

    /**
     * 指定位置添加元素
     *
     * @param index 添加的位置
     * @param t     添加的数据
     */
    public void add(int index, T t) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("插入的位置不合法");
        } else {
            Node<T> temp = head;
            int counter = -1;
            while (temp != null) {
                // 找到了目标节点的前一个节点
                if ((index - 1) == counter) {
                    //创建新的Node
                    Node<T> node = new Node<T>(t);
                    // 这个就是目标i位置上的那个节点
                    Node<T> back = temp.next;
                    temp.next = node; // 将目标i前面的那个的next指向插入的，
                    node.next = back; // 将插入的node的next指向原来的node的next
                    size++; // 新增数据成功
                    break;
                }
                temp = temp.next;
                counter++;
            }
        }
    }

    /**
     * 删除
     *
     * @param i 目标
     */
    public void delete(int i) {
        if (i < 0 || i > size) {
            throw new ArrayIndexOutOfBoundsException("删除的位置不合法");
        } else {
            Node<T> temp = head;
            int counter = -1;
            while (temp != null) {
                //将i前面的节点指向i后面的节点
                if ((i - 1) == counter) {
                    temp.next = temp.next.next;
                    size--;
                    return;
                }
                counter++;
                temp = temp.next;
            }
        }
    }

    /**
     * 改
     */
    public T upData(int index, T t) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException("获取的位置不合法");
        } else {
            Node<T> temp = head;
            int counter = -1;
            while (temp != null) {
                // 找到目标
                if (counter == index) {
                    temp.t = t;
                    return temp.t;
                }
                temp = temp.next;
                counter++;
            }
        }
        return null;
    }

    /**
     * 获取元素值
     *
     * @param i 获取第几个
     * @return 目标数据
     */
    public T get(int i) {
        if (i < 0 || i > size - 1) {
            throw new ArrayIndexOutOfBoundsException("获取的位置不合法");
        } else {
            //把第一个节点给临时节点temp，让temp遍历
            Node<T> temp = head;
            //counter用来计数，找到i在链表里的节点位置,头节点不算链表的真实节点，所以从-1开始计数
            int counter = -1;
            while (temp != null) {
                if (counter == i) {
                    return (T) temp.t;
                }
                temp = temp.next;
                counter++;
            }
        }
        return null;
    }

    /**
     * 获取 当前链表的数量
     *
     * @return
     */
    public int getSize() {
        return size;
    }


    public static void main(String[] args) {

        LinkedList<String> list = new LinkedList<>();

        System.out.println(list.size);
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        System.out.println(list.size);

        list.add(0, "E");
        System.out.println(list.size);

        list.delete(1);
        System.out.println(list.size);

        // 修改数据
        list.upData(0,"AA");

        for (int i = 0; i < list.size; i++) {
            System.out.println("i=" + list.get(i));
        }
    }

}
