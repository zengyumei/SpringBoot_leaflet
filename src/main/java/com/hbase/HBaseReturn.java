package com.hbase;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.protobuf.generated.CellProtos.Cell;
import org.assertj.core.util.DateUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Controller
@CacheConfig(cacheNames = "WMTS/WorldMap/{z}/{x}/{y}.png")
public class HBaseReturn {
    public boolean HbaseConnect = false;
    @RequestMapping(value="WMTS/WorldMap/{z}/{x}/{y}.png",method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(cacheNames = {"WMTS/WorldMap/{z}/{x}/{y}.png"})
    public ResponseEntity<byte[]> PNG(@PathVariable("z") String MapZ, @PathVariable("x") String MapX, @PathVariable("y") String MapY)throws IOException
    {
        byte[] bs;
        HttpHeaders headers;
        String rowkeyStr=Rowkey.getRowkey(MapZ,MapY,MapX);
       // String rowkeyStr=MapZ+MapX+MapY+".png";
        System.out.println("rowkey=" + rowkeyStr + "---wwww\n");
        long startTime = System.currentTimeMillis(); //运行开始时间
        Result result =HBaseConnection.getData_World(rowkeyStr);
       /* 
        System.out.println("result.toString = " + result.toString());
        System.out.println("result.listCells = " + result.listCells());
        for (org.apache.hadoop.hbase.Cell cell : result.rawCells()){
            System.out.println(cell.toString());
            String format = DateUtil.timeToStr(cell.getTimestamp());
            System.out.println(format);
            Date parse = DateUtil.parse(cell.getTimestamp());
            System.out.println(parse);
        }
        */
        long endTime = System.currentTimeMillis(); //运行结束时间
        long time = endTime - startTime; //用来测试程序的运行时间
        System.out.println(time + "---usetime");
        bs=result.value();
        headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
      /*  HBaseHelper.connect();
        String rowkeyStr=MapZ+MapX+MapY+".png";
        System.out.println(rowkeyStr);
        Result result =HBaseHelper.getData("MapTileTest",rowkeyStr);
        byte[] bs=result.value();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        HBaseHelper.close();*/
      /* String imgPath="F:\\huawei\\gdal\\"+MapZ+"\\"+MapX+"\\"+MapY+".png";
       System.out.println(imgPath);
        FileInputStream fis=new FileInputStream(imgPath);
        byte[] bs=new byte[fis.available()];
        fis.read(bs);
        fis.close();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        HBaseHelper.close();
        System.out.println("hhh");*/
        return new ResponseEntity<byte[]>(bs,headers, HttpStatus.OK);
    }
    
    @RequestMapping(value="WMTS/Thailand/{z}/{x}/{y}.png",method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(cacheNames = {"WMTS/Thailand/{z}/{x}/{y}.png"})
    public ResponseEntity<byte[]> PNG1(@PathVariable("z") String MapZ, @PathVariable("x") String MapX, @PathVariable("y") String MapY)throws IOException
    {
        byte[] bs;
        HttpHeaders headers;
        String rowkeyStr=Rowkey.getRowkey(MapZ,MapY,MapX);
       // String rowkeyStr=MapZ+MapX+MapY+".png";
        System.out.println("rowkey=" + rowkeyStr + "---ttt\n");
        long startTime = System.currentTimeMillis();
        Result result =HBaseConnection.getData_Thai(rowkeyStr);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println(time + "---usetime");
        bs=result.value();
        headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
  
        return new ResponseEntity<byte[]>(bs,headers, HttpStatus.OK);
    }

    @RequestMapping(value="WMTS/Landsat/{z}/{x}/{y}.png",method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(cacheNames = {"WMTS/Landsat/{z}/{x}/{y}.png"})
    public ResponseEntity<byte[]> PNG2(@PathVariable("z") String MapZ, @PathVariable("x") String MapX, @PathVariable("y") String MapY)throws IOException
    {
        byte[] bs;
        HttpHeaders headers;
        String rowkeyStr=Rowkey.getRowkey(MapZ,MapY,MapX);
       // String rowkeyStr=MapZ+MapX+MapY+".png";
        System.out.println("rowkey=" + rowkeyStr + "---lll\n");
        long startTime = System.currentTimeMillis();
        Result result =HBaseConnection.getData_Landsat(rowkeyStr);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println(time + "---usetime");
        bs=result.value();
        headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bs,headers, HttpStatus.OK);
    }
}
