import java.util.*;

class Course {
    private String courseCode;
    private String description;
    private String title;
    private String schedule;
    private int capacity;
    private int enrolled;
    private List<Student> enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.title = title;
        this.capacity = capacity;
        this.courseCode = courseCode;
        this.description = description;
        this.enrolled = 0;
        this.schedule = schedule;
        this.enrolledStudents = new ArrayList<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSlots() {
        return capacity - enrolled;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public boolean registerStudent(Student student) {
        if (enrolled < capacity) {
            enrolled++;
            enrolledStudents.add(student);
            return true;
        }
        return false;
    }

    public boolean dropStudent(Student student) {
        if (enrolled > 0 && enrolledStudents.remove(student)) {
            enrolled--;
            return true;
        }
        return false;
    }

    public List<Student> getEnrolledStudents(){
        return enrolledStudents;
    }
}

class Student {
    private String name;
    private String studentID;
    private List<Course> registeredCourses;

    public Student(String studentID, String nameString) {
        this.name = nameString;
        this.studentID = studentID;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return name;
    }

    public List<Course> getRegisteredCourse() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (!registeredCourses.contains(course) && course.registerStudent(this)) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent(this);
            return true;
        }
        return false;
    }
}

class DataBase {
    private Map<String, Student> students;
    private Map<String, Course> courses;

    public DataBase() {
        this.courses = new HashMap<>();
        this.students = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCourseCode(), course);
    }

    public void addStudent(Student student) {
        students.put(student.getStudentID(), student);
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    public Student getStudent(String studentId) {
        return students.get(studentId);
    }

    public Map<String, Course> getAvailableCourses() {
        return courses;
    }

    public Map<String, Student> getAvailableStudents(){
        return students;
    }
}

public class Student_Course_Registration_System {
    private DataBase db;
    private Scanner sc;

    public Student_Course_Registration_System() {
        this.db = new DataBase();
        this.sc = new Scanner(System.in);
    }

    public void initializeCourses() {
        db.addCourse(new Course("20CS101", "Intro to Computer Science", "Basics of CS", 30, "MWF 10-11am"));
        db.addCourse(new Course("20CS102", "OOP in Java", "Object oriented programming", 25, "TTh 9-10:30am"));
        db.addCourse(new Course("20CS103", "Data Structures", "Study of data organization", 40, "MWF 2-3pm"));
        db.addCourse(new Course("20CS104", "Algorithms", "Introduction to algorithms", 35, "MWF 11-12pm"));
        db.addCourse(new Course("20CS105", "Operating Systems", "Basics of OS concepts", 30, "TTh 1-2:30pm"));
        db.addCourse(new Course("20CS106", "Database Systems", "Introduction to databases", 25, "MWF 9-10am"));
        db.addCourse(new Course("20CS107", "Computer Networks", "Fundamentals of networking", 20, "TTh 3-4:30pm"));

    }

    public void initializeStudents() {
        db.addStudent(new Student("S001", "Aarav"));
        db.addStudent(new Student("S002", "Aditi"));
        db.addStudent(new Student("S003", "Arjun"));
        db.addStudent(new Student("S004", "Ishita"));
        db.addStudent(new Student("S005", "Kiran"));
        db.addStudent(new Student("S006", "Rohan"));
        db.addStudent(new Student("S007", "Sanya"));
        db.addStudent(new Student("S008", "Priya"));
        db.addStudent(new Student("S009", "Vikram"));
        db.addStudent(new Student("S010", "Ananya"));

    }

    public void showCourseList() {
        System.out.println("\nAvailable Courses:");
        for (Course course : db.getAvailableCourses().values()) {
            System.out.println(course.getCourseCode() + ": " + course.getTitle() + " - " + course.getAvailableSlots() + " slots available.");
        }
    }

    public void showStudentList() {
        System.out.println("\nAvailable Students:");
        for (Student student : db.getAvailableStudents().values()) {
            System.out.println(student.getStudentID() + ": " + student.getStudentName());
        }
    }


    public void registerStudent() {
        System.out.print("Enter Student ID: ");
        String studentId = sc.next();
        Student student = db.getStudent(studentId);

        if (student != null) {
            showCourseList();
            System.out.print("Enter Course Code to register: ");
            String courseCode = sc.next();
            Course course = db.getCourse(courseCode);

            if (course != null && student.registerCourse(course)) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Course full or already registered.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    public void dropStudentCourse() {
        System.out.print("Enter Student ID: ");
        String studentId = sc.next();
        Student student = db.getStudent(studentId);

        if (student != null) {
            System.out.println("Registered Courses:");
            for (Course course : student.getRegisteredCourse()) {
                System.out.println(course.getCourseCode() + ": " + course.getTitle());
            }
            System.out.print("Enter Course Code to drop: ");
            String courseCode = sc.next();
            Course course = db.getCourse(courseCode);

            if (course != null && student.dropCourse(course)) {
                System.out.println("Course dropped successfully.");
            } else {
                System.out.println("Course not found or not registered.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    public void showCourseRegistrations() {
        System.out.println("\nCourses and Registered Students:");
        for (Course course : db.getAvailableCourses().values()) {
            System.out.println("\nCourse: " + course.getTitle() + " (" + course.getCourseCode() + ")");
            System.out.println("Registered Students:");
            if (course.getEnrolledStudents().isEmpty()) {
                System.out.println("  No students registered.");
            } else {
                for (Student student : course.getEnrolledStudents()) {
                    System.out.println("  - " + student.getStudentID() + ": " + student.getStudentName());
                }
            }
        }
    }
    

    public void run() {
        initializeCourses();
        initializeStudents();

        int choice;
        do {
            System.out.println("\nStudent Course Registration System");
            System.out.println("1. View Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View all Students");
            System.out.println("5. View all Courses and Registered Students");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> showCourseList();
                case 2 -> registerStudent();
                case 3 -> dropStudentCourse();
                case 4 -> showStudentList();
                case 5 -> showCourseRegistrations();
                case 6 -> System.out.println("Thank You for Consulting!!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    public static void main(String[] args) {
        new Student_Course_Registration_System().run();
    }
}
