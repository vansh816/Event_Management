///---->Yeh Example without controller service repository h
//@Data
//public class Student {
//    private int id;
//    private String name;
//    private int marks;
//}
//--------->
//@RestController
//public class StudentController {
//
//    private List<Student> students = new ArrayList<>(Arrays.asList(
//            new Student(1,90,"vansh"),
//            new Student(2,70,"rahul")
//    ));
//    @GetMapping("/students")
//    public List<Student> getstudents(){
//        return students;
//    }
//    @PostMapping("/students")
//    public Student createstudent(@RequestBody Student student){
//        students.add(student);
//        return student;
//    }
//    @GetMapping
//    public CsrfToken getcsrftoken(HttpServletRequest request){
//        return (CsrfToken) request.getAttribute("_csrf");
//    }
//    @DeleteMapping("/students/{myid}")
//    public boolean delete(@PathVariable int myid){
//        students.remove(myid);
//        return true;
//    }
//}
