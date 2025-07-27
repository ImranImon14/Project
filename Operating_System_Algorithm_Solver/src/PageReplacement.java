import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PageReplacement {
    public static  void performFIFOPageReplacement(String string,String frame_no,DefaultTableModel tableModel) {
        try {
            String[] referenceString = string.trim().split(","); // string holds page reference string
            int frameCount = Integer.parseInt(frame_no.trim());  // frame_no holds number of frames

            Queue<Integer> fifoQueue = new LinkedList<>();
            StringBuilder calc = new StringBuilder();

            int pageFaults = 0;

            for (String s : referenceString) {
                int page = Integer.parseInt(s.trim());

                if (!fifoQueue.contains(page)) {
                    if (fifoQueue.size() == frameCount) {
                        fifoQueue.poll(); // Remove oldest page
                    }
                    fifoQueue.add(page);
                    pageFaults++;
                    calc.append(page).append(" (F), ");
                } else {
                    calc.append(page).append(" (H), ");
                }
            }

            // Clean up trailing comma
            if (calc.length() > 2) {
                calc.setLength(calc.length() - 2);
            }

            int totalPages = referenceString.length;
            double missRatio = (double) pageFaults / totalPages;
            double hitRatio = 1.0 - missRatio;

            // Display in table
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("FIFO Page Replacement");

            tableModel.addRow(new Object[]{"Calculation: " + calc.toString()});
            tableModel.addRow(new Object[]{"Number of Page Faults = " + pageFaults});
            tableModel.addRow(new Object[]{
                    "Miss Ratio = " + pageFaults + " / " + totalPages + " = " + String.format("%.2f", missRatio)
            });
            tableModel.addRow(new Object[]{
                    "Hit Ratio = 1 - " + String.format("%.2f", missRatio) + " = " + String.format("%.2f", hitRatio)
            });

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
    //LRU ===========================================
    public static void performLRUPageReplacement(String string,String frame_no,DefaultTableModel tableModel) {
        try {
            String[] referenceString = string.trim().split(","); // string holds page reference string
            int frameCount = Integer.parseInt(frame_no.trim());  // frame_no holds number of frames

            List<Integer> frames = new ArrayList<>();
            StringBuilder calc = new StringBuilder();
            int pageFaults = 0;

            for (String s : referenceString) {
                int page = Integer.parseInt(s.trim());

                if (!frames.contains(page)) {
                    if (frames.size() == frameCount) {
                        frames.remove(0); // Remove the least recently used (first) page
                    }
                    frames.add(page);
                    pageFaults++;
                    calc.append(page).append(" (F), ");
                } else {
                    // Move the page to the most recently used (end of list)
                    frames.remove((Integer) page);
                    frames.add(page);
                    calc.append(page).append(" (H), ");
                }
            }

            // Remove the last comma and space
            if (calc.length() > 2) {
                calc.setLength(calc.length() - 2);
            }

            int totalPages = referenceString.length;
            double missRatio = (double) pageFaults / totalPages;
            double hitRatio = 1.0 - missRatio;

            // Format to 2 decimal places and include the formulas
            String missFormula = pageFaults + "/" + totalPages + " = " + String.format("%.2f", missRatio);
            String hitFormula = "1 - " + String.format("%.2f", missRatio) + " = " + String.format("%.2f", hitRatio);

            // Display in table
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("LRU Page Replacement");

            tableModel.addRow(new Object[]{"Calculation: " + calc.toString()});
            tableModel.addRow(new Object[]{"Number of Page Faults = " + pageFaults});
            tableModel.addRow(new Object[]{"Miss Ratio = " + missFormula});
            tableModel.addRow(new Object[]{"Hit Ratio = " + hitFormula});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
    //OPTIMAL====================================================
    public static void performOptimalPageReplacement(String string,String frame_no,DefaultTableModel tableModel) {
        try {
            String[] referenceString = string.trim().split(","); // string holds page reference string
            int frameCount = Integer.parseInt(frame_no.trim());  // frame_no holds number of frames

            List<Integer> frames = new ArrayList<>();
            StringBuilder calc = new StringBuilder();
            int pageFaults = 0;

            for (int i = 0; i < referenceString.length; i++) {
                int page = Integer.parseInt(referenceString[i].trim());

                if (!frames.contains(page)) {
                    if (frames.size() == frameCount) {
                        int indexToReplace = -1;
                        int farthest = i + 1;

                        for (int j = 0; j < frames.size(); j++) {
                            int nextUse = -1;
                            for (int k = i + 1; k < referenceString.length; k++) {
                                if (Integer.parseInt(referenceString[k].trim()) == frames.get(j)) {
                                    nextUse = k;
                                    break;
                                }
                            }

                            if (nextUse == -1) {
                                indexToReplace = j;
                                break;
                            } else if (nextUse > farthest) {
                                farthest = nextUse;
                                indexToReplace = j;
                            }
                        }

                        if (indexToReplace == -1) indexToReplace = 0; // fallback

                        frames.remove(indexToReplace);
                    }

                    frames.add(page);
                    pageFaults++;
                    calc.append(page).append(" (F), ");
                } else {
                    calc.append(page).append(" (H), ");
                }
            }

            // Remove the last comma and space
            if (calc.length() > 2) {
                calc.setLength(calc.length() - 2);
            }

            int totalPages = referenceString.length;
            double missRatio = (double) pageFaults / totalPages;
            double hitRatio = 1.0 - missRatio;

            // Format to 2 decimal places and include the formulas
            String missFormula = pageFaults + "/" + totalPages + " = " + String.format("%.2f", missRatio);
            String hitFormula = "1 - " + String.format("%.2f", missRatio) + " = " + String.format("%.2f", hitRatio);

            // Display in table
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Optimal Page Replacement");

            tableModel.addRow(new Object[]{"Calculation: " + calc.toString()});
            tableModel.addRow(new Object[]{"Number of Page Faults = " + pageFaults});
            tableModel.addRow(new Object[]{"Miss Ratio = " + missFormula});
            tableModel.addRow(new Object[]{"Hit Ratio = " + hitFormula});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
}
