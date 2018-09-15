package goodr0ne.trampwitter;

import com.google.gson.JsonObject;

import java.sql.*;

class TrampWeetRepoConnector {
    private static TrampWeetRepoConnector singleton = new TrampWeetRepoConnector();

    static TrampWeetRepoConnector getInstance() {
        return singleton;
    }

    private TrampWeetRepoConnector() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Failed to instantiate TrampWeetRepoConnector singleton");
        }
    }

    private final String JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:~/test";
    private final String USER = "sa";
    private final String PASS = "";
    private boolean isCreated = false;

    private Connection conn = null;
    Statement stmt = null;

    private synchronized void createTable() {
        System.out.println("Hello, i'm TrampWeetRepoConnector.createTable()!");
        stmt = null;
        if (isCreated) {
            System.out.println("Table already created");
            System.out.println("Goodbye, that was TrampWeetRepoConnector.createTable()!");
            return;
        }
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS trampweets " +
                    "(id BIGINT not NULL," +
                    " timestamp BIGINT not NULL," +
                    " body VARCHAR(1255) UNIQUE, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.createTable()!");
            }
        }
        flushTable();
        isCreated = true;
        System.out.println("Goodbye, that was TrampWeetRepoConnector.createTable()!");
    }

    synchronized void flushTable() {
        System.out.println("Hello, i'm TrampWeetRepoConnector.flushTable()!");
        stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "TRUNCATE TABLE trampweets";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.flushTable()!");
            }
        }
        System.out.println("Goodbye, that was TrampWeetRepoConnector.flushTable()!");
    }

    synchronized void deleteTable() {
        System.out.println("Hello, i'm TrampWeetRepoConnector.deleteTable()!");
        stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "DROP TABLE trampweets";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.deleteTable()!");
            }
        }
        System.out.println("Goodbye, that was TrampWeetRepoConnector.deleteTable()!");
    }

    synchronized void insertTweet(TrampWeet tweet) {
        System.out.println("Hello, i'm TrampWeetRepoConnector.insertTweet()!");
        if (!isCreated) {
            createTable();
        }
        PreparedStatement stmt = null;
        try {
            JsonObject tweetObj = tweet.getAsJson();
            String myStmt = "INSERT INTO trampweets (id, timestamp, body) VALUES (?, ?, ?)";
            System.out.println("Trying to insert next tweet:\n" + tweetObj.toString());
            stmt = conn.prepareStatement(myStmt);
            stmt.setLong(1, tweetObj.get("id").getAsLong());
            stmt.setLong(2, tweetObj.get("timestamp").getAsLong());
            stmt.setString(3, tweetObj.get("body").getAsString());
            stmt.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.insertTweet()!");
            }
        }
        System.out.println("Goodbye, that was TrampWeetRepoConnector.insertTweet()!");
    }

    synchronized TrampWeet[] getAllTweets() {
        System.out.println("Hello, i'm TrampWeetRepoConnector.getAllTweets()!");
        if (!isCreated) {
            createTable();
        }
        stmt = null;
        TrampWeet[] tweets;
        try {
            tweets = new TrampWeet[recount()];
            stmt = conn.createStatement();
            String sql = "SELECT id, timestamp, body FROM trampweets";
            ResultSet rs = stmt.executeQuery(sql);
            int j = 0;
            while(rs.next()) {
                JsonObject tweet = new JsonObject();
                tweet.addProperty("id", rs.getLong("id"));
                tweet.addProperty("timestamp", rs.getLong("timestamp"));
                tweet.addProperty("body",
                        rs.getString("body"));
                tweets[j] = new TrampWeet(tweet);
                j++;
            }
            rs.close();
            stmt.close();
            return tweets;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.getAllTweets()!");
            }
        }
        System.out.println("Goodbye, that was TrampWeetRepoConnector.getAllTweets()!");
        return new TrampWeet[0];
    }

    synchronized int recount() {
        int count = -1;
        System.out.println("Hello, i'm TrampWeetRepoConnector.recount()!");
        if (!isCreated) {
            createTable();
        }
        stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM trampweets";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.recount()!");
            }
        }
        System.out.println("Goodbye, that was TrampWeetRepoConnector.recount()!");
        return count;
    }
}
