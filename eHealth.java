import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthdb","root","ritik");
            Statement statement = conn.createStatement();

            information(); //Telling the user about the health care system.

            //Getting required information from the user.
            System.out.println("Enter your name:-");
            Scanner n = new Scanner(System.in);
            String name = n.nextLine();

            System.out.println("Enter your age:-");
            Scanner a = new Scanner(System.in);
            Integer age = a.nextInt();

            System.out.println("Enter your symptoms, if you have more than one symptom" +
                    " then please enter it in seperation with commas:-");
            Scanner s = new Scanner(System.in);
            String symp = s.nextLine();
            String symptom[] = symp.split(",");


                ResultSet res = statement.executeQuery(
                        "select * " +
                        "from cure");

                boolean matched = false;
                String dis = "";
                String med = "";

                while (res.next()){
                    if(matched) {
                        break;
                    }

                    String retrieved_symp = res.getString("symptoms");
                    String retrieved_symp_arr[] = retrieved_symp.split(",");

                    for(int j = 0; j < symptom.length; j++) {
                        if(matched)
                            break;
                        for (int k = 0; k < retrieved_symp_arr.length; k++) {
                            if (symptom[j].toLowerCase().equals(retrieved_symp_arr[k].toLowerCase())) {
                                matched = true;
                                dis = res.getString("disease");
                                med = res.getString("medicine");
                                System.out.println(name + ", you are suffering from "+dis + ".");
                                System.out.println("Your cure could be " + med + ".");
                                break;
                            }
                        }
                    }
                }

            System.out.println("Please Enter Y if you are completely satisfied by our suggestion");
            Scanner sat = new Scanner(System.in);
            String satisfied = sat.next();
            if(satisfied.toUpperCase().equals("Y")){
                //Update information to the database only if the user is satisfied.
                statement.execute("insert into user values('" + name + "'," + age + ",'" + dis + "','" + med + "');"
                );
            }

//                res.close();
//                statement.close();
//                conn.close();

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Connection Failure!");
        }finally {
            System.out.println("We pray for your well being!");
        }
    }
    private static void information(){
        System.out.println("Welcome To e-Health Care System!");
        System.out.println("Unless and until you are satisfied by our suggestions, we won't update " +
                "your information to our database.");
        System.out.println("Have a smooth experience.");
        System.out.println("Please register yourself.");
    }
}
