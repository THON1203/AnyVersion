package av.av;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.ModMetadataParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class AnyVersionMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Get the list of all installed mods
        List<ModContainer> mods = FabricLoader.getInstance().getAllMods();

        // Iterate through each installed mod
        for (ModContainer mod : mods) {
            // Get the mod's file path
            File modFile = mod.getRootPath().toFile();

            // Load and apply the mod
            loadAndApplyMod(modFile);
        }
    }

    private void loadAndApplyMod(File modFile) {
        // Load the mod metadata from the fabric.mod.json file
        ModMetadata modMetadata = loadModMetadata(modFile);

        if (modMetadata != null) {
            // Check if the mod is compatible with the current version
            if (isCompatible(modMetadata)) {
                // Apply the mod to the game
                applyMod(modMetadata);
            } else {
                System.out.println("Mod " + modMetadata.getName() + " is not compatible with the current version.");
            }
        }
    }

    private ModMetadata loadModMetadata(File modFile) {
        // Parse the mod metadata from the fabric.mod.json file
        ModMetadataParser parser = new ModMetadataParser();
        ModMetadata modMetadata = parser.parseMetadata(modFile);

        if (modMetadata != null) {
            System.out.println("Loaded mod: " + modMetadata.getName() + " (" + modMetadata.getVersion().getFriendlyString() + ")");
        } else {
            System.out.println("Failed to load mod metadata for file: " + modFile.getAbsolutePath());
        }

        return modMetadata;
    }

    private boolean isCompatible(ModMetadata modMetadata) {
        // Here you would implement the version compatibility logic based on your specific requirements
        // For example, you can compare the mod's minimum and maximum supported versions with the current game version

        // Get the current game version
        String gameVersion = FabricLoader.getInstance().getGameVersion();

        // Parse the mod's supported versions
        String modMinimumVersion = modMetadata.getCustomValue("mod_minimum_version").getAsString();
        String modMaximumVersion = modMetadata.getCustomValue("mod_maximum_version").getAsString();

        // Compare the mod's supported versions with the current game version
        return VersionDeserializer.deserialize(modMinimumVersion).compareTo(gameVersion) <= 0 &&
                VersionDeserializer.deserialize(modMaximumVersion).compareTo(gameVersion) >= 0;
    }

    private void applyMod(ModMetadata modMetadata) {
        // Here you would implement the code to apply the mod to the game based on the mod metadata
        // You can access various information from the mod metadata, such as its name, version, authors, and dependencies
        // You would need to handle the actual loading and applying logic specific to your mod and game

        System.out.println("Applying mod: " + modMetadata.getName() + " (" + modMetadata.getVersion().getFriendlyString() + ")");
        
        // Example: Load the mod's classes, register hooks, modify game behavior, etc.
        // ...

        // Modify the mod's fabric.mod.json file
        modifyFabricModJson(modMetadata);
    }

    private void modifyFabricModJson(ModMetadata modMetadata) {
        File modFile = modMetadata.getOriginFile().toFile();

        // Create a temporary copy of the mod file
        File tempModFile = new File(modFile.getParent(), "temp_" + modFile.getName());

        try {
            // Open the mod JAR file
            try (JarFile jarFile = new JarFile(modFile);
                 JarOutputStream jarOutputStream = new JarOutputStream(FileUtils.openOutputStream(tempModFile))) {

                // Iterate through the entries in the mod JAR file
                for (JarEntry entry : jarFile.entries()) {
                    String entryName = entry.getName();

                    // Look for the fabric.mod.json file
                    if (entryName.equals("fabric.mod.json")) {
                        // Read the content of the fabric.mod.json file
                        byte[] entryBytes = FileUtils.toByteArray(jarFile.getInputStream(entry));
                        String jsonContent = new String(entryBytes, StandardCharsets.UTF_8);

                        // Replace the values of "fabric", "fabricloader", and "minecraft" fields with "*"
                        String modifiedJsonContent = jsonContent.replace("\"fabric\": \"here\"", "\"fabric\": \"*\"")
                                .replace("\"fabricloader\": \"here\"", "\"fabricloader\": \"*\"")
                                .replace("\"minecraft\": \"here\"", "\"minecraft\": \"*\"");

                        // Create a new entry with the modified content
                        JarEntry modifiedEntry = new JarEntry(entryName);
                        jarOutputStream.putNextEntry(modifiedEntry);
                        jarOutputStream.write(modifiedJsonContent.getBytes(StandardCharsets.UTF_8));
                        jarOutputStream.closeEntry();
                    } else {
                        // Copy the entry to the new JAR file as is
                        jarOutputStream.putNextEntry(entry);
                        FileUtils.copyInputStreamToFile(jarFile.getInputStream(entry), new File(tempModFile.getParent(), entryName));
                        jarOutputStream.closeEntry();
                    }
                }
            }

            // Replace the original mod file with the modified temp file
            FileUtils.deleteQuietly(modFile);
            FileUtils.moveFile(tempModFile, modFile);

            System.out.println("Modified fabric.mod.json file for mod: " + modMetadata.getName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Delete the temporary file if it exists
            FileUtils.deleteQuietly(tempModFile);
        }
    }
}
