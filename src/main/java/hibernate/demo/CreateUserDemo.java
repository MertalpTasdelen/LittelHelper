package hibernate.demo;

import entity.DBUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateUserDemo {

    public static void main(String[] args) {



        //create sessioon Factory
        SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(DBUser.class)
                .buildSessionFactory();

        //create session

        Session session = factory.getCurrentSession();
        try{
            //use the session object to save Java object
            DBUser tempDBUser = new DBUser(1234,"Mertalp","Tasdelen","Mertalp", false);
            //start a transaction
            session.beginTransaction();
            //save the student object
            session.save(tempDBUser);
            //commit transaction
            session.getTransaction().commit();

        }finally {
            factory.close();
        }
    }
}
