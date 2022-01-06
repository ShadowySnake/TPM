package modList;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class modifiedOptimistic<T> {
    public Node head;
    AtomicInteger version;

    public modifiedOptimistic() {
        this.head  = new Node(Integer.MIN_VALUE);
        this.head.next = new Node(Integer.MAX_VALUE);
        this.version = new AtomicInteger(0);
    }

    public boolean add(T item) {
        int key = item.hashCode();
        while (true) {
            Node pred = this.head;
            Node current = pred.next;
            while (current.key < key) {
                pred = current; current = current.next;
            }
            pred.lock(); current.lock();
            try {
                if (validate(pred, current)) {
                    if (current.key == key) { // present
                        return false;
                    } else {               // not present
                        Node entry = new Node(item);
                        entry.next = current;
                        pred.next = entry;
                        return true;
                    }
                }
            } finally {                // always unlock
                pred.unlock(); current.unlock();
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        while (true) {
            Node pred = this.head;
            Node current = pred.next;
            while (current.key < key) {
                pred = current; current = current.next;
            }
            pred.lock(); current.lock();
            try {
                if (validate(pred, current)) {
                    if (current.key == key) { // present in list
                        pred.next = current.next;
                        return true;
                    } else {               // not present in list
                        return false;
                    }
                }
            } finally {                // always unlock
                pred.unlock(); current.unlock();
            }
        }
    }

    public boolean contains(T item) {
        int key = item.hashCode();
        while (true) {
            Node pred = this.head; // sentinel node;
            Node current = pred.next;
            while (current.key < key) {
                pred = current; current = current.next;
            }
            try {
                pred.lock(); current.lock();
                if (validate(pred, current)) {
                    return (current.key == key);
                }
            } finally {                // always unlock
                pred.unlock(); current.unlock();
            }
        }
    }

    private boolean validate(Node pred, Node current){
       Node entry = head;
        int i = 0;
        while (i <= version.get()) {
            if (entry == pred) return pred.next == current;
            entry = entry.next;
            i++;
        }
        return true;
    }

    public void printList(){
        Node start = head;
        while (start != null){
            System.out.println(start.item);
            start = start.next;
        }
    }

    private class Node {
        T item;
        int key;
        Node next;
        Lock lock;

        Node(T item) {
            this.item = item;
            this.key = item.hashCode();
            lock = new ReentrantLock();
        }

        Node(int key) {
            this.key = key;
            lock = new ReentrantLock();
        }

        void lock() {lock.lock();}

        void unlock() {lock.unlock();}
    }
}