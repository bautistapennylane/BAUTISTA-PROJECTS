import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.util.Scanner;

public class CRUDRowSetConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/phonebook_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nSelect an operation:");
                System.out.println("[1] Create");
                System.out.println("[2] Read");
                System.out.println("[3] Update");
                System.out.println("[4] Delete");
                System.out.println("[5] Find");
                System.out.println("[6] Search");
                System.out.println("[0] Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Enter name:");
                        String name = scanner.nextLine();
                        System.out.println("Enter contact number:");
                        String contactNum = scanner.nextLine();
                        createRecord(name, contactNum);
                        break;
                    case 2:
                        readRecords();
                        break;
                    case 3:
                        System.out.println("Enter ID to update:");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter new name:");
                        String newName = scanner.nextLine();
                        System.out.println("Enter new contact number:");
                        String newContactNum = scanner.nextLine();
                        updateRecord(updateId, newName, newContactNum);
                        break;
                    case 4:
                        System.out.println("Enter ID to delete:");
                        int deleteId = scanner.nextInt();
                        deleteRecord(deleteId);
                        break;
                    case 5:
                        System.out.println("Enter ID to find:");
                        int findId = scanner.nextInt();
                        findRecord(findId);
                        break;
                    case 6:
                        System.out.println("Enter name keyword to search:");
                        String keyword = scanner.nextLine();
                        searchRecords(keyword);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice! Please select a valid option.");
                }
            }
        }
    }

    private static void createRecord(String name, String contactNum) {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);
            rowSet.setCommand("SELECT * FROM bookofphones");
            rowSet.execute();
            rowSet.moveToInsertRow();
            rowSet.updateString("Name", name);
            rowSet.updateString("Contact_Num", contactNum);
            rowSet.insertRow();
            System.out.println("Record created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readRecords() {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);
            rowSet.setCommand("SELECT * FROM bookofphones");
            rowSet.execute();
            while (rowSet.next()) {
                System.out.println("ID: " + rowSet.getInt("ID") + ", Name: " + rowSet.getString("Name") + ", Contact Number: " + rowSet.getString("Contact_Num"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateRecord(int id, String name, String contactNum) {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);
            rowSet.setCommand("SELECT * FROM bookofphones WHERE ID = ?");
            rowSet.setInt(1, id);
            rowSet.execute();
            if (rowSet.next()) {
                rowSet.updateString("Name", name);
                rowSet.updateString("Contact_Num", contactNum);
                rowSet.updateRow();
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("Record not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteRecord(int id) {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);
            rowSet.setCommand("SELECT * FROM bookofphones WHERE ID = ?");
            rowSet.setInt(1, id);
            rowSet.execute();
            if (rowSet.next()) {
                rowSet.deleteRow();
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("Record not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void findRecord(int id) {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);
            rowSet.setCommand("SELECT * FROM bookofphones WHERE ID = ?");
            rowSet.setInt(1, id);
            rowSet.execute();
            if (rowSet.next()) {
                System.out.println("ID: " + rowSet.getInt("ID") + ", Name: " + rowSet.getString("Name") + ", Contact Number: " + rowSet.getString("Contact_Num"));
            } else {
                System.out.println("Record not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void searchRecords(String keyword) {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);
            rowSet.setCommand("SELECT * FROM bookofphones WHERE Name LIKE ?");
            rowSet.setString(1, "%" + keyword + "%");
            rowSet.execute();
            while (rowSet.next()) {
                System.out.println("ID: " + rowSet.getInt("ID") + ", Name: " + rowSet.getString("Name") + ", Contact Number: " + rowSet.getString("Contact_Num"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
