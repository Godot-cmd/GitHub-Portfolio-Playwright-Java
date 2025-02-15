package utils;

import base.BaseClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VersionUtil {

    public static String getFileVersion(String filePath) {
        String fileVersion = "Unknown";
        try {
            String command = "powershell.exe (Get-Item '" + filePath + "').VersionInfo.ProductVersion";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            fileVersion = reader.readLine().trim();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileVersion;
    }
}
