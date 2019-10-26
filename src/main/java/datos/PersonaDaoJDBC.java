package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import domain.PersonaDTO;

public class PersonaDaoJDBC implements PersonaDao{

    private Connection userConn;
    private final static String SQL_INSERT = "INSERT INTO personas(nombre, apellido) VALUES(?,?)";
    private final static String SQL_UPDATE = "UPDATE personas SET nombre = ?, apellido = ? WHERE id_persona = ?";
    private final static String SQL_DELETE = "DELETE personas WHERE id_persona = ?";
    private final static String SQL_SELECT = "SELECT * FROM personas";

    public PersonaDaoJDBC() {
    }

    public PersonaDaoJDBC(Connection conn) {
        this.userConn = conn;
    }

    @Override
    public List<PersonaDTO> select() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        PersonaDTO persona = null;
        List<PersonaDTO> personas = new ArrayList<>();

        try {
            conn = this.userConn != null ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);
            System.out.println("Ejecutando query select");
            rs = ps.executeQuery();
            while (rs.next()) {
                persona = new PersonaDTO();
                persona.setId_persona(rs.getInt("id_persona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                personas.add(persona);
            }

        } finally {
            Conexion.close(ps);
            Conexion.close(rs);
            if (this.userConn == null) {
                Conexion.close(conn);
            }

        }
        return personas;
    }

    @Override
    public int insert(PersonaDTO persona) throws SQLException {

        PreparedStatement ps = null;
        Connection conn = null;
        int rows = 0;

        try {
            conn = this.userConn != null ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            System.out.println("Ejecutando query insert...");
            rows = ps.executeUpdate();
            System.out.println("Numero de filas afectadas " + rows);

        } finally {
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }

        }

        return rows;
    }

    @Override
    public int update(PersonaDTO persona) throws SQLException {
        PreparedStatement ps = null;
        Connection conn = null;
        int rows = 0;

        try {
            conn = this.userConn != null ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setInt(5, persona.getId_persona());
            System.out.println("Ejecutando query update");
            rows = ps.executeUpdate();
            System.out.println("Filas afectadas " + rows);

        } finally {
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }

        }
        return rows;

    }

    @Override
    public int delete(PersonaDTO persona) throws SQLException {
        PreparedStatement ps = null;
        Connection conn = null;
        int rows = 0;

        try {
            conn = this.userConn != null ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, persona.getId_persona());
            System.out.println("Ejecutando query delete");
            rows = ps.executeUpdate();
            System.out.println("Filas afectadas " + rows);
        } finally {

            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);

            }

        }

        return rows;
    }

}
