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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;


class ServiceMockTest {

    @Mock
    private StudentXMLRepository fileRepository1Mock;
    @Mock
    private HomeworkXMLRepository fileRepository2Mock;
    @Mock
    private GradeXMLRepository fileRepository3Mock;

    @InjectMocks
    private Service service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        fileRepository1Mock = mock(StudentXMLRepository.class);
        fileRepository2Mock = mock(HomeworkXMLRepository.class);
        fileRepository3Mock = mock(GradeXMLRepository.class);
        service = new Service(fileRepository1Mock, fileRepository2Mock, fileRepository3Mock);

    }

    @Test
    void saveStudentShouldPass() {
        // hiba: AbstractXmlRepository
        Student st = new Student("S123","Bela",111);
        Mockito.doReturn(st).when(fileRepository1Mock).save(st);
        int res = service.saveStudent("S123","Bela",111);
        Mockito.verify(fileRepository1Mock).save(st);
        Assertions.assertEquals(0,res, "Student saved correctly");

    }


    @Test
    void saveHomeWorkShouldFail() {
        Homework hw = new Homework("12","verval",15,16);
        Mockito.when(fileRepository2Mock.save(any(Homework.class))).thenReturn(hw);
        int res = service.saveHomework("12","verval",15,16);
        Mockito.verify(fileRepository2Mock).save(hw);
        Assertions.assertEquals(0,res);
    }

    @Test
    void deleteHomeworkShouldPass() {

//        String id = "3";
//        Homework hw = fileRepository2Mock.findOne(id);
        Homework hwDummy = new Homework("dummyid","dummydesc",10,5);
        Mockito.when(fileRepository2Mock.delete(anyString())).thenReturn(hwDummy);
        int result = service.deleteHomework("asd");
        Mockito.verify(fileRepository2Mock).delete("asd");
        Assertions.assertTrue(result == 1);
    }


}