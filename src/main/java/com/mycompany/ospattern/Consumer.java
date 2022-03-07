/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ospattern;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author hp640
 */
public class Consumer extends Thread {
    private String name;
    private Semaphore s;
    
    public Consumer(String name,Semaphore s) {
    this.name = name;
    this.s=s;
    }

    @Override
    public void run() {
    try {
    while (true) {
        System.out.println(name + ": lấy quyền đọc dữ liệu…");
        System.out.println(name + ": Số quyền truy cập hiện tại của Consumer: " + s.getReadPermits());
        // thời gian giả lập để lấy và xử lý dữ liệu
        Thread.sleep(ThreadLocalRandom.current().nextInt(500, 3000)); 
        s.exportData(name);

        System.out.println(name + ": cấp quyền ghi cho Producer…");
        System.out.println(name + ": Số quyền truy cập hiện tại của Producer: " + s.getWritePermits());
        }
    } catch (InterruptedException e) {
        System.out.println("Dây chuyền bị gián đoạn!");
        e.printStackTrace();
        }   
    }
    }
