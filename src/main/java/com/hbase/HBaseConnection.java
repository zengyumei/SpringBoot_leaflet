package com.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HBaseConnection {
    private HBaseConnection(){
        
    }
    private static Connection connection = null;
    private static Admin admin;
    private static Table table_World = null;
    private static Table table_Thai = null;
    private static Table table_Landsat = null;
    public static void HbaseGetConnection(){
        if(connection == null){
            ExecutorService pool = Executors.newFixedThreadPool(10);
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.rootdir", "hdfs://master:9000/hbase");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conf.set("hbase.zookeeper.quorum", "master,slave1,slave2");
            try{
                connection = ConnectionFactory.createConnection(conf,pool);
                admin = connection.getAdmin();
                //table=connection.getTable(TableName.valueOf("MapTileTest"));
                table_World = connection.getTable(TableName.valueOf("WorldMapL"));
                table_Thai = connection.getTable(TableName.valueOf("ThailandL"));
                table_Landsat = connection.getTable(TableName.valueOf("LandsatImageL"));
                
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("Connnection to Hbase, connnetion is " + (connection==null));
    }
    public static Result getData_World(String row){
        //System.out.println(table_World);
        Get get = new Get(Bytes.toBytes(row));
        Get scan = new Get(Bytes.toBytes(row));
        scan.setMaxVersions();
        
        Result result = null;
        try {
            result = table_World.get(get);
            //result = table_Landsat.get(scan);
            //System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static Result getData_Thai(String row){
        //System.out.println(table_Thai);
        Get get = new Get(Bytes.toBytes(row));
        Result result = null;
        try {
            result = table_Thai.get(get);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static Result getData_Landsat(String row){
        //System.out.println(table_Landsat);
        Get get = new Get(Bytes.toBytes(row));
        Result result = null;
        try {
            result = table_Landsat.get(get);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    

}
