import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Provider provider = new Provider();
        Resources resources = new Resources("quotes.txt");

        NewsletterManager newsletterManager = new NewsletterManager(provider, resources);
        
        newsletterManager.addSubscriber();
    }
}