package com;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by zhoulf on 2017/1/24.
 */
public class TLink {
    public Object v;
    public TLink next;

    public TLink(Object v) {
        this.v = v;
    }

    public TLink() {
    }

    public int getInt() {
        return Integer.parseInt(v.toString());
    }

    public  TLink uniq(){
        TLink pre = this.next;
        TLink cur;
        while(pre!=null){
            cur = pre.next;
            if(cur!=null && cur.v.equals(pre.v)){
                pre.next = cur.next;
                cur = null;
            }else{
                pre = cur;
            }
        }
        return this;
    }

    public static TLink reverse(TLink head, int from, int to) {
        TLink result = head;
        TLink cur = head.next;
        int i;
        for (i = 0; i < from - 1; i++) {
            head = cur;
            cur = cur.next;
        }
        TLink pre = cur;
        cur = cur.next;
        to--;
        TLink next;
        for (; i < to; i++) {
            next = cur.next;
            cur.next = head.next;
            head.next = cur;
            pre.next = next;
            cur = next;
        }
        return result;
    }

    public static TLink add(TLink at, TLink bt) {
        return add(at, bt, true);
    }

    public static TLink add(TLink at, TLink bt, boolean asc) {
        TLink a = at.next;
        TLink b = bt.next;
        TLink sum = new TLink();
        TLink link = sum;
        int cur = 0;
        while (a != null && b != null) {
            int v = a.getInt() + b.getInt() + cur;
            cur = v / 10;
            link = append(new TLink(v % 10), link, asc);
            a = a.next;
            b = b.next;
        }
        TLink temp = a == null ? b : a;
        while (temp != null) {
            int v = temp.getInt() + cur;
            cur = v / 10;
            link = append(new TLink(v % 10), link, asc);
            temp = temp.next;
        }
        if (cur != 0) {
            append(new TLink(cur), link, asc);
        }
        return sum;
    }

    private static TLink append(TLink newNode, TLink link, boolean asc) {
        if (asc) {
            link.next = newNode;
            link = newNode;
        } else {
            newNode.next = link.next;
            link.next = newNode;
        }
        return link;
    }

    public static TLink build(Integer n) {
        TLink a = new TLink();
        TLink h = a;
        while (n > 0) {
            TLink t = new TLink();
            t.v = n % 10;
            a.next = t;
            a = t;
            n /= 10;
        }
        return h;
    }
    /**
     * h->1->2->3->4->5->null
     * @param s
     * @return
     */
    public static TLink build(String s) {
        TLink a = new TLink();
        TLink h = a;
        for (int i = s.length() - 1; i >= 0; i--) {
            TLink t = new TLink(s.charAt(i));
            a.next = t;
            a = t;
        }
        return h;
    }
    /**
     * h->5->4->3->2->1->null
     * @param s
     * @return
     */
    public static TLink buildReverse(String s) {
        TLink a = new TLink();
        TLink h = a;
        for (int i = 0; i <s.length() ; i++) {
            TLink t = new TLink(s.charAt(i));
            a.next = t;
            a = t;
        }
        return h;
    }

    public TLink print() {
        TLink t = this.next;
        LinkedList q = new LinkedList();
        while (t != null) {
            q.push(t.v);
            t = t.next;
        }
        for (Object v : q) {
            System.out.print(v);
        }
        System.out.println();
        return this;
    }

    public void printAseOrder() {
        TLink t = this.next;
        while (t != null) {
            System.out.print(t.v);
            t = t.next;
        }
    }

    public static void main(String[] args) {
//        add(build(735), build(452)).print();
//        reverse(build("123456789abc"), 3, 6).print();
        build("12222333344223345553").uniq().print();
    }
}
