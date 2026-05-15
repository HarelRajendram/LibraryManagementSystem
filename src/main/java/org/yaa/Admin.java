package org.yaa;

public class Admin extends User implements Reportable {

    public Admin(String userId, String userName) {
        super(userId, userName);
    }

    @Override
    public int getBorrowLimit() {
        return Integer.MAX_VALUE;
    }

    public void backup(Library library) throws LibraryException {
        library.backupData();
    }

    /**
     * generates a report for the admin
     * @return the report with information to the admin
     */

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Admin report for ").append(userName).append("\n");
        sb.append("Borrowed Items:\n");

        for (Item item : borrowedItems) {
            sb.append("- ").append(item.getItemId())
                    .append(" (").append(item.getItemStatus()).append(")\n");
        }

        return sb.toString();
    }
}
