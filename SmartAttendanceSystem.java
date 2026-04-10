import java.io.*;
import java.util.*;

class Student {
    int id;
    String name;
    int attendance;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.attendance = 0;
    }
}

public class SmartAttendanceSystem {
    static List<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();

        while (true) {
            System.out.println("\n--- Smart Attendance System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Mark Attendance");
            System.out.println("3. View Attendance");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    markAttendance();
                    break;
                case 3:
                    viewAttendance();
                    break;
                case 4:
                    saveData();
                    System.out.println("Data Saved. Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.id == id) {
                System.out.println("Student with this ID already exists!");
                return;
            }
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        students.add(new Student(id, name));
        System.out.println("Student added successfully!");
    }

    static void markAttendance() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.id == id) {
                s.attendance++;
                System.out.println("Attendance marked for " + s.name);
                return;
            }
        }
        System.out.println("Student not found!");
    }

    static void viewAttendance() {
        System.out.println("\n--- Attendance Report ---");

        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }

        for (Student s : students) {
            System.out.println("ID: " + s.id +
                    ", Name: " + s.name +
                    ", Attendance: " + s.attendance);
        }
    }

    static void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("attendance.txt"))) {
            for (Student s : students) {
                bw.write(s.id + "," + s.name + "," + s.attendance);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }

    static void loadData() {
        File file = new File("attendance.txt");
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Student s = new Student(Integer.parseInt(data[0]), data[1]);
                    s.attendance = Integer.parseInt(data[2]);
                    students.add(s);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data!");
        }
    }
}