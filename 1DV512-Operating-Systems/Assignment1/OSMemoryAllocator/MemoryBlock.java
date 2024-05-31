public class MemoryBlock {
    private Integer startAddress;
    private Integer id;
    private Integer size;

    public MemoryBlock(Integer id, int startAddress, int size) {
        setId(id);
        setStartAddress(startAddress);
        setSize(size);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setStartAddress(Integer startAddress) {
        this.startAddress = startAddress;
    }

    public Integer getStartAddress() {
        return startAddress;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }
}
