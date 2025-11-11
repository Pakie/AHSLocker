package com.pakie.ahslocker.parent;

import com.pakie.ahslocker.student.Student;
import com.pakie.ahslocker.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="parents")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    @NotNull
    private Long parentIdNumber;
    private String parentTitle;
    private String parentName;
    private String parentSurname;
    private String parentEmailAddress;
    private String parentAddress;
    private String parentPhoneNumber;
    @OneToMany(mappedBy = "parent")
    List<Student> students;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public  Parent() {
    }

    public Parent(Long parentIdNumber, String parentTitle, String parentName, String parentSurname, String parentEmailAddress, String parentAddress, String parentPhoneNumber, List<Student> students) {
        this.parentIdNumber = parentIdNumber;
        this.parentTitle = parentTitle;
        this.parentName = parentName;
        this.parentSurname = parentSurname;
        this.parentEmailAddress = parentEmailAddress;
        this.parentAddress = parentAddress;
        this.parentPhoneNumber = parentPhoneNumber;
        this.students = students;
    }

    public Long getParentIdNumber() {
        return parentIdNumber;
    }

    public void setParentIdNumber(Long parentIdNumber) {
        this.parentIdNumber = parentIdNumber;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentSurname() {
        return parentSurname;
    }

    public void setParentSurname(String parentSurname) {
        this.parentSurname = parentSurname;
    }

    public String getParentEmailAddress() {
        return parentEmailAddress;
    }

    public void setParentEmailAddress(String parentEmailAddress) {
        this.parentEmailAddress = parentEmailAddress;
    }

    public String getParentAddress() {
        return parentAddress;
    }

    public void setParentAddress(String parentAddress) {
        this.parentAddress = parentAddress;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public void setParentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", parentIdNumber=" + parentIdNumber +
                ", parentTitle='" + parentTitle + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentSurname='" + parentSurname + '\'' +
                ", parentEmailAddress='" + parentEmailAddress + '\'' +
                ", parentAddress='" + parentAddress + '\'' +
                ", parentPhoneNumber='" + parentPhoneNumber + '\'' +
                ", students=" + students +
                '}';
    }
}
