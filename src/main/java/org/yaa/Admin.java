package org.yaa;

public class Admin extends User implements Reportable {

    public Admin(String userId, String userName) {
        super(userId, userName);
    }

    @Override
    public int getBorrowLimit() {
        return Integer.MAX_VALUE;
    }
    public void backupData() throws LibraryException {

    }
    public String generateReport() {
        return "Report not implemented but soon";
    }
}
