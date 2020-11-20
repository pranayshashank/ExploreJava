package in.pks.journal.java.stream;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class StreamingUtils {

    /**
     * Using Stream Iterator to create an infinite stream of integers.
     * Though, it is theoretically infinite as it is bounded by the constraint
     * that JRE only allows signed long value < 2^63.
     *
     * @param l
     * @return
     */
    public static Stream<Long> generateInfiniteIntegers(long l) {
        if (l < 0) {
            l = 0;
        }

        UnaryOperator<Long> unaryOp = new UnaryOperator<Long>() {
            @Override
            public Long apply(Long aLong) {
                Objects.requireNonNull(aLong);
                return aLong + 1;
            }
        };

        return Stream.iterate(new Long(0), unaryOp).limit(l);
    }


    public static Stream<Map<String, Object>> executeQuery(String sql, Connection connection) throws SQLException {
        Objects.requireNonNull(connection);
        Stream stream = Stream.empty();

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        final List<String> headers = new ArrayList<>();
        if (Objects.nonNull(rs)) {
            ResultSetMetaData meta = rs.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                headers.add(meta.getColumnName(i));
            }

            return Stream.generate(() -> {
                Map<String, Object> _dataMap = new HashMap<>();

                try {
                    if (rs.next()) {
                        headers.stream().forEach(column -> {
                            try {
                                _dataMap.put(column, rs.getObject(column));
                            } catch (SQLException e) {
                                _dataMap.put(column, new Object());
                            }
                        });
                    } else {
                        throw new RuntimeException("EOS Reached");
                    }
                } catch (SQLException e) {
                    System.err.println("Exception~ " + e.getMessage());
                }

                return _dataMap;
            });
        }


        return stream;
    }

    public static void main(String[] args) {
        Stream<Long> stream = StreamingUtils.generateInfiniteIntegers(0);
        System.out.println("Start-time: " + LocalDateTime.now());

        stream./*filter(num->{
            if(num == Integer.MAX_VALUE){
                System.out.println("End-time : " + LocalDateTime.now());
                return true;
            }
            return false;
        }).*/forEach(System.out::println);

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");

            Stream<Map<String, Object>> data = executeQuery("select * from mac.MENU", connection);
            data.forEach(_map -> {
                System.out.println("\n");
                System.out.println("\t" + _map.toString());
            });

        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RuntimeException rex) {
            System.err.println(rex.getMessage());
        } finally {
            if (Objects.nonNull(connection)) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
