package com.apex.gradetracker.controller;

import com.apex.gradetracker.model.Student;
import com.apex.gradetracker.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class GradeController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        List<Student> students = studentRepository.findAll();
        Map<String, Object> stats = new HashMap<>();

        if (students.isEmpty()) {
            stats.put("average", 0.0);
            stats.put("highest", 0.0);
            stats.put("highestName", "N/A");
            stats.put("lowest", 0.0);
            stats.put("lowestName", "N/A");
            stats.put("count", 0);
            
            stats.put("mathAverage", 0.0);
            stats.put("physicsAverage", 0.0);
            stats.put("chemistryAverage", 0.0);
            stats.put("csAverage", 0.0);
            return stats;
        }

        double sum = 0;
        double mathSum = 0;
        double physicsSum = 0;
        double chemistrySum = 0;
        double csSum = 0;
        
        double highest = students.get(0).getGrade();
        double lowest = students.get(0).getGrade();
        String highestName = students.get(0).getName();
        String lowestName = students.get(0).getName();

        for (Student s : students) {
            double g = s.getGrade();
            sum += g;
            mathSum += s.getMathGrade();
            physicsSum += s.getPhysicsGrade();
            chemistrySum += s.getChemistryGrade();
            csSum += s.getCsGrade();
            
            if (g > highest) {
                highest = g;
                highestName = s.getName();
            }
            if (g < lowest) {
                lowest = g;
                lowestName = s.getName();
            }
        }

        int count = students.size();
        stats.put("average", sum / count);
        stats.put("highest", highest);
        stats.put("highestName", highestName);
        stats.put("lowest", lowest);
        stats.put("lowestName", lowestName);
        stats.put("count", count);
        
        stats.put("mathAverage", mathSum / count);
        stats.put("physicsAverage", physicsSum / count);
        stats.put("chemistryAverage", chemistrySum / count);
        stats.put("csAverage", csSum / count);

        return stats;
    }
}
