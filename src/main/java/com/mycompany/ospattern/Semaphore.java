/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ospattern;

import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp640
 */
public class Semaphore {
    private static final int BUFFER_SIZE = 4;//giới hạn bộ đệm
    private final java.util.concurrent.Semaphore writePermits = new java.util.concurrent.Semaphore(BUFFER_SIZE);
    private final java.util.concurrent.Semaphore readPermits = new java.util.concurrent.Semaphore(0);
    private final Stack<Integer> buffer = new Stack<>();

    public static int getBUFFER_SIZE() {
        return BUFFER_SIZE;
    }

    public int getWritePermits() {
        return writePermits.availablePermits();
    }

    public int getReadPermits() {
        return readPermits.availablePermits();
    }

    public void importdata(String nameP,int data)
    {
        
        try {
            //lấy quyền ghi dữ liệu
            this.writePermits.acquire();
            System.out.println(nameP+": Đã có được quyền!");
            
        } catch (InterruptedException ex) {
            System.out.println("Dây chuyền bị gián đoạn!");
        }
        //đẩy dữ liệu vào bộ đệm
        this.buffer.push(data);
        System.out.println(nameP+": Đã sản xuất dữ liệu "+ data);
        
        //trả quyền đọc dữ liệu cho Consumer
        this.readPermits.release();
    }
    public void exportData(String nameC)
    {
        try {
            //lấy quyền đọc dữ liệu
            this.readPermits.acquire();
            
        } catch (InterruptedException ex) {
            System.out.println("Dây chuyền bị gián đoạn!");
        }
        //xóa dữ liệu ra khỏi bộ đệm
        System.out.println(nameC+": Đã tiêu thụ dữ liệu "+ this.buffer.pop());
        
        //trả quyền ghi dữ liệu cho Producer
        this.writePermits.release();
    }
}
