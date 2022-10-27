package subscriptionmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

        public static ArrayList<Subscription> GetAllSubscriptions() {

                ArrayList<Subscription> SubscriptionList = new ArrayList<Subscription>();
                try {
                        File file = new File("SubscriptionManager/current.txt");

                        if (!file.isFile()) {
                                file.createNewFile();
                                return SubscriptionList;
                        }

                        Scanner myReader = new Scanner(file);
                        while (myReader.hasNextLine()) {
                                Subscription insertSubscription = new Subscription(myReader.nextLine());
                                SubscriptionList.add(insertSubscription);
                        }
                        myReader.close();
                } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                        e.printStackTrace();

                } catch (IOException e) {
                        System.out.println("Failed to create file");
                        e.printStackTrace();

                }

                return SubscriptionList;
        }

        public static void AddSubscriptionToFile(Subscription subscription) {

                System.out.print("Writing to file");

                String fileString = subscription.ConvertToFileString();

                try {

                        File file = new File("SubscriptionManager/current.txt");

                        if (!file.isFile()) {
                                file.createNewFile();
                        }

                        FileWriter fileWriter = new FileWriter("SubscriptionManager/current.txt", true);
                        fileWriter.append(fileString);
                        fileWriter.append("\r\n");
                        fileWriter.close();

                } catch (IOException e) {
                        System.out.println("Failed to create file");
                        e.printStackTrace();
                }

                System.out.println("...Done");
        }
}
