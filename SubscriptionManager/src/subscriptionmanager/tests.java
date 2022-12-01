package subscriptionmanager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import subscriptionmanager.Subscription.PaymentTerms;
import subscriptionmanager.Subscription.SubPackages;

public class tests {

        @Test
        public void MakeSubscription() {

                Subscription testSub;
                testSub = new Subscription("Test", SubPackages.Gold, 3, PaymentTerms.OneOff, "-");
                assertEquals(true, testSub.StartSubscription());

                testSub = new Subscription();

                assertEquals(false, testSub.StartSubscription());

                testSub = new Subscription("", SubPackages.Gold, 3, PaymentTerms.OneOff, "-");
                assertEquals(false, testSub.StartSubscription());

                testSub = new Subscription("Test", SubPackages.Gold, 0, PaymentTerms.OneOff, "-");
                assertEquals(false, testSub.StartSubscription());

                testSub = new Subscription("Test", SubPackages.Gold, 0, PaymentTerms.OneOff, "greg");
                assertEquals(false, testSub.StartSubscription());

        }

        @Test
        public void GetSubscriptionName() {
                Subscription testSub;
                testSub = new Subscription("Test", SubPackages.Gold, 3, PaymentTerms.OneOff, "-");

                String getName = testSub.GetName();

                assertEquals("Test", getName);
        }

        @Test
        public void GetSubscriptionSubPackage() {
                Subscription testSub;
                SubPackages subPackage;
                testSub = new Subscription("Test", SubPackages.Gold, 3, PaymentTerms.OneOff, "-");

                subPackage = testSub.GetSubPackage();

                assertEquals(SubPackages.Gold, subPackage);

                testSub = new Subscription("Test", SubPackages.Silver, 3, PaymentTerms.OneOff, "-");

                subPackage = testSub.GetSubPackage();

                assertEquals(SubPackages.Silver, subPackage);

                testSub = new Subscription("Test", SubPackages.Bronze, 3, PaymentTerms.OneOff, "-");

                subPackage = testSub.GetSubPackage();

                assertEquals(SubPackages.Bronze, subPackage);
        }

        public void GetSubscription() {
                Subscription testSub;
                SubPackages subPackage;
                testSub = new Subscription("Test", SubPackages.Gold, 3, PaymentTerms.OneOff, "-");

                subPackage = testSub.GetSubPackage();

                assertEquals(SubPackages.Gold, subPackage);

                testSub = new Subscription("Test", SubPackages.Silver, 3, PaymentTerms.OneOff, "-");

                subPackage = testSub.GetSubPackage();

                assertEquals(SubPackages.Silver, subPackage);

                testSub = new Subscription("Test", SubPackages.Bronze, 3, PaymentTerms.OneOff, "-");

                subPackage = testSub.GetSubPackage();

                assertEquals(SubPackages.Bronze, subPackage);
        }
}
