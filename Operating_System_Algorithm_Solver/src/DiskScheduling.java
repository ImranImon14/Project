import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;


public class DiskScheduling {
    public static void performFCFSD(String headText,String queueText,DefaultTableModel tableModel) {
        try {
            int headPosition = Integer.parseInt(headText.trim());
            String[] queueStrings = queueText.trim().split(",");
            int[] qu = new int[queueStrings.length];

            for (int i = 0; i < queueStrings.length; i++) {
                qu[i] = Integer.parseInt(queueStrings[i].trim());
            }

            int totalSeek = 0;
            int current = headPosition;
            StringBuilder calc = new StringBuilder();

            for (int i = 0; i < qu.length; i++) {
                calc.append("|").append(current).append("-").append(qu[i]).append("|");
                if (i < qu.length - 1) calc.append("+");
                totalSeek += Math.abs(current - qu[i]);
                current = qu[i];
            }

            // Final formatted result
            String result = "Total Seek Distance: " + calc + " = " + totalSeek;

            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Seek Time Calculation");
            tableModel.addRow(new Object[]{result});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
    //SSTF______________________________________________________________
    public static void performSSTF(String headpointer,String queue,DefaultTableModel tableModel) {
        try {
            int headPosition = Integer.parseInt(headpointer.trim());
            String[] queueStrings = queue.trim().split(",");
            int[] requests = new int[queueStrings.length];

            for (int i = 0; i < queueStrings.length; i++) {
                requests[i] = Integer.parseInt(queueStrings[i].trim());
            }

            boolean[] visited = new boolean[requests.length];
            int totalSeek = 0;
            int current = headPosition;
            StringBuilder calc = new StringBuilder();

            for (int i = 0; i < requests.length; i++) {
                int minDistance = Integer.MAX_VALUE;
                int idx = -1;
                for (int j = 0; j < requests.length; j++) {
                    if (!visited[j]) {
                        int distance = Math.abs(current - requests[j]);
                        if (distance < minDistance) {
                            minDistance = distance;
                            idx = j;
                        }
                    }
                }

                visited[idx] = true;
                calc.append("|").append(current).append("-").append(requests[idx]).append("|");
                if (i < requests.length - 1) calc.append("+");
                totalSeek += Math.abs(current - requests[idx]);
                current = requests[idx];
            }

            String result = "Total Seek Distance: " + calc + " = " + totalSeek;

            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Seek Time Calculation");
            tableModel.addRow(new Object[]{result});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
    //SCAN+===================================================
    public static  void performSCAN(String headpointer,String track,String queue,DefaultTableModel tableModel) {
        try {
            int headPosition = Integer.parseInt(headpointer.trim());
            int totalTracks = Integer.parseInt(track.trim());
            String[] queueStrings = queue.trim().split(",");
            int[] requests = new int[queueStrings.length + 1];

            for (int i = 0; i < queueStrings.length; i++) {
                requests[i] = Integer.parseInt(queueStrings[i].trim());
            }

            requests[queueStrings.length] = headPosition;
            Arrays.sort(requests);

            int headIndex = 0;
            for (int i = 0; i < requests.length; i++) {
                if (requests[i] == headPosition) {
                    headIndex = i;
                    break;
                }
            }

            int totalSeek = 0;
            int current = headPosition;
            StringBuilder calc = new StringBuilder();

            // Move right and service all requests
            for (int i = headIndex + 1; i < requests.length; i++) {
                calc.append("|").append(current).append("-").append(requests[i]).append("|");
                totalSeek += Math.abs(current - requests[i]);
                current = requests[i];
                if (i < requests.length - 1 || headIndex > 0) calc.append("+");
            }

            // Move to the end of the track if not already there
            if (current != totalTracks - 1) {
                calc.append("|").append(current).append("-").append(totalTracks - 1).append("|");
                totalSeek += Math.abs(current - (totalTracks - 1));
                current = totalTracks - 1;
                if (headIndex > 0) calc.append("+");
            }

            // Move left and service all remaining requests
            for (int i = headIndex - 1; i >= 0; i--) {
                calc.append("|").append(current).append("-").append(requests[i]).append("|");
                totalSeek += Math.abs(current - requests[i]);
                current = requests[i];
                if (i > 0) calc.append("+");
            }

            String result = "Total Seek Distance: " + calc + " = " + totalSeek;

            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Seek Time Calculation");
            tableModel.addRow(new Object[]{result});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
    //CSCAN===============================================
    public static  void performCSCAN(String headpointer,String track,String queue,DefaultTableModel tableModel) {
        try {
            int headPosition = Integer.parseInt(headpointer.trim());
            int totalTracks = Integer.parseInt(track.trim()); // Number of tracks, e.g., 256
            String[] queueStrings = queue.trim().split(",");
            int[] requests = new int[queueStrings.length + 1];

            for (int i = 0; i < queueStrings.length; i++) {
                requests[i] = Integer.parseInt(queueStrings[i].trim());
            }

            requests[queueStrings.length] = headPosition; // Include head in the list
            Arrays.sort(requests);

            int headIndex = 0;
            for (int i = 0; i < requests.length; i++) {
                if (requests[i] == headPosition) {
                    headIndex = i;
                    break;
                }
            }

            int totalSeek = 0;
            int current = headPosition;
            StringBuilder calc = new StringBuilder();

            // Move right (towards max track)
            for (int i = headIndex + 1; i < requests.length; i++) {
                calc.append("|").append(current).append("-").append(requests[i]).append("|+");
                totalSeek += Math.abs(current - requests[i]);
                current = requests[i];
            }

            // Move to end (if needed)
            if (current != totalTracks - 1) {
                calc.append("|").append(current).append("-").append(totalTracks - 1).append("|+");
                totalSeek += Math.abs(current - (totalTracks - 1));
                current = totalTracks - 1;
            }

            // Move to beginning
            calc.append("|").append(current).append("-0|+");
            totalSeek += current;
            current = 0;

            // Move right again through requests before head
            for (int i = 0; i < headIndex; i++) {
                calc.append("|").append(current).append("-").append(requests[i]).append("|");
                if (i < headIndex - 1) calc.append("+");
                totalSeek += Math.abs(current - requests[i]);
                current = requests[i];
            }

            // Trim trailing '+'
            if (calc.charAt(calc.length() - 1) == '+') {
                calc.setLength(calc.length() - 1);
            }

            String result = "Total Seek Distance: " + calc + " = " + totalSeek;

            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Seek Time Calculation");
            tableModel.addRow(new Object[]{result});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
    //LOOOK===========================================================
    public static  void performLOOK(String headpointer,String track,String queue,DefaultTableModel tableModel) {
        try {
            int headPosition = Integer.parseInt(headpointer.trim());
            //int totalTracks = Integer.parseInt(track.getText().trim());
            String[] queueStrings = queue.trim().split(",");
            int[] requests = new int[queueStrings.length + 1];

            for (int i = 0; i < queueStrings.length; i++) {
                requests[i] = Integer.parseInt(queueStrings[i].trim());
            }

            requests[queueStrings.length] = headPosition;
            Arrays.sort(requests);

            int headIndex = 0;
            for (int i = 0; i < requests.length; i++) {
                if (requests[i] == headPosition) {
                    headIndex = i;
                    break;
                }
            }

            int totalSeek = 0;
            int current = headPosition;
            StringBuilder calc = new StringBuilder();

            // Move right and service all requests
            for (int i = headIndex + 1; i < requests.length; i++) {
                calc.append("|").append(current).append("-").append(requests[i]).append("|");
                if (i < requests.length - 1) calc.append("+");
                totalSeek += Math.abs(current - requests[i]);
                current = requests[i];
            }

            // Then go left and service remaining
            for (int i = headIndex - 1; i >= 0; i--) {
                calc.append("+|").append(current).append("-").append(requests[i]).append("|");
                totalSeek += Math.abs(current - requests[i]);
                current = requests[i];
            }

            String result = "Total Seek Distance: " + calc + " = " + totalSeek;

            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Seek Time Calculation");
            tableModel.addRow(new Object[]{result});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
    //C_LOOK=======================================
    public static void performCLOOK(String headpointer, String track, String queue, DefaultTableModel tableModel) {
        try {
            int headPosition = Integer.parseInt(headpointer.trim());
            String[] queueStrings = queue.trim().split(",");
            int[] requests = new int[queueStrings.length + 1];

            for (int i = 0; i < queueStrings.length; i++) {
                requests[i] = Integer.parseInt(queueStrings[i].trim());
            }

            requests[queueStrings.length] = headPosition; // include head
            Arrays.sort(requests);

            int headIndex = 0;
            for (int i = 0; i < requests.length; i++) {
                if (requests[i] == headPosition) {
                    headIndex = i;
                    break;
                }
            }

            int totalSeek = 0;
            int current = headPosition;
            StringBuilder calc = new StringBuilder();

            // Move right (higher than head)
            for (int i = headIndex + 1; i < requests.length; i++) {
                calc.append("|").append(current).append("-").append(requests[i]).append("|+");
                totalSeek += Math.abs(current - requests[i]);
                current = requests[i];
            }

            // Jump to the lowest request (simulate circular movement)
            if (headIndex > 0) {
                int jumpTo = requests[0];
                calc.append("|").append(current).append("-").append(jumpTo).append("|+");
                totalSeek += Math.abs(current - jumpTo);
                current = jumpTo;
            }

            // Move right again through remaining lower values
            for (int i = 0; i < headIndex; i++) {
                calc.append("|").append(current).append("-").append(requests[i]).append("|");
                if (i < headIndex - 1) calc.append("+");
                totalSeek += Math.abs(current - requests[i]);
                current = requests[i];
            }

            // Remove last '+' if exists
            if (calc.length() > 0 && calc.charAt(calc.length() - 1) == '+') {
                calc.setLength(calc.length() - 1);
            }

            String result = "Total Seek Distance: " + calc + " = " + totalSeek;

            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Seek Time Calculation");
            tableModel.addRow(new Object[]{result});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }

}
