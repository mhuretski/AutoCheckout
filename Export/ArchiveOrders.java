package Export;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ArchiveOrders{

    public ArchiveOrders(OrderListing orderListing, OrderWriter orderWriter){
        pushToArchive(orderListing, orderWriter);
    }

    private void pushToArchive(OrderListing orderListing, OrderWriter orderWriter) {

        String zipFile = orderListing.getOutputFolder() + "orderXMLs.zip";
        try {
            byte[] buffer = new byte[1024];
            FileOutputStream fileOutput = new FileOutputStream(zipFile);
            addFileToArchive(buffer, fileOutput, orderListing, orderWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFileToArchive(byte[] buffer, FileOutputStream fileOutput, OrderListing orderListing, OrderWriter orderWriter) throws IOException {
        ZipOutputStream zipOutput = new ZipOutputStream(fileOutput);

        for (String fileName : orderWriter.getListOfFiles()) {
            File srcFile = new File(orderListing.getOutputFolder() + fileName);
            FileInputStream fileInput = new FileInputStream(srcFile);
            zipOutput.putNextEntry(new ZipEntry(fileName));

            int length;
            while ((length = fileInput.read(buffer)) > 0) {
                zipOutput.write(buffer, 0, length);
            }
            zipOutput.closeEntry();
            fileInput.close();
        }
        zipOutput.close();
    }

}
