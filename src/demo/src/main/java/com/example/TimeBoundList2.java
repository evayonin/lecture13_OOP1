package com.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// המחלקה שאני כתבתי לתרגול עצמי בשיטה של יצירת מחלקה עבור אובייקט אלמנט בלי שימוש במפה
public class TimeBoundList2<E> {
  private int timeout; // in sec
  private List<BoundElement<E>> boundElemensList; // האות ביהלום כי המחלקה כאן גנרית
  private List<BoundElement<E>> removedElements;
  // אם לא היינו רוצים להגביל את הטייפה של הרשימה:
  // private List<E> list = new ArrayList();
  // נעדיף מצד שמאל לקבוע כליסט ולא כסוג הרשימה כדי שיהיה ניתן לשנות אם צריך ולא
  // לקבע את הפעולות של סוג מסויים של רשימה

  public TimeBoundList2(int timeout) {
    this.timeout = timeout;
    this.boundElemensList = new ArrayList<>();
    this.removedElements = new ArrayList<>();
    removeElements();
  }

  public void add(E element, Long value) { // אם רוצים ליצור את האובייקט במתודה ולהכניס לרשימה ביחד (קצר יותר במיין)
    boundElemensList.add(new BoundElement<E>(element)); // מקבל רק אלמנט כי את הערך של הזמן יוצרים בבנאי שלו
  }

  // או:
  // אם יצרנו את האובייקט של האלמנט במיין:
  // public void add(BoundElement boundElement) {
  // boundElemensList.add(boundElement);
  // }

  private void removeElements() {
    new Thread(() -> {
      // כל הרשימה קיימת (הוגדרה עם הגדרת אובייקט הטיים-באונד-ליסט במיין)יבדוק אותה
      // לנצח
      while (true) {
        // כדי לרוץ על רשימת האלמנטים נעשה איטרטור (או רשימה מקבילה)
        Iterator<BoundElement<E>> iterator = boundElemensList.iterator(); // עם האות ביהלום כי מחלקה גנרית
        // כותבים בהגדרה משמאל את הטייפ של האיברים באוסף (רשימה) שהופכים אותו לאופרטור
        while (iterator.hasNext()) {
          BoundElement<E> currentBoundElement = iterator.next();
          Long now = System.currentTimeMillis();
          if ((now - currentBoundElement.getValue()) > timeout / 1000) { // אם התיישן
            iterator.remove(); // מוציא את הנוכחי (שעליו מצביע הנקסט) מהרשימה
            removedElements.add(currentBoundElement); // ג׳
            System.out.println("removed the element: " + currentBoundElement.getElement());
          }
        }
        try {
          Thread.sleep(10); // יבדוק כל 10 מילי שנניות
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  // סעיף ב
  public List getRemovedElement() {
    return this.removedElements;
  }

  // סעיף ג
  // ברשימה שלא כמו במפה - כן ניתן לפנות לאינדקסים
  public void refresh(int index) {
    boundElemensList.get(index).setValue(System.currentTimeMillis());
  }
}

// צריך לשים יהלום עם האות בהגדרות
// כי בגלל שהמחלקה שבה מגדירים את הרשימה הזאת היא גנרית אי אפשר להכניס לרשימה
// טייפ מסוים אז חייב להוסיף את היישום הגנרי.
// כדי לשלא יהיה אפשר להוסיף אליה סתם אינט מכאן וכאלה.