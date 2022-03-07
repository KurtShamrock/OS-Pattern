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
public class Producer extends Thread {
    private String name;
    private Semaphore s;
    
    public Producer(String name,Semaphore s) {
    this.name = name;
    this.s=s;
    }

    @Override
    public void run() {
    try {
    while (true) {
        System.out.println(name + ": bắt đầu lấy quyền ghi dữ liệu…");
        System.out.println(name + ": Số quyền truy cập hiện tại của Producer: " + s.getWritePermits());

        Thread.sleep(500); // thời gian giả lập đang thực hiện công việc
                
        int data = ThreadLocalRandom.current().nextInt(100);

        s.importdata(name,data);

        System.out.println(name + ": cấp quyền đọc dữ liệu…");
        System.out.println(name + ": Số quyền truy cập hiện tại của Consumer: " + s.getReadPermits());
        }
    } catch (InterruptedException e) {
        System.out.println("Dây chuyền bị gián đoạn!");
        }
    }
    }
