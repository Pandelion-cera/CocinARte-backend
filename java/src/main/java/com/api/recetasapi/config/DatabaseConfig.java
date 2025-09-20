package com.api.recetasapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DatabaseConfig {

    @Value("${DB_SECRET:}")
    private String dbSecret;

    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "production")
    public DataSource productionDataSource() {
        System.out.println("=".repeat(80));
        System.out.println("🔧 STARTING Aurora MySQL DataSource Configuration");
        System.out.println("=".repeat(80));

        try {
            // Log all environment variables related to database
            System.out.println("📋 ALL Environment Variables:");
            System.getenv().entrySet().stream()
                .filter(entry -> entry.getKey().contains("DB") || entry.getKey().contains("SECRET") || entry.getKey().contains("COPILOT"))
                .forEach(entry -> System.out.println("   " + entry.getKey() + " = " +
                    (entry.getKey().toLowerCase().contains("password") ? "[HIDDEN]" : entry.getValue())));

            System.out.println("🔍 DB_SECRET Variable Analysis:");
            System.out.println("   - DB_SECRET is null: " + (dbSecret == null));
            System.out.println("   - DB_SECRET is empty: " + (dbSecret != null && dbSecret.isEmpty()));
            System.out.println("   - DB_SECRET length: " + (dbSecret != null ? dbSecret.length() : "N/A"));

            if (dbSecret == null || dbSecret.isEmpty()) {
                System.err.println("❌ CRITICAL: DB_SECRET environment variable is not set or empty");
                System.err.println("📋 Full environment dump:");
                System.getenv().forEach((k, v) -> System.err.println("   " + k + " = " + v));
                throw new RuntimeException("DB_SECRET environment variable is not set");
            }

            System.out.println("🔍 DB_SECRET Content Preview:");
            System.out.println("   - First 100 chars: " + dbSecret.substring(0, Math.min(100, dbSecret.length())));
            System.out.println("   - Last 50 chars: " + dbSecret.substring(Math.max(0, dbSecret.length() - 50)));
            System.out.println("   - Contains 'host': " + dbSecret.contains("host"));
            System.out.println("   - Contains '{': " + dbSecret.contains("{"));
            System.out.println("   - Contains '}': " + dbSecret.contains("}"));

            // Parse the JSON secret
            System.out.println("🔄 Parsing JSON secret...");
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> secretMap;

            try {
                secretMap = objectMapper.readValue(dbSecret, Map.class);
                System.out.println("✅ JSON parsing successful");
            } catch (Exception jsonException) {
                System.err.println("❌ JSON parsing failed: " + jsonException.getMessage());
                System.err.println("📄 Raw DB_SECRET content:");
                System.err.println(dbSecret);
                throw jsonException;
            }

            System.out.println("📋 Parsed secret fields: " + secretMap.keySet());
            secretMap.forEach((key, value) -> {
                if (key.toLowerCase().contains("password")) {
                    System.out.println("   " + key + " = [HIDDEN - length: " + (value != null ? value.toString().length() : "null") + "]");
                } else {
                    System.out.println("   " + key + " = " + value);
                }
            });

            // Extract connection details
            System.out.println("🔍 Extracting connection details...");
            String host = (String) secretMap.get("host");
            Object portObj = secretMap.get("port");
            String port = portObj != null ? portObj.toString() : null;
            String dbname = (String) secretMap.get("dbname");
            String username = (String) secretMap.get("username");
            String password = (String) secretMap.get("password");

            System.out.println("🔍 Connection Details:");
            System.out.println("   - host: " + host);
            System.out.println("   - port: " + port);
            System.out.println("   - dbname: " + dbname);
            System.out.println("   - username: " + username);
            System.out.println("   - password: " + (password != null ? "[HIDDEN - length: " + password.length() + "]" : "null"));

            if (host == null || port == null || dbname == null || username == null || password == null) {
                System.err.println("❌ CRITICAL: Missing required database fields");
                System.err.println("📋 Required fields check:");
                System.err.println("   - host: " + (host != null ? "✅" : "❌"));
                System.err.println("   - port: " + (port != null ? "✅" : "❌"));
                System.err.println("   - dbname: " + (dbname != null ? "✅" : "❌"));
                System.err.println("   - username: " + (username != null ? "✅" : "❌"));
                System.err.println("   - password: " + (password != null ? "✅" : "❌"));
                throw new RuntimeException("Missing required database connection fields");
            }

            String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=true&requireSSL=true&serverTimezone=UTC", host, port, dbname);

            System.out.println("🔗 Final Connection Details:");
            System.out.println("   - JDBC URL: " + url);
            System.out.println("   - Driver: com.mysql.cj.jdbc.Driver");
            System.out.println("   - Username: " + username);

            System.out.println("🔧 Creating DataSource...");
            DataSource dataSource = DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url(url)
                    .username(username)
                    .password(password)
                    .build();

            System.out.println("✅ DataSource created successfully!");
            System.out.println("🧪 Testing connection...");

            try {
                dataSource.getConnection().close();
                System.out.println("✅ Database connection test successful!");
            } catch (Exception connTest) {
                System.err.println("⚠️  Database connection test failed: " + connTest.getMessage());
                System.err.println("   This might be normal during startup - connection will be retried");
            }

            System.out.println("=".repeat(80));
            System.out.println("✅ Aurora MySQL DataSource Configuration COMPLETED");
            System.out.println("=".repeat(80));

            return dataSource;

        } catch (Exception e) {
            System.err.println("=".repeat(80));
            System.err.println("❌ FATAL: Aurora MySQL DataSource Configuration FAILED");
            System.err.println("=".repeat(80));
            System.err.println("❌ Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
            System.err.println("=".repeat(80));
            throw new RuntimeException("Failed to configure database connection", e);
        }
    }
}