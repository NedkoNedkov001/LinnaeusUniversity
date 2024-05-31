The application consists of several classes:

App.java - The application starts running in this class. Its only purpose is to call the Manager, which is the main functionality class and give it a file to read from.
Manager.java - The main controller class. It reads operations from a file and then acts upon them. It is also responsible for keeping track of errors and writing the output files.
Memory.java - The main model class, where the logic is actually executed to control the data. It keeps track of free and allocated blocks in a memory. 
MemoryBlock.java - A single memory block class, holding information about a block (Id, start address, size).
FitStrategy.java - A fit strategy interface.
FirstFitStrategy.java, BestFitStrategy.java and WorstFitStrategy.java - Classes implementing the FitStrategy.java interface. Their purpose is to receive a list of free blocks and a size to allocate and return a suitable free block.
