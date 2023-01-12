package subscriptionmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to read and right subs from file
 */
public class FileIO {

        /**
         * This is probably a mistake, however it's what is wrote in the specification
         * I would imagine you want to write to current.txt
         */
        public final static String _WriteFile = "Subscription.txt";
        public final static ArrayList<String> _ReadFiles = new ArrayList<String>();

        /**
         * Static constructor for FileIO
         */
        static
        {
                _ReadFiles.add("current.txt");
                _ReadFiles.add("sample.txt");
        }

        /**
         * Get's all subscriptions from the file
         * 
         * @return array list of subs
         */
        public static ArrayList<Subscription> GetAllSubscriptions(String fileName) {

                ArrayList<Subscription> SubscriptionList = new ArrayList<Subscription>();
                try {
                        File file = new File(fileName);

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

        /**
         * Adds subscription to file
         * 
         * @param subscription subscription to add
         */
        public static void AddSubscriptionToFile(Subscription subscription) {

                System.out.print("Writing to file");

                String fileString = subscription.ConvertToFileString();

                try {

                        File file = new File(_WriteFile);

                        if (!file.isFile()) {
                                file.createNewFile();
                        }

                        FileWriter fileWriter = new FileWriter(_WriteFile, true);
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
