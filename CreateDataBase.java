package sample;
import java.sql.*;


public class CreateDataBase {

    private Connection conn;
    private String CONNECTION_STRING="jdbc:sqlite:C:\\Users\\samin\\IdeaProjects\\SQL_DATBASEFX\\src\\datbase.db";
    private Statement statement;
    private PreparedStatement stm1;
    private PreparedStatement stm2;
    private PreparedStatement stm3;


    public boolean open() throws SQLException {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            System.out.println("SOMETHING WENT WRONG TRYING TO OPEN DATABASE: " + e.getMessage());
            return false;
        }
    }


    public void CreateTable(){
        try{
            statement=conn.createStatement();;
            statement.execute("CREATE TABLE IF NOT EXISTS Students (studentID integer PRIMARY KEY, firstName text, lastName text, sex text)");
            statement.execute("CREATE TABLE IF NOT EXISTS Courses (courseID integer PRIMARY KEY, courseTitle text, department text)");
            statement.execute("CREATE TABLE IF NOT EXISTS Classes (classCode integer NOT NULL, courseID integer, studentID integer NOT NULL," +
                    "             year text, semester text, GPA text, PRIMARY KEY(classCode, studentID))");

        }catch (SQLException e){
            System.out.println("FAILED CREATING THE TABLE " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void InsertTableStudents(int studentID, String firstName, String lastName, String sex){
        try{
            stm1=conn.prepareStatement("INSERT INTO Students VALUES(?, ?, ?, ?)");
            stm1.setInt(1, studentID);
            stm1.setString(2, firstName);
            stm1.setString(3, lastName);
            stm1.setString(4, sex);
            stm1.executeUpdate();
        }catch(SQLException e){
            System.out.println("PROBLEM INSERTING INTO STUDENTS TABLE "+ e.getMessage());
            e.printStackTrace();
        }
    }




    public void InsertTableCourses(int courseID, String courseTitle, String department){
        try{
            stm2=conn.prepareStatement("INSERT INTO Courses VALUES(?, ?, ?)");
            stm2.setInt(1, courseID);
            stm2.setString(2, courseTitle);
            stm2.setString(3, department);
            stm2.executeUpdate();

        }catch(SQLException e){
            System.out.println("FAILED INSERTING INTO COURSES TABLE " + e.getMessage());
            e.printStackTrace();
        }
    }




    public void InsetTableClasses(int classCode, int courseID, int studentID, int year, String semester, String GPA){
        try{
            stm3=conn.prepareStatement("INSERT INTO Classes VALUES(?,?,?,?,?,?)");
            stm3.setInt(1, classCode);
            stm3.setInt(2, courseID);
            stm3.setInt(3, studentID);
            stm3.setInt(4, year);
            stm3.setString(5, semester);
            stm3.setString(6, GPA);
            stm3.executeUpdate();

        }catch(SQLException e){
            System.out.println("FAILED INSERTING CLASSES TABLE" + e.getMessage());
            e.printStackTrace();
        }
    }



    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("FAILED TO CLOSE THE DATABASE "  + e.getMessage());
            e.printStackTrace();
        }

    }
}
