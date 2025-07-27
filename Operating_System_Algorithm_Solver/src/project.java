import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

public class project{
    public static void main(String[] args) {
        new Schedule();
    }
}
class Schedule extends JFrame{
    JLabel l,l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14;
    JComboBox<String> MainComboBox,SubComboBox;
    JTextField arrival,burst,quantam,priority,headpointer,queue,frame_no,string,blockSize,processSize,track;
    JTable output;
    DefaultTableModel tableModel;
    JButton button;
    Container c;

    Schedule(){
        super("Operating System Algorithm Solver");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200,600);
        setLocationRelativeTo(null);
        setLayout(null);

        c=getContentPane();
        c.setBackground(new Color(255,239,213)); //peach color
        //Input Label
        l = new JLabel("Input");
        l.setBounds(10,10,200,50);
        Font f = new Font("Monaco",Font.BOLD,20);
        l.setFont(f);
        add(l);

        //Main ComboBox
        l1 = new JLabel("Select Category");
        l1.setBounds(10,60,200,50);
        add(l1);

        String[] items ={"Select","Process Scheduling","Disk Scheduling","Page Replacement","Memory Allocation"};
        MainComboBox = new JComboBox<>(items);
        MainComboBox.setBounds(10,100,200,30);
        add(MainComboBox);

        //Sub ComboBox
        l2 = new JLabel("Select Algorithm");
        l2.setBounds(10,140,200,50);
        add(l2);

        String[] choose ={"Select a category first"};
        SubComboBox = new JComboBox<>(choose);
        SubComboBox.setBounds(10,180,200,30);
        add(SubComboBox);

        //Arrival time level and field
        l3 = new JLabel("Arrival Time (comma separated)");
        l3.setBounds(10,220,200,50);
        add(l3);

        arrival = new JTextField();
        arrival.setBounds(10,260,200,40);
        arrival.setFont(new Font("Arial", Font.PLAIN, 13)); // You can change 18 to any size

        add(arrival);

        //Burst time level and field
        l4 = new JLabel("Burst Time (comma separated)");
        l4.setBounds(10,300,200,50);
        add(l4);

        burst = new JTextField();
        burst.setBounds(10,340,200,40);
        burst.setFont(new Font("Arial", Font.PLAIN, 13));
        add(burst);

        //quantam time label and field
        l5 = new JLabel("Quantam Time");
        l5.setBounds(10,380,200,50);
        add(l5);

        quantam = new JTextField();
        quantam.setBounds(10,420,200,40);
        quantam.setFont(new Font("Arial", Font.PLAIN, 13));
        add(quantam);

        //head pointer label and field
        l6 = new JLabel("Head Pointer");
        l6.setBounds(10,220,200,50);
        add(l6);

        headpointer = new JTextField();
        headpointer.setBounds(10,260,200,40);
        headpointer.setFont(new Font("Arial", Font.PLAIN, 13));
        add(headpointer);

        //Queue Label and field
        l7 = new JLabel("Queue (comma separated)");
        l7.setBounds(10,300,200,50);
        add(l7);

        queue = new JTextField();
        queue.setBounds(10,340,200,40);
        queue.setFont(new Font("Arial", Font.PLAIN, 13));
        add(queue);

        l14= new JLabel("No of track");
        l14.setBounds(10,380,200,50);
        add(l14);

        track=new JTextField();
        track.setBounds(10,420,200,40);
        track.setFont(new Font("Arial", Font.PLAIN, 13));
        add(track);

        //priority label and field
        l9 = new JLabel("Priority List");
        l9.setBounds(10,380,200,50);
        add(l9);

        priority = new JTextField();
        priority.setBounds(10,420,200,40);
        priority.setFont(new Font("Arial", Font.PLAIN, 13));
        add(priority);
        //for paging
        l10 = new JLabel("Number of Frames");
        l10.setBounds(10,220,200,50);
        add(l10);

        frame_no = new JTextField();
        frame_no.setBounds(10,260,200,40);
        frame_no.setFont(new Font("Arial", Font.PLAIN, 13));;
        add(frame_no);

        //string level and field
        l11 = new JLabel("String (comma separated)");
        l11.setBounds(10,300,200,50);
        add(l11);

        string = new JTextField();
        string.setBounds(10,340,200,40);
        string.setFont(new Font("Arial", Font.PLAIN, 13));
        add(string);

        //memory allocation
        l12 = new JLabel("Block Size (comma separated)");
        l12.setBounds(10,220,200,50);
        add(l12);

        blockSize = new JTextField();
        blockSize.setBounds(10,260,200,40);
        blockSize.setFont(new Font("Arial", Font.PLAIN, 13));
        add(blockSize);

        //processs level and field
        l13 = new JLabel("Process Size (comma separated)");
        l13.setBounds(10,300,200,50);
        add(l13);

        processSize = new JTextField();
        processSize.setBounds(10,340,200,40);
        processSize.setFont(new Font("Arial", Font.PLAIN, 13));
        add(processSize);



        //Solve button
        button = new JButton("Solve");
        button.setBounds(10,480,100,50);
        button.setBackground(Color.red);
        button.setForeground(Color.white);
        Font fo = new Font("Monaco",Font.BOLD,20);
        button.setFont(fo);
        // button.setBackground(new Color(33, 37, 41)); // blue
        // button.setForeground(Color.WHITE); // Clean white text

        add(button);

        //Output Label
        l8 = new JLabel("Output");
        l8.setBounds(700,10,200,50);
        l8.setFont(f);
        add(l8);

        //Table for output
        tableModel = new DefaultTableModel(); //init with no column
        output = new JTable(tableModel);
        output.setRowHeight(30);
        output.setGridColor(Color.BLACK);
        output.setShowGrid(true);

        //for table scrollpane
        JScrollPane scroll = new JScrollPane(output);
        scroll.setBounds(300,70,850,450);
        add(scroll);

        hideAllInputFileds();

        MainComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String selectItems = (String) MainComboBox.getSelectedItem();
                SubComboBox.removeAllItems();
                if("Disk Scheduling".equals(selectItems)){
                    SubComboBox.addItem("FCFS (disc)");  //its name can be changed
                    SubComboBox.addItem("SSTF");
                    SubComboBox.addItem("SCAN");
                    SubComboBox.addItem("C-SCAN");
                    SubComboBox.addItem("LOOK");
                    SubComboBox.addItem("C-LOOK");

                }
                else if("Process Scheduling".equals(selectItems)){
                    SubComboBox.addItem("FCFS");
                    SubComboBox.addItem("SJF (non_preemptive)");
                    SubComboBox.addItem("Round Robin");
                    SubComboBox.addItem("SJF (preemptive)");
                    SubComboBox.addItem("Priority (preemptive)");
                    SubComboBox.addItem("Priority (non_preemptive)");

                }
                else if("Deadlock Avoidance".equals(selectItems)){
                    SubComboBox.addItem("Bankers Algorithm");
                }
                else if("Page Replacement".equals(selectItems)){
                    SubComboBox.addItem("FIFO (Page)");
                    SubComboBox.addItem("LRU");
                    SubComboBox.addItem("Optimal");
                }
                else if("Memory Allocation".equals(selectItems)){
                    SubComboBox.addItem("First Fit");
                    SubComboBox.addItem("Best Fit");
                    SubComboBox.addItem("Worst Fit");

                }

                else{
                    SubComboBox.addItem("Select a category first");
                }

            }
        });

        //Action Listener for SubComboBox
        SubComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String selectAlgorithm = (String) SubComboBox.getSelectedItem();

                if("FCFS".equals(selectAlgorithm) || "SJF (preemptive)".equals(selectAlgorithm) || "SJF (non_preemptive)".equals(selectAlgorithm)){
                    showProcessFiled();
                }
                else if("Round Robin".equals(selectAlgorithm)){
                    showRRField();

                }
                else if("Priority (preemptive)".equals(selectAlgorithm) || "Priority (non_preemptive)".equals(selectAlgorithm)){
                    showPriorityField();
                }
                else if("FCFS (disc)".equals(selectAlgorithm) || "SSTF".equals(selectAlgorithm)){
                    showDiskField();
                }
                else if("LOOK".equals(selectAlgorithm)|| "C-LOOK".equals(selectAlgorithm) || "SCAN".equals(selectAlgorithm) || "C-SCAN".equals(selectAlgorithm)){
                    showDiskField();
                }

                else if("FIFO (Page)".equals(selectAlgorithm) || "LRU".equals(selectAlgorithm) ||"Optimal".equals(selectAlgorithm)){
                    showPageField();
                }
                else if ("First Fit".equals(selectAlgorithm) || "Worst Fit".equals(selectAlgorithm) || "Best Fit".equals(selectAlgorithm)){
                    showMemoryField();
                }

                else{
                    hideAllInputFileds();
                }
            }
        });

        //Solve Button action
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String selectItems = (String) MainComboBox.getSelectedItem();
                String selectAlgorithm = (String) SubComboBox.getSelectedItem();
                //edited
                if ("Select".equals(selectItems) || "Select a category first".equals(selectAlgorithm) || "Select".equals(selectAlgorithm)) {
                    JOptionPane.showMessageDialog(null, "Please select a category and an algorithm.");
                    // return;
                }
                //edited

                else if ("Disk Scheduling".equals(selectItems) && "FCFS (disc)".equals(selectAlgorithm)){
                    DiskScheduling.performFCFSD(headpointer.getText(), queue.getText(), tableModel);

                }
                else if ("Disk Scheduling".equals(selectItems) && "SSTF".equals(selectAlgorithm)){
                    DiskScheduling.performSSTF(headpointer.getText(), queue.getText(), tableModel);
                }
                else if ("Disk Scheduling".equals(selectItems) && "SCAN".equals(selectAlgorithm)){
                    DiskScheduling.performSCAN(headpointer.getText(),track.getText(), queue.getText(), tableModel);
                }
                else if ("Disk Scheduling".equals(selectItems) && "C-SCAN".equals(selectAlgorithm)){
                    DiskScheduling.performCSCAN(headpointer.getText(),track.getText(), queue.getText(), tableModel);
                }
                else if ("Disk Scheduling".equals(selectItems) && "LOOK".equals(selectAlgorithm)){
                    DiskScheduling.performLOOK(headpointer.getText(),track.getText(), queue.getText(), tableModel);
                }
                else if ("Disk Scheduling".equals(selectItems) && "C-LOOK".equals(selectAlgorithm)){
                    DiskScheduling.performCLOOK(headpointer.getText(),track.getText(), queue.getText(), tableModel);
                }

                else if ("Process Scheduling".equals(selectItems) && "FCFS".equals(selectAlgorithm)){
                    ProcessScheduling.performFCFS(arrival.getText(), burst.getText(), tableModel);
                }
                else if ("Process Scheduling".equals(selectItems) && "Round Robin".equals(selectAlgorithm)) {
                    ProcessScheduling.performRoundRobin(arrival.getText(), burst.getText(), quantam.getText(), tableModel);
                }

                else if ("Process Scheduling".equals(selectItems) && "SJF (non_preemptive)".equals(selectAlgorithm)){
                    ProcessScheduling.performSJF(arrival.getText(), burst.getText(), tableModel);
                }

                else if ("Process Scheduling".equals(selectItems) && "SJF (preemptive)".equals(selectAlgorithm)){
                    ProcessScheduling.performSJFPreemptive(arrival.getText(), burst.getText(), tableModel);
                }

                else if ("Process Scheduling".equals(selectItems) && "Priority (non_preemptive)".equals(selectAlgorithm)){
                    ProcessScheduling.performPriorityNonPreemptive(arrival.getText(), burst.getText(), priority.getText(), tableModel);
                }
                else if ("Process Scheduling".equals(selectItems) && "Priority (preemptive)".equals(selectAlgorithm)){
                    ProcessScheduling.performPriorityPreemptive(arrival.getText(), burst.getText(), priority.getText(), tableModel);
                }
                else if ("Page Replacement".equals(selectItems) && "FIFO (Page)".equals(selectAlgorithm)){
                    PageReplacement.performFIFOPageReplacement(string.getText(),frame_no.getText(),tableModel);
                }
                else if ("Page Replacement".equals(selectItems) && "LRU".equals(selectAlgorithm)){
                    PageReplacement.performLRUPageReplacement(string.getText(),frame_no.getText(),tableModel);
                }
                else if ("Page Replacement".equals(selectItems) && "Optimal".equals(selectAlgorithm)){
                    PageReplacement.performOptimalPageReplacement(string.getText(),frame_no.getText(),tableModel);
                }

                else if ("Memory Allocation".equals(selectItems) && "First Fit".equals(selectAlgorithm)) {
                    MemoryManagement.performFirstFit(blockSize.getText(),processSize.getText(),tableModel);
                }
                else if ("Memory Allocation".equals(selectItems) && "Best Fit".equals(selectAlgorithm)) {
                    MemoryManagement.performBestFit(blockSize.getText(),processSize.getText(),tableModel);
                }
                else if ("Memory Allocation".equals(selectItems) && "Worst Fit".equals(selectAlgorithm)) {
                    MemoryManagement.performWorstFit(blockSize.getText(),processSize.getText(),tableModel);
                }

                else{
                    JOptionPane.showMessageDialog(null,"Algorithm not implemented yet!");

                }
            }
        });
    }

    public void showDiskField(){
        l6.setVisible(true);
        headpointer.setVisible(true);
        l7.setVisible(true);
        queue.setVisible(true);
        l3.setVisible(false);
        arrival.setVisible(false);
        l4.setVisible(false);
        burst.setVisible(false);
        l5.setVisible(false);
        quantam.setVisible(false);
        l9.setVisible(false);
        priority.setVisible(false);
        l10.setVisible(false);
        frame_no.setVisible(false);
        l11.setVisible(false);
        string.setVisible(false);
        //  l14.setVisible(false);
        //  track.setVisible(false);
        l14.setVisible(true);
        track.setVisible(true);

    }
    // private void showTrackField(){
    //  showDiskField();
    //  l14.setVisible(true);
    //   track.setVisible(true);
    //  }

    //method show process schediling fildes
    public void showProcessFiled(){
        l3.setVisible(true);
        arrival.setVisible(true);
        l4.setVisible(true);
        burst.setVisible(true);
        l5.setVisible(false);
        quantam.setVisible(false);
        l6.setVisible(false);
        headpointer.setVisible(false);
        l7.setVisible(false);
        queue.setVisible(false);
        l9.setVisible(false);
        priority.setVisible(false);
        l10.setVisible(false);
        frame_no.setVisible(false);
        l11.setVisible(false);
        string.setVisible(false);
        l14.setVisible(false);
        track.setVisible(false);




    }
    //show RR fields
    public void showRRField(){
        showProcessFiled();
        l5.setVisible(true);
        quantam.setVisible(true);
    }
    //show Priority filed
    public void showPriorityField(){
        showProcessFiled();
        l9.setVisible(true);
        priority.setVisible(true);
    }
    //show Page field
    public void showPageField(){

        l10.setVisible(true);
        frame_no.setVisible(true);
        l11.setVisible(true);
        string.setVisible(true);
        l6.setVisible(false);
        headpointer.setVisible(false);
        l7.setVisible(false);
        queue.setVisible(false);
        l3.setVisible(false);
        arrival.setVisible(false);
        l4.setVisible(false);
        burst.setVisible(false);
        l5.setVisible(false);
        quantam.setVisible(false);
        l9.setVisible(false);
        priority.setVisible(false);
        l14.setVisible(false);
        track.setVisible(false);
    }
    //
    public void showMemoryField(){
        l12.setVisible(true);
        blockSize.setVisible(true);
        l13.setVisible(true);
        processSize.setVisible(true);

        l6.setVisible(false);
        headpointer.setVisible(false);
        l7.setVisible(false);
        queue.setVisible(false);
        l3.setVisible(false);
        arrival.setVisible(false);
        l4.setVisible(false);
        burst.setVisible(false);
        l5.setVisible(false);
        quantam.setVisible(false);
        l9.setVisible(false);
        priority.setVisible(false);
        l10.setVisible(false);
        frame_no.setVisible(false);
        l11.setVisible(false);
        string.setVisible(false);
        l14.setVisible(false);
        track.setVisible(false);

    }

    //method hide all input fields
    public void hideAllInputFileds(){
        l6.setVisible(false);
        headpointer.setVisible(false);
        l7.setVisible(false);
        queue.setVisible(false);
        l3.setVisible(false);
        arrival.setVisible(false);
        l4.setVisible(false);
        burst.setVisible(false);
        l5.setVisible(false);
        quantam.setVisible(false);
        l9.setVisible(false);
        priority.setVisible(false);
        l10.setVisible(false);
        frame_no.setVisible(false);
        l11.setVisible(false);
        string.setVisible(false);
        l12.setVisible(false);
        blockSize.setVisible(false);
        l13.setVisible(false);
        processSize.setVisible(false);
        l14.setVisible(false);
        track.setVisible(false);
    }





    //FCFS implementation

    // SJF Implementation


    //perform round robin


    //SJF      Prremptivr


    //Priority     Non preemptive

    //priiority   premmptive

    //FIFO implementation


    //SSTF    disk scheduling

    //Scan disk schdeuling




    //CScan disk scheduling


    //LOOK disk schdeduling

    //C-Look schedulinh


    //------------------------------------------------------------------------------------
    //page replacement FIFO


    //LRU PAGE REPLACEMENT

//Optimal Page replacement algortihm

//--------------------------------MEMORY MANAGEMENT---------------------------------------------
//First fit
//BestFIT

//WorstFit


}


//-------------------------------------------------I Quit-------------------------------------------------------




