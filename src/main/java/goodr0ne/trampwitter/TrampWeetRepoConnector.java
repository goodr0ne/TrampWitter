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
    private boolean verbose = false;

    private Connection conn = null;
    Statement stmt = null;

    synchronized void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    private synchronized void createTable() {
        if (verbose) {
            System.out.println("Hello, i'm TrampWeetRepoConnector.createTable()!");
        }
        stmt = null;
        if (isCreated) {
            if (verbose) {
                System.out.println("Table already created");
                System.out.println("Goodbye, that was TrampWeetRepoConnector.createTable()!");
            }
            return;
        }
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS trampweets " +
                    "(id BIGINT not NULL," +
                    " timestamp BIGINT not NULL," +
                    " body varchar(1255) UNIQUE, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch(Exception e) {
            if (verbose) {
                e.printStackTrace();
            }
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
        if (verbose) {
            System.out.println("Goodbye, that was TrampWeetRepoConnector.createTable()!");
        }
    }

    synchronized void flushTable() {
        if (verbose) {
            System.out.println("Hello, i'm TrampWeetRepoConnector.flushTable()!");
        }
        stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "TRUNCATE TABLE trampweets";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch(Exception e) {
            if (verbose) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.flushTable()!");
            }
        }
        if (verbose) {
            System.out.println("Goodbye, that was TrampWeetRepoConnector.flushTable()!");
        }
    }

    synchronized void deleteTable() {
        if (verbose) {
            System.out.println("Hello, i'm TrampWeetRepoConnector.deleteTable()!");
        }
        stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "DROP TABLE trampweets";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch(Exception e) {
            if (verbose) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.deleteTable()!");
            }
        }
        isCreated = false;
        if (verbose) {
            System.out.println("Goodbye, that was TrampWeetRepoConnector.deleteTable()!");
        }
    }

    synchronized void insertTweet(TrampWeet tweet) {
        if (verbose) {
            System.out.println("Hello, i'm TrampWeetRepoConnector.insertTweet()!");
        }
        if (!isCreated) {
            createTable();
        }
        stmt = null;
        try {
            JsonObject tweetObj = tweet.getAsJson();
            stmt = conn.createStatement();
            String myStmt = "INSERT INTO trampweets (id, timestamp, body) VALUES ("
                    + tweetObj.get("id").getAsLong() + ", "
                    + tweetObj.get("timestamp").getAsLong() + ", '"
                    +tweetObj.get("body").getAsString()  + "')";
            stmt.executeUpdate(myStmt);
            stmt.close();
        } catch(Exception e) {
            if (verbose) {
                System.out.println("This tweet already crawled!");
            }
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.insertTweet()!");
            }
        }
        if (verbose) {
            System.out.println("Goodbye, that was TrampWeetRepoConnector.insertTweet()!");
        }
    }

    synchronized void insertTestTweet() {
        if (verbose) {
            System.out.println("Hello, i'm TrampWeetRepoConnector.insertTestTweet()!");
        }
        if (!isCreated) {
            createTable();
        }
        stmt = null;
        try {
            JsonObject tweetObj = new JsonObject();
            tweetObj.addProperty("id", 1337);
            tweetObj.addProperty("timestamp", 7331);
            tweetObj.addProperty("body", "testing");
            String sql = "INSERT INTO trampweets VALUES (1337, 7331, 'testing')";
            if (verbose) {
                System.out.println("Trying to insert next tweet:\n" + tweetObj.toString());
            }
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch(Exception e) {
            if (verbose) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.insertTestTweet()!");
            }
        }
        if (verbose) {
            System.out.println("Goodbye, that was TrampWeetRepoConnector.insertTestTweet()!");
        }
    }

    synchronized TrampWeet[] getAllTweets() {
        if (verbose) {
            System.out.println("Hello, i'm TrampWeetRepoConnector.getAllTweets()!");
        }
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
            if (verbose) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.getAllTweets()!");
            }
        }
        if (verbose) {
            System.out.println("Goodbye, that was TrampWeetRepoConnector.getAllTweets()!");
        }
        return new TrampWeet[0];
    }

    synchronized int recount() {
        int count = -1;
        if (verbose) {
            System.out.println("Hello, i'm TrampWeetRepoConnector.recount()!");
        }
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
            if (verbose) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.out.println("Error with finally block in " +
                        "TrampWeetRepoConnector.recount()!");
            }
        }
        if (verbose) {
            System.out.println("Goodbye, that was TrampWeetRepoConnector.recount()!");
        }
        if (count < 0) {
            if (verbose) {
                System.out.println("All tweets are gone with table itself! Let's start it again...");
            }
            isCreated = false;
            createTable();
        }
        return count;
    }
}
