package com.oskelly;

public class BusinessLogic {

    public static void sleepAndRandomThrowRuntimeException(int seconds, int exceptionProbabilityProc) {
        try {
            Thread.sleep((long) (seconds * 1000 * Math.random()));
        } catch (InterruptedException e) {
        }
        int randomProc = (int) (100 * Math.random());
        System.out.println(exceptionProbabilityProc + "---" + randomProc);
        if (exceptionProbabilityProc > randomProc) throw new RuntimeException();
    }

    public static void doSomeWorkOnNotification() {
        System.out.println("do some work for notification");
        sleepAndRandomThrowRuntimeException(2, 10);
    }

    public static void doSomeWorkOnCommentCreation() {
        System.out.println("do some work for comment");
        sleepAndRandomThrowRuntimeException(1, 30);
    }
}
