package application.main.model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DatabaseHandlerFactory
{
  public Connection establishConnection()
  {
    try
    {
      return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=master;encrypt=true;trustServerCertificate=true;integratedSecurity=true");
    }
    catch (SQLException e)
    {
      throw new RuntimeException("Something went wrong while trying to connect to the database.");
    }
  }
}
