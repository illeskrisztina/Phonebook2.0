package application.main.model.database;

import application.main.model.exception.DatabaseConnectionException;

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
      throw new DatabaseConnectionException("Something went wrong while trying to connect to the database.");
    }
  }
}
