package org.example.travelexpertdesktopapplication.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import org.tinylog.Logger;

import java.io.File;

public class StorageService {
    private static StorageService instance;

    private final BlobServiceClient blobServiceClient;
    private final BlobContainerClient containerClient;

    private StorageService(String connectionString, String containerName) {
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        containerClient = blobServiceClient.getBlobContainerClient(containerName);
    }

    public static StorageService getInstance() {
        if (instance == null) {
            String connectionString = System.getenv("AZURE_STORAGE_CONNECTION_STRING");
            String containerName = "package-pictures";
            instance = new StorageService(connectionString, containerName);
        }
        return instance;
    }

    public String uploadFile(String blobName, File file) {
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        blobClient.uploadFromFile(file.getAbsolutePath(), true);
        Logger.debug("Uploaded: " + blobName);
        return blobClient.getBlobUrl();
    }

}
