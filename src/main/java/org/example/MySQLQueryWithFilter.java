package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MySQLQueryWithFilter {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_nations";
        String username = "root";
        String password = "33969696pp";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Inserisci una stringa di ricerca: ");
            String searchQuery = scanner.nextLine();

            // Utilizza un PreparedStatement per creare una query parametrica
            String query = "SELECT " +
                    "countries.country_id, " +
                    "countries.name AS country_name, " +
                    "regions.name AS region_name, " +
                    "continents.name AS continent_name " +
                    "FROM countries " +
                    "JOIN regions ON countries.region_id = regions.region_id " +
                    "JOIN continents ON regions.continent_id = continents.continent_id " +
                    "WHERE countries.name LIKE ? OR " +
                    "regions.name LIKE ? OR " +
                    "continents.name LIKE ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            String searchString = "%" + searchQuery + "%"; // Aggiungi il parametro di ricerca con il LIKE
            preparedStatement.setString(1, searchString);
            preparedStatement.setString(2, searchString);
            preparedStatement.setString(3, searchString);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int countryId = resultSet.getInt("country_id");
                String countryName = resultSet.getString("country_name");
                String regionName = resultSet.getString("region_name");
                String continentName = resultSet.getString("continent_name");

                System.out.println("Country ID: " + countryId);
                System.out.println("Country Name: " + countryName);
                System.out.println("Region Name: " + regionName);
                System.out.println("Continent Name: " + continentName);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}