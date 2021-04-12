package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static int totalWater = 400;
    public static int totalMilk = 540;
    public static int totalBeans = 120;
    public static int totalCups = 9;
    public static int totalMoney = 550;
    enum Action { BUY, FILL, TAKE, REMAINING, EXIT }

    public static void main(String[] args) {
        boolean need_action = true;

        while (need_action) {
            String action = getAction();
            switch (action) {
                case "buy":
                    buy_action();
                    break;
                case "fill":
                    fill_action();
                    break;
                case "take":
                    take_action();
                    break;
                case "remaining":
                    show_state();
                    break;
                case "exit":
                    need_action = false;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }

    }

    private static void take_action() {
        System.out.printf("I gave you $%d%n%n", totalMoney);
        totalMoney = 0;
    }

    private static void fill_action() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water do you want to add:");
        int fillWater = scanner.nextInt();
        totalWater += fillWater;
        System.out.println("Write how many ml of milk do you want to add:");
        int fillMilk = scanner.nextInt();
        totalMilk += fillMilk;
        System.out.println("Write how many grams of coffee beans do you want to add:");
        int fillGrams = scanner.nextInt();
        totalBeans += fillGrams;
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        int fillCups = scanner.nextInt();
        totalCups += fillCups;
    }

    private static void buy_action() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
        String drink = scanner.nextLine();
        if (check_resources(drink)) {
            System.out.printf("I have enough resources, making you a %s!%n", drink);
            switch (drink) {
                case "1":
                    buy_espresso();
                    break;
                case "2":
                    buy_latte();
                    break;
                case "3":
                    buy_cappuccino();
                    break;
                case "back":
                    return;
                default:
                    break;
            }
        }
    }

    private static boolean check_resources(String drink) {
        boolean resourcesGood = false;
        switch (drink) {
            case "1":
                if (check_water(250) && check_beans(16)) {
                    resourcesGood = true;
                }
                break;
            case "2":
                if (check_water(350) &&
                check_beans(20) &&
                check_milk(75)) {
                    resourcesGood = true;
                }
                break;
            case "3":
                if(check_water(200) &&
                check_beans(100) &&
                check_milk(12)) {
                    resourcesGood = true;
                }
                break;
            default:
                break;
        }
        return resourcesGood;
    }

    private static boolean check_beans(int beans) {
        if (totalBeans >= beans) {
            return true;
        } else {
            System.out.println("Sorry, not enough coffee!");
            return false;
        }
    }

    private static boolean check_water(int water) {
        if (totalWater >= water) {
            return true;
        } else {
            System.out.println("Sorry, not enough water!");
            return false;
        }
    }

    private static boolean check_milk(int milk) {
        if (totalMilk >= milk) {
            return true;
        } else {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
    }

    private static void useCup() {
        totalCups -= 1;
    }

    private static void buy_cappuccino() {
            totalWater -= 200;
            totalMilk -= 100;
            totalBeans -= 12;
            totalMoney += 6;
            useCup();
    }


    private static void buy_latte() {
        totalWater -= 350;
        totalMilk -= 75;
        totalBeans -= 20;
        totalMoney += 7;
        useCup();
    }

    private static void buy_espresso() {
        totalWater -= 250;
        totalBeans -= 16;
        totalMoney += 4;
        useCup();
    }

    private static String getAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        return scanner.nextLine();
    }

    private static void show_state() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water%n", totalWater);
        System.out.printf("%d of milk%n", totalMilk);
        System.out.printf("%d of coffee beans%n", totalBeans);
        System.out.printf("%d of disposable cups%n", totalCups);
        System.out.printf("%d of money%n", totalMoney);

    }

    private static void showCapacity(int mlWater, int mlMilk, int gCoffee, int cupsNeeded) {
        int cupsForGivenWater = calcCupsForWater(mlWater);
        int cupsForGivenMilk = calcCupsForGivenMilk(mlMilk);
        int cupsForGivenBeans = calcCupsForGivenBeans(gCoffee);
        int leastCups = findLeastCups(cupsForGivenWater, cupsForGivenMilk, cupsForGivenBeans);

        if (cupsNeeded == leastCups) {
            System.out.println("Yes, I can make that amount of coffee");
        } else if (cupsNeeded < leastCups) {
            System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)", (leastCups - cupsNeeded));
        } else {
            System.out.printf("No, I can make only %d cup(s) of coffee", leastCups);
        }
    }

    private static int findLeastCups(int... numbers) {
        int leastNumber = numbers[0];

        for(int number = 1; number < numbers.length; number++) {
            leastNumber = Math.min(leastNumber, numbers[number]);
            }
        return leastNumber;
    }

    private static int calcCupsForGivenBeans(int gCoffee) {
        return gCoffee/ totalBeans;
    }

    private static int calcCupsForGivenMilk(int mlMilk) {
        return mlMilk/ totalMilk;
    }

    private static int calcCupsForWater(int mlWater) {
        return mlWater/ totalWater;
    }
}
