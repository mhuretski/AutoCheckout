package Export;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ArchiveOrders {

    public ArchiveOrders(OrderListing orderListing, OrderWriter orderWriter) {
        pushToArchive(orderListing, orderWriter);
        deleteUnarchivedFiles(orderListing, orderWriter);
    }

    private void pushToArchive(OrderListing orderListing, OrderWriter orderWriter) {

        String zipFile = zipFileName(orderListing, orderWriter);
        try (FileOutputStream fileOutput = new FileOutputStream(zipFile)) {
            byte[] buffer = new byte[1024];
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

    private String zipFileName(OrderListing orderListing, OrderWriter orderWriter) {
        int amountOfOrders = orderWriter.getListOfFiles().size();
        String zipName = orderListing.getOutputFolder() + String.valueOf(amountOfOrders);
        if (amountOfOrders != 1)
            zipName += " orderXMLs.zip";
        else zipName += " orderXML.zip";
        return zipName;
    }

    private void deleteUnarchivedFiles(OrderListing orderListing, OrderWriter orderWriter) {
        for (String fileName : orderWriter.getListOfFiles()) {
            File srcFile = new File(orderListing.getOutputFolder() + fileName);
            if (!srcFile.delete())
                System.out.println(fileName + " is not found.");
        }
    }

}
