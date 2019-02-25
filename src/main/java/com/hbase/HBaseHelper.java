package com.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HBaseHelper {
    private static Configuration configuration;
    private static Connection connection;
    private static Admin admin;
    private HBaseHelper() {}
    
    /**
     * Connect to HBase.
     * @return boolean
     */
    public static boolean connect() {
        boolean isConnected = false;
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.rootdir", "hdfs://master:9000/hbase");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "master,slave1,slave2");
        // try to connect to HBase.
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
            isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
            isConnected = false;
        }
        return isConnected;
    }
    /**
     * Close the connection to HBase.
     * */
    public static void close() {
        try {
            if(admin != null) {
                admin.close();
            }
            if(connection != null) {
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Get data by row key.
     * @param tbName: table name
     * @param row: row key
     * @return org.apache.hadoop.hbase.client.Result
     */
    public static Result getData( HTable table,String row) {

        Get get = new Get(Bytes.toBytes(row));
        Result result = null;
        try {
            result = table.get(get);

            table.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

}
