package org.yaa;

public class SortUserByName implements Sorting {

    @Override
    public int compare(Object o1, Object o2) {
        User u1 = (User) o1;
        User u2 = (User) o2;

        return u1.getUserName().compareToIgnoreCase(u2.getUserName());
    }
}