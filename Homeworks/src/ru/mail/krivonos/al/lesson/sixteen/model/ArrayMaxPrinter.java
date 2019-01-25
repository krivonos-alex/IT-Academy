package ru.mail.krivonos.al.lesson.sixteen.model;

import ru.mail.krivonos.al.lesson.sixteen.RandomService;

public class ArrayMaxPrinter extends RandomArrayUser {


    public ArrayMaxPrinter(String s, int capacity, int range, RandomService randomService) {
        super(s, capacity, range, randomService);
    }

    @Override
    public void run() {
        int[] array = randomService.getIntegersArray(capacity, range);
        int max = getMaxArrayElement(array);
        System.out.println(getName() + " - max element: " + max);
    }

    private int getMaxArrayElement(int[] array) {
        int max = array[0];
        for (int element : array) {
            max = Math.max(max, element);
        }
        return max;
    }
}
