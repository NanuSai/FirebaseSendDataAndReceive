package com.saiproject.firebase2.Model;

public class Computer {


    //Private variables

    private String computerName;
    private int computerPower;
    private int computerSpeed;
    private int computerRAM;

    public Computer(){

    }

    //Constructor
    public Computer(String computerName, int computerPower, int computerSpeed, int computerRAM) {
        this.computerName = computerName;
        this.computerPower = computerPower;
        this.computerSpeed = computerSpeed;
        this.computerRAM = computerRAM;
    }


    //Getters and setters


    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public int getComputerPower() {
        return computerPower;
    }

    public void setComputerPower(int computerPower) {
        this.computerPower = computerPower;
    }

    public int getComputerSpeed() {
        return computerSpeed;
    }

    public void setComputerSpeed(int computerSpeed) {
        this.computerSpeed = computerSpeed;
    }

    public int getComputerRAM() {
        return computerRAM;
    }

    public void setComputerRAM(int computerRAM) {
        this.computerRAM = computerRAM;
    }
}
