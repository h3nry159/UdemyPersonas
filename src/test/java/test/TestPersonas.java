
package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.PersonaDTO;
import datos.Conexion;
import datos.PersonaDao;
import datos.PersonaDaoJDBC;

public class TestPersonas {

    public static void main(String[] args) {
        
        
        Connection conexion = null;
        
        
        try {
            conexion = Conexion.getConnection();
            if(conexion.getAutoCommit()){
                conexion.setAutoCommit(false);
            }
            PersonaDao personaDao = new PersonaDaoJDBC(conexion);
            List<PersonaDTO> personas = personaDao.select();;
           
            for(PersonaDTO persona : personas){
               
               System.out.println(persona);
           
           }
           conexion.commit();
           
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace(System.out);
            }
            
        }
        
        
 
    }
    
}
