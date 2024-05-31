import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {
    private String fileName;
    private Memory memory;
    private FitStrategy fitStrategy;
    private String fitStrategyName;
    private ArrayList<String> errors;
    private ArrayList<Integer> failIds;
    private Integer numIntFiles;

    public void run(String fileName) {
        this.fileName = "resources/" + fileName;
        removePreviousFiles();
        ArrayList<String> ops = readOps(this.fileName);
        runFitStrategy(new FirstFitStrategy(), "First fit", ops);
        runFitStrategy(new BestFitStrategy(), "Best fit", ops);
        runFitStrategy(new WorstFitStrategy(), "Worst fit", ops);
    }

    private ArrayList<String> readOps(String path) {
        ArrayList<String> ops = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String op = fileReader.nextLine();
                ops.add(op);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("No such file.");
        }
        return ops;
    }

    private void removePreviousFiles() {
        File folder = new File("resources/");
        for (File file : folder.listFiles()) {
            String outName = fileName.replace(".in", ".out");
            String path = file.getPath().replace("\\", "/");
            if (path.contains(outName)) {
                file.delete();
            }
        }
    }

    private void runFitStrategy(FitStrategy fitStrategy, String fitStrategyName, ArrayList<String> ops) {
        this.fitStrategy = fitStrategy;
        this.fitStrategyName = fitStrategyName;
        this.errors = new ArrayList<>();
        this.failIds = new ArrayList<>();
        this.numIntFiles = 0;
        memory = new Memory((Integer.parseInt(ops.get(0))));
        for (int i = 0; i < ops.size(); i++) {
            doOperation(ops.get(i), i);
        }
        output(false);
    }

    private void doOperation(String op, Integer opNumber) {
        String[] opData = op.split(";");
        switch (opData[0]) {
            case "A":
                allocate(Integer.parseInt(opData[1]), Integer.parseInt(opData[2]), opNumber);
                break;
            case "D":
                deallocate(Integer.parseInt(opData[1]), opNumber);
                break;
            case "C":
                memory.compact(fitStrategy);
                break;
            case "O":
                output(true);
                break;
            default:
                break;
        }
    }

    private void allocate(Integer blockId, Integer blockSize, Integer opNumber) {
        if (!memory.allocateBlock(blockId, blockSize, fitStrategy)) {
            errors.add("A;" + opNumber + ";" + memory.getLargestFreeSize());
            failIds.add(blockId);
        }
    }

    private void deallocate(Integer blockId, Integer opNumber) {
        if (!memory.deallocateBlock(blockId)) {
            if (failIds.contains(blockId)) {
                errors.add("D;" + opNumber + ";" + "1");
            } else {
                errors.add("D;" + opNumber + ";" + "0");
            }
        }
    }

    private String output(Boolean isIntermediate) {
        String name = fileName.replace(".in", ".out");
        if (isIntermediate) {
            name = name + ++numIntFiles;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(fitStrategyName + "\n");
        sb.append(memory + "\n");
        sb.append("Errors\n");
        if (errors.size() == 0) {
            sb.append("None\n");
        } else {
            for (String error : errors) {
                sb.append(error + "\n");
            }
        }
        sb.append("\n");
        writeToFile(name, sb.toString());
        return sb.toString();
    }

    private void writeToFile(String name, String content) {
        try {
            FileWriter myWriter = new FileWriter(name, true);
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
        }
    }
}
