package src;

import src.enums.Department;
import src.enums.Position;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {

    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private Double salary;
    private Department department;
    private Boolean isEmployer;
    private LocalDate startDate;
    private String email;
    private String phoneNumber;
    private Position position;
    private String address;


    public Employee(Integer id, String name, String surname, Integer age, Double salary, Department department, boolean isEmployer, LocalDate startDate, String email, String phoneNumber, Position position, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.isEmployer = isEmployer;
        this.startDate = startDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.address = address;
    }

    //For null check result
    public Employee(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Boolean isEmployer() {
        return isEmployer;
    }

    public void setEmployer(Boolean employer) {
        isEmployer = employer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(surname, employee.surname) && Objects.equals(age, employee.age) && Objects.equals(salary, employee.salary) && department == employee.department && Objects.equals(isEmployer, employee.isEmployer) && Objects.equals(startDate, employee.startDate) && Objects.equals(email, employee.email) && Objects.equals(phoneNumber, employee.phoneNumber) && position == employee.position && Objects.equals(address, employee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age, salary, department, isEmployer, startDate, email, phoneNumber, position, address);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", department=" + department +
                ", isEmployer=" + isEmployer +
                ", startDate=" + startDate +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", position=" + position +
                ", address='" + address + '\'' +
                '}';
    }
}
