package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

class ServiceTest {

    private Validator<Student> studentValidator;
    private Validator<Homework> homeworkValidator;
    private Validator<Grade> gradeValidator;

    private StudentXMLRepository fileRepository1;
    private HomeworkXMLRepository fileRepository2;
    private GradeXMLRepository fileRepository3;

    private Service service;

    @BeforeEach
     void setUp() {
        studentValidator = new StudentValidator();
        homeworkValidator = new HomeworkValidator();
        gradeValidator = new GradeValidator();
        fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");
        service = new Service(fileRepository1, fileRepository2, fileRepository3);

    }

    @Test
    void saveStudentShouldPass() {
        // hiba: AbstractXmlRepository
        int result = service.saveStudent("S151125","Janos",121);
        Assertions.assertEquals(0, result, "Student saved correctly");
    }

    @Test
    void saveStudentShouldFail() {
        int result = service.saveStudent("","Janos",121);
        Assertions.assertEquals(0, result, "Student save failed.");
    }

    @Test
    void deleteStudentShouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    service.deleteStudent(null);
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1","-2",""," "})
    void updateStudentShouldFail(String id) {
        String nameToAll = "Bela";
        int group = 220;
        int result = service.updateStudent(id,nameToAll,group);
        Assertions.assertFalse(result == 1);
    }

    @Test
    void saveHomeWorkShouldFail() {
        int result = service.saveHomework("12","verval",15,16);
        Assertions.assertEquals(1,result);
    }

    @Test
    void deleteHomeworkShouldPass() {

        // jelen futasnal azert adhat falset true helyett, mivel mar toroltem es meg egyszer torolni nem lehet
        String id = "2";
        int result = service.deleteHomework(id);
        Assertions.assertTrue(result == 1);
    }


}