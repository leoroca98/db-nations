package org.example;

import java.sql.*;

class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_nations";
        String username = "root";
        String password = "33969696pp";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT " +
                    "countries.country_id, " +
                    "countries.name AS country_name, " +
                    "regions.name AS region_name, " +
                    "continents.name AS continent_name " +
                    "FROM countries " +
                    "JOIN regions ON countries.region_id = regions.region_id " +
                    "JOIN continents ON regions.continent_id = continents.continent_id " +
                    "ORDER BY countries.name";

            ResultSet resultSet = statement.executeQuery(query);

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
