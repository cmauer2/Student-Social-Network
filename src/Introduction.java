/**
 * Introduction
 * Driver code that performs the file-reading, user-interactions, operations, and prints.  
 * @author Carter Mauer, Colton, Genesis, Bella
 */
import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Introduction {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Reading in Student Network Data...");
        Scanner studentFile = new Scanner(new File("students.csv"));
        Scanner networkFile = new Scanner(new File("network.csv"));

        //skip header lines
        studentFile.nextLine();
        networkFile.nextLine();

        SocialNetwork studentNetwork = new SocialNetwork();

        // Loop through the student and network files to extract data
        while(studentFile.hasNext() && networkFile.hasNext()) {
        	// Split the student data by commas
            String[] studentData = studentFile.nextLine().split(","); 
            String[] studentNetworkData = networkFile.nextLine().split(",");
            // Extract student ID, wait time, and name
            int student = Integer.parseInt(studentData[0]);
            int studentWait = Integer.parseInt(studentData[3]);
            String studentName = studentData[1] + " " + studentData[2];
            // Add the student's name to the network
            studentNetwork.addStudentName(studentName);
            // Extract network connections for the student (up to 5 targets)
            String[] studentTargets = Arrays.copyOfRange(studentNetworkData, 3, 8);
            // for each string in studentTargets, add an edge to the social network (with wait time)
            for(String target : studentTargets){
                studentNetwork.addEdge(student, Integer.parseInt(target), studentWait);
            }
        }

        studentFile.close();
        networkFile.close();
        Scanner scan = new Scanner(System.in);
        int choice = 0;
        // Loop for the main menu, offering operations until the user quits (choice 5)
        while(choice != 5) {
            System.out.println("\nStudent Network Graph User Operations:\n[1]: Print network list for a given student.\n[2]: Find the quickest path from a given student to another given student.\n[3]: Remove a given student from another given student's network.\n[4]: Update wait days for a given student by a given value.\n[5]: Quit.");
          //try catch: ensures input is an integer, otherwise displays message informing user and tries again to prompt user for valid input
            try {
                System.out.println("\nEnter the integer corresponding to your desired operation: ");
                choice = scan.nextInt();
                scan.nextLine();
                
                //check for valid input range
                if(choice < 1 || choice > 5){ 
                	//if input invalid, inform user and re-prompt them to enter a valid choice
                    System.out.println("Invalid input. Please enter an integer that corresponds to an operation (1-5).");
                } else {
                	//if choice is 5, exit, quit program
                    if(choice == 5) System.out.println("Exiting...");
                    //switch-case structure for remaining options
                    switch(choice) {
                    	// 1: Print network list for a given student
                        case 1 -> {
                            int userInput = 0;
                            do { 
                            	//try catch: ensures input is an integer, otherwise displays message informing user and tries again to prompt user for valid input
                                try {
                                	System.out.println("\nEnter the enrollment # of a student to print their network: ");
                                    userInput = scan.nextInt();
                                    //if given enrollment number is not in network, inform user, and restart do/while loop
                                    if (!studentNetwork.isInNetwork(userInput)) {
                                        System.out.println("Student " + userInput + " is not in the network.");
                                    //else if given enrollment number is in network, print their network
                                    } else {
                                    	studentNetwork.printNetwork(userInput);
                                    }
                                //inform user of invalid non-integer input
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Enrollment # should be input as an integer.");
                                    scan.next();
                                }
                            //do/while loop is restarted if in-network check is failed    
                            } while (!(studentNetwork.isInNetwork(userInput)));
                        }
                        //2: Find the quickest path from a given student to another given student
                        case 2 -> {
                            int userInputSource = 0;
                            int userInputTarget = 0;
                            do { 
                            	//try catch: ensures inputs are both integers, otherwise displays message informing user and tries again to prompt user for valid input
                                try {
                                	System.out.println("\nEnter the enrollment # of a student to be the source: ");
                                    userInputSource = scan.nextInt();
                                    System.out.println("Enter the enrollment # of a student to be the target: ");
                                    userInputTarget = scan.nextInt();
                                    //if source student is not in network, inform user
                                    if (!studentNetwork.isInNetwork(userInputSource)) {
                                        System.out.println("Student " + userInputSource + " is not in the network.");
                                    //else if target student in not in network, inform user
                                    } else if(!studentNetwork.isInNetwork(userInputTarget)) {
                                        System.out.println("Student " + userInputTarget + " is not in the network.");
                                    //else if both target and source student are in network, call findShortestPath method to get shortest path
                                    } else {
                                        studentNetwork.findShortestPath(userInputSource, userInputTarget);
                                    }
                                //inform user of invalid, non-integer input
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Enrollment # for both students should be input as an integer.");
                                    scan.next();
                                }
                            //if BOTH the source student and the target student are not in network, do/while loop restarts and user is re-prompted for new enrollment numbers. 
                            //However, if only one is not in network, nothing happens and the user is re-prompted to choose between the five main actions
                            } while (!(studentNetwork.isInNetwork(userInputSource)) && !(studentNetwork.isInNetwork(userInputTarget)));
                        }
                        //3: Remove a given student from another given student's network
                        case 3 -> {
                            int userInputSource = 0;
                            int userInputTarget = 0;
                            do { 
                            	//try catch: ensures inputs are both integers, otherwise displays message informing user and tries again to prompt user for valid input
                                try {
                                	System.out.println("\nEnter the enrollment # of the student whose network you wish to remove from: ");
                                    userInputSource = scan.nextInt();
                                    System.out.println("Enter the enrollment # of the student to be removed: ");
                                    userInputTarget = scan.nextInt();
                                    //if source student is not in network,inform user
                                    if (!studentNetwork.isInNetwork(userInputSource)) {
                                        System.out.println("Student " + userInputSource + " is not in the network.");
                                    //else if target student is not in network, inform user
                                    } else if(!studentNetwork.isInNetwork(userInputTarget)) {
                                        System.out.println("Student " + userInputTarget + " is not in the network.");
                                    //else if both students are in network, call removeEdge method to remove target student from source student's network
                                    } else {
                                        System.out.println("Removing student " + userInputTarget + " from student " + userInputSource +"'s network.");
                                        studentNetwork.removeEdge(userInputSource, userInputTarget);
                                    }
                                //inform user of invalid, non-integer input
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Enrollment # for both students should be input as an integer.");
                                    scan.next();
                                }
                                //if BOTH the source student and the target student are not in network, do/while loop restarts and user is re-prompted for new enrollment numbers. 
                                //However, if only one is not in network, nothing happens and the user is re-prompted to choose between the five main actions
                            } while (!(studentNetwork.isInNetwork(userInputSource)) && !(studentNetwork.isInNetwork(userInputTarget)));
                        }
                        //4: Update wait days for a given student by a given value
                        case 4 -> {
                            int userInputStudent = 0;
                            int userInputUpdate = 0;
                            do { 
                            	//try catch: ensures inputs are integers, otherwise displays message informing user and tries again to prompt user for valid input
                                try {
                                    System.out.println("\nEnter the enrollment # of a student to update their wait time: ");
                                    userInputStudent = scan.nextInt();
                                    System.out.println("\nEnter an integer (+/-) to update their wait days by: ");
                                    userInputUpdate = scan.nextInt();

                                    //if given student is not in network, inform user
                                    if (!studentNetwork.isInNetwork(userInputStudent)) {
                                        System.out.println("Student " + userInputStudent + " is not in the network.");
                                    //else if given student is in network, call updateWeight method to update the student's wait time with the given value
                                    } else {
                                        System.out.println("Updating student " + userInputStudent + "'s wait time by " + userInputUpdate + ".");
                                        studentNetwork.updateWeight(userInputStudent, userInputUpdate);
                                    }
                                //inform user of invalid, non-integer input
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Enrollment # and wait time update should both be input as an integer.");
                                    scan.next();
                                }
                            //if given student is not in network do/while loop is restarted
                            } while (!(studentNetwork.isInNetwork(userInputStudent)));
                        }
                    }
                }
            //inform user when input is invalid
            } catch(InputMismatchException e) {
            	System.out.println("Invalid input. Please enter an integer (1-5) only.");
                scan.next();
            }
        }
        scan.close();
    } 
}
