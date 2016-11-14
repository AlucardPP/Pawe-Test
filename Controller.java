package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Alucard on 2016-10-30.
 */
public class Controller {
    private final static int MOUSE_COUNT = 2;
    final Image unseleted = new Image("https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-arrow-up-b-32.png");
    final Image selected = new Image("https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-arrow-down-b-32.png");
    final ImageView toggleSelected = new ImageView();
    final ObservableList<String> choice = FXCollections.observableArrayList("", "Type of Element", "Name", "Last edit", "Size");
    private final String[] extension=new String[]{"txt","xml", "html", "css", "ini"};

    public Controller() {
    }

    public void addRoot(ComboBox<File> combo) {
        File[] root = File.listRoots();
        for (int i = 0; i < root.length; i++) {
            if (root[i].canRead()) {
                combo.getItems().add(root[i]);
            }
        }
    }

    public void chooserInCombo(ComboBox<File> combo, ListView list) {
        combo.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.refresh();
                list.getItems().clear();
                list.getItems().addAll(combo.getValue().listFiles());
            }
        });
    }

    public void showFile(ListView list, File file) {

        if (file.isDirectory()) {
            File[] folder = file.listFiles();
            list.refresh();
            list.getItems().clear();
            list.getItems().add(file.getAbsoluteFile().getParent());
            list.getItems().addAll(folder);
        }

    }

    public void mouseClicker(ListView list) {
        list.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getClickCount() == MOUSE_COUNT) {
                    Object observableList = list.getSelectionModel().getSelectedItem();
                    String path = observableList.toString();
                    File file = new File(path);
                    showFile(list, file);
                }
            }
        });

    }

    public void searchField(javafx.scene.control.TextField text, ListView list) {
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == null) {
                return;
            } else {
                String path = oldValue.toString();
                text.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode().equals(KeyCode.ENTER)) {
                            String file = text.getText();
                            list.getItems().clear();
                            list.getItems().add(path);
                            search(path, file, list);
                        }
                    }
                });
            }


        });


    }

    public void copyFile(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdir();
            }
            String[] files = source.list();
            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);
                copyFile(srcFile, destFile);
            }
        } else {
            Files.copy(source.toPath(), destination.toPath());
        }
    }


    public static void search(String path, String fileToSearch, ListView list) {
        File file = new File(path);
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                String fileString = f.getAbsolutePath().toString();
                if (f.isFile()) {
                    String filename = f.getName();
                    if (filename.contains(fileToSearch)) {
                        list.getItems().addAll(f);
                    }
                }
                search(fileString, fileToSearch, list);
            }
        }


    }

    public void forceListRefreshOn(ListView lsv, File file) {
        lsv.getItems().clear();
        File[] fi = file.listFiles();
        lsv.getItems().add(file.getParent());
        for (File f : fi) {

            lsv.getItems().add(f);
        }
    }

    public void sortByType(ListView list) throws ArrayIndexOutOfBoundsException, NullPointerException {
        Object[] a = list.getItems().toArray();

        if (a[1] == null) {
            return;
        } else {
            String path = a[a.length - 1].toString();
            File file = new File(path);
            String parent = file.getParent().toString();
            File fi = new File(parent);
            File[] files = fi.listFiles();
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    String s1 = ((File) o1).getName().toLowerCase();
                    String s2 = ((File) o2).getName().toLowerCase();
                    int s1dot = s1.lastIndexOf('.');
                    int s2dot = s2.lastIndexOf('.');
                    if ((s1dot == -1) == (s2dot == -1)) {
                        s1 = s1.substring(s1dot + 1);
                        s2 = s2.substring(s2dot + 1);
                        return s1.compareTo(s2);
                    } else if (s1dot == -1) {
                        return -1;
                    } else {
                        return 1;
                    }

                }
            });

            list.getItems().clear();
            list.getItems().add(fi.getParent());
            list.getItems().addAll(files);
        }


    }

    public void reverseSortByType(ListView list) {
        Object[] a = list.getItems().toArray();

        if (a[1] == null) {
            return;
        } else {
            String path = a[a.length - 1].toString();
                File file = new File(path);
                String parent = file.getParent().toString();
                File fi = new File(parent);
                File[] files = fi.listFiles();
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        String s1 = ((File) o1).getName().toLowerCase();
                        String s2 = ((File) o2).getName().toLowerCase();
                        int s1dot = s1.lastIndexOf('.');
                        int s2dot = s2.lastIndexOf('.');
                        if ((s1dot == -1) == (s2dot == -1)) {
                            s1 = s1.substring(s1dot + 1);
                            s2 = s2.substring(s2dot + 1);
                            return s1.compareTo(s2);
                        } else if (s1dot == -1) {
                            return 1;
                        } else {
                            return -1;
                        }

                    }
                });
                list.getItems().clear();
                list.getItems().add(fi.getParent());
                list.getItems().addAll(files);
            }


    }

    public void sortByName(ListView list) {
        Object[] a = list.getItems().toArray();

        if (a[1] == null) {
            return;
        } else {
            String path = a[a.length - 1].toString();
                File file = new File(path);
            String parent = file.getParent().toString();
            File fi = new File(parent);
                File[] files = fi.listFiles();
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return (o1.getName().compareTo((o2.getName())));
                    }
                });
                list.getItems().clear();
                list.getItems().add(fi.getParent());
                list.getItems().addAll(files);
            }

    }

    public void reverseSortByName(ListView list) {
        Object[] a = list.getItems().toArray();

        if (a[1] == null) {
            return;
        } else {
            String path = a[a.length - 1].toString();
            File file = new File(path);
            String parent = file.getParent().toString();
            File fi = new File(parent);
            File[] files = fi.listFiles();
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return -(o1.getName().compareTo((o2.getName())));
                    }
                });
            list.getItems().clear();
            list.getItems().add(fi.getParent());
            list.getItems().addAll(files);
            }

    }

    public void sortByLastEdit(ListView list) {
        Object[] a = list.getItems().toArray();

        if (a[1] == null) {
            return;
        } else {
            String path = a[a.length - 1].toString();
            File file = new File(path);
            String parent = file.getParent().toString();
            File fi = new File(parent);
            File[] files = fi.listFiles();
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return Long.compare(o1.lastModified(), o2.lastModified());
                    }
                });
            list.getItems().clear();
            list.getItems().add(fi.getParent());
            list.getItems().addAll(files);
            }

    }

    public void reverseSortByLastEdit(ListView list) {
        Object[] a = list.getItems().toArray();

        if (a[1] == null) {
            return;
        } else {
            String path = a[a.length - 1].toString();
            File file = new File(path);
            String parent = file.getParent().toString();
            File fi = new File(parent);
            File[] files = fi.listFiles();
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return -Long.compare(o1.lastModified(), o2.lastModified());
                    }
                });
            list.getItems().clear();
            list.getItems().add(fi.getParent());
            list.getItems().addAll(files);
            }

    }

    public void sortBySize(ListView list) {
        Object[] a = list.getItems().toArray();

        if (a[1] == null) {
            return;
        } else {
            String path = a[a.length - 1].toString();
            File file = new File(path);
            String parent = file.getParent().toString();
            File fi = new File(parent);
            File[] files = fi.listFiles();
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return Long.compare(o1.length(), o2.length());
                    }
                });
            list.getItems().clear();
            list.getItems().add(fi.getParent());
            list.getItems().addAll(files);
            }

    }

    public void reverseSortBySize(ListView list) {
        Object[] a = list.getItems().toArray();

        if (a[1] == null) {
            return;
        } else {
            String path = a[a.length - 1].toString();
            File file = new File(path);
            String parent = file.getParent().toString();
            File fi = new File(parent);
            File[] files = fi.listFiles();
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return -Long.compare(o1.length(), o2.length());
                    }
                });
            list.getItems().clear();
            list.getItems().add(fi.getParent());
            list.getItems().addAll(files);
            }

    }

    public void showEditableFile(String path, TextArea showArea) {
        File file = new File(path);


        if (file.isDirectory()) {
            showArea.setText("Nie mogłem wyświetlić folderu jako tekst. Dobrze wiesz o tym :D");
            showArea.setDisable(true);
        }
        else if(checkExtension(file)==false){
            showArea.setText("Nie mogłem wyświetlić akurat tego pliku :) " +
                    "(nie obsługuję plików z takimi roszerzeniami,za kogo mnie masz puszczalską przeglądarkę plików? " +
                    "Mam swój gust w \"txt\", \"xml\", \"html\", \"css\", \"ini\" ;P )");
            showArea.setDisable(true);
        }

        else if(checkExtension(file)==true) {
            try(FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);) {
                StringBuilder sb=new StringBuilder();
                String line=br.readLine();
                while (line!=null) {
                   sb.append(line);
                    line=br.readLine();

                }
                showArea.setText(sb.toString());
            } catch (FileNotFoundException ex) {
                System.err.println(ex);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public boolean checkExtension(File file)
    {
        for (String check : extension)
        {
            if (file.getName().toLowerCase().endsWith(check))
            {
                return true;
            }
        }
        return false;
    }

    public void saveFile(String path,TextArea textArea){
        File file=new File(path);
        try(FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);) {
            bw.write(textArea.getText());

        }
        catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }



/*

     public static void searchFile(String path,String nameToFind){
         Path rootPath = Paths.get(path);
         try {

             Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

                 @Override
                 public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                     String fileName=file.getFileName().toString();
                     if (fileName.contains(nameToFind)) {
                         System.out.println(file.toAbsolutePath());
                         return FileVisitResult.CONTINUE;
                     }
                     return FileVisitResult.CONTINUE;
                 }
             });
         } catch (IOException e) {
             e.printStackTrace();
         }

     }
     */

}
