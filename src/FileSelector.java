import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public interface FileSelector
{
    public static String selectPpfFile()
    {
        File foundFile;
        String filePath;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("choosertitle");
        //
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF format (.pdf)", "pdf");
        //
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            foundFile = chooser.getSelectedFile();
            filePath = foundFile.getAbsolutePath();
            return filePath;
        } else {
            return null;
        }
    }
}