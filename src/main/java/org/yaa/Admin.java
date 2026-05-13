package org.yaa;

public class Admin extends User implements Reportable {

    public Admin(String userId, String userName) {
        super(userId, userName);
    }

    @Override
    public int getBorrowLimit() {
        return Constants.MAX_ITEMS_TEACHER;
    }
    public void backupData() {

    }
    public String generateReport() {

    }
}
