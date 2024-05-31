import java.util.ArrayList;

public interface FitStrategy {
    MemoryBlock getPreferredBlock(ArrayList<MemoryBlock> freeBlocks, int size);
}
