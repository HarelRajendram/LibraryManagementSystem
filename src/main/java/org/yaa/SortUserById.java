package org.yaa;

public class SortUserById implements Sorting {

    @Override
    public int compare(Object o1, Object o2) {
        User u1 = (User) o1;
        User u2 = (User) o2;

        return u1.getUserId().compareToIgnoreCase(u2.getUserId());
    }
}
