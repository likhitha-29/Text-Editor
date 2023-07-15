import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.JOptionPane;

public class TextEditor extends Frame {
    private TextArea textArea;
    private Clipboard clipboard;

    public TextEditor() {
        setTitle("Text Editor");

        textArea = new TextArea();
        add(textArea);

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        MenuBar menuBar = new MenuBar();
        setMenuBar(menuBar);

        Menu fileMenu = new Menu("File");
        menuBar.add(fileMenu);

        MenuItem newMenuItem = new MenuItem("New");
        newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        fileMenu.add(newMenuItem);

        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(TextEditor.this, "Open", FileDialog.LOAD);
                fileDialog.setVisible(true);
                String directory = fileDialog.getDirectory();
                String filename = fileDialog.getFile();
                if (filename != null) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(directory + filename));
                        String line;
                        StringBuilder content = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        reader.close();
                        textArea.setText(content.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        fileMenu.add(openMenuItem);

        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(TextEditor.this, "Save", FileDialog.SAVE);
                fileDialog.setVisible(true);
                String directory = fileDialog.getDirectory();
                String filename = fileDialog.getFile();
                if (filename != null) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(directory + filename));
                        writer.write(textArea.getText());
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        fileMenu.add(saveMenuItem);

        MenuItem saveAsMenuItem = new MenuItem("Save As");
        saveAsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(TextEditor.this, "Save As", FileDialog.SAVE);
                fileDialog.setVisible(true);
                String directory = fileDialog.getDirectory();
                String filename = fileDialog.getFile();
                if (filename != null) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(directory + filename));
                        writer.write(textArea.getText());
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        fileMenu.add(saveAsMenuItem);

        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        Menu editMenu = new Menu("Edit");
        menuBar.add(editMenu);

        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
            }
        });
        editMenu.add(deleteMenuItem);

        MenuItem cutMenuItem = new MenuItem("Cut");
        cutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection selection = new StringSelection(textArea.getSelectedText());
                clipboard.setContents(selection, null);
                textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
            }
        });
        editMenu.add(cutMenuItem);

        MenuItem copyMenuItem = new MenuItem("Copy");
        copyMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection selection = new StringSelection(textArea.getSelectedText());
                clipboard.setContents(selection, null);
            }
        });
        editMenu.add(copyMenuItem);

        MenuItem pasteMenuItem = new MenuItem("Paste");
        pasteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Transferable contents = clipboard.getContents(null);
                if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        String text = (String) contents.getTransferData(DataFlavor.stringFlavor);
                        int position = textArea.getCaretPosition();
                        textArea.insert(text, position);
                    } catch (UnsupportedFlavorException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        editMenu.add(pasteMenuItem);

        MenuItem selectAllMenuItem = new MenuItem("Select All");
        selectAllMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll();
            }
        });
        editMenu.add(selectAllMenuItem);
        
        MenuItem replaceMenuItem = new MenuItem("Replace");
        replaceMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchText = textArea.getSelectedText();
                if (searchText != null && searchText.length() > 0) {
                    String replaceText = JOptionPane.showInputDialog(TextEditor.this, "Replace with:");
                    if (replaceText != null) {
                        String content = textArea.getText();
                        String updatedContent = content.replace(searchText, replaceText);
                        textArea.setText(updatedContent);
                    }
                }
            }
        });
        editMenu.add(replaceMenuItem);

        setSize(800, 600);
        setVisible(true);
        
    }

    public static void main(String[] args) {
        new TextEditor();
    }
}
