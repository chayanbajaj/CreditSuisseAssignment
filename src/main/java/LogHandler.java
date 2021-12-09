import helpers.*;
import org.apache.commons.*;
import java.io.*;
import java.io.*;
import java.sql.*;

public class LogHandler {
    private final static Log logger = LogFactory.getLog(Parser.class);

    public static void main(String[] ar) {
        if (ar == null || ar.length != 1) {
            logger.error("Enter the file as arguments");
            throw new IllegalArgumentException("Incorrect arguments");
        }
        String file = ar[0];
        try {
            BufferedReader bR = new BufferedReader(new FileReader(file));
            DAO dX = new DAO();
            dX.createEventsTable();
            logger.debug("Parsing file <" + file + "> for events.");
            Parser prs = new Parser();
            prs.parseLogs(bR, dX);
            dX.selectAll();
            dX.deleteAll();
            dX.closeDatabase();
        } catch (IOException e) {
            logger.error("Error parsing file < " + file + " >");
            e.printStackTrace();
        } catch (SQLException e) {
            logger.error("Error encountered with DataBase");
            e.printStackTrace();
        }
    }
}
