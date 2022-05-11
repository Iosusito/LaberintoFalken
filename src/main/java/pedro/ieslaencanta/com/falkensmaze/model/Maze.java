/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.falkensmaze.model;

import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;
import jakarta.xml.bind.Unmarshaller;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.System.in;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import pedro.ieslaencanta.com.falkensmaze.Size;

/**
 *
 * @author Pedro
 */
@XmlRootElement
public class Maze implements Serializable {

    private Size size;
    private Block[][] blocks;
    private double time;
    private String sound;

    public Maze() {
    }

    public void init() {
        this.setBlocks(new Block[this.getSize().getHeight()][this.getSize().getWidth()]);
        for (int i = 0; i < this.getBlocks().length; i++) {
            for (int j = 0; j < this.getBlocks()[i].length; j++) {
                this.getBlocks()[i][j] = new Block();

            }
        }
    }

    public void reset() {
        for (int i = 0; i < this.getBlocks().length; i++) {
            for (int j = 0; j < this.getBlocks()[i].length; j++) {
                this.getBlocks()[i][j] = null;

            }
        }
        this.setBlocks(null);
    }

    public void reset(Size newsize) {
        this.reset();;
        this.setSize(newsize);
        this.init();
    }

    public void setBlockValue(String value, int row, int col) {
        this.getBlocks()[col][row].setValue(value);
    }

    public String getBlockValue(int row, int col) {
        return this.getBlocks()[row][col].getValue();
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }

    public static Maze load(File file) throws IOException, JAXBException {
        Maze m = null;
        //sacar la extension del archivo xml, json, bin
        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        //switch con la extension
        switch (extension) {
            case "json":
                m = Maze.loadJSON(file);
                break;
            case "xml":
                m = Maze.loadXML(file);
                break;
            case "bin":
                m = Maze.loadBin(file);
        }
        return m;
    }

    public static void save(Maze maze, File file) throws Exception {
        /*if (maze.sound == null || maze.sound.equals("")) {
            throw new Exception("Es necesario indicar el sonido del laberinto");
        }*/
        //sacar la extension del archivo xml, json, bin
        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        //switch con la extension
        switch (extension) {
            case "json":
                Maze.saveJSON(maze, file);
                break;
            case "xml":
                Maze.saveXML(maze, file);
                break;
            case "bin":
                Maze.saveBin(maze, file);
        }
    }

    private static Maze loadJSON(File file) throws IOException {
        Maze m = null;
        Gson gson = new Gson();
        Reader r = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
        m = gson.fromJson(r, Maze.class);
        //System.out.println(m);
        return m;
    }

    private static Maze loadXML(File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Maze.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Maze m = (Maze) unmarshaller.unmarshal(file);
        return m;
    }

    public static Maze loadBin(File file) {
        return null;
    }

    private static void saveJSON(Maze maze, File file) throws FileNotFoundException {
        Gson gson = new Gson();
        //pasar a json
        String json = gson.toJson(maze);
        //System.out.println(json);
        //se escribe el json en el archivo
        PrintWriter p = new PrintWriter(file);
        p.write(json);
        //cerramos el archivo
        p.close();
    }

    private static void saveXML(Maze maze, File file) throws JAXBException {
        JAXBContext contexto = JAXBContext.newInstance(maze.getClass());
        Marshaller marshaller = contexto.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(maze, file);
    }

    public static void saveBin(Maze maze, File file) {
        
    }
}
