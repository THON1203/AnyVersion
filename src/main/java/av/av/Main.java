package av.av;

import net.fabricmc.api.ModInitializer;

public class AnyVersion implements ModInitializer {
    @Override
    public void onInitialize() {
        // Your mod initialization code goes here
        System.out.println("Initializing AnyVersion...");
        
        // Call the initialization method of your mod
        initAnyVersion();
        
        System.out.println("AnyVersion initialized successfully!");
    }
    
    private void initAnyVersion() {
        // Your mod initialization logic goes here
        // This method will be called during mod initialization
        
        // Example: Register your mod's features, blocks, items, etc.
        // ...
    }
}
