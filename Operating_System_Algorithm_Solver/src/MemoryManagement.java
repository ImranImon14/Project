import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MemoryManagement {
    public static  void performFirstFit(String blockSize,String processSize,DefaultTableModel tableModel) {
        try {
            String[] blockSizeStrings = blockSize.trim().split(",");
            String[] processSizeStrings = processSize.trim().split(",");

            int numBlocks = blockSizeStrings.length;
            int numProcesses = processSizeStrings.length;

            int[] blockSizes = new int[numBlocks];
            boolean[] blockUsed = new boolean[numBlocks];
            int[] processSizes = new int[numProcesses];
            int[] allocation = new int[numProcesses];
            String[] fragmentation = new String[numProcesses];

            for (int i = 0; i < numBlocks; i++) {
                blockSizes[i] = Integer.parseInt(blockSizeStrings[i].trim());
            }
            for (int i = 0; i < numProcesses; i++) {
                processSizes[i] = Integer.parseInt(processSizeStrings[i].trim());
                allocation[i] = -1;
                fragmentation[i] = "";
            }

            int totalInternalFrag = 0;

            for (int i = 0; i < numProcesses; i++) {
                for (int j = 0; j < numBlocks; j++) {
                    if (!blockUsed[j] && blockSizes[j] >= processSizes[i]) {
                        allocation[i] = j;
                        blockUsed[j] = true;

                        int frag = blockSizes[j] - processSizes[i];
                        totalInternalFrag += frag;
                        fragmentation[i] = String.valueOf(frag);
                        break;
                    }
                }
            }

            // Set up the table
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Process No.");
            tableModel.addColumn("Process Size");
            tableModel.addColumn("Block Size");
            tableModel.addColumn("Internal Fragmentation");

            for (int i = 0; i < numProcesses; i++) {
                if (allocation[i] != -1) {
                    tableModel.addRow(new Object[]{
                            i + 1,
                            processSizes[i],
                            blockSizes[allocation[i]],
                            fragmentation[i]
                    });
                } else {
                    tableModel.addRow(new Object[]{
                            i + 1,
                            processSizes[i],
                            "Not Allocated",
                            "N/A"
                    });
                }
            }

            // Add unused blocks as individual rows with "(unused)"
            for (int i = 0; i < numBlocks; i++) {
                if (!blockUsed[i]) {
                    totalInternalFrag += blockSizes[i];
                    tableModel.addRow(new Object[]{
                            "", "", blockSizes[i] + " (unused)", blockSizes[i]
                    });
                }
            }

            // Add total internal fragmentation row
            tableModel.addRow(new Object[]{
                    "Total", "", "", totalInternalFrag
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
    //BEST FIT+===================================================
    public static  void performBestFit(String blockSize,String processSize,DefaultTableModel tableModel) {
        try {
            String[] blockSizeStrings = blockSize.trim().split(",");
            String[] processSizeStrings = processSize.trim().split(",");

            int numBlocks = blockSizeStrings.length;
            int numProcesses = processSizeStrings.length;

            int[] blockSizes = new int[numBlocks];
            boolean[] blockUsed = new boolean[numBlocks];
            int[] processSizes = new int[numProcesses];
            int[] allocation = new int[numProcesses];
            int[] internalFrag = new int[numProcesses];

            for (int i = 0; i < numBlocks; i++) {
                blockSizes[i] = Integer.parseInt(blockSizeStrings[i].trim());
            }
            for (int i = 0; i < numProcesses; i++) {
                processSizes[i] = Integer.parseInt(processSizeStrings[i].trim());
                allocation[i] = -1;
            }

            int totalInternalFrag = 0;

            // Best Fit Allocation
            for (int i = 0; i < numProcesses; i++) {
                int bestIdx = -1;
                for (int j = 0; j < numBlocks; j++) {
                    if (!blockUsed[j] && blockSizes[j] >= processSizes[i]) {
                        if (bestIdx == -1 || blockSizes[j] < blockSizes[bestIdx]) {
                            bestIdx = j;
                        }
                    }
                }

                if (bestIdx != -1) {
                    allocation[i] = bestIdx;
                    blockUsed[bestIdx] = true;
                    internalFrag[i] = blockSizes[bestIdx] - processSizes[i];
                    totalInternalFrag += internalFrag[i];
                }
            }

            // Prepare table
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Process No.");
            tableModel.addColumn("Process Size");
            tableModel.addColumn("Block Size");
            tableModel.addColumn("Internal Fragmentation");

            for (int i = 0; i < numProcesses; i++) {
                if (allocation[i] != -1) {
                    tableModel.addRow(new Object[]{
                            i + 1,
                            processSizes[i],
                            blockSizes[allocation[i]],
                            internalFrag[i]
                    });
                } else {
                    tableModel.addRow(new Object[]{
                            i + 1,
                            processSizes[i],
                            "Not Allocated",
                            "N/A"
                    });
                }
            }

            // Add unused blocks one row at a time
            for (int i = 0; i < numBlocks; i++) {
                if (!blockUsed[i]) {
                    totalInternalFrag += blockSizes[i];
                    tableModel.addRow(new Object[]{
                            "",
                            "",
                            blockSizes[i] + " (unused)",
                            blockSizes[i]
                    });
                }
            }

            // Final total row
            tableModel.addRow(new Object[]{
                    "Total",
                    "",
                    "",
                    totalInternalFrag
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
    //Worstfit================================================
    public static  void performWorstFit(String blockSize,String processSize,DefaultTableModel tableModel) {
        try {
            String[] blockSizeStrings = blockSize.trim().split(",");
            String[] processSizeStrings = processSize.trim().split(",");

            int numBlocks = blockSizeStrings.length;
            int numProcesses = processSizeStrings.length;

            int[] blockSizes = new int[numBlocks];
            boolean[] blockUsed = new boolean[numBlocks];
            int[] processSizes = new int[numProcesses];
            int[] allocation = new int[numProcesses];
            int[] internalFrag = new int[numProcesses];

            for (int i = 0; i < numBlocks; i++) {
                blockSizes[i] = Integer.parseInt(blockSizeStrings[i].trim());
            }
            for (int i = 0; i < numProcesses; i++) {
                processSizes[i] = Integer.parseInt(processSizeStrings[i].trim());
                allocation[i] = -1;
            }

            int totalInternalFrag = 0;

            // Worst Fit Allocation
            for (int i = 0; i < numProcesses; i++) {
                int worstIdx = -1;
                for (int j = 0; j < numBlocks; j++) {
                    if (!blockUsed[j] && blockSizes[j] >= processSizes[i]) {
                        if (worstIdx == -1 || blockSizes[j] > blockSizes[worstIdx]) {
                            worstIdx = j;
                        }
                    }
                }

                if (worstIdx != -1) {
                    allocation[i] = worstIdx;
                    blockUsed[worstIdx] = true;
                    internalFrag[i] = blockSizes[worstIdx] - processSizes[i];
                    totalInternalFrag += internalFrag[i];
                }
            }

            // Prepare table
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Process No.");
            tableModel.addColumn("Process Size");
            tableModel.addColumn("Block Size");
            tableModel.addColumn("Internal Fragmentation");

            for (int i = 0; i < numProcesses; i++) {
                if (allocation[i] != -1) {
                    tableModel.addRow(new Object[]{
                            i + 1,
                            processSizes[i],
                            blockSizes[allocation[i]],
                            internalFrag[i]
                    });
                } else {
                    tableModel.addRow(new Object[]{
                            i + 1,
                            processSizes[i],
                            "Not Allocated",
                            "N/A"
                    });
                }
            }

            // Add unused blocks one row at a time
            for (int i = 0; i < numBlocks; i++) {
                if (!blockUsed[i]) {
                    totalInternalFrag += blockSizes[i];
                    tableModel.addRow(new Object[]{
                            "",
                            "",
                            blockSizes[i] + " (unused)",
                            blockSizes[i]
                    });
                }
            }

            // Final total row
            tableModel.addRow(new Object[]{
                    "Total",
                    "",
                    "",
                    totalInternalFrag
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }



}
