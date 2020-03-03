package sample;

import javafx.scene.canvas.GraphicsContext;
import java.sql.*;
import java.util.HashMap;

public class QueryDataBase {

    public HashMap<String, Integer> counter= new HashMap<String, Integer>() ;
    private String[] array = {"A", "B", "C", "D", "F"};
    private int[] occurenceGpa = {0,0,0,0,0};
    private Connection con;
    private String CONNECTION_STRING="jdbc:sqlite:C:\\Users\\samin\\IdeaProjects\\SQL_DATBASEFX\\src\\datbase.db";
    private PreparedStatement pstm;
    private PreparedStatement pstm1;
    private PreparedStatement pstm2;




    public void queryStudentsTable() {
        try {
            con = DriverManager.getConnection(CONNECTION_STRING);
            pstm = con.prepareStatement("SELECT studentID, firstName, lastName, sex from Students");
            ResultSet rsl = pstm.executeQuery();
            while (rsl.next()) {
                System.out.print(rsl.getInt(1) + "\t\t");
                System.out.print(rsl.getString(2) + "\t\t");
                System.out.print(rsl.getString(3) + "\t\t");
                System.out.println(rsl.getString(4) + "\t\t");
            }
            pstm.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void queryCourseTable() {
        try {
            con = DriverManager.getConnection(CONNECTION_STRING);
            pstm = con.prepareStatement("SELECT courseID, courseTitle, department from Courses");
            ResultSet rsl1 = pstm.executeQuery();
            while (rsl1.next()) {
                System.out.print(rsl1.getInt(1) + "\t");
                System.out.print(rsl1.getString(2) + "\t");
                System.out.println(rsl1.getString(3) + "\t");
            }
            pstm.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void queryClassesTable() {
        try {
            con = DriverManager.getConnection(CONNECTION_STRING);
            pstm = con.prepareStatement("SELECT classCode, courseID, studentID, year, semester, GPA FROM Classes");
            ResultSet rsl2 = pstm.executeQuery();
            while (rsl2.next()) {
                System.out.print(rsl2.getInt(1) + "\t");
                System.out.print(rsl2.getInt(2) + "\t");
                System.out.print(rsl2.getInt(3) + "\t");
                System.out.print(rsl2.getInt(4) + "\t");
                System.out.print(rsl2.getString(5) + "\t");
                System.out.print(rsl2.getString(6) + "\t");
            }

            pstm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void QueryStudentTable(int courseNumber){
        try{
            con=DriverManager.getConnection(CONNECTION_STRING);
            pstm=con.prepareStatement("SELECT studentID, firstName, lastName, sex FROM Student");
            ResultSet rsl3 = pstm.executeQuery();
            while (rsl3.next()) {
                System.out.print(rsl3.getInt(1) + "\t");
                System.out.print(rsl3.getString(2) + "\t");
                System.out.print(rsl3.getString(3) + "\t");
                System.out.println(rsl3.getString((4)));
            }

        }catch(SQLException e){
            System.out.println("PROBLEM QUERYING STUDENT YOU WANTED TO QUERY" + e.getMessage());
            e.printStackTrace();
        }
    }



    public void count(int CourseNumber, String year) {
        year=year+"%";
        String courseNumber=String.valueOf(CourseNumber);
        try {
            con = DriverManager.getConnection(CONNECTION_STRING);
           pstm = con.prepareStatement("SELECT gpa, count(*) FROM Classes WHERE courseID=" +courseNumber +" AND year LIKE '"+ year  + "' GROUP BY gpa");
           pstm1=con.prepareStatement("SELECT firstName, lastName, gpa FROM Classes INNER JOIN Students "+
                           "ON Students.studentID=Classes.studentID WHERE courseID= "+ courseNumber +" AND year LIKE '"+ year  + "'");


            ResultSet rsl4 = pstm.executeQuery();
            ResultSet rsl5= pstm1.executeQuery();
            int i=0;
            while(rsl4.next()){
                array[i]=rsl4.getString("gpa");
                occurenceGpa[i]=rsl4.getInt("COUNT(*)");
               i++;
            }

            while(rsl5.next()){
                System.out.println(rsl5.getString("firstName")+"\t" + rsl5.getString("lastName")+"\t"+ rsl5.getString("gpa"));
            }

            sort();


            for (int j = 0; j <= 4; j++) {
                counter.put(array[j], occurenceGpa[j]);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


    public void draw_chart(GraphicsContext gc){
        int input=0;
       for(int m=0; m<=4; m++){
           if(occurenceGpa[m]>0){
               input++;
           }
       }
       PieChart pie= new PieChart(counter, input);
       pie.draw(gc);
    }

    void sort() //Sorts from highest probability to lowest
    {
        for ( int i= 0 ;i<=4 ;i++)
        {
            for ( int j=i+ 1 ;j<=4 ;j++)
            {
                if ( occurenceGpa [j]> occurenceGpa[i])
                {
                    int temp= occurenceGpa[j];
                    occurenceGpa[j]= occurenceGpa[i];
                    occurenceGpa[i]=temp;
                    String temp2= array[j];
                    array[j]= array[i];
                    array[i]=temp2;
                }
            }
        }
    }


    public void Delete_element_Classes(int courseID, int ID){
        try{
            con = DriverManager.getConnection(CONNECTION_STRING);
            pstm2 = con.prepareStatement("DELETE FROM Classes WHERE courseID= ? AND studentID= ?");
            pstm2.setInt(1,courseID);
            pstm2.setInt(2, ID);
            int del= pstm2.executeUpdate();
            System.out.println("Number of deleted records: " + del);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void Delete_element_Courses(int courseID){
        try{
            con = DriverManager.getConnection(CONNECTION_STRING);
            pstm2 = con.prepareStatement("DELETE FROM Courses WHERE courseID= ?");
            pstm2.setInt(1,courseID);
            int del= pstm2.executeUpdate();
            System.out.println("Number of deleted records: " + del);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void Delete_element_Stduents(int courseID){
        try{
            con = DriverManager.getConnection(CONNECTION_STRING);
            pstm2 = con.prepareStatement("DELETE FROM Students WHERE courseID= ?");
            pstm2.setInt(1,courseID);
            int del= pstm2.executeUpdate();
            System.out.println("Number of deleted records: " + del);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

