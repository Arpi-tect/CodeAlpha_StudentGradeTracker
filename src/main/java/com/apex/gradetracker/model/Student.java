package com.apex.gradetracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double mathGrade;
    private double physicsGrade;
    private double chemistryGrade;
    private double csGrade;

    public Student() {}

    public Student(String name, double mathGrade, double physicsGrade, double chemistryGrade, double csGrade) {
        this.name = name;
        this.mathGrade = mathGrade;
        this.physicsGrade = physicsGrade;
        this.chemistryGrade = chemistryGrade;
        this.csGrade = csGrade;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getMathGrade() { return mathGrade; }
    public void setMathGrade(double mathGrade) { this.mathGrade = mathGrade; }
    
    public double getPhysicsGrade() { return physicsGrade; }
    public void setPhysicsGrade(double physicsGrade) { this.physicsGrade = physicsGrade; }
    
    public double getChemistryGrade() { return chemistryGrade; }
    public void setChemistryGrade(double chemistryGrade) { this.chemistryGrade = chemistryGrade; }
    
    public double getCsGrade() { return csGrade; }
    public void setCsGrade(double csGrade) { this.csGrade = csGrade; }

    // Calculates and returns the average grade
    public double getGrade() {
        return (mathGrade + physicsGrade + chemistryGrade + csGrade) / 4.0;
    }
}
