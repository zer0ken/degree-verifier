package org.konkuk.client;

class AppModelTest {
//    AppModel appModel = AppModel.getInstance();
//
//    @Test
//    void verifyStudent_throws_RuntimeException_with_student_list_not_ready() throws InterruptedException {
//        appModel.loadVerifier();
//        Thread.sleep(1000);
//        assertThrows(
//                RuntimeException.class,
//                () -> appModel.verifyStudent(appModel.getStudents().get(0)),
//                "Student list not loaded yet"
//        );
//    }
//
//    @Test
//    void verifyStudent_throws_RuntimeException_with_verifier_not_ready() throws InterruptedException {
//        appModel.loadStudentList();
//        Thread.sleep(1000);
//        assertThrows(
//                RuntimeException.class,
//                () -> appModel.verifyStudent(appModel.getStudents().get(0)),
//                "Verifier not loaded yet"
//        );
//    }
//
//    @Nested
//    class verifyStudent_tests {
//        verifyStudent_tests() throws InterruptedException {
//            appModel.loadStudentList();
//            appModel.loadVerifier();
//            Thread.sleep(2000);
//        }
//
//        @Test
//        void verify_first_student() throws InterruptedException {
//            Student student = appModel.getStudents().get(0);
//            appModel.verifyStudent(student);
//            Thread.sleep(2000);
//            assertTrue(student.isVerified());
//            assertEquals(4, student.getSufficientDegrees().size());
//        }
//
//        @Test
//        void verify_second_student() throws InterruptedException {
//            Student student = appModel.getStudents().get(1);
//            appModel.verifyStudent(student);
//            Thread.sleep(2000);
//            assertTrue(student.isVerified());
//            assertEquals(1, student.getSufficientDegrees().size());
//        }
//    }
}