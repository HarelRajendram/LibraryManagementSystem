package org.yaa;

public class Admin extends User implements Reportable {

    public Admin(String userId, String userName) {
        super(userId, userName);
    }

    @Override
    public int getBorrowLimit() {
        return Integer.MAX_VALUE;
    }
    public void backupData(Library library) throws LibraryException {
        library.backupData();
    }
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Admin report").append(userName).append("/n");
        sb.append("Borrowed Items: /n");

        for (Item item : borrowedItems) {
            sb.append("-").append(item.getItemId())
                    .append("(").append(item.getItemStatus())
                    .append(")");
        }
        return sb.toString();
    }
}
