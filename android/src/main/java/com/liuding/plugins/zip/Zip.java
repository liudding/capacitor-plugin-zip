package com.liuding.plugins.zip;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.net.URL;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


@NativePlugin
public class Zip extends Plugin {

    @PluginMethod
    public void zip(PluginCall call) {
        call.unimplemented();
    }

    @PluginMethod
    public void unzip(PluginCall call) {
        String src = call.getString("src");
        String dest = call.getString("dest");

        File fromFile = getFile(src);
        File destDir = getFile(dest);

        ZipInputStream zis;

        try {
            zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(fromFile)));
        } catch (Exception e) {
            call.reject("error");

            return;
        }

        try {

            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(destDir, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            }

            call.resolve();
        } catch (Exception e) {
            call.reject("error");
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (Exception e) {

                }

            }
        }
    }


    private static File getFile(String urlOrPath) {
        try {
            return new File(new URL(urlOrPath).toURI());
        } catch (Exception e) {
            return new File(urlOrPath);
        }
    }


}
