import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;
import java.io.File;


public class Connect {
    
    
    /**
     * Connect or create to the database
     */
    public static void connect() {
        Connection conn = null;
        Statement stmt = null;
        // Create the variables 
        int a,b,res,movieid, result,year,rating;
        String moviename,actor,actress,director,querystring;
        char choice  ;
        char ch;
        Scanner dbChoiceObj = new Scanner(System.in);
        Scanner dbQueryObj = new Scanner(System.in);
        Scanner dbScannerObj = new Scanner(System.in);
        Scanner dbScannerInsertObj = new Scanner(System.in);
            System.out.println("Enter Database Name");
            String dbScannerName = dbScannerObj.nextLine(); 

            try { //If database Exists
                File dbFile = new File(dbScannerName);
                if(dbFile.exists() && !dbFile.isDirectory()){
                    System.out.println("Database Already Exist Connecting To Existing Database");
                }
                // db parameters
                String url = "jdbc:sqlite:./" + dbScannerName;
                // create a connection to the database
                conn = DriverManager.getConnection(url);
                stmt = conn.createStatement();
                System.out.println("Connection to SQLite has been established.");
                try {

                    String sqlTable = "CREATE TABLE MOVIES " +
                    "(id INTEGER PRIMARY KEY   NOT NULL," +
                    " MovieNAME        TEXT, " + 
                    " ACTOR           TEXT ,   " + 
                    " ACTRESS           TEXT ,   " + 

                    " DIRECTOR TEXT,"+
                    " YEAR    INTEGER, " + 
                    " RATING            INTEGER )";
 
                    do {  // Main menu println
                    System.out.println("\n\nMain Menu : \n1.Create Movies Table \n2.Insert Data Into Table\n3.Query Table\n4.Select Specific Query\n5.Exit\n");
                    System.out.print("Enter your choice : ");
                    choice = dbChoiceObj.next().charAt(0);
                    switch(choice){ //Create Table
                        case '1':
                        result = stmt.executeUpdate(sqlTable);
                             System.out.println("Movies Table Sucessfully Created");
                             break;
                        case '2': //Insert Table
                        String sqlInsert = "INSERT INTO MOVIES (id,MovieNAME,Actor,Actress,DIRECTOR,YEAR,RATING) VALUES (?,?,?,?,?,?,?)";
                        PreparedStatement pstmt = conn.prepareStatement(sqlInsert,
                              Statement.RETURN_GENERATED_KEYS);
                        System.out.print("Enter the Movie ID,Movie Name, Actor, Actress, Director, Year, Rating");  
                        System.out.println("Movie ID:");
                        movieid = dbScannerInsertObj.nextInt();
                        pstmt.setInt(1, movieid);
                        // System.out.println("Movie Name:");
                        // moviename = dbScannerInsertObj.nextLine();
                        // pstmt.setString(2, moviename);
                        actor = dbScannerInsertObj.nextLine();
                        System.out.println("Movie Name:");
                        moviename = dbScannerInsertObj.nextLine();
                        pstmt.setString(2, moviename);
                        System.out.println("Actor Name:");
                        actor = dbScannerInsertObj.nextLine();
                        pstmt.setString(3, actor);
                        System.out.println("Actress Name:");
                        actress = dbScannerInsertObj.nextLine();
                        pstmt.setString(4, actress);
                        System.out.println("Director Name:");
                        director = dbScannerInsertObj.nextLine();
                        pstmt.setString(5, director);
                        System.out.println("Year");
                        year = dbScannerInsertObj.nextInt();
                        pstmt.setInt(6, year);
                        System.out.println("Rating");
                        rating = dbScannerInsertObj.nextInt();
                        pstmt.setInt(7,rating);
                        System.out.println("\n");
                        System.out.println("Inserting into DB");
                        
                        result = pstmt.executeUpdate();
                        System.out.println("Sucessfully Inserted Data in Movies Database");
                        break;
                        case '3': //Query Table
                        String sqlquery = "SELECT id,movieNAME,Actor,Actress,DIRECTOR,YEAR,YEAR,RATING FROM MOVIES";
                        ResultSet resultquery = stmt.executeQuery(sqlquery);
                        while(resultquery.next()){
                            System.out.println(resultquery.getInt("id") + "\t"+ resultquery.getString("movieNAME") + "\t"+
                        resultquery.getString("Actor") + "\t" + resultquery.getString("Actress") + "\t" + resultquery.getString("DIRECTOR") + "\t" +
                        resultquery.getInt("YEAR") + "\t" + resultquery.getInt("RATING") + "\n"
                        );
                        }
                        
                        break;
                        case '4': //Selection Query
                       
                        System.out.println("Please Insert a Column to Query");
                        querystring = dbQueryObj.nextLine();
                        String sqlqueryone = "SELECT "+querystring+" FROM MOVIES";
                        ResultSet resultqueryone = stmt.executeQuery(sqlqueryone);
                        while(resultqueryone.next()){
                            {
                                if(resultqueryone.getString(querystring) != null){
                                    System.out.println(resultqueryone.getString(querystring));
                                }else{
                                    System.out.println(resultqueryone.getInt(querystring));
                                };
                            };
                        }
                        break;
                        case '5':
                        break;
                        default:
                        System.out.println("Enter your choice!");
                        break;
                    }
                   
                }  while( choice != 5);
            }
        catch (Exception e) {
                    System.out.println("The Program experienced an error :"+ e);
                }
            
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }     //Query Table & Create Table Logic
   
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();
    }

}