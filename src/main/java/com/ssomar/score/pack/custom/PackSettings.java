package com.ssomar.score.pack.custom;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.logging.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Getter
public class PackSettings {

    private UUID uuid;

    private SPlugin sPlugin;

    @Setter
    private String filePath;

    private String customPromptMessage;

    private boolean force;

    private PackHttpInjector injector;

    private HostedPathType hostedPathType;

    private String hostedPath;

    private boolean deleteInitialFile = false;

    @Getter
    private byte[] hash;

    public PackSettings(SPlugin sPlugin, UUID uuid, String filePath, String customPromptMessage, boolean force, boolean deleteInitialFile) {
        this.sPlugin = sPlugin;
        this.uuid = uuid;
        this.filePath = filePath;
        this.customPromptMessage = customPromptMessage;
        this.force = force;
        this.injector = new PackHttpInjector(this);
        //netAddress.getLocalHost().getHostAddress()
        hostedPathType = null;
        hostedPath = null;
        this.deleteInitialFile = deleteInitialFile;
        this.hash = computeSha1(new File(filePath));
    }

    /**
     * Compute SHA-1 hash of a file for resource pack integrity verification.
     */
    public static byte[] computeSha1(File file) {
        if (file == null || !file.exists()) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }
            return digest.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convert a byte array hash to hex string.
     */
    public static String hashToHex(byte[] hash) {
        if (hash == null) return null;
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Recompute the hash from the current file path (e.g. after the file is copied to cache).
     */
    public void recomputeHash() {
        this.hash = computeSha1(new File(filePath));
    }

    public enum HostedPathType {
        EXTERNAL,
        LOCAL_IP,
        LOCALHOST
    }

    /**
     * Returns the cached hosted path without triggering HTTP connectivity checks.
     * Returns null if the path hasn't been resolved yet.
     */
    public String getCachedHostedPath() {
        return hostedPath;
    }

    public String getHostedPath() {
        if (hostedPath != null) return hostedPath;

        if (!GeneralConfig.getInstance().getSelfHostPackIp().isEmpty()) {
            String url = "http://" + GeneralConfig.getInstance().getSelfHostPackIp() + ":" + Bukkit.getServer().getPort() + "/score/" + getFileName();
            hostedPathType = HostedPathType.EXTERNAL;
            hostedPath = url;
            Utils.sendConsoleMsg("&7Pack hosted at: &e" + url + " (with self host IP)");
            logSelfHostingWarning();
            return url;
        }


        String url = "http://" + getExternalIP() + ":" + Bukkit.getServer().getPort() + "/score/" + getFileName();
        if ((hostedPathType == null || hostedPathType == HostedPathType.EXTERNAL) && isHttpURLReachable(url, 10000)) {
            hostedPathType = HostedPathType.EXTERNAL;
            hostedPath = url;
            Utils.sendConsoleMsg("&7Pack hosted at: &e" + url + " (with external IP)");
            return url;
        }
        if (GeneralConfig.getInstance().isSelfHostPackDebug()) Utils.sendConsoleMsg("&cCannot connect to &6" + url);

        try {
            url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + Bukkit.getServer().getPort() + "/score/" + getFileName();
            if ((hostedPathType == null || hostedPathType == HostedPathType.LOCAL_IP) && isHttpURLReachable(url, 10000)) {
                hostedPathType = HostedPathType.LOCAL_IP;
                hostedPath = url;
                Utils.sendConsoleMsg("&7Pack hosted at: &e" + url + " (with local IP)");
                logSelfHostingWarning();
                return url;
            }
        } catch (UnknownHostException e) {
        }
        if (GeneralConfig.getInstance().isSelfHostPackDebug()) Utils.sendConsoleMsg("&cCannot connect to &6" + url);

        try {
            url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + Bukkit.getServer().getPort() + "/score/" + getFileName();
            if ((hostedPathType == null || hostedPathType == HostedPathType.LOCAL_IP) && isHttpURLReachable(url, 10000)) {
                hostedPathType = HostedPathType.LOCAL_IP;
                hostedPath = url;
                Utils.sendConsoleMsg("&7Pack hosted at: &e" + url + " (with local IP)");
                logSelfHostingWarning();
                return url;
            }
        } catch (UnknownHostException e) {
        }
        if (GeneralConfig.getInstance().isSelfHostPackDebug()) Utils.sendConsoleMsg("&cCannot connect to &6" + url);

        url = "http://localhost:" + Bukkit.getServer().getPort() + "/score/" + getFileName();
        if ((hostedPathType == null || hostedPathType == HostedPathType.LOCALHOST) && isHttpURLReachable(url, 10000)) {
            hostedPathType = HostedPathType.LOCALHOST;
            hostedPath = url;
            Utils.sendConsoleMsg("&7Pack hosted at: &e" + url + " (with localhost)");
            return url;
        }
        if (GeneralConfig.getInstance().isSelfHostPackDebug()) Utils.sendConsoleMsg("&cCannot connect to &6" + url);

        return null;
    }

    /**
     * Check if an HTTP URL is reachable
     *
     * @param urlString The URL to check
     * @param timeout   Timeout in milliseconds
     * @return true if the URL is reachable, false otherwise
     */
    public static boolean isHttpURLReachable(String urlString, int timeout) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD"); // Use HEAD instead of GET for efficiency

            int responseCode = connection.getResponseCode();
            if (GeneralConfig.getInstance().isSelfHostPackDebug())
                Utils.sendConsoleMsg("&7Debug response output: &7" + responseCode);
            return (200 <= responseCode && responseCode <= 399); // Success codes

        } catch (IOException e) {
            if (GeneralConfig.getInstance().isSelfHostPackDebug())
                Utils.sendConsoleMsg("&7Debug &e" + urlString + " &7- &e" + e.getMessage());

            if (e.getMessage().contains("Unexpected end of file from server")) {
                return true;
            }
            return false;
        }
    }


    public String getFileName() {
        return getFile().getName().replaceAll(" ", "_").replaceAll(".zip", "");
    }

    public File getFile() {
        return new File(filePath);
    }

    public static String getMinecraftServerIP() {
        try {
            InetSocketAddress address = new InetSocketAddress(Bukkit.getServer().getIp(), Bukkit.getServer().getPort());

            return address.getAddress().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    public static String getExternalIP() {
        String externalIP = "null";
        try {
            // Connect to an external service that returns your IP
            URL url = new URL("https://api.ipify.org");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            // Read the response (this will be your public IP)
            externalIP = reader.readLine();
            reader.close();
            //System.out.println("External IP Address: " + externalIP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return externalIP;
    }

    /**
     * Logs a warning message about self-hosting compatibility issues
     */
    private void logSelfHostingWarning() {
        Utils.sendConsoleMsg("&6WARNING: &7Self-hosting may not work on all hosting providers due to firewall/NAT restrictions.");
        Utils.sendConsoleMsg("&7If the resource pack fails to load for players, disable self-hosting by setting &e'selfHostPack: false' &7in your config.yml");
        Utils.sendConsoleMsg("&7and configure &e'texturesPackUrl' &7with an external hosting URL (e.g., Dropbox, GitHub Pages).");
    }
}
