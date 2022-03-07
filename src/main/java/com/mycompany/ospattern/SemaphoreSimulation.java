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
    
    public static void main(String[] args) throws InterruptedException 
    { 
    com.mycompany.ospattern.Semaphore s = new com.mycompany.ospattern.Semaphore();

    Producer producer = new Producer("Producer 1",s);
    producer.start();

    for (int i = 1; i <= 3; i++) {
        System.out.println("--Có Consumer mới vào dây chuyền--\n");
        Consumer consumer = new Consumer("Consumer " + i,s);
        consumer.start();
    }

    Thread.sleep(5000); // Sau 5 giây sẽ có 1 người tiêu dùng thứ 4
    System.out.println("--Có Consumer mới vào dây chuyền--\n");
    Consumer consumer = new Consumer("Consumer " + 4,s);
    consumer.start();
    }
    }
