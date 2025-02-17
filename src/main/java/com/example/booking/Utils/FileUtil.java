package com.example.booking.Utils;


public class FileUtil {

    private FileUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String[] formatFileSize(long size) {
        if (size <= 0) return new String[]{"0", "B"};
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        String formattedSize = String.format("%.1f", size / Math.pow(1024, digitGroups));
        return new String[]{formattedSize, units[digitGroups]};
    }
}