package CA_2;

import java.util.*;
import java.io.*;

public class CA2App {
    // List to store all employees
    private static ArrayList<Employee> employees = new ArrayList<>();
    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load names from file at the start
        loadApplicantsFromFile("Applicants_Form.txt");
        boolean running = true;
        // Main program loop
        while (running) {
            printMenu(); // Show menu options
            int choice = getUserChoice(); // Get user selection
            // Handle menu choice using enum
            switch (MenuOption.values()[choice - 1]) {
                case SORT:
                    // Sort employee list and show first 20
                    employees = mergeSort(employees);
                    displayFirst20();
                    break;
                case SEARCH:
                    // Search for an employee by name
                    searchEmployee();
                    break;
                case ADD_RECORDS:
                    // Add a new employee with user input
                    addEmployee();
                    break;
                case GENERATE_RANDOM:
                    // Add 5 random employees
                    generateRandomEmployees(5);
                    break;
                case EXIT:
                    // Exit the program
                    running = false;
                    break;
            }
        }
    }

    // Print the main menu options
    private static void printMenu() {
        System.out.println("\nPlease select an option:");
        for (int i = 0; i < MenuOption.values().length; i++) {
            System.out.println((i + 1) + ". " + MenuOption.values()[i]);
        }
    }

    // Get a valid menu choice from the user
    private static int getUserChoice() {
        int choice = 0;
        while (choice < 1 || choice > MenuOption.values().length) {
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                choice = 0; // Invalid input, ask again
            }
        }
        return choice;
    }

    // Load names from a file and create Employee objects with random manager and department
    private static void loadApplicantsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String name;
            while ((name = br.readLine()) != null) {
                Employee emp = new Employee(
                    name.trim(),
                    ManagerType.values()[new Random().nextInt(ManagerType.values().length)],
                    DepartmentType.values()[new Random().nextInt(DepartmentType.values().length)]
                );
                employees.add(emp);
            }
            System.out.println("File read successfully.");
        } catch (IOException e) {
            // If file not found, start with empty list
            System.out.println("Applicants_Form.txt not found. Starting with an empty list.");
        }
    }

    // Show the first 20 employees in the list
    private static void displayFirst20() {
        System.out.println("\nFirst 20 employees (sorted):");
        for (int i = 0; i < Math.min(20, employees.size()); i++) {
            System.out.println(employees.get(i));
        }
    }

    // Recursive merge sort for sorting employees by name
    /*
    Merge sort is the best fit for sorting the employee list because it always
    gives fast and reliable results, even as the list gets bigger. Unlike
    simpler algorithms like insertion sort, which slow down a lot as the list
    grows, merge sort keeps its speed no matter how much data I add. It also
    keeps employees with the same name in their original order, which can be
    important for the records. While quicksort is sometimes faster, it can be
    unpredictable and is not stable. Merge sort’s predictable performance and
    stability make it the safest and most consistent choice in this case.
    */
    private static ArrayList<Employee> mergeSort(ArrayList<Employee> list) {
        if (list.size() <= 1) return list; // Base case
        int mid = list.size() / 2;
        ArrayList<Employee> left = new ArrayList<>(list.subList(0, mid));
        ArrayList<Employee> right = new ArrayList<>(list.subList(mid, list.size()));
        // Recursively sort both halves and merge them
        return merge(mergeSort(left), mergeSort(right));
    }

    // Merge two sorted lists into one sorted list
    private static ArrayList<Employee> merge(ArrayList<Employee> left, ArrayList<Employee> right) {
        ArrayList<Employee> merged = new ArrayList<>();
        int i = 0, j = 0;
        // Compare names and add in order
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getName().compareToIgnoreCase(right.get(j).getName()) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        // Add any remaining items
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));
        return merged;
    }

    // Search for an employee by name using binary search
    private static void searchEmployee() {
        System.out.print("Enter employee name to search: ");
        String name = scanner.nextLine().trim();
        employees = mergeSort(employees); // Make sure list is sorted
        int idx = binarySearch(employees, name);
        if (idx != -1) {
            // Show employee details if found
            System.out.println("Employee found: " + employees.get(idx));
        } else {
            System.out.println("Employee not found.");
        }
    }

    // Binary search in the sorted employee list
    /*
    Binary search is much faster than checking each name one by one, especially
    as the list gets bigger. It quickly finds the name by repeatedly narrowing
    down the search range, making it much more efficient than linear search. 
    Since my list is already sorted, binary search is the best choice for speed
    and efficiency.
    */
    private static int binarySearch(ArrayList<Employee> list, String name) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = list.get(mid).getName().compareToIgnoreCase(name);
            if (cmp == 0) return mid; // Found
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return -1; // Not found
    }

    // Add a new employee with user input and validation
    private static void addEmployee() {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine().trim();

        // Select manager type
        ManagerType managerType = null;
        while (managerType == null) {
            System.out.println("Select Manager Type:");
            for (int i = 0; i < ManagerType.values().length; i++) {
                System.out.println((i + 1) + ". " + ManagerType.values()[i]);
            }
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                managerType = ManagerType.values()[choice - 1];
            } catch (Exception e) {
                System.out.println("Invalid choice. Try again.");
            }
        }

        // Select department type
        DepartmentType departmentType = null;
        while (departmentType == null) {
            System.out.println("Select Department:");
            for (int i = 0; i < DepartmentType.values().length; i++) {
                System.out.println((i + 1) + ". " + DepartmentType.values()[i]);
            }
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                departmentType = DepartmentType.values()[choice - 1];
            } catch (Exception e) {
                System.out.println("Invalid choice. Try again.");
            }
        }

        // Create and add the new employee
        Employee emp = new Employee(name, managerType, departmentType);
        employees.add(emp);
        System.out.println("Employee added: " + emp);
    }

    // Generate and add random employees for testing
    private static void generateRandomEmployees(int count) {
        String[] names = {"Alex", "Jamie", "Morgan", "Taylor", "Jordan", "Casey", "Riley", "Drew", "Skyler", "Avery"};
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            String name = names[rand.nextInt(names.length)] + rand.nextInt(1000);
            ManagerType manager = ManagerType.values()[rand.nextInt(ManagerType.values().length)];
            DepartmentType dept = DepartmentType.values()[rand.nextInt(DepartmentType.values().length)];
            Employee emp = new Employee(name, manager, dept);
            employees.add(emp);
        }
        System.out.println(count + " random employees generated.");
        // Show all employees after adding
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }
}
