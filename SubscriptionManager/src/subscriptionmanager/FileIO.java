package subscriptionmanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileIO {

        public static ArrayList<Subscription> GetAllSubscriptions() {

                ArrayList<Subscription> SubscriptionList = new ArrayList<Subscription>();
                try {
                        File myObj = new File("SubscriptionManager/current.txt");
                        
                        if (!myObj.isFile()) {
                                myObj.createNewFile();
                                return SubscriptionList;
                        }

                        Scanner myReader = new Scanner(myObj);
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
}
