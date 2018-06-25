package nl.hu.ict.dp.nscasus;
import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    private static String orclcfg = "nl.hu.ict.jpa.oracle";
    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        // get the Hibernate - JPA entityManager
        EntityManager em = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(orclcfg);
            em = entityManagerFactory.createEntityManager();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        
        // Aanmaken van de OV Chipkaart domein objecten
         
        OVChipkaart kaart = new OVChipkaart();
        kaart.setKaartopdruk("mijn eerste kaart");
        
        //Aanmaken van Reiziger domein objecten
        
        Reiziger r1 = new Reiziger();
	      r1.setVoorletters("E");
	      r1.setAchternaam("Nube");
	      r1.setGeboortedatum(Date.valueOf("1997-08-14"));
	      
	      //voor het toevoegen in de tabel
	      Reiziger r2 = new Reiziger();
	      r2.setVoorletters("DP");
	      r2.setAchternaam("van den Bunt");
	      r2.setGeboortedatum(Date.valueOf("1996-01-16"));
	      
	      //om r1 te updaten
	      Reiziger r3 = new Reiziger();
	      r3.setVoorletters("P");
	      r3.setAchternaam("Nube");
	      r3.setGeboortedatum(Date.valueOf("1964-03-02"));

        
         // Opslaan van de data in de domein objecten
         
	        em.getTransaction().begin();
	        em.persist(kaart);
	        em.persist(r1);
	        em.persist(r2);
	        em.persist(r3);
	        em.getTransaction().commit();
	        
	      //Read
	        Reiziger r = em.find(Reiziger.class, r1.getReizigerID());
	        System.out.println("Reiziger opgehaald: " + r);
	        
	        //Update
	        Reiziger rr =  em.find(Reiziger.class, r2.getReizigerID());
	        System.out.println("voor verandering: " + rr);
	        rr.setAchternaam("Test");
	        System.out.println("na verandering: " + rr);
	        
	        //Delete
	        Reiziger rrr = em.find(Reiziger.class, r3.getReizigerID());
	        System.out.println("voor verwijderen: " + rrr);
	        em.remove(r3);
	        
	        //OV-Chipkaarten aanmaken en koppelen
	        OVChipkaart k1 = new OVChipkaart();
	        k1.setReiziger(r2);
	        k1.setKaartopdruk("Ik hoor bij r2");
	        
	        OVChipkaart k2 = new OVChipkaart();
	        k2.setReiziger(r3);
	        k2.setKaartopdruk("Ik hoor bij r3");
	        
	        OVChipkaart k3 = new OVChipkaart();
	        k3.setReiziger(r1);
	        k3.setKaartopdruk("Ik hoor bij r1");
	        
	        em.getTransaction().begin();
	        //CREATE
	        em.persist(k1);
	        em.persist(k2);
	        em.persist(k3);
	        
	        //READ
	        OVChipkaart k = em.find(OVChipkaart.class, k3.getKaartnr());
	        System.out.println("Kaart gelezen: " + k);
	        
	        //UPDATE
	        k.setKaartopdruk("Lorum ipsum dolor");
	        
	        //DELETE
	        em.remove(k3);
	
	        em.close();
	        System.out.println("-- einde --");
    }
}

