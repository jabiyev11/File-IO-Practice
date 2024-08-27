package src;

import src.enums.Department;
import src.enums.Position;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskSolutions {

    private static final String EMPLOYEE_DATA = "src/data/employees.txt";

    public static List<Employee> readEmployeeData() {

        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_DATA))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                Integer id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String surname = parts[2];
                Integer age = Integer.parseInt(parts[3]);
                Double salary = Double.parseDouble(parts[4]);
                Department department = Department.valueOf(parts[5]);
                Boolean isEmployer = Boolean.parseBoolean(parts[6]);
                LocalDate startDate = LocalDate.parse(parts[7]);
                String email = parts[8];
                String phoneNumber = parts[9];
                Position position = Position.valueOf(parts[10]);
                String address = parts[11];

                Employee employee = new Employee(id, name, surname, age, salary, department, isEmployer, startDate, email, phoneNumber, position, address);

                employees.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred during the process of reading file");
        }

//        employees.forEach(System.out::println);

        //Uncomment above code when you run only 1st method for reading

        return employees;
    }

    public static List<Employee> filterSortModify() {

        List<Employee> employees = readEmployeeData();

        List<Employee> filteredList = employees
                .stream()
                .filter(employee -> Position.INTERN.equals(employee.getPosition()))
                .toList();

        List<Employee> sortedList = employees
                .stream()
                .sorted(Comparator.comparing(Employee::getStartDate))
                .toList();

        List<Employee> modifiedList = employees
                .stream()
                .filter(employee -> Department.IT.equals(employee.getDepartment()))
                .filter(employee -> LocalDate.now().minusYears(5).isAfter(employee.getStartDate()))
                .map(employee -> {
                    employee.setSalary(employee.getSalary() * 1.10);
                    return employee;
                })
                .toList();

//        filteredList.forEach(System.out::println);
//        sortedList.forEach(System.out::println);
//        modifiedList.forEach(System.out::println);

        //uncomment above lines of code when you run this method only in main

        return modifiedList;
    }

    public static void writeProcessedData() {

        List<Employee> processedEmployees = filterSortModify();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/processed_employees.txt"))) {

            for (Employee employee : processedEmployees) {
                writer.write(employee.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Problem during the process of writing to a file");
        }
    }

    public static void generateReportsByDepartment() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/department_summary.txt"))) {
            List<Employee> employees = readEmployeeData();

            Map<Department, List<Employee>> departmentToEmployees = employees
                    .stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment));

            departmentToEmployees.forEach((department, employeeList) -> {
                try {
                    writer.write("Department: " + department);

                    Double totalSalaryPerDepartment = employeeList
                            .stream()
                            .mapToDouble(Employee::getSalary)
                            .sum();

                    writer.write("\nTotal Salary: $" + totalSalaryPerDepartment);

                    Double averageSalaryPerDepartment = employeeList
                            .stream()
                            .collect(Collectors.averagingDouble(Employee::getSalary));

                    writer.write("\nAverage Salary: $" + averageSalaryPerDepartment);

                    Long employeeCountPerDepartment = employeeList
                            .stream()
                            .count();

                    writer.write("\nEmployee Count: " + employeeCountPerDepartment);
                    writer.write("\n-------------------------------------------------\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("Error during the process of writing to a file");
        }
    }

    public static void employeesInLast10Years() {

        List<Employee> employees = readEmployeeData();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/new_employees.txt"))) {

            List<Employee> newEmployeesList = employees
                    .stream()
                    .filter(employee -> LocalDate.now().minusYears(10).isBefore(employee.getStartDate()))
                    .toList();

            writer.write("New Employees\n");
            writer.write("--------------------------------\n");

            for (Employee employee : newEmployeesList) {
                employeeDetails(employee, writer);
            }

        } catch (IOException e) {
            System.out.println("Error during the process of writing to a file");
        }
    }

    //There were not any duplicate emails, so I adjusted original data to see difference
    public static void removeDuplicates() {

        List<Employee> employees = readEmployeeData();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/unique_employees.txt"))) {

            Set<String> uniqueEmails = new HashSet<>();

            List<Employee> uniqueEmployees = employees
                    .stream()
                    .filter(employee -> uniqueEmails.add(employee.getEmail()))
                    .toList();

            for (Employee employee : uniqueEmployees) {
                employeeDetails(employee, writer);
            }
        } catch (IOException e) {
            System.out.println("Error while writing to a file");
        }

    }

    //Helper method
    private static void employeeDetails(Employee employee, BufferedWriter writer) throws IOException {
        writer.write("ID: " + employee.getId() + "\n");
        writer.write("Name: " + employee.getName() + "\n");
        writer.write("Department: " + employee.getDepartment() + "\n");
        writer.write("Start Date: " + employee.getStartDate() + "\n");
        writer.write("Salary: $" + employee.getSalary() + "\n");
        writer.write("--------------------------------\n");
    }

    public static void averageSalaryPerDepartment() {
        List<Employee> employees = readEmployeeData();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/average_salary_by_department.txt"))) {

            Map<Department, List<Employee>> departmentToEmployees = employees
                    .stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment));

            departmentToEmployees.forEach((department, employeeList) -> {
                try {
                    writer.write("----------------------------");
                    writer.write("\nDepartment: " + department);

                    Double averageSalaryPerDepartment = employeeList
                            .stream()
                            .collect(Collectors.averagingDouble(Employee::getSalary));

                    writer.write("\nAverage Salary: " + averageSalaryPerDepartment);
                    writer.newLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("Error while writing to a file");
        }
    }

    public static void longestServingEmployee() {

        List<Employee> employees = readEmployeeData();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/longest_serving_employee.txt"))) {

            Optional<Employee> longestServingEmployee = employees
                    .stream()
                    .max(Comparator.comparing(Employee::getStartDate).reversed());

            Employee employee = longestServingEmployee.orElse(new Employee("No Employee"));

            employeeDetails(employee, writer);


        } catch (IOException e) {
            System.out.println("Error while writing to a file");
        }
    }

    public static void countOfEmployeesPerPosition() {

        List<Employee> employees = readEmployeeData();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/employees_by_position.txt"))) {

            Map<Position, List<Employee>> positionToEmployees = employees
                    .stream()
                    .collect(Collectors.groupingBy(Employee::getPosition));

            positionToEmployees.forEach(((position, employeeList) -> {

                try {
                    writer.write("\n-------------------------\n");
                    writer.write("Position: " + position);

                    Long countOfEmployeesPerPosition = employeeList
                            .stream()
                            .count();

                    writer.write("\nEmployee Count: " + countOfEmployeesPerPosition);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));


        } catch (IOException e) {
            System.out.println("Error while writing to a file");
        }
    }

    public static void groupByDepartmentAndPosition() {

        List<Employee> employees = readEmployeeData();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/employees_by_department_and_position.txt"))) {

            Map<Department, List<Employee>> departmentToEmployees = employees
                    .stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment));

            departmentToEmployees.forEach((department, employeeList) -> {

                try {
                    writer.write("                          ");
                    writer.write("Department: " + department);

                    Map<Position, List<Employee>> positionToEmployeesPerDepartment = employeeList
                            .stream()
                            .collect(Collectors.groupingBy(Employee::getPosition));

                    positionToEmployeesPerDepartment.forEach((position, employeeListForPosition) -> {

                        try {
                            writer.newLine();
                            writer.write("\nPosition: " + position);
                            writer.newLine();
                            for (Employee employee : employeeListForPosition) {
                                employeeDetails(employee, writer);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("Error while writing to a file");
        }
    }


    public static void main(String[] args) {
        readEmployeeData();
        filterSortModify();
        writeProcessedData();
        generateReportsByDepartment();
        employeesInLast10Years();
        removeDuplicates();
        averageSalaryPerDepartment();
        longestServingEmployee();
        countOfEmployeesPerPosition();
        groupByDepartmentAndPosition();

    }
}
