/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ospattern;
import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
/**
 *
 * @author hp640
 */
public class SemaphoreSimulation {
    private static final int BUFFER_SIZE = 4;//giới hạn bộ đệm
    private final Semaphore writePermits = new Semaphore(BUFFER_SIZE);
    private final Semaphore readPermits = new Semaphore(0);
    private final Stack<Integer> buffer = new Stack<>();

    class Producer implements Runnable {
    private String name;

    public Producer(String name) {
    this.name = name;
    }

    @Override
    public void run() {
    try {
    while (true) {
        System.out.println(name + ": lấy khóa…");
        System.out.println(name + ": Số quyền truy cập hiện tại của Producer: " + writePermits.availablePermits());
        writePermits.acquire();
        System.out.println(name + ": Đã có được quyền!");

        Thread.sleep(500); // thời gian giả lập đang thực hiện công việc
        int data = ThreadLocalRandom.current().nextInt(100);
        System.out.println(name + ": đã sản xuất dữ liệu " + buffer.push(data));

        System.out.println(name + ": bỏ khóa…");
        readPermits.release();
        System.out.println(name + ": Số quyền truy cập hiện tại của Consumer: " + readPermits.availablePermits());
        }
    } catch (InterruptedException e) {
        System.out.println("Dây chuyền bị gián đoạn!");
        e.printStackTrace();
        }
    }
    }

    class Consumer implements Runnable {
    private String name;

    public Consumer(String name) {
    this.name = name;
    }

    @Override
    public void run() {
    try {
    while (true) {
        System.out.println(name + ": lấy khóa…");
        System.out.println(name + ": Số quyền truy cập hiện tại của Consumer: " + readPermits.availablePermits());
        readPermits.acquire();

        Thread.sleep(ThreadLocalRandom.current().nextInt(50, 300)); // thời gian giả lập để làm việc
        System.out.println(name + ": đã tiêu thụ dữ liệu " + buffer.pop());

        System.out.println(name + ": gỡ khóa…");
        writePermits.release();
        System.out.println(name + ": Số quyền truy cập hiện tại của Producer: " + writePermits.availablePermits());
        }
    } catch (InterruptedException e) {
        System.out.println("Dây chuyền bị gián đoạn!");
        e.printStackTrace();
        }   
    }
    }
    
    public static void main(String[] args) throws InterruptedException {
    SemaphoreSimulation obj = new SemaphoreSimulation();
    Producer producer = obj.new Producer("Producer 1");
    new Thread(producer).start();

    for (int i = 1; i <= 3; i++) {
        Consumer consumer = obj.new Consumer("Consumer " + i);
        new Thread(consumer).start();
    }

    Thread.sleep(5000); // Sau 5 giây sẽ có 1 nhà sản xuất mới
    Consumer consumer = obj.new Consumer("Consumer " + 4);
    new Thread(consumer).start();
    }
    }
