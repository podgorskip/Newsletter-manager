import core.NewsletterManager;
import core.Provider;
import core.Resources;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Provider provider = new Provider("newsletter.manager.app@gmail.com");
        Resources resources = new Resources("quotes.txt");

        NewsletterManager newsletterManager = new NewsletterManager(provider, resources);
        newsletterManager.start();
       
    }
}