import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.util.Queue;
import java.util.Arrays;
import java.util.LinkedList;



public class ProcessScheduling {

    public static void performFCFS(String arrival, String burst, DefaultTableModel tableModel) {
        try {
            String[] arrivalStrings = arrival.trim().split(",");
            String[] burstStrings = burst.trim().split(",");

            if (arrivalStrings.length != burstStrings.length) {
                JOptionPane.showMessageDialog(null, "Arrival and burst time count must match.");
                return;
            }

            int n = arrivalStrings.length;
            int[] arrivalTimes = new int[n];
            int[] burstTimes = new int[n];

            for (int i = 0; i < n; i++) {
                arrivalTimes[i] = Integer.parseInt(arrivalStrings[i].trim());
                burstTimes[i] = Integer.parseInt(burstStrings[i].trim());
            }

            // Pair up arrival and burst, and sort by arrival
            int[][] processes = new int[n][3]; // [][0]=arrival, [][1]=burst, [][2]=original index
            for (int i = 0; i < n; i++) {
                processes[i][0] = arrivalTimes[i];
                processes[i][1] = burstTimes[i];
                processes[i][2] = i;
            }

            // Sort by arrival time
            java.util.Arrays.sort(processes, java.util.Comparator.comparingInt(a -> a[0]));

            int currentTime = 0;
            int[] completionTimes = new int[n];
            int[] turnaroundTimes = new int[n];
            int[] waitingTimes = new int[n];

            for (int i = 0; i < n; i++) {
                int arrivalTime = processes[i][0];
                int burstTime = processes[i][1];

                if (currentTime < arrivalTime) {
                    currentTime = arrivalTime;
                }

                currentTime += burstTime;
                completionTimes[i] = currentTime;
                turnaroundTimes[i] = currentTime - arrivalTime;
                waitingTimes[i] = turnaroundTimes[i] - burstTime;
            }

            int totalWT = 0, totalTAT = 0;
            for (int i = 0; i < n; i++) {
                totalWT += waitingTimes[i];
                totalTAT += turnaroundTimes[i];
            }

            double avgWT = (double) totalWT / n;
            double avgTAT = (double) totalTAT / n;

            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Process");
            tableModel.addColumn("Arrival Time");
            tableModel.addColumn("Burst Time");
            tableModel.addColumn("Completion Time");
            tableModel.addColumn("Turnaround Time");
            tableModel.addColumn("Waiting Time");

            for (int i = 0; i < n; i++) {
                int arrivalTime = processes[i][0];
                int burstTime = processes[i][1];
                int pid = processes[i][2];
                int tat = turnaroundTimes[i];
                int wt = waitingTimes[i];
                int ct = arrivalTime + tat;

                tableModel.addRow(new Object[]{ "P" + (pid + 1), arrivalTime, burstTime, ct, tat, wt });
            }

            tableModel.addRow(new Object[]{"Total", "-", "-", "-", totalTAT, totalWT});
            tableModel.addRow(new Object[]{"Average", "-", "-", "-", avgTAT, avgWT});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }


    //sjf================
    public static void performSJF(String arrival,String burst,DefaultTableModel tableModel) {
        try {
            String[] arrivalStrings = arrival.trim().split(",");
            String[] burstStrings = burst.trim().split(",");

            int n = arrivalStrings.length;
            int[] arrivalTimes = new int[n];
            int[] burstTimes = new int[n];
            int[] completionTimes = new int[n];
            int[] waitingTimes = new int[n];
            int[] turnaroundTimes = new int[n];
            boolean[] isCompleted = new boolean[n];

            for (int i = 0; i < n; i++) {
                arrivalTimes[i] = Integer.parseInt(arrivalStrings[i].trim());
                burstTimes[i] = Integer.parseInt(burstStrings[i].trim());
            }

            int currentTime = 0, completed = 0;
            while (completed < n) {
                int idx = -1;
                int minBurstTime = Integer.MAX_VALUE;

                for (int i = 0; i < n; i++) {
                    if (arrivalTimes[i] <= currentTime && !isCompleted[i]) {
                        if (burstTimes[i] < minBurstTime) {
                            minBurstTime = burstTimes[i];
                            idx = i;
                        }
                    }
                }

                if (idx != -1) {
                    currentTime += burstTimes[idx];
                    completionTimes[idx] = currentTime;
                    turnaroundTimes[idx] = completionTimes[idx] - arrivalTimes[idx];
                    waitingTimes[idx] = turnaroundTimes[idx] - burstTimes[idx];
                    isCompleted[idx] = true;
                    completed++;
                } else {
                    currentTime++;
                }
            }

            int TWaitingTime = 0, TTurnaroundTime = 0;
            double avgWaitingTime=0,avgTurnaroundTime=0;
            for (int i = 0; i < n; i++) {
                TWaitingTime += waitingTimes[i];
                TTurnaroundTime += turnaroundTimes[i];
            }
            avgWaitingTime= (double)TWaitingTime/ n;
            avgTurnaroundTime=(double)TTurnaroundTime / n;

            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            tableModel.addColumn("Process");
            tableModel.addColumn("Arrival Time");
            tableModel.addColumn("Burst Time");
            tableModel.addColumn("Completion Time");
            tableModel.addColumn("Turnaround Time");
            tableModel.addColumn("Waiting Time");

            for (int i = 0; i < n; i++) {
                tableModel.addRow(new Object[]{i + 1, arrivalTimes[i], burstTimes[i], completionTimes[i], turnaroundTimes[i], waitingTimes[i]});
            }
            tableModel.addRow(new Object[]{"Total", "-", "-", "-", TTurnaroundTime, TWaitingTime});
            tableModel.addRow(new Object[]{"Average", "-", "-", "-", avgTurnaroundTime, avgWaitingTime});

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your values.");
        }
    }
    //round robin=================

    public static void performRoundRobin(String arrivalText, String burstText, String quantumText, DefaultTableModel tableModel) {
        try {
            String[] arrivalStr = arrivalText.split(",");
            String[] burstStr = burstText.split(",");
            int quantum = Integer.parseInt(quantumText.trim());

            int n = arrivalStr.length;
            int[] arrival = new int[n];
            int[] burst = new int[n];
            int[] remaining = new int[n];
            int[] completion = new int[n];
            int[] waiting = new int[n];
            int[] turnaround = new int[n];
            boolean[] isCompleted = new boolean[n];
            boolean[] inQueue = new boolean[n];

            for (int i = 0; i < n; i++) {
                arrival[i] = Integer.parseInt(arrivalStr[i].trim());
                burst[i] = Integer.parseInt(burstStr[i].trim());
                remaining[i] = burst[i];
            }

            int time = 0;
            int completed = 0;
            Queue<Integer> queue = new LinkedList<>();

            // Add all processes that arrive at time 0 or earliest arrival
            int earliest = Arrays.stream(arrival).min().orElse(0);
            for (int i = 0; i < n; i++) {
                if (arrival[i] == earliest) {
                    queue.add(i);
                    inQueue[i] = true;
                }
            }
            time = earliest;

            while (completed < n) {
                if (queue.isEmpty()) {
                    // Find next arrival
                    int nextArrival = Integer.MAX_VALUE;
                    int nextIndex = -1;
                    for (int i = 0; i < n; i++) {
                        if (!isCompleted[i] && arrival[i] < nextArrival) {
                            nextArrival = arrival[i];
                            nextIndex = i;
                        }
                    }

                    time = nextArrival;
                    queue.add(nextIndex);
                    inQueue[nextIndex] = true;
                }

                int idx = queue.poll();

                if (remaining[idx] <= quantum) {
                    time += remaining[idx];
                    remaining[idx] = 0;
                    completion[idx] = time;
                    isCompleted[idx] = true;
                    completed++;
                } else {
                    time += quantum;
                    remaining[idx] -= quantum;
                }

                // Add all processes that have arrived till now and not in queue
                for (int i = 0; i < n; i++) {
                    if (!inQueue[i] && !isCompleted[i] && arrival[i] <= time) {
                        queue.add(i);
                        inQueue[i] = true;
                    }
                }

                if (!isCompleted[idx]) {
                    queue.add(idx);
                }
            }

            int totalTAT = 0;
            int totalWT = 0;
            for (int i = 0; i < n; i++) {
                turnaround[i] = completion[i] - arrival[i];
                waiting[i] = turnaround[i] - burst[i];
                totalTAT += turnaround[i];
                totalWT += waiting[i];
            }

            String[] columns = { "Process", "Arrival Time", "Burst Time", "Completion Time", "Turnaround Time", "Waiting Time" };
            tableModel.setDataVector(new Object[0][0], columns);

            for (int i = 0; i < n; i++) {
                tableModel.addRow(new Object[]{
                        "P" + (i + 1), arrival[i], burst[i], completion[i], turnaround[i], waiting[i]
                });
            }

            tableModel.addRow(new Object[]{"Total", "", "", "", totalTAT, totalWT});
            tableModel.addRow(new Object[]{"Average", "", "", "", (double) totalTAT / n, (double) totalWT / n});
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please ensure all values are correct.");
        }
    }

    //SJF PREEMpTIVE-----------------------------------------
    public static void performSJFPreemptive(String arrivalText,String burstText,DefaultTableModel tableModel) {
        try {
            String[] arrivalStr = arrivalText.split(",");
            String[] burstStr = burstText.split(",");

            int n = arrivalStr.length;
            int[] arrival = new int[n];
            int[] burst = new int[n];
            int[] remaining = new int[n];
            int[] completion = new int[n];
            int[] turnaround = new int[n];
            int[] waiting = new int[n];

            for (int i = 0; i < n; i++) {
                arrival[i] = Integer.parseInt(arrivalStr[i].trim());
                burst[i] = Integer.parseInt(burstStr[i].trim());
                remaining[i] = burst[i];
            }

            int time = 0, completed = 0;
            boolean[] isCompleted = new boolean[n];

            while (completed != n) {
                int idx = -1;
                int min = Integer.MAX_VALUE;

                for (int i = 0; i < n; i++) {
                    if (arrival[i] <= time && !isCompleted[i] && remaining[i] < min && remaining[i] > 0) {
                        min = remaining[i];
                        idx = i;
                    }
                }

                if (idx != -1) {
                    remaining[idx]--;
                    time++;

                    if (remaining[idx] == 0) {
                        completion[idx] = time;
                        isCompleted[idx] = true;
                        completed++;
                    }
                } else {
                    time++;
                }
            }

            int totalTAT = 0, totalWT = 0;

            for (int i = 0; i < n; i++) {
                turnaround[i] = completion[i] - arrival[i];
                waiting[i] = turnaround[i] - burst[i];
                totalTAT += turnaround[i];
                totalWT += waiting[i];
            }

            String[] columns = { "Process", "Arrival Time", "Burst Time", "Completion Time", "Turnaround Time", "Waiting Time" };
            tableModel.setDataVector(new Object[0][0], columns);

            for (int i = 0; i < n; i++) {
                tableModel.addRow(new Object[]{
                        "P" + (i + 1), arrival[i], burst[i], completion[i],
                        turnaround[i], waiting[i]
                });
            }

            tableModel.addRow(new Object[]{
                    "Total", "", "", "", totalTAT, totalWT
            });
            tableModel.addRow(new Object[]{
                    "Avg", "", "", "", (double) totalTAT / n, (double) totalWT / n
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please ensure all values are correct.");
        }
    }
    //Piority non==========================
    public static void performPriorityNonPreemptive(String arrivalText,String burstText,String priorityText,DefaultTableModel tableModel) {
        try {
            String[] arrivalStr = arrivalText.split(",");
            String[] burstStr = burstText.split(",");
            String[] priorityStr = priorityText.split(",");

            int n = arrivalStr.length;
            int[] arrival = new int[n];
            int[] burst = new int[n];
            int[] priority = new int[n];
            int[] completion = new int[n];
            int[] turnaround = new int[n];
            int[] waiting = new int[n];
            boolean[] isCompleted = new boolean[n];

            for (int i = 0; i < n; i++) {
                arrival[i] = Integer.parseInt(arrivalStr[i].trim());
                burst[i] = Integer.parseInt(burstStr[i].trim());
                priority[i] = Integer.parseInt(priorityStr[i].trim());
            }

            int time = 0, completed = 0;

            while (completed != n) {
                int idx = -1;
                int minPriority = Integer.MAX_VALUE;

                for (int i = 0; i < n; i++) {
                    if (arrival[i] <= time && !isCompleted[i]) {
                        if (priority[i] < minPriority) {
                            minPriority = priority[i];
                            idx = i;
                        } else if (priority[i] == minPriority) {
                            if (arrival[i] < arrival[idx]) {
                                idx = i;
                            }
                        }
                    }
                }

                if (idx != -1) {
                    time += burst[idx];
                    completion[idx] = time;
                    isCompleted[idx] = true;
                    completed++;
                } else {
                    time++;
                }
            }

            int totalTAT = 0, totalWT = 0;

            for (int i = 0; i < n; i++) {
                turnaround[i] = completion[i] - arrival[i];
                waiting[i] = turnaround[i] - burst[i];
                totalTAT += turnaround[i];
                totalWT += waiting[i];
            }

            String[] columns = { "Process", "Arrival", "Burst", "Priority", "Completion", "TAT", "Waiting" };
            tableModel.setDataVector(new Object[0][0], columns);

            for (int i = 0; i < n; i++) {
                tableModel.addRow(new Object[]{
                        "P" + (i + 1), arrival[i], burst[i], priority[i], completion[i],
                        turnaround[i], waiting[i]
                });
            }

            tableModel.addRow(new Object[]{
                    "Total", "", "", "", "", totalTAT, totalWT
            });
            tableModel.addRow(new Object[]{
                    "Avg", "", "", "", "", (double) totalTAT / n, (double) totalWT / n
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please ensure all values are correct.");
        }
    }
    //priority preemptive==============================================
    public static void performPriorityPreemptive(String arrivalText,String burstText,String priorityText,DefaultTableModel tableModel) {
        try {
            String[] arrivalStr = arrivalText.split(",");
            String[] burstStr = burstText.split(",");
            String[] priorityStr = priorityText.split(",");

            int n = arrivalStr.length;
            int[] arrival = new int[n];
            int[] burst = new int[n];
            int[] priority = new int[n];
            int[] remaining = new int[n];
            int[] completion = new int[n];
            int[] turnaround = new int[n];
            int[] waiting = new int[n];
            boolean[] isCompleted = new boolean[n];

            for (int i = 0; i < n; i++) {
                arrival[i] = Integer.parseInt(arrivalStr[i].trim());
                burst[i] = Integer.parseInt(burstStr[i].trim());
                priority[i] = Integer.parseInt(priorityStr[i].trim());
                remaining[i] = burst[i];
            }

            int time = 0, completed = 0;

            while (completed != n) {
                int idx = -1;
                int minPriority = Integer.MAX_VALUE;

                for (int i = 0; i < n; i++) {
                    if (arrival[i] <= time && remaining[i] > 0) {
                        if (priority[i] < minPriority) {
                            minPriority = priority[i];
                            idx = i;
                        } else if (priority[i] == minPriority && arrival[i] < arrival[idx]) {
                            idx = i;
                        }
                    }
                }

                if (idx != -1) {
                    remaining[idx]--;
                    time++;

                    if (remaining[idx] == 0) {
                        completion[idx] = time;
                        isCompleted[idx] = true;
                        completed++;
                    }
                } else {
                    time++;
                }
            }

            int totalTAT = 0, totalWT = 0;

            for (int i = 0; i < n; i++) {
                turnaround[i] = completion[i] - arrival[i];
                waiting[i] = turnaround[i] - burst[i];
                totalTAT += turnaround[i];
                totalWT += waiting[i];
            }

            String[] columns = {"Process", "Arrival", "Burst", "Priority", "Completion", "TAT", "Waiting"};
            tableModel.setDataVector(new Object[0][0], columns);

            for (int i = 0; i < n; i++) {
                tableModel.addRow(new Object[]{
                        "P" + (i + 1), arrival[i], burst[i], priority[i], completion[i],
                        turnaround[i], waiting[i]
                });
            }

            tableModel.addRow(new Object[]{
                    "Total", "", "", "", "", totalTAT, totalWT
            });
            tableModel.addRow(new Object[]{
                    //"Avg", "", "", "", "", (double) totalTAT / n, (double) totalWT / n
                    "Avg", "", "", "", "", String.format("%.1f", (double) totalTAT / n), String.format("%.1f", (double) totalWT / n)
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please ensure all values are correct.");
        }
    }



}
