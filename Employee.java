package CA_2;

// Class to store employee details
public class Employee {
    private String name;
    private ManagerType managerType;
    private DepartmentType departmentType;

    // Constructor to set all fields
    public Employee(String name, ManagerType managerType, DepartmentType departmentType) {
        this.name = name;
        this.managerType = managerType;
        this.departmentType = departmentType;
    }

    public String getName() { return name; }
    public ManagerType getManagerType() { return managerType; }
    public DepartmentType getDepartmentType() { return departmentType; }

    // Show employee as a string
    @Override
    public String toString() {
        return name + " | " + managerType + " | " + departmentType;
    }
}
