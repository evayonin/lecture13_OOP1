package com.example;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        // נכניס בלולאת ווייל טרו איברים לרשימה כדי לראות איך זה עובד, גם זה חייב להיות
        // בתהליכון
        // כדי שלא ייתקע את התכנית בלולאה ולא יתקדם (רוצים שההכנסה התמידית וההסרה יהיו
        // בו זמנית אז זה מובן מאליו)
        // נעשה אותה מסוג אינטג׳ר כדי להוסיף באופן אקראי מספרים מטווח מספרים בלי הפסקה.

        TimeBoundList<Integer> timeBoundList = new TimeBoundList<>(10); // יסיר איבר 10 שניות אחרי שהוסיף אותו
        new Thread(() -> {
            while (true) {
                timeBoundList.add(new Random().nextInt(100)); // מוסיף מספר אקראי עד 100 כל 3 שניות
                try { // חייב להיות בתוך הווייל שבתהליכון כדי שיעשה את זה כל כמה שניות במקביל
                    Thread.sleep(3000);
                    System.out.println(timeBoundList);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // תהליכון להדפסת רשימת האיברים המחוקים במקביל, לבדיקה

        new Thread(() -> {
            while (true) {
                System.out.println("REMOVED LIST: " + timeBoundList.getRemovedElements());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
}