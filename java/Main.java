public class Main {
    public static void main(String[] args) {
        String[] strings = new InternetPostText().getPost().split(" ");

        CountableBloomFilter countableBloomFilter = new CountableBloomFilter(0.0001, strings.length);
        for (int i = 0; i < strings.length; i++){
            countableBloomFilter.addValue(strings[i]);
        }

        System.out.println(countableBloomFilter.contains("Python"));
        System.out.println(countableBloomFilter.contains("Ruby"));

        countableBloomFilter.addValue("Python");
        System.out.println(countableBloomFilter.contains("Python"));

        countableBloomFilter.addValue("Ruby");
        countableBloomFilter.addValue("iPhone");
        countableBloomFilter.addValue("Data");
        countableBloomFilter.addValue("ITIS");
        countableBloomFilter.addValue("Profile");
        countableBloomFilter.addValue("Page");
        countableBloomFilter.addValue("URL");
        countableBloomFilter.addValue("voice");
        countableBloomFilter.addValue("variable");


        System.out.println("-------------------------------------");
        System.out.println(countableBloomFilter.contains(("Ruby")));
        System.out.println(countableBloomFilter.contains(("iPhone")));
        System.out.println(countableBloomFilter.contains(("variable")));
        System.out.println(countableBloomFilter.contains(("Data")));
        System.out.println(countableBloomFilter.contains(("ITIS")));
        System.out.println(countableBloomFilter.contains(("Profile")));
        System.out.println(countableBloomFilter.contains(("Page")));
        System.out.println(countableBloomFilter.contains(("URL")));
        System.out.println(countableBloomFilter.contains(("voice")));
        countableBloomFilter.delete("voice");
        System.out.println(countableBloomFilter.contains(("voice")));
    }
}
