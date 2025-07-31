package com.example;

// לפי ההצעה של רומן ואיך ששי היה עושה - היה יוצר מחלקה לאובייקט של האלמנט עם
// הערך שלו ועושה במחלקה של הרשימה פשוט רשימה של האיברים האלה ולא מפה.

public class BoundElement<E> {
  private E element;
  private Long value; // addition time;

  public BoundElement(E elemnt, Long value) {
    this.element = elemnt;
    this.value = System.currentTimeMillis();
  }

  public long getValue() {
    return this.value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  public E getElement() {
    return this.element;
  }

  public void setElement(E element) {
    this.element = element;
  }

}
