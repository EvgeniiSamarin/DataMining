public class InternetPostText {
    private String post = "Don’t do this when you write code in Swift-1\n" +
    "I am ashamed whenever I look back at my code after few weeks. Even though it shows the progress, it’ll be killing when I add or refactor something to the code. Here are few things which will improve your coding and assist you to write Clean Code. I believe this will prevent you from that embarrassing moment I had.\n" +
    "What is a Clean Code?\n" +
    "A code with well-structured architecture based on the project modules, which is effortless to modify, add, remove and replace any part of it. Easily readable and understandable.\n" +
    "Why Clean Code?\n" +
    "It doesn’t matter that you write code to an app that is trending or a fun-project. Think twice before writing any code regardless of its importance. You won’t be having time to refactor all the code you wrote when the world going with new updates and new features.\n" +
    "As we can’t predict the future of our life itself, we can’t define the roadmap of our project precisely. Our code should be the finest to incorporate any changes\n" +
    "You’re not the only one who’s going to work on a project (Perhaps if you’re single). Write code that is surely readable and modifiable\n" +
    "This will be a series of blogs in which you’ll go through stuff that make your code better readable and organized. We will start with few basics tips in this series.\n" +
    "2. Never regret writing an extension to a class.Declare separate extensions whenever needed in the following cases.Protocol Confirmation: Whenever conforming to a protocol, delegate, or a datasource use a separate extension with a pragma mark.Methods Grouping based on functionality: Group methods based on their functionality. For example group methods which are dealing with UI rendering, group methods which are performing animations.Grouping based on access: Declare a separate extension for public methods and private methods. Where you can easily jump there for publically exposed methods.\n" +
    "3. Avoid strings and use enum in resource loading.No Raw String: Do not use raw strings to load resources from assets. You have to modify it everywhere whenever you remove or modify a resource.Use Enum: Maintain an enum that has all the names of available assets. Declare a convenience constructor with the enum as the parameter. Advantage: You have to modify just the enum values based upon the changes.Use _dark and _light suffix for assets that have different colors based on the system theme. Add the suffix along with the rawValue based on the theme.\n" +
    "4. Grouping lines in a code block using local closure.No use: When you declare a variable, it’ll be having a scope through the entire code block you declared it. Perhaps you just used it to declare some other constant. Where it has no-meaning of having scope for the entire code block. Closure: Declare a local closure, which holds the logic of creation and configuration of an object.\n" +
    "5. Spacing!!! Give space to take a breath. Spacing is the important thing which gives you a better reading. Which you have to give apt spacing. When?: Leave space only after comma and colon. Before and after curly braces. If you’re too lazy, use ctrl + i after selecting a code block, where Xcode will help you.In methods: Declare parameters on a new line if the method signature is too long. Group parameters if it makes sense. For example, instead of getting the width and height of a view as separate argument use CGSize as the parameter type.\n" +
    "All things start with a small point, the above recommendations won’t be a big deal. But definitely, it’ll be one of the beautifiers for your code. Try it from the next line which you’ll be coding after reading this. Simply, if not now? then when?Stay tuned for the next one! P.S: Clap? Ready to learn from your suggestions and feedback!\n";

    public String getPost() {
        return this.post;
    }

}
