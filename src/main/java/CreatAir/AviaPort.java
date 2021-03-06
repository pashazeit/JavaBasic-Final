package CreatAir;

import InterFace.Bank;
import InterFace.goFuel;
import Main.MainGo;

import java.util.ArrayList;

import static InterFace.Bank.ticket_cost;

/**
 * Created by Zeit on 25.10.2016.
 */

public class AviaPort extends Aircraft implements goFuel, Bank, java.io.Serializable {


    public static ArrayList<AviaPort> all = new ArrayList<>();
    public String name;
    public int speed;
    public int range;
    public int weight;
    public int fuel;
    public int tank;
    public int number;
    public int passenger;
    public int passenger_seat;
    public int flyCost;


    public AviaPort() {
        this.name = super.name();
        this.speed = super.speed();
        this.range = super.range();
        this.weight = super.weight();
        this.fuel = super.fuel();
        this.number = super.number();
        this.tank = super.tank();
        this.passenger = super.passenger();
        this.passenger_seat = super.passenger_seat();
        this.flyCost = this.passenger * ticket_cost;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public int getWeight() {
        return weight;
    }

    public int getFuel() {
        return fuel;
    }

    public int getTank() {
        return tank;
    }

    public int getPassenger() {
        return passenger;
    }

    public int getPassenger_seat() {
        return passenger_seat;
    }

    public int getFlyCost() {
        return flyCost;
    }

    public int getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void inPassenger() {
        if ((this.passenger + 10) > this.passenger_seat) this.passenger = this.passenger_seat;
        else this.passenger += 10;
        this.flyCost = this.passenger * ticket_cost;
    }

    public void outPassenger() {
        if (this.passenger >= 10) {
            this.passenger -= 10;
        } else this.passenger = 0;
        this.flyCost = this.passenger * ticket_cost;
    }

    public void inFuel() {
        if (MainGo.Money - inBak * MainGo.fuel_cost > 0) {
            if ((this.fuel + inBak) > this.tank) this.fuel = this.tank;
            else {
                this.fuel += inBak;
                MainGo.Money -= inBak * MainGo.fuel_cost;
            }
        }


    }


    public void outFuel() {

        if (this.fuel > 100) {
            this.fuel -= super.outBak();
            MainGo.Money += 100 * MainGo.fuel_cost;
        } else this.fuel = 0;

    }


    public String toString() {
        return "aircraft model - " + this.name + "-" + this.number + "! speed - " +
                this.speed + "! range - " + this.range + "! weight - " +
                this.weight + "! fuel - " + this.fuel + "! tank capacity - " + this.tank + "! passenger - " +
                this.passenger + "! passenger seat - " +
                this.passenger_seat + "! fly cost - " + this.flyCost;
    }


    public static int settings(int setting, int aa) {
        if (setting == 1)
            for (AviaPort a : all) {
                if (a.getNumber() == aa) {
                    a.inFuel();
                }
            }
        return setting;
    }

    public static int settingss(int setting, int aa) {
        if (setting == 2)
            for (AviaPort a : all) {
                if (a.getNumber() == aa) {
                    a.outFuel();
                }
            }
        return setting;
    }


}


