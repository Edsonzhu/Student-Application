package sample;

/**
 * @author      Edson Zhu <efkzhu @ myseneca.ca>
 * @version     1.0
 * @since       1.0
 */

import javax.swing.text.TableView;
import java.io.Serializable;

/**
 * Student class
 */
public class Student implements Serializable{
    private String name;
    private String course;
    private int grade;
    private int id;


    /**
     * Contructor of the student class
     * @param recName name of the student
     * @param recCourse course of the student
     * @param recGrade grade of the student
     */
    public Student(String recName, String recCourse, int recGrade, int recId){
        this.name = recName;
        this.course = recCourse;
        this.grade = recGrade;
        this.id = recId;
    }

    /**
     * Get student's name
     * @return student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get student's course
     * @return student's course
     */
    public String getCourse() {
        return course;
    }

    /**
     * Get student's grade
     * @return student's grade
     */
    public String getGrade() {
        return String.valueOf(grade);
    }

    /**
     * Get student's ID
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Change course
     * @param course
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * Change grade
     * @param grade
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * Change Name
     * @param name
     */
    public void setName(String name) {
        this.name= name;
    }
}
