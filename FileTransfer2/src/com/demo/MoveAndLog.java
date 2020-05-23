package com.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class MoveAndLog {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties conf = new Properties();
		conf.load(new FileInputStream("C:\\Users\\srinivasulum\\Documents\\workspace-sts-3.9.11.RELEASE\\FileTransfer2\\conf.properties"));
		
		moveAndMark(conf);
	}
	
	private static void moveAndMark(Properties conf) throws IOException {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HHmm");
		String dest = conf.getProperty("destination");
		String src = conf.getProperty("source");
		File srcDir = new File(src);
		File processed = new File(src + File.separator + "processed");
		File loggerDir = new File(src + File.separator + "logger");
		File logger = new File(loggerDir + File.separator + "logger_" + formatter.format(new Date()) + ".txt");
		
		processed.mkdir();
		loggerDir.mkdir();
		logger.createNewFile();
		
		FileWriter fw = new FileWriter(logger, true);
		
		for(File file : srcDir.listFiles()) {
			if(file.getName().endsWith(".zip") || file.getName().endsWith(".7z")) {
				Files.copy(file.toPath(), Paths.get(dest + File.separator + file.getName()));
				Files.move(file.toPath(), Paths.get(processed.getAbsolutePath() + File.separator + file.getName() + ".processed"));
//				fw.write(file.getName() + "\n");
				fw.write(file.getName() + "\r");
			}
		}
		fw.close();
	}
}
