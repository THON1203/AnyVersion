import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ModifyFabricModJson {

    public static void main(String[] args) {
        // Path to the JAR file containing the fabric.mod.json file
        Path jarPath = Path.of("path/to/your/jar/file.jar");

        // Read the fabric.mod.json file
        String fabricModJson = readFabricModJson(jarPath);

        // Modify the fabric.mod.json contents
        String modifiedFabricModJson = modifyFabricModJson(fabricModJson);

        // Write the modified fabric.mod.json back to the JAR file
        writeFabricModJson(jarPath, modifiedFabricModJson);
    }

    private static String readFabricModJson(Path jarPath) {
        try (var fileSystem = FileSystems.newFileSystem(jarPath, null)) {
            Path fabricModJsonPath = fileSystem.getPath("fabric.mod.json");
            return Files.readString(fabricModJsonPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String modifyFabricModJson(String fabricModJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(fabricModJson).getAsJsonObject();

        // Modify the necessary fields
        jsonObject.addProperty("fabric", "*");
        jsonObject.addProperty("fabricloader", "*");
        jsonObject.addProperty("minecraft", "*");

        return gson.toJson(jsonObject);
    }

    private static void writeFabricModJson(Path jarPath, String modifiedFabricModJson) {
        try (var fileSystem = FileSystems.newFileSystem(jarPath, null)) {
            Path fabricModJsonPath = fileSystem.getPath("fabric.mod.json");
            Files.writeString(fabricModJsonPath, modifiedFabricModJson, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ModifyFabricModJson {

    public static void main(String[] args) {
        // Path to the JAR file containing the fabric.mod.json file
        Path jarPath = Path.of("path/to/your/jar/file.jar");

        // Read the fabric.mod.json file
        String fabricModJson = readFabricModJson(jarPath);

        // Modify the fabric.mod.json contents
        String modifiedFabricModJson = modifyFabricModJson(fabricModJson);

        // Write the modified fabric.mod.json back to the JAR file
        writeFabricModJson(jarPath, modifiedFabricModJson);
    }

    private static String readFabricModJson(Path jarPath) {
        try (var fileSystem = FileSystems.newFileSystem(jarPath, null)) {
            Path fabricModJsonPath = fileSystem.getPath("fabric.mod.json");
            return Files.readString(fabricModJsonPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String modifyFabricModJson(String fabricModJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(fabricModJson).getAsJsonObject();

        // Modify the necessary fields
        jsonObject.addProperty("fabric", "*");
        jsonObject.addProperty("fabricloader", "*");
        jsonObject.addProperty("minecraft", "*");

        return gson.toJson(jsonObject);
    }

    private static void writeFabricModJson(Path jarPath, String modifiedFabricModJson) {
        try (var fileSystem = FileSystems.newFileSystem(jarPath, null)) {
            Path fabricModJsonPath = fileSystem.getPath("fabric.mod.json");
            Files.writeString(fabricModJsonPath, modifiedFabricModJson, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
