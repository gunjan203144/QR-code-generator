/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qr_code_generator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.DriverManager;
import java.util.Scanner;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import java.sql.*;

/**
 *
 * @author gunjan
 */
class Details
{
    String name;
    String profession;
    String email;
    String contact;
    String fileName;
}
public class QR_Code_Generator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Scanner scan= new Scanner(System.in);
        System.out.println("-------------------------QR Code Generator---------------------");
        Details info=new Details();
        System.out.println("Enter Your details for which QR code should be generated");
        System.out.println("Enter Name:-");
        info.name=scan.nextLine();
        System.out.println("Enter Profession details:-");
        info.profession=scan.nextLine();
        while(true)
        {
            System.out.println("Enter E-mail id:-");
            info.email=scan.nextLine();
            if(!info.email.contains("@") || !info.email.contains("."))
                System.out.println("Invalid Email!");
            else
                break;
        }
        while(true)
        {
            System.out.println("Enter Contact Number:-");
            info.contact=scan.nextLine();
            if(info.contact.length()!=10)
                System.out.println("Invalid contact number!");
            else
                break;
        }
        String details="Name: "+info.name+", Profession: "+info.profession+", Email: "+info.email+", Mobile: "+info.contact;
        System.out.println("Enter the file name which should contain QR code");
        info.fileName=scan.nextLine();
        ByteArrayOutputStream out= QRCode.from(details).to(ImageType.JPG).stream();
        String path="D:\\QR\\"+info.fileName+".jpg";
        File f=new File(path);
        FileOutputStream fop=new FileOutputStream(f);
        fop.write(out.toByteArray());
        fop.flush();
        try
        {
            Class.forName("com.mysql.jdbc.Driver"); //load driver(register)
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "redhat"); //DriverManager class has implemented Connection and getConnection is a static method.
            PreparedStatement st=con.prepareStatement("insert into qrdatabase values(?,?,?,?,?)");
            st.setString(1,info.name);
            st.setString(2,info.profession);
            st.setString(3,info.email);
            st.setString(4,info.contact);
            st.setString(5,info.fileName);
            st.executeUpdate();
        }
        catch(Exception e)
        {
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Your details are:-");
        System.out.println("Name : "+ info.name);
        System.out.println("Profession : "+ info.profession);
        System.out.println("Email : "+ info.email);
        System.out.println("Contact : "+ info.contact);
        System.out.println(info.fileName+" file containg your details has been created!");
        System.out.println("Thank you!, For using our services");
    }    
}
