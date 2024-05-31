import java.util.ArrayList;

public class BestFitStrategy implements FitStrategy {

    public MemoryBlock getPreferredBlock(ArrayList<MemoryBlock> freeBlocks, int size) {
        MemoryBlock preferredBlock = null;
        for (MemoryBlock memoryBlock : freeBlocks) {
            if (memoryBlock.getSize() >= size) {
                if ((preferredBlock == null) ||
                        (preferredBlock.getSize() > memoryBlock.getSize()) ||
                        (preferredBlock.getSize() == memoryBlock.getSize()
                                && preferredBlock.getStartAddress() > memoryBlock.getStartAddress())) {
                    preferredBlock = memoryBlock;
                }
            }
        }
        return preferredBlock;
    }
}
