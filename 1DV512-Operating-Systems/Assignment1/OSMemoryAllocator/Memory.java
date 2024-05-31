import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Memory {
    private Integer initSize;
    private ArrayList<MemoryBlock> allocatedBlocks = new ArrayList<>();
    private ArrayList<MemoryBlock> freeBlocks = new ArrayList<>();

    public Memory(Integer initSize) {
        this.initSize = initSize;
        initStartBlock();
    }

    public Integer getSize() {
        return initSize;
    }

    public ArrayList<MemoryBlock> getFreeBlocks() {
        return freeBlocks;
    }

    public ArrayList<MemoryBlock> getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public Boolean allocateBlock(Integer id, int size, FitStrategy fitStrategy) {
        MemoryBlock allocationBlock = fitStrategy.getPreferredBlock(freeBlocks, size);
        if (allocationBlock == null) {
            return false;
        }

        MemoryBlock newBlock = new MemoryBlock(id, allocationBlock.getStartAddress(), size);
        allocatedBlocks.add(newBlock);

        int remainingSize = allocationBlock.getSize() - newBlock.getSize();
        if (remainingSize == 0) {
            freeBlocks.remove(allocationBlock);
        } else {
            allocationBlock.setStartAddress(allocationBlock.getStartAddress() + size);
            allocationBlock.setSize(allocationBlock.getSize() - size);
        }

        sortBlocksByAddress(allocatedBlocks);
        return true;
    }

    public Boolean deallocateBlock(Integer blockId) {
        for (MemoryBlock memoryBlock : allocatedBlocks) {
            if (memoryBlock.getId() == blockId) {
                memoryBlock.setId(null);
                allocatedBlocks.remove(memoryBlock);
                freeBlocks.add(memoryBlock);
                sortBlocksByAddress(freeBlocks);
                for (int i = 0; i < freeBlocks.size() - 1; i++) {
                    MemoryBlock currBlock = freeBlocks.get(i);
                    MemoryBlock nextBlock = freeBlocks.get(i + 1);
                    if (currBlock.getStartAddress() + currBlock.getSize() == nextBlock.getStartAddress()) {
                        freeBlocks.add(i, new MemoryBlock(null, currBlock.getStartAddress(),
                                currBlock.getSize() + nextBlock.getSize()));
                        freeBlocks.remove(currBlock);
                        freeBlocks.remove(nextBlock);
                        i--;
                    }
                }
                sortBlocksByAddress(freeBlocks);
                return true;
            }
        }
        return false;
    }

    public void compact(FitStrategy fitStrategy) {
        sortBlocksByAddress(freeBlocks);
        sortBlocksByAddress(allocatedBlocks);
        int lastEndAddress = 0;
        for (MemoryBlock allocatedBlock : allocatedBlocks) {
            allocatedBlock.setStartAddress(lastEndAddress);
            lastEndAddress += allocatedBlock.getSize();
        }

        freeBlocks.clear();
        freeBlocks.add(new MemoryBlock(null, lastEndAddress, initSize - lastEndAddress));
    }

    private void sortBlocksByAddress(ArrayList<MemoryBlock> blocks) {
        Collections.sort(blocks, Comparator.comparingInt(MemoryBlock::getStartAddress));
    }

    private void sortBlocksById(ArrayList<MemoryBlock> blocks) {
        Collections.sort(blocks, Comparator.comparingInt(MemoryBlock::getId));
    }

    private double getFragmentation(List<MemoryBlock> freeBlocks, StringBuilder sb) {
        Integer largestFreeSize = getLargestFreeSize();
        Integer totalFreeSize = 0;
        for (MemoryBlock memoryBlock : freeBlocks) {
            totalFreeSize += memoryBlock.getSize();
        }
        Double fragmentation = (1 - ((largestFreeSize * 1.0) / totalFreeSize));
        return fragmentation;
    }

    public Integer getLargestFreeSize() {
        Integer largestFreeSize = 0;
        for (MemoryBlock memoryBlock : freeBlocks) {
            if (memoryBlock.getSize() > largestFreeSize) {
                largestFreeSize = memoryBlock.getSize();
            }
        }
        return largestFreeSize;
    }

    private void initStartBlock() {
        freeBlocks.add(new MemoryBlock(null, 0, initSize));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Allocated blocks\n");
        sortBlocksById(allocatedBlocks);
        for (MemoryBlock memoryBlock : allocatedBlocks) {
            sb.append(memoryBlock.getId() + ";" + memoryBlock.getStartAddress() + ";"
                    + (memoryBlock.getStartAddress() + memoryBlock.getSize() - 1) + "\n");
        }
        sb.append("Free blocks\n");
        for (MemoryBlock memoryBlock : freeBlocks) {
            sb.append(
                    memoryBlock.getStartAddress() + ";" + (memoryBlock.getStartAddress() + memoryBlock.getSize() - 1)
                            + "\n");
        }
        sb.append("Fragmentation\n");
        sb.append(String.format("%,.6f", getFragmentation(freeBlocks, sb)));
        return sb.toString();
    }
}
