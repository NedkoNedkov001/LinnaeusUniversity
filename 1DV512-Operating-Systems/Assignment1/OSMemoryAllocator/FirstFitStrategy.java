import java.util.ArrayList;

public class FirstFitStrategy implements FitStrategy {
    public MemoryBlock getPreferredBlock(ArrayList<MemoryBlock> freeBlocks, int size) {
        MemoryBlock preferredBlock = null;
        for (MemoryBlock memoryBlock : freeBlocks) {
          if (memoryBlock.getSize() >= size) {
            if ((preferredBlock == null) ||
                (preferredBlock.getStartAddress() > memoryBlock.getStartAddress())) {
              preferredBlock = memoryBlock;
            }
          }
        }
        return preferredBlock;
    }
}
